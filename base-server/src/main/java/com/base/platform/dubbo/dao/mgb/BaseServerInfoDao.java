package com.base.platform.dubbo.dao.mgb;

import com.base.platform.dubbo.domain.BaseServerInfo;
import com.base.platform.dubbo.domain.BaseServerInfoCondition;
import java.util.List;

public interface BaseServerInfoDao {
    /**
     *
     * @mbg.generated
     */
    long countByCondition(BaseServerInfoCondition condition);

    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(BaseServerInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(BaseServerInfo record);

    /**
     *
     * @mbg.generated
     */
    List<BaseServerInfo> selectByCondition(BaseServerInfoCondition condition);

    /**
     *
     * @mbg.generated
     */
    BaseServerInfo selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseServerInfo record);
}