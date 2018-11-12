package com.example.demo;

import com.example.demo.entity.Order;
import com.example.demo.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqStudyApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private OrderSender orderSender;

    @Test
    public void testsend01() throws Exception{
        Order order = new Order();
        order.setId("123456");
        order.setName("测试订单003");
        order.setMessageId(System.currentTimeMillis() + "$" + UUID.randomUUID().toString());
        orderSender.send(order);
    }

}
