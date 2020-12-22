package ym.batch.job.Corona.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ym.batch.job.Corona.producer.item.CoronaData;

@Service
public class CoronaSendProducer {

    private static final String EXCHANGE = "x.inform.work";
    private static final String ROUTINGKEY = "corona";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();//java클래스를 json으로 변환하기 위해

    public void sendMessage(CoronaData corona){
        try {
            String json = objectMapper.writeValueAsString(corona);//java클래스를 json string으로 변환해줌
            rabbitTemplate.convertAndSend(EXCHANGE,ROUTINGKEY,json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
