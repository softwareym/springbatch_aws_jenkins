INSERT INTO tblMicroDust (dataDate, itemCode, districtName, moveName, issueDate, issueTime, issueVal, issueGbn, clearDate, clearTime, clearVal) VALUES ('2020-10-08','PM25', '경남','거제권역','2020-08-07','13:00',85,'주의보','2020-08-07','21:00',21);

INSERT INTO tblStation (stationName, addr, year, operationAgency, stationPhoto, stationVrml, map, mangName, measureItem, dmx, dmy, regdate) VALUES ('종로구','addr', 2020,'agency','photo','vrml', 'mapimg', 'mangname', 'item', 85,21, now());

INSERT INTO tblAirData
(     stationName   , dataTime    , so2Value    , coValue    , o3Value    , no2Value    , pm10Value    , pm10Value24    , pm25Value    , pm25Value24
    , khaiValue    , khaiGrade    , so2Grade    , coGrade    , o3Grade    , no2Grade    , pm10Grade    , pm25Grade    , pm10Grade1h    , pm25Grade1h    , regdate
)VALUES(
   '종로구', NOW(),1.4564654,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,NOW()
)