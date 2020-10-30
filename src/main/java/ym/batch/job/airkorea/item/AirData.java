package ym.batch.job.airkorea.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class AirData {

    private Date dataTime;      //'측정일'
    private String stationName;    //측정소명
    private double so2Value;      //'아황산가스 농도(단위 : ppm)'
    private double coValue; 	  //'일산화탄소 농도(단위 : ppm)'
    private double o3Value; 	  //'오존 농도(단위 : ppm)'
    private double no2Value; 	  //'이산화질소 농도(단위 : ppm)'
    private double pm10Value; 	  //'미세먼지(PM10) 농도'
    private double pm10Value24;   //'미세먼지(PM10)24시간예측이동농도'
    private double pm25Value; 	  //'미세먼지(PM2.5) 농도'
    private double pm25Value24;   //'미세먼지(PM2.5) 24시간예측이동농도'
    private double khaiValue; 	  //'통합대기환경수치'
    private double khaiGrade; 	  //'통합대기환경지수'
    private double so2Grade; 	  //'아황산가스 지수'
    private double coGrade; 	  //'일산화탄소 지수'
    private double o3Grade; 	  //'오존 지수'
    private double no2Grade; 	  //'이산화질소 지수'
    private double pm10Grade; 	  //'미세먼지(PM10) 24시간 등급자료'
    private double pm25Grade; 	  //'미세먼지(PM25) 24시간 등급자료'
    private double pm10Grade1h;   //'미세먼지(PM10) 1시간 등급자료'
    private double pm25Grade1h;   //'미세먼지(PM25) 1시간 등급자료'
    private String regdate; 	  //'등록일'

}
