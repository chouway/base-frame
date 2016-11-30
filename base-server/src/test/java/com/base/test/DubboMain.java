package com.base.test;

import com.base.framwork.CommonTest;
import org.junit.Test;

/**
 * DubboMain
 * @author zhouyw
 * @date 2016.11.30
 */
public class DubboMain extends CommonTest {

    @Test
    public void main(){
        com.alibaba.dubbo.container.Main.main(null);
    }
}
