package com.base.framwork.log;

/**
 * DubboLogServiceFilter
 * @author zhouyw
 * @date 2016.11.30
 */
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
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
