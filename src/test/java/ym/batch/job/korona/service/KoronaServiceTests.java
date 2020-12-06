package ym.batch.job.korona.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KoronaServiceTests extends TestCase {
    @Autowired
    private KoronaService service;


    @Test
    @DisplayName("CoronaData_호출확인")
    public void callCoronaData() throws UnsupportedEncodingException {
        Object response = service.callCoronaData();
        assertNotNull(response);
    }
}
