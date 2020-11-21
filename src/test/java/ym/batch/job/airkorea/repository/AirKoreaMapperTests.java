package ym.batch.job.airkorea.repository;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ym.batch.job.airkorea.item.ApiCallManageDto;
import ym.batch.job.airkorea.item.ApiCallManageVo;
import ym.batch.job.common.status.CallDiv;
import ym.batch.job.common.status.TreateStts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirKoreaMapperTests extends TestCase {
    @Autowired
    AirKoreaMapper mapper;

    @Test
    @DisplayName("api_call대기_목록_조회")
    public void selectStationNamesForCall_test(){
        // givne
        ApiCallManageVo apiCallManageVo = new ApiCallManageVo();
        apiCallManageVo.setCallDiv(CallDiv.AIRDATA.getStatusCode());
        apiCallManageVo.setTreateStts(TreateStts.WAIT.getTreateSttsCode());
        apiCallManageVo.setWorkDate(LocalDate.now());

        // when
        List<ApiCallManageDto> stationList = mapper.selectStationNamesForCall(apiCallManageVo);

        // then
        assertNotNull(stationList);
    }

    @Test
    @DisplayName("api_call_manage_관리테이블_상태값_DN처리")
    public void updateTreeteStts(){
        // given
        List<Long> apiCallManageSrls = new ArrayList<>();
        apiCallManageSrls.add(547L);

        // when
        mapper.updateTreeteStts(apiCallManageSrls);

    }

}
