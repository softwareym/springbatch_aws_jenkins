package ym.batch.demo.job.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component                              //property에서 value 주입하기 위해 사용
@ToString
@Setter
@Getter
public class MicroDust {

    @Value("${openapi.servicekey}")
    private String servicekey;

    private String dataDate;
    private String itemCode;
    private String districtName;
    private String moveName;
    private String issueDate;
    private String issueTime;
    private int issueVal;
    private String issueGbn;
    private String clearDate;
    private String clearTime;
    private int clearVal;

}
