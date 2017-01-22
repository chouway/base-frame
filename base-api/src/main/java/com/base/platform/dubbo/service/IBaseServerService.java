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
     * @return object object
     */
    Object baseServer(String serverKey);


    /**
     * Add server base server info.
     * @param baseServerInfo the base server info
     * @return base server info
     * @throws BusinessException the business exception
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
     * @param params the params
     * @return list ext by map
     * @throws BusinessException the business exception
     */
    List<BaseServerInfo> getExtByMap(Map<String,Object> params)throws BusinessException;

    /**
     * Get ext by page
     * createby zhouyw on 2016.12.09
     * @param params the params
     * @param page   the page
     * @return page ext by page
     * @throws BusinessException the business exception
     */
    Page<BaseServerInfo> getExtByPage(Map<String,Object> params, Page page)throws BusinessException;

    /**
     * sendQueueMsg 处理需要消息队列处理的业务 点对点
     * createby zhouyw on 2016.12.09
     * @throws BusinessException the business exception
     */
    void sendQueueMsg()throws BusinessException;

    /**
     * publishTopicMsg 处理需要消息队列处理的业务 订阅-发布
     * createby zhouyw on 2016.12.09
     * @throws BusinessException the business exception
     */
    void publishTopicMsg()throws BusinessException;


    /**
     * See first my batis cache.
     * mybtais查询一级缓存  默认开启 当update/delete/insert commit操作时刷新缓存
     * 缓存的级别为 sqlSession； 各个sqlSession下的一级缓存独立，互不影响
     * createby zhouyw on 2016.12.10
     * @throws BusinessException the business exception
     */
    void seeFirstMyBatisCache()throws BusinessException;


    /**
     * Get datat with redis cache
     * createby zhouyw on 2016.12.21
     * @param id
     * @throws BusinessException
     */
    BaseServerInfo getDatatWithRedisCache(String id)throws BusinessException;

    /**
     * Add data with redis cache
     * createby zhouyw on 2016.12.21
     * @param baseServerInfo
     * @return int
     * @throws BusinessException
     */
    BaseServerInfo  addDataWithRedisCache(BaseServerInfo baseServerInfo)throws BusinessException;

    /**
     * Drool dubbo
     * createby zhouyw on 2017.01.16
     */
    void droolDubbo();
}
