package com.base.framework.log;

/**
 * DubboLogServiceFilter
 * @author zhouyw
 * @date 2016.11.30
 */

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class DubboLogReferenceFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(DubboLogReferenceFilter.class);

    public DubboLogReferenceFilter() {
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("DubboLogReferenceFilter-->invoker={}", invoker);

        String logSerial = MDC.get("logSerial");
        String username = MDC.get("username");
        RpcContext.getContext().setAttachment("username", username);
        RpcContext.getContext().setAttachment("logSerial", logSerial);
        Result result = invoker.invoke(invocation);
        RpcContext.getContext().removeAttachment("username");
        RpcContext.getContext().removeAttachment("logSerial");
        return result;
    }
}
