DROP TABLE IF EXISTS tblMicroDust;

CREATE TABLE tblMicroDust(
    seq LONG PRIMARY KEY AUTO_INCREMENT
    , dataDate VARCHAR NOT NULL
    , itemCode VARCHAR
    , districtName varchar
    , moveName VARCHAR
    , issueDate VARCHAR
    , issueTime VARCHAR
    , issueVal INT
    , issueGbn VARCHAR
    , clearDate VARCHAR
    , clearTime VARCHAR
    , clearVal INT
);


