package ym.batch.job.Corona.producer;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ym.batch.job.Corona.producer.item.CoronaData;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CoronaSendProducerTests {
    @Autowired
    private CoronaSendProducer producer;


    @Test
    @DisplayName("producer_publisher_확인")
    public void sendMessage(){
        // given
        CoronaData data = new CoronaData();
        data.setNewCase(2000);
        data.setStationName("인천");
        data.setEmail("whdms705@nate.com");
        data.setCreateDate(LocalDate.now());

        // then
        producer.sendMessage(data);
    }
}
