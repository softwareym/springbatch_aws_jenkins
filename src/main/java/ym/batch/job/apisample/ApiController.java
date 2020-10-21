package ym.batch.job.apisample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ym.batch.job.apisample.repository.ApiMapper;
import ym.batch.job.apisample.service.ApiService;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiMapper apiMapper;

    /**
     * db연결 test
     */
    @GetMapping("/test")
    public HashMap<String, Object> selectAllJobInstance() throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> map = apiService.selectAllJobInstance();
        result.put("result", map);
        return result;

    }
}
