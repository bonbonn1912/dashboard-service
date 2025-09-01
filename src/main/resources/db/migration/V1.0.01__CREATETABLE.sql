CREATE TABLE TUYA_DEVICE (
                             ID BIGINT NOT NULL AUTO_INCREMENT,
                             NAME VARCHAR(100) NOT NULL,
                             TYPE VARCHAR(50) NOT NULL,
                             CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (ID)
);

CREATE TABLE TUYA_REGULATOR (
                                ID BIGINT NOT NULL AUTO_INCREMENT,
                                DEVICE_ID BIGINT NOT NULL,
                                T1 INT NOT NULL,
                                T2 INT NOT NULL,
                                T3 INT NOT NULL,
                                LAST_UPDATE TIMESTAMP NOT NULL,
                                PRIMARY KEY (ID),
                                CONSTRAINT FK_TUYA_REGULATOR_DEVICE
                                    FOREIGN KEY (DEVICE_ID) REFERENCES TUYA_DEVICE(ID)
                                        ON DELETE CASCADE
);

CREATE TABLE TUYA_ELECTRICITY_METER (
                                        ID BIGINT NOT NULL AUTO_INCREMENT,
                                        DEVICE_ID BIGINT NOT NULL,
                                        USAGE_VALUE INT NOT NULL,
                                        LAST_UPDATE TIMESTAMP NOT NULL,
                                        PRIMARY KEY (ID),
                                        CONSTRAINT FK_TUYA_ELECTRICITY_METER_DEVICE
                                            FOREIGN KEY (DEVICE_ID) REFERENCES TUYA_DEVICE(ID)
                                                ON DELETE CASCADE
);

INSERT INTO TUYA_DEVICE (NAME, TYPE)
VALUES
    ('regulatorLeda', 'REGULATOR'),
    ('regulatorRoth', 'REGULATOR'),
    ('electricityMeter', 'ELECTRICITY_METER');