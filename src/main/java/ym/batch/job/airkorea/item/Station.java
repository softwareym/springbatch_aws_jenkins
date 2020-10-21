package ym.batch.job.airkorea.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class Station {

    private String stationName;      //'측정소명'
    private String addr;             //'측정소 주소'
    private int year;                //'설치년도'
    private String operationAgency;  //'관리기관명'
    private String stationPhoto;     //'측정소 이미지'
    private String stationVrml;	     //'측정소 전경'
    private String map;				 //'지도이미지'
    private String mangName;	  	 //'측정망'
    private String measureItem;      //'측정항목'
    private double dmx;			     //'위도(Latitude)'
    private double dmy;              //'경도(Longitude)'
    private String regdate; 		 //'등록일'
}
