package com.base.test;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * CmdRun
 * @author zhouyw
 * @date 2016.08.01
 */
public class CmdRun {

    private static Logger logger = LoggerFactory.getLogger(CmdRun.class);

    private static final String CMD_RUN_BROWER = "cmd /k cordova run browser";

    private static final String CMD_TEST = "cmd /c echo abcdefg";

    private static final String CMD_ZOOKEPPER = "cmd /c /develop/program/servers/zookeeper/zookeeper_along/bin/zkServer.cmd";

    public static void main(String[] args)throws Exception {
//     load("/cmd_logback.xml");  手动指定某个 日志logback.xml
       long start_time = System.currentTimeMillis();
       Runtime runtime = Runtime.getRuntime();
        BufferedReader br = null;
        try {
            String cmd = CMD_ZOOKEPPER;
            logger.info("cmd={}", cmd);

            Process process = runtime.exec(cmd);
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            //将调用结果打印到控制台上
            String line = null;
            while ((line = br.readLine()) != null) {
//                logger.info("in.read={}", line);
            }
        } catch (Exception e) {
            logger.error("error:CMD-->e={}", e, e);
        } finally {
            if (br != null) {
                logger.info("br close");
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        long end_time = System.currentTimeMillis();
        logger.info("CMD:spend time-->{}ms", end_time - start_time);
    }

    /**
     * Simple Utility class for loading an external config file for logback
     * @author daniel
     */
    public static void load(String externalConfigFileLocation) throws Exception {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        URL url = CmdRun.class.getResource(externalConfigFileLocation);
        logger.info("-->url={}",  JSON.toJSONString(url));
        File externalConfigFile = new File(url.toURI());
        if (!externalConfigFile.exists()) {
            throw new IOException("Logback External Config File Parameter does not reference a file that exists");
        } else {
            if (!externalConfigFile.isFile()) {
                throw new IOException("Logback External Config File Parameter exists, but does not reference a file");
            } else {
                if (!externalConfigFile.canRead()) {
                    throw new IOException("Logback External Config File exists and is a file, but cannot be read.");
                } else {
                    JoranConfigurator configurator = new JoranConfigurator();
                    configurator.setContext(lc);
                    lc.reset();
                    configurator.doConfigure(externalConfigFile.getAbsolutePath());
                    StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
                }
            }
        }

    }
}