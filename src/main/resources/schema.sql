DROP TABLE IF EXISTS tblMicroDust;

CREATE TABLE tblMicroDust(
    seq LONG PRIMARY KEY AUTO_INCREMENT
    , dataDate VARCHAR NOT NULL
    , itemCode VARCHAR
    , districtName VARCHAR
    , moveName VARCHAR
    , issueDate VARCHAR
    , issueTime VARCHAR
    , issueVal INT
    , issueGbn VARCHAR
    , clearDate VARCHAR
    , clearTime VARCHAR
    , clearVal INT
);

DROP TABLE IF EXISTS tblStation;
CREATE TABLE tblStation(
     seq LONG PRIMARY KEY AUTO_INCREMENT
    , stationName VARCHAR NOT NULL
    , addr VARCHAR
    , year INT
    , operationAgency VARCHAR
    , stationPhoto VARCHAR
    , stationVrml VARCHAR
    , map VARCHAR
    , mangName VARCHAR
    , measureItem VARCHAR
    , dmx double
    , dmy double
    , regdate VARCHAR
);


DROP TABLE IF EXISTS tblAirData;
CREATE TABLE tblAirData(
     seq LONG PRIMARY KEY AUTO_INCREMENT
    , stationName VARCHAR
    , dataTime VARCHAR NOT NULL
    , so2Value double
    , coValue double
    , o3Value double
    , no2Value double
    , pm10Value double
    , pm10Value24 double
    , pm25Value double
    , pm25Value24 double
    , khaiValue double
    , khaiGrade double
    , so2Grade double
    , coGrade double
    , o3Grade double
    , no2Grade double
    , pm10Grade double
    , pm25Grade double
    , pm10Grade1h double
    , pm25Grade1h double
    , regdate VARCHAR
);

