package ym.batch.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("Success... 메시지가 스위치로 성공적으로 전송되었습니다.! correlationData:{}", correlationData);
        } else {
            log.info("Fail... 스위치에 메시지를 보내지 못했습니다.! correlationData:{}", correlationData);
        }
    }
}
