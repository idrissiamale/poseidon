DROP DATABASE IF EXISTS demo;
CREATE DATABASE demo;
USE demo;

CREATE TABLE bidList (
  bidListId tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DOUBLE,
  askQuantity DOUBLE,
  bid DOUBLE ,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bidListDate TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (bidListId)
);

CREATE TABLE trade (
  tradeId tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DOUBLE,
  sellQuantity DOUBLE,
  buyPrice DOUBLE ,
  sellPrice DOUBLE,
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP ,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP ,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),

  PRIMARY KEY (tradeId)
);

CREATE TABLE curvepoint (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  curveId tinyint,
  asOfDate TIMESTAMP,
  term DOUBLE ,
  value DOUBLE ,
  creationDate TIMESTAMP ,

  PRIMARY KEY (id)
);

CREATE TABLE rating (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber tinyint,

  PRIMARY KEY (id)
);

CREATE TABLE ruleName (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),

  PRIMARY KEY (id)
);

CREATE TABLE users (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),

  PRIMARY KEY (id)
);

--INITIALIZATION OF USERS TABLE
--amale/Password1@
--johndoe/Password1$
insert into users(fullname, username, password, role) values("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");
insert into users(fullname, username, password, role) values("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");
insert into users(fullname, username, password, role) values("Amale Idrissi", "amale", "$2a$10$RfGpw.RRjKk.e36IEi1Uc.9r.7kJp79cSKndathD5YbFmmR34YFZe", "ADMIN");
insert into users(fullname, username, password, role) values("John Doe", "johndoe", "$2a$10$664dSPocRv2NDo9aUu9.2OIyxlgUbXF6Nu5dmQ6/9UGO73v093mjy", "USER");

--INITIALIZATION OF RULENAME TABLE
insert into ruleName(name, description, json, template, sqlStr, sqlPart) values("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

--INITIALIZATION OF RATING TABLE
insert into rating(moodysRating, sandPRating, fitchRating, orderNumber) values("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

--INITIALIZATION OF CURVEPOINT TABLE
insert into curvepoint(curveId, asOfDate, term, value, creationDate) values(10, "2021-05-03 14:01:22", 15.7, 25.0, "2020-05-07 22:01:22");

--INITIALIZATION OF TRADE TABLE
insert into trade(account, type, buyQuantity) values("account name", "type name", 55.75);

--INITIALIZATION OF BIDLIST TABLE
insert into bidList(account, type, bidQuantity) values("account name", "type name", 55.75);