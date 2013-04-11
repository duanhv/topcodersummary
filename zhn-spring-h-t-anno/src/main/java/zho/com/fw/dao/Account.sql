CREATE TABLE 'account' (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'username' varchar(30) DEFAULT NULL,
  'password' varchar(30) DEFAULT NULL,
  'firstName' varchar(15) DEFAULT NULL,
  'lastName' varchar(30) DEFAULT NULL,
  PRIMARY KEY ('id')
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
