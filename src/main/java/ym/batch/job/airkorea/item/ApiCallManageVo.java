package ym.batch.job.airkorea.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/*
Mapper 쿼리에 데이터 전달을 위한 ApiCallManageVo
 */
@Getter
@Setter
public class ApiCallManageVo {
    private String callDiv;
    private String treateStts;
    private String param;
    private LocalDate workDate;
}
