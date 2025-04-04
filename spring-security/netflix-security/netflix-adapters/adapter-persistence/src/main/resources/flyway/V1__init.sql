DROP TABLE IF EXISTS `netflix`.`sample`;
CREATE TABLE `netflix`.`sample`
(
    SAMPLE_ID       VARCHAR(255) NOT NULL,
    SAMPLE_NAME     VARCHAR(255) NOT NULL,
    SAMPLE_DESC     VARCHAR(255) NOT NULL,

    PRIMARY KEY (SAMPLE_ID)
);

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

DROP TABLE IF EXISTS `netflix`.`social_users`;
CREATE TABLE `netflix`.`social_users`
(
    SOCIAL_USER_ID          VARCHAR(255) NOT NULL,
    USER_NAME               VARCHAR(255) NOT NULL,
    PROVIDER                VARCHAR(255) NOT NULL,
    PROVIDER_ID             VARCHAR(255) NOT NULL,

    CREATED_AT               DATETIME     NOT NULL COMMENT '생성일자',
    CREATED_BY               VARCHAR(50)  NOT NULL COMMENT '생성자',
    MODIFIED_AT              DATETIME     NOT NULL COMMENT '수정일자',
    MODIFIED_BY              VARCHAR(50)  NOT NULL COMMENT '수정자',

    PRIMARY KEY (SOCIAL_USER_ID)
);

DROP TABLE IF EXISTS `netflix`.`user_subscriptions`;
CREATE TABLE `netflix`.`user_subscriptions`
(
    USER_SUBSCRIPTION_ID VARCHAR(255)   NOT NULL COMMENT '사용자 구독 ID',
    USER_ID              VARCHAR(255)   NOT NULL COMMENT '사용자 ID',
    SUBSCRIPTION_NAME    VARCHAR(255)   NOT NULL COMMENT '구독권 이름',
    START_AT             DATETIME       NOT NULL COMMENT '시작 일시 (yyyyMMdd HH:mm:dd)',
    END_AT               DATETIME       NOT NULL COMMENT '종료 일시 (yyyyMMdd HH:mm:dd)',
    VALID_YN             TINYINT(1)     NOT NULL COMMENT '구독권 유효 여부',

    CREATED_AT           DATETIME       NOT NULL COMMENT '생성일자',
    CREATED_BY           VARCHAR(50)    NOT NULL COMMENT '생성자',
    MODIFIED_AT          DATETIME       NOT NULL COMMENT '수정일자',
    MODIFIED_BY          VARCHAR(50)    NOT NULL COMMENT '수정자',

    PRIMARY KEY (USER_SUBSCRIPTION_ID)
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

DROP TABLE IF EXISTS `netflix`.`movies`;
CREATE TABLE `netflix`.`movies`
(
    MOVIE_ID    VARCHAR(255) NOT NULL COMMENT '영화 ID',
    MOVIE_NAME  VARCHAR(255) NOT NULL COMMENT '영화 명',
    IS_ADULT    TINYINT(1) COMMENT '성인영화 여부',
    GENRE       VARCHAR(255) COMMENT '장르',
    OVERVIEW    VARCHAR(255) COMMENT '설명',
    RELEASED_AT VARCHAR(255) COMMENT '출시일자',

    CREATED_AT  DATETIME     NOT NULL COMMENT '생성일자',
    CREATED_BY  VARCHAR(50)  NOT NULL COMMENT '생성자',
    MODIFIED_AT DATETIME     NOT NULL COMMENT '수정일자',
    MODIFIED_BY VARCHAR(50)  NOT NULL COMMENT '수정자',

    PRIMARY KEY (MOVIE_ID)
);

DROP TABLE IF EXISTS `netflix`.`user_movie_likes`;
CREATE TABLE `netflix`.`user_movie_likes`
(
    USER_MOVIE_LIKE_ID VARCHAR(255) NOT NULL COMMENT 'PK',
    USER_ID            VARCHAR(255) NOT NULL COMMENT '사용자 ID',
    MOVIE_ID           VARCHAR(255) NOT NULL COMMENT '영화 ID',
    LIKE_YN            TINYINT(1) COMMENT '좋아요 여부',

    CREATED_AT         DATETIME     NOT NULL COMMENT '생성일자',
    CREATED_BY         VARCHAR(50)  NOT NULL COMMENT '생성자',
    MODIFIED_AT        DATETIME     NOT NULL COMMENT '수정일자',
    MODIFIED_BY        VARCHAR(50)  NOT NULL COMMENT '수정자',

    PRIMARY KEY (USER_MOVIE_LIKE_ID)
)