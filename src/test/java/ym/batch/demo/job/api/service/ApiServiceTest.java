package ym.batch.demo.job.api.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApiServiceTest {

    @Autowired
    public ApiService apiService;

    @Test
    public void ApiService_호출() throws Exception {
        HashMap<String,Object> result = apiService.selectAllJobInstance();
        assertNotNull(result);
    }

}