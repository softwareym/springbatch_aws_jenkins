package ym.batch.demo.job.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ym.batch.demo.job.api.repository.ApiMapper;
import ym.batch.demo.job.api.service.ApiService;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private ApiMapper apiMapper;


    @GetMapping("/test")
    public HashMap<String, Object> selectAllJobInstance() throws Exception {

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> map = apiService.selectAllJobInstance();
        map.put("result", map);
        return map;

    }
}
