package com.base.platform.dubbo.dao.mgb;

import com.base.platform.dubbo.domain.BaseConsumeInfo;
import com.base.platform.dubbo.domain.BaseConsumeInfoCondition;
import java.util.List;

public interface BaseConsumeInfoDao {
    /**
     *
     * @mbg.generated
     */
    long countByCondition(BaseConsumeInfoCondition condition);

    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(BaseConsumeInfo record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(BaseConsumeInfo record);

    /**
     *
     * @mbg.generated
     */
    List<BaseConsumeInfo> selectByCondition(BaseConsumeInfoCondition condition);

    /**
     *
     * @mbg.generated
     */
    BaseConsumeInfo selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseConsumeInfo record);
}