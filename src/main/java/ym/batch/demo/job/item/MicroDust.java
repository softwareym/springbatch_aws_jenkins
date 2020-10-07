package ym.batch.demo.job.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mysql script
 create table MicroDust (
     seq         	bigint not null auto_increment,
     dataDate    	varchar(100),
     itemCode	 	varchar(100),
     districtName	varchar(200),
     moveName		varchar(200),
     issueDate    	varchar(100),
     issueTime 		varchar(100),
     issueVal		bigint,
     issueGbn		varchar(100),
     clearDate		varchar(100),
     clearTime		varchar(100),
     clearVal		bigint
     primary key (seq)
 ) engine = InnoDB;

 */

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
