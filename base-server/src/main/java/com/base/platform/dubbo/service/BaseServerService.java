package com.base.platform.dubbo.service;

import com.alibaba.fastjson.JSON;
import com.base.framework.bean.page.Page;
import com.base.framework.exception.BusinessException;
import com.base.framework.message.IQueueSender;
import com.base.framework.plugin.page.PageHelper;
import com.base.platform.dubbo.constant.DestinationConstant;
import com.base.platform.dubbo.dao.mgb.BaseServerInfoDao;
import com.base.platform.dubbo.dao.ext.BaseServerInfoDaoExt;
import com.base.framework.context.BaseService;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.domain.BaseServerInfoCondition;
import com.base.framework.util.UUIDUtils;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BaseServer
 * @author zhouyw
 * @date 2016.09.04
 */
@Service
@Transactional
public class BaseServerService extends BaseService implements IBaseServerService{

    @Value("${application.preFixedUrl}")
    private String preFixedUrl;

    @KSession("ksession")//注： 这里的值与配置文件中的值是一样的
    private KieSession ksession;

    @PostConstruct
    private void init(){
        logger.info("@PostConstruct init-->preFixedUrl={}", preFixedUrl);
    }

    @Autowired
    private BaseServerInfoDao baseServerInfoDao;

    @Autowired
    private IQueueSender queueSender;

    @Override
    public Object baseServer(String serverKey) {
        logger.info("-->serverKey={}", serverKey);
        BaseServerInfo baseServerInfo = baseServerInfoDao.selectByPrimaryKey(serverKey);
        logger.info("-->baseServerInfo={}", baseServerInfo);

        BaseServerInfo baseServerInfo_getDao = this.getDao(BaseServerInfoDao.class).selectByPrimaryKey(serverKey);

        logger.info("-->baseServerInfo_getDao={}", baseServerInfo_getDao);
        return this.getBean(baseServerInfo.getServerName());
    }

    @Override
    public BaseServerInfo addServer(BaseServerInfo baseServerInfo) throws RuntimeException {
        String id = UUIDUtils.generate();
        baseServerInfo.setId(id);
        logger.info("I-->id={},insert={}", id,baseServerInfoDao.insert(baseServerInfo));
        return baseServerInfo;
    }

    @Override
    public long countServer() {
        BaseServerInfoCondition condition = new BaseServerInfoCondition();
        condition.createCriteria();
        return this.getDao(BaseServerInfoDao.class).countByCondition(condition);
    }

    @Override
    public List<BaseServerInfo> getExtByMap(Map<String, Object> params) throws BusinessException {
        return this.getDao(BaseServerInfoDaoExt.class).getByMap(params);
    }


    //test succes 服务集成page 自动分页 使用ibatis 拦截器
    @Override
    public Page<BaseServerInfo> getExtByPage(Map<String, Object> params, Page page) throws BusinessException {
        PageHelper.startPage(page);
        this.getDao(BaseServerInfoDaoExt.class).getByMap(params);
        return PageHelper.endPage();
    }


    /**
     * Deal need msg busi 处理需要消息队列处理的业务
     * createby zhouyw on 2016.12.09
     * @throws BusinessException
     */
    @Override
    public void dealNeedMsgBusi() throws BusinessException {
        logger.info("dealNeedMsgBusi-->STR");
        BaseServerInfo baseServerInfo = new BaseServerInfo();
        baseServerInfo.setId("test-info-msg-obj");
        queueSender.send(DestinationConstant.QUEUE_BASE_SERVER_SERVICE,baseServerInfo);

        queueSender.send(DestinationConstant.QUEUE_BASE_SERVER_SERVICE,"test-info-msg-text");
        logger.info("dealNeedMsgBusi-->END");
    }

    @Override
    public void seeFirstMyBatisCache() throws BusinessException {
        BaseServerInfoCondition condition = new BaseServerInfoCondition();
        condition.createCriteria();
        BaseServerInfoDao baseServerInfoDao = this.getDao(BaseServerInfoDao.class);
        long count_0 = baseServerInfoDao.countByCondition(condition);
        logger.info("first-->count_0={}", count_0);

        long count_1 = baseServerInfoDao.countByCondition(condition);
        logger.info("second-->count_1={}", count_1);
    }

    /**
     * Get datat with redis cache
     * createby zhouyw on 2016.12.21
     * @param id
     * @throws BusinessException
     */
    @Override
    @Cacheable(value = "baseServerInfo", key = "'base_server_info_'+#id")
    public BaseServerInfo getDatatWithRedisCache(String id) throws BusinessException {
        logger.info("getDatatWithRedisCache-->id={}", id);
        if("0".equals(id)){
            BaseServerInfo baseServerInfo  = new BaseServerInfo();
            baseServerInfo.setId("0");
            baseServerInfo.setServerName("0_test_0");
            return baseServerInfo;
        }
        return null;
    }

    /**
     * Add data with redis cache
     * createby zhouyw on 2016.12.21
     * @param baseServerInfo
     * @return int
     * @throws BusinessException
     */
    @Override
    @Caching(put = {@CachePut(value = "baseServerInfo", key = "'base_server_info_'+#baseServerInfo.id")})
    public BaseServerInfo addDataWithRedisCache(BaseServerInfo baseServerInfo) throws BusinessException {
        logger.info("addDataWithRedisCache-->baseServerInfo={}", JSON.toJSONString(baseServerInfo));
        return baseServerInfo;
    }

    /**
     * Drool dubbo
     * createby zhouyw on 2017.01.16
     */
    @Override
    public void droolDubbo() {//运行测试类 如果 在测试 resource下没有相应的drl 它是找不到的。。。
        BaseServerInfo baseServerInfo = new BaseServerInfo();
        baseServerInfo.setId("test");
        logger.info("bf-->baseServerInfo={}", JSON.toJSONString(baseServerInfo));
        ksession.insert(baseServerInfo);
        int i = ksession.fireAllRules();

        logger.info("af-->i={},baseServerInfo={}", i,JSON.toJSONString(baseServerInfo));
    }


    @JmsListener(destination = DestinationConstant.QUEUE_BASE_SERVER_SERVICE)//def: containerFactory = "jmsListenerContainerFactory"
    public void onMessage(Message message) {
        try{
           logger.info("-->message={}", message.getClass());
           if(message instanceof TextMessage){
               TextMessage textMsg = (TextMessage) message;
               logger.info("receive msg-->textMsg={}", textMsg.getText());
           }else if(message instanceof ObjectMessage){
               Serializable object = ((ObjectMessage) message).getObject();
               logger.info("receive msg-->object={}", JSON.toJSONString(object));
           }else{   
               throw new BusinessException("暂未定义的消息类型");
           }
        }catch (BusinessException e){
           logger.error("busi error:{}-->[message]={}",e.getMessage(), message,e);
           throw e;
        }catch (Exception e){
           logger.error("error:{}-->[message]={}",e.getMessage(),message,e);
           throw new BusinessException("系统错误");   
        }
    }
}
