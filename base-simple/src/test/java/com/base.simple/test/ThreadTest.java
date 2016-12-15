package com.base.simple.test;

import com.base.simple.common.CommonTest;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadTest
 * @author zhouyw
 * @date 2016.12.14
 */
public class ThreadTest extends CommonTest {

    private final int max = 10;

    private final long sleep = 60* 1000l;

    private AtomicInteger countNormal = new AtomicInteger(0);

    private AtomicInteger countCache = new AtomicInteger(0);

    private AtomicInteger countSingle = new AtomicInteger(0);

    private AtomicInteger countFixed = new AtomicInteger(0);

    private long busiTime = 200l;

    @Test
    public void compareNormal2Cache()throws Exception{
        logger.info("-->max={}", max);
        this.normalThread();
        this.cacheThread();
        this.singleThread();
        this.fixedThread();
        Thread.sleep(sleep);
    }

    @Test
    public void normalThread()throws Exception{
        final long strT = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doBusi();
                    int temp = countNormal.addAndGet(1);
//                  logger.info("nomal-->temp={}", temp);
                    if(temp == max){
                        long endT = System.currentTimeMillis();
                        logger.info("normal_spent-->time={}", (endT - strT));
                    }
                }
            }).start();
        }
    }

    @Test
    public void cacheThread()throws Exception{
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();//有oom的风险
        cacheExcute(cachedThreadPool);
    }

    @Test
    public void singleThread(){
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        singleExcute(singleThread);
    }

    @Test
    public void fixedThread(){
        ExecutorService singleThread = Executors.newFixedThreadPool(10);
        fixedExcute(singleThread);

    }

    private void fixedExcute(ExecutorService singleThread) {
        final long strT = System.currentTimeMillis();
        for(int i =  0; i < max; i++) {
            singleThread.execute(new Runnable() {
                @Override
                public void run() {
                    doBusi();
                    int temp = countFixed.addAndGet(1);
//                  logger.info("fixed-->temp={}", temp);
                    if(temp == max){
                        long endT = System.currentTimeMillis();
                        logger.info("fixed_spent-->time={}", (endT - strT));
                    }
                }
            });
        }
    }

    private void singleExcute(ExecutorService singleThread) {
        final long strT = System.currentTimeMillis();
        for(int i =  0; i < max; i++) {
            singleThread.execute(new Runnable() {
                @Override
                public void run() {
                    doBusi();
                    int temp = countSingle.addAndGet(1);
//                  logger.info("single-->temp={}", temp);
                    if(temp == max){
                        long endT = System.currentTimeMillis();
                        logger.info("single_spent-->time={}", (endT - strT));
                    }
                }
            });
        }
    }


    private void cacheExcute(ExecutorService cachedThreadPool)throws Exception {
        final long strT = System.currentTimeMillis();
        for(int i =  0; i < max; i++) {
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    doBusi();
                    int temp = countCache.addAndGet(1);
//                    logger.info("cache-->temp={}", temp);
                    if(temp == max){
                        long endT = System.currentTimeMillis();
                        logger.info("cache_spent-->time={}", (endT - strT));
                    }
                }
            });
        }
    }

    private void doBusi(){
        if(busiTime>0l){
            try{
               Thread.sleep(busiTime);
            }catch(Exception e){
                logger.error("error:dobusi-->e={}", e,e);
            }
        }
    }
}
