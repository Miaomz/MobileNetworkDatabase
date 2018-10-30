package org.casual.entity;

import org.junit.Test;

import java.lang.reflect.Method;


/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class MesUsageTest {

    @Test
    public void test1(){
        for (Method method : MesUsage.class.getMethods()) {
            System.out.println(method);
        }
    }
}