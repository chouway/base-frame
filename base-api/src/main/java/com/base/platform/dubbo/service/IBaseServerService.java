package com.base.platform.dubbo.service;


import com.base.framework.bean.page.Page;
import com.base.framework.exception.BusinessException;
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
     * @return the long
     */
    long countServer();

    /**
     * Get ext by map
     * createby zhouyw on 2016.12.09
     * @param params
     * @return list
     * @throws BusinessException
     */
    List<BaseServerInfo> getExtByMap(Map<String,Object> params)throws BusinessException;

    /**
     * Get ext by page
     * createby zhouyw on 2016.12.09
     * @param params
     * @param page
     * @return page
     * @throws BusinessException
     */
    Page<BaseServerInfo> getExtByPage(Map<String,Object> params, Page page)throws BusinessException;

    /**
     * Deal need msg busi 处理需要消息队列处理的业务
     * createby zhouyw on 2016.12.09
     * @throws BusinessException
     */
    void dealNeedMsgBusi()throws BusinessException;
}
