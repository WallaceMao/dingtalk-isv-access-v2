package com.rishiqing.dingtalk.svc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Wallace Mao
 * Date: 2019-01-18 15:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-service-dingmain.xml",
        "classpath:spring-queue-ali.xml"})
public class BaseUnitTest {
    @Test
    public void test(){
        assertEquals(1 + 1, 2);
    }
}
