package com.base.platform.dubbo.dao.mgb;

import com.base.platform.dubbo.domain.BaseServerConsumeRel;
import com.base.platform.dubbo.domain.BaseServerConsumeRelCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaseServerConsumeRelDao {
    /**
     *
     * @mbg.generated
     */
    long countByCondition(BaseServerConsumeRelCondition condition);

    /**
     *
     * @mbg.generated
     */
    int deleteByCondition(BaseServerConsumeRelCondition condition);

    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(BaseServerConsumeRel record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(BaseServerConsumeRel record);

    /**
     *
     * @mbg.generated
     */
    List<BaseServerConsumeRel> selectByCondition(BaseServerConsumeRelCondition condition);

    /**
     *
     * @mbg.generated
     */
    BaseServerConsumeRel selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByCondition(@Param("record") BaseServerConsumeRel record, @Param("example") BaseServerConsumeRelCondition condition);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BaseServerConsumeRel record);
}