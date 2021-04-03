package ym.batch.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("Fail... message:{},\n" +
                        "슬레이브 스위치 exchange:{}, routingKey:{}," +
                        "일치하는 대기열이 없습니다. ，replyCode:{},replyText:{}",
                message, exchange, routingKey, replyCode, replyText);
    }
}
