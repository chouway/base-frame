package com.base.platform.dubbo.service;

import com.base.framwork.bean.page.Page;
import com.base.framwork.exception.BusinessException;
import com.base.framwork.plugin.page.PageHelper;
import com.base.platform.dubbo.dao.mgb.BaseServerInfoDao;
import com.base.platform.dubbo.dao.ext.BaseServerInfoDaoExt;
import com.base.framwork.context.BaseService;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.domain.BaseServerInfoCondition;
import com.base.framwork.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * BaseServer
 * @author zhouyw
 * @date 2016.09.04
 */
@Service
@Transactional
public class BaseServerService extends BaseService implements IBaseServerService {

    @Value("${application.preFixedUrl}")
    private String preFixedUrl;

    @PostConstruct
    private void init(){
        logger.info("@PostConstruct init-->preFixedUrl={}", preFixedUrl);
    }

    @Autowired
    private BaseServerInfoDao baseServerInfoDao;

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
    public long countServer(BaseServerInfoCondition condition) {
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


}
