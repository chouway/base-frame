package com.base.platform.dubbo.service;


import com.base.framwork.bean.page.Page;
import com.base.framwork.exception.BusinessException;
import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.domain.BaseServerInfoCondition;


import java.util.List;
import java.util.Map;

/**
 * IBaseServer
 * @author zhouyw
 * @date 2016.09.04
 */
public interface IBaseServerService {

    /**
     * 通过服务key 获取 相关的服务
     * @param serverKey the server key
     * @return object
     */
    Object baseServer(String serverKey);


    /**
     * Add server base server info.
     * @param baseServerInfo the base server info
     * @return base server info
     * @throws RuntimeException the runtime exception
     */
    BaseServerInfo addServer(BaseServerInfo baseServerInfo) throws BusinessException;

    /**
     * Count server long.
     * @param condition the condition
     * @return the long
     */
    long countServer(BaseServerInfoCondition condition);

    List<BaseServerInfo> getExtByMap(Map<String,Object> params)throws BusinessException;

    Page<BaseServerInfo> getExtByPage(Map<String,Object> params, Page page)throws BusinessException;
}
