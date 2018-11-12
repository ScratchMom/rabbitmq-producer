package com.example.demo.producer;

import com.example.demo.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author laowang
 * @date 2018/11/6 4:35 PM
 * @Description:
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(Order order) throws Exception {

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        rabbitTemplate.convertAndSend(
                "order-exchange",
                "order.abcd",
                order,  // 消息体内容
                correlationData // 消息唯一ID
                );
    }

}
