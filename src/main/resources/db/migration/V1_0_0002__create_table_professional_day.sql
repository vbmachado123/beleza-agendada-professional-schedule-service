CREATE TABLE professional_day
(
ID  VARCHAR(255) NOT NULL,
PROFESSIONAL_DATE DATE NOT NULL,
INIT_TIME TIME NOT NULL,
END_TIME TIME NOT NULL,
DAY_OF_WEEK VARCHAR(50) NOT NULL,
PROFESSIONAL_ID VARCHAR(255) NOT NULL,
CREATE_DATE DATE,
UPDATE_DATE DATE,
CONSTRAINT pk_professional_day PRIMARY KEY (id)
);
