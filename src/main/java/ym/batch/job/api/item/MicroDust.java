package ym.batch.job.api.item;

import lombok.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mysql script
 create table tblMicroDust (
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
     clearVal		bigint,
     primary key (seq)
 ) engine = InnoDB;

 */
@NoArgsConstructor                     //매개변수 없는 생성자 새성
@ToString
@Getter
@Setter
public class MicroDust {

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
