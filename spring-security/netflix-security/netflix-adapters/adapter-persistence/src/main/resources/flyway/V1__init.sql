DROP TABLE IF EXISTS `netflix`.`users`;
CREATE TABLE `netflix`.`users`
(
    USER_ID         VARCHAR(255)    NOT NULL COMMENT '사용자 ID (UUID)',
    USER_NAME       VARCHAR(50)     NOT NULL COMMENT '사용자 이름',
    PASSWORD        VARCHAR(255)    NOT NULL COMMENT '사용자 비밀번호 (암호화)',
    EMAIL           VARCHAR(255)    NOT NULL COMMENT '이메일',
    PHONE           VARCHAR(255)    NOT NULL COMMENT '전화번호',

    CREATED_AT      DATETIME        NOT NULL COMMENT '생성일자',
    CREATED_BY      VARCHAR(50)     NOT NULL COMMENT '생성자',
    MODIFIED_AT     DATETIME        NOT NULL COMMENT '수정일자',
    MODIFIED_BY     VARCHAR(50)     NOT NULL COMMENT '수정자',

    PRIMARY KEY (USER_ID)
);

DROP TABLE IF EXISTS `netflix`.`sample`;
CREATE TABLE `netflix`.`sample`
(
    SAMPLE_ID       VARCHAR(255) NOT NULL,
    SAMPLE_NAME     VARCHAR(255) NOT NULL,
    SAMPLE_DESC     VARCHAR(255) NOT NULL,

    PRIMARY KEY (SAMPLE_ID)
);

DROP TABLE IF EXISTS `netflix`.`tokens`;
CREATE TABLE `netflix`.`tokens`
(
    TOKEN_ID                 VARCHAR(255) NOT NULL COMMENT '토큰 PK',
    USER_ID                  VARCHAR(255) NOT NULL COMMENT '사용자 ID',
    ACCESS_TOKEN             VARCHAR(255) COMMENT '액세스 토큰',
    REFRESH_TOKEN            VARCHAR(255) COMMENT '리프레시 토큰',
    ACCESS_TOKEN_EXPIRES_AT  DATETIME COMMENT '액세스 토큰 만료시간',
    REFRESH_TOKEN_EXPIRES_AT DATETIME COMMENT '리프레시 토큰 만료시간',

    CREATED_AT               DATETIME     NOT NULL COMMENT '생성일자',
    CREATED_BY               VARCHAR(50)  NOT NULL COMMENT '생성자',
    MODIFIED_AT              DATETIME     NOT NULL COMMENT '수정일자',
    MODIFIED_BY              VARCHAR(50)  NOT NULL COMMENT '수정자',

    PRIMARY KEY (TOKEN_ID)
);