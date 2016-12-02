/**
 * 
 */
package com.base.framework.web.view;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.apache.velocity.tools.view.ViewToolContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author zhouyw
 * @date  2015年12月30日
 */
public class MyVelocityToolboxView extends VelocityToolboxView {

    private Logger logger = LoggerFactory.getLogger(MyVelocityToolboxView.class);

    @Override
    protected Context createVelocityContext(Map<String, Object> model,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {// Create a
                                // ChainedContext
                                // instance.
        long start_time = System.currentTimeMillis();
        ViewToolContext ctx;

        ctx = new ViewToolContext(getVelocityEngine(), request, response,
                getServletContext());
//      logger.info("test zhouyw-->this={},ctx={}", this,ctx);
        ctx.putAll(model);
        this.initCtx(ctx);
        long end_time = System.currentTimeMillis();logger.info("myViewTool:spend time-->{}ms", end_time - start_time);
        return ctx;
    }

    private void initCtx(ViewToolContext ctx) {
        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = new ToolManager();
            tm.setVelocityEngine(getVelocityEngine());

            String realPath = getServletContext().getRealPath(
                    getToolboxConfigLocation());
//          logger.info("-->realPath={}", realPath);

            tm.configure(realPath);
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
//              logger.info("-->Scope.REQUEST={}", Scope.REQUEST);
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
//              logger.info("-->Scope.APPLICATION={}", Scope.APPLICATION);
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.APPLICATION));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
//              logger.info("-->Scope.SESSION={}", Scope.SESSION);
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(
                        Scope.SESSION));
            }
        }
    }
}

	