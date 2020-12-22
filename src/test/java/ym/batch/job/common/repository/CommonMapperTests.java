package ym.batch.job.common.repository;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ym.batch.job.common.item.UserVo;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommonMapperTests {
    @Autowired
    CommonMapper commonMapper;

    @Test
    @DisplayName("User_insert확인")
    public void user_insert(){
        UserVo user = new UserVo();
        user.setStationName("인천");
        for (int i =0;i<2;i++){
            user.setName("조은호");
            user.setEmail("whdms705@nate.com");
            commonMapper.insertUser(user);
        }

        user.setStationName("서울");
        for (int i =1;i<10000;i++){
            user.setName(user.getStationName()+":"+i);
            user.setEmail("Seoul@test.com");
            commonMapper.insertUser(user);
        }

        user.setStationName("부산");
        for (int i =1;i<10000;i++){
            user.setName(user.getStationName()+":"+i);
            user.setEmail("busan@test.com");
            commonMapper.insertUser(user);
        }

        user.setStationName("대구");
        for (int i =1;i<10000;i++){
            user.setName(user.getStationName()+":"+i);
            user.setEmail("dague@test.com");
            commonMapper.insertUser(user);
        }

        user.setStationName("제");
        for (int i =1;i<10000;i++){
            user.setName(user.getStationName()+":"+i);
            user.setEmail("jeju@test.com");
            commonMapper.insertUser(user);
        }


    }
}
