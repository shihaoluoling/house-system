package com.example.admin.center.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shihao
 * @Title: rabbitMqConf
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@Configuration
public class rabbitMqConf {
    private static String EXCHANGE_NAME="hello111";
    //声明两个队列
//    @Bean
//    public Queue queue(){
//        return new Queue("myQueue");
//    }

    @Bean
    public Queue queue1(){
        return new Queue("myQueue3");
    }
    //声明一个fanout的交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE_NAME);
    }
    //将队列和交互机进行绑定
    @Bean
    public Binding bindfanout(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);

    }

    @Bean
    public Binding bindfanout1(Queue queue1,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue1).to(fanoutExchange);

    }

}
