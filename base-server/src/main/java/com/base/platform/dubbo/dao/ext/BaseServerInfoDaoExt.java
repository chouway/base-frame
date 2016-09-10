package com.base.platform.dubbo.dao.ext;


import com.base.platform.dubbo.domain.BaseServerInfo;

import java.util.List;
import java.util.Map;

public interface BaseServerInfoDaoExt {

    List<BaseServerInfo> getByMap(Map<String,Object> params);
}