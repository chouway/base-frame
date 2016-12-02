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

public class DubboLogServiceFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(DubboLogServiceFilter.class);

    public DubboLogServiceFilter() {
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String logSerial = context.getAttachment("logSerial");
        String username = context.getAttachment("username");
        MDC.put("username", username);
        MDC.put("logSerial", logSerial);
        Result result = invoker.invoke(invocation);
        if(result.hasException()) {
            Throwable e = result.getException();
            this.logger.error(e.getMessage(), e);
        }

        MDC.remove("logSerial");
        MDC.remove("username");
        return result;
    }
}
