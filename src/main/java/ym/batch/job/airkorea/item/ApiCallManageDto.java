package ym.batch.job.airkorea.item;


import lombok.Getter;
import lombok.Setter;



/*
Mapper 쿼리에 실행 후 반환 데이터를 담을 ApiCallManageDto
 */

@Getter
@Setter
public class ApiCallManageDto {
    private Long  apiCallManageSeq;
    private String param;
}
