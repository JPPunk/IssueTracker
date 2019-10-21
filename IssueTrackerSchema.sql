CREATE DATABASE IssueTracker;
\c IssueTracker;

DROP TABLE IF EXISTS A3_ISSUE;
DROP TABLE IF EXISTS A3_USER;

CREATE TABLE A3_USER
(
	FIRSTNAME VARCHAR(100) not null, 
	LASTNAME VARCHAR(100) not null,
	USER_ID SERIAL primary key
);

Insert into A3_USER (FIRSTNAME,LASTNAME) values ('Dean','Smith');
Insert into A3_USER (FIRSTNAME,LASTNAME) values ('John','Peter');

CREATE TABLE A3_ISSUE 
(
	TITLE VARCHAR(100),  
	CREATOR INTEGER not null REFERENCES A3_USER, 
	RESOLVER INTEGER REFERENCES A3_USER, 
	VERIFIER INTEGER REFERENCES A3_USER,
	DESCRIPTION VARCHAR(1000),
	ISSUE_ID SERIAL primary key
);

Insert into A3_ISSUE (TITLE,DESCRIPTION,CREATOR,RESOLVER,VERIFIER) values ('Factorial with addition anomaly','Performing a factorial and then addition produces an off by 1 error',2,1,2);

Insert into A3_ISSUE (TITLE,DESCRIPTION,CREATOR,RESOLVER,VERIFIER) values ('Division by zero','Division by 0 doesn''t yield error or infinity as would be expected. Instead it results in -1.',1,1,1);

Insert into A3_ISSUE (TITLE,DESCRIPTION,CREATOR,RESOLVER,VERIFIER) values ('Incorrect BODMAS order','Addition occurring before multiplication',2,2,1);

commit;
