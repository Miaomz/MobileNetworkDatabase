package org.casual.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author miaomuzhi
 * @since 2018/10/30
 */
public class UsageTypeTest {

    @Test
    public void test1(){
        assertEquals(UsageType.MES_USAGE, UsageType.values()[1]);
    }

    @Test
    public void test2(){
        assertEquals(UsageType.CALL_USAGE, UsageType.values()[0]);
    }
    @Test
    public void test3(){
        assertEquals(UsageType.LOCAL_TRAFFIC, UsageType.values()[2]);
    }
    @Test
    public void test4(){
        assertEquals(UsageType.DOMESTIC_TRAFFIC, UsageType.values()[3]);
    }
}