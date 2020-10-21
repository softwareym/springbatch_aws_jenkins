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

