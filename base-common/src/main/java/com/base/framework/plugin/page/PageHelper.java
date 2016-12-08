package com.base.framework.plugin.page;

import com.alibaba.fastjson.JSON;
import com.base.framework.bean.page.Page;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.plugin.*;
import org.springframework.messaging.simp.annotation.SendToUser;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis - 通用分页拦截器   参考 http://blog.csdn.net/isea533/article/details/23831273 @author liuzh/abel533/isea Created by liuzh on 14-4-15.
 * changeby zhouyw on 2016.09.10
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class,Integer.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class PageHelper implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

    public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();



    /**
     * 开始分页
     * @param pageNum
     * @param pageSize
     */
    public static void startPage(int currentPage, int pageSize) {
        localPage.set(new Page(currentPage, pageSize));
    }

    /**
     * 开始分页
     * @param pageNum
     * @param pageSize
     */
    @SuppressWarnings("unchecked")
    public static void startPage(Page page) {
        if(page == null) page = new Page();
        localPage.set(page);
    }

    /**
     * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Page endPage() {
        Page page = localPage.get();
        localPage.remove();
        return page;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Invocation invocation) throws Throwable {
        if (localPage.get() == null) {
            return invocation.proceed();
        }
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            //分页信息if (localPage.get() != null) {
            Page page = localPage.get();
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            // 重写sql
            String pageSql = buildPageSql(sql, page);
            //重写分页sql
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
            setPageParameter(sql, connection, mappedStatement, boundSql, page);
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            Page page = localPage.get();
            page.setRecords((List)result);
            return result;
        }
        return null;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL  changeby zhouyw on 2016.09.10
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Page page) {
        StringBuilder pageSql = new StringBuilder(200);
        if(logger.isDebugEnabled()){
            logger.debug("prepare buildPageSql STR-->sql={},page={}", sql, JSON.toJSONString(page));
        }
        String targetSql = sql.trim();//从末尾侧找到第一个不是空白的字符

        char lastChar = targetSql.charAt(targetSql.length() - 1);
        if(lastChar == ';'){
            pageSql.append(targetSql.substring(0,targetSql.length()-1));
            appendPostgresPageSql(pageSql,page);
            pageSql.append(";");
        }else if(lastChar == ')'){
            pageSql.append(targetSql.substring(0,targetSql.length()-1));
            appendPostgresPageSql(pageSql,page);
            pageSql.append(")");
        }else{
            pageSql.append(targetSql);
            appendPostgresPageSql(pageSql,page);
        }
        if(logger.isDebugEnabled()){
            logger.debug("prepare buildPageSql END-->pageSql={}",  pageSql.toString());
        }
        return pageSql.toString();
    }

    /**
     * db数据库为 postgres  分页 结尾写法  createby zhouyw on 2016.09.10
     */
    private void appendPostgresPageSql(StringBuilder pageSql,Page page){
        pageSql.append(" LIMIT ").append(page.getPageSize())
               .append(" OFFSET ").append(page.getRecordStart());
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Page page) {
        // 记录总记录数
        // 句法分析  提炼成 select count(1) from ( sql 主体部分 ); changeby zhouyw on 2016.09.10
        String countSql =  this.countSQL(sql);

        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalRecord(totalCount);
        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    //句法分析  提炼成 select count(1) from ( sql 主体部分 ); changeby zhouyw on 2016.09.10
    private String countSQL(String sql) {
        if (logger.isDebugEnabled()) {
            logger.debug("prepare countSQL STR-->sql={}", sql);
        }
        StringBuilder countSQL = new StringBuilder();
        //找到第一个Select
        Pattern pattern_select = Pattern.compile("select|SELECT");
        int targetSelectIndex = 0;
        Matcher matcher_select = pattern_select.matcher(sql);
        if(matcher_select.find()){
            targetSelectIndex = matcher_select.end();
        }


        //寻址 第一个SELECT /select    , 找到下一个FROM/from    得到中间的括号数 如果成对的话 即是目标 from;
        Pattern pattern = Pattern.compile("(\\(|\\)|from|FROM)");
        Matcher matcher = pattern.matcher(sql);
        int leftB = 0;
        int rigthB = 0;
        int targetFromIndex = -1;
        while(matcher.find()){
            int start = matcher.start();
            if(targetSelectIndex>start){
                continue;
            }

            String group = matcher.group(0);
            if("(".equals(group)){
                ++leftB;
            }else if(")".equals(group)){
                ++rigthB;
            }else{
                if(leftB == rigthB){
                    targetFromIndex = start;
                    break;
                }
            }
        }

        countSQL.append(sql.substring(0,targetSelectIndex))
                .append(" count(1) ")
                .append(sql.substring(targetFromIndex,sql.length()));
        if (logger.isDebugEnabled()) {
            logger.debug("prepare countSQL END-->countSQL={}", countSQL.toString());
        }
        return countSQL.toString();
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }
}
