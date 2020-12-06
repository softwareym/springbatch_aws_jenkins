package ym.batch.job.korona.repository;


import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KoronaMapperTests extends TestCase {

    @Autowired
    private KoronaMapper mapper;

    @Test
    @DisplayName("Corona_api_data_insert_확인")
    public void insertCoronaRegist(){

    }
}
