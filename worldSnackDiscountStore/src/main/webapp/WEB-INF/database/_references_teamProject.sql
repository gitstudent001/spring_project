/*
    -- system 에서 세계과자프로젝트 유저 생성 --
    CREATE USER worldSnack IDENTIFIED BY 1234;
    
    -- 세션 접속 권한 부여 --
    GRANT CREATE SESSION TO worldSnack;
    
    -- 테이블 생성 권한 부여 --
    GRANT CREATE TABLE TO worldSnack;
       
    -- tablespace 할당량 권한 부여 --
    ALTER USER worldSnack DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
*/

DROP SEQUENCE USER_SEQ;
-- user 테이블 시퀀스 (24.08.14 아직 실행은 안했음) --
CREATE SEQUENCE USER_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0;


DROP TABLE USER_TABLE PURGE;
-- 사용자 정보 테이블 --
CREATE TABLE USER_TABLE(
	USER_IDX NUMBER CONSTRAINT PK_USER PRIMARY KEY,
	USER_NAME VARCHAR2(50) NOT NULL,
	USER_ID VARCHAR2(100) NOT NULL,
	USER_PW VARCHAR2(100) NOT NULL,
    USER_EMAIL VARCHAR2(100) NOT NULL,
    USER_NICKNAME VARCHAR2(100) NOT NULL,
    USER_CONTENT_COUNT NUMBER DEFAULT 1,
    USER_FIRST_JOIN DATE DEFAULT SYSDATE
);

SELECT * FROM USER_CONSTRAINTS WHERE TABLE_NAME = 'tabnam';
SELECT * FROM USER_TABLE;



DROP SEQUENCE CATEGORY_SEQ;
-- content 테이블 시퀀스 (24.08.14 아직 실행은 안했음) --
CREATE SEQUENCE CATEGORY_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0;

--DROP TABLE CATEGORY_INFO_TABLE PURGE;
DROP TABLE CATEGORY_TABLE PURGE;
-- 카테고리 목록 테이블 --
CREATE TABLE CATEGORY_TABLE (
	CATEGORY_IDX NUMBER CONSTRAINT PK_CATEGORY PRIMARY KEY,
	CATEGORY_NAME VARCHAR2(500) NOT NULL
);

-- 제품 카테고리 데이터 추가
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '쿠키');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '젤리');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '스낵');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '사탕');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '파이');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '비스켓');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '카라멜');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '초콜릿');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '초코바');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '껌');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '감자칩');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '나초');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '샌드');
INSERT INTO CATEGORY_TABLE VALUES(CATEGORY_SEQ.NEXTVAL, '기타');

SELECT * FROM CATEGORY_TABLE;
--ROLLBACK;
COMMIT;


/*
DROP TABLE CATEGORY_SELECT_TABLE;
-- 제품 검색용 분류 테이블 --
CREATE TABLE CATEGORY_SELECT_TABLE(
    --CONTENT_IDX NUMBER CONSTRAINT FK_CATEGORY_SELECT REFERENCES CONTENT_TABLE(CONTENT_IDX) NOT NULL,
    --CATEGORY_INFO_IDX NUMBER CONSTRAINT FK2_CATEGORY_SELECT REFERENCES CATEGORY_INFO_TABLE(CATEGORY_INFO_IDX) NOT NULL,
    CATEGORY_SELECT_IDX NUMBER CONSTRAINT PK_CATEGORY_SELECT PRIMARY KEY,
    CONTENT_IDX NUMBER NOT NULL,
    CATEGORY_INFO_IDX NUMBER NOT NULL,
    CATEGORY_SELECT_NAME VARCHAR2(500)
);
*/



DROP SEQUENCE CONTENT_SEQ;
-- content 테이블 시퀀스 (24.08.14 아직 실행은 안했음) --
CREATE SEQUENCE CONTENT_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0;

DROP TABLE CONTENT_TABLE PURGE;
-- 게시글 정보 테이블 --
CREATE TABLE CONTENT_TABLE(
	CONTENT_IDX NUMBER CONSTRAINT PK_CONTENT PRIMARY KEY,
    CATEGORY_IDX NUMBER CONSTRAINT FK_CONTENT2 REFERENCES CATEGORY_TABLE(CATEGORY_IDX) NOT NULL,
    --CATEGORY_IDX NUMBER NOT NULL,
	CONTENT_SUBJECT VARCHAR2(500) NOT NULL,
	CONTENT_TEXT CLOB NOT NULL, --LONG 타입 -> CLOB로 바꿈
	CONTENT_FILE VARCHAR2(500),
	CONTENT_WRITER_IDX NUMBER CONSTRAINT FK_CONTENT REFERENCES USER_TABLE(USER_IDX) NOT NULL,
    --CONTENT_WRITER_IDX NUMBER NOT NULL,
    CONTENT_MAKE VARCHAR2(50) NOT NULL,
    CONTENT_COUNTRY VARCHAR2(50) NOT NULL,
    CONTENT_PRODNO VARCHAR2(10) NOT NULL,
    CONTENT_PRODPRICE NUMBER NOT NULL,
    CONTENT_VIEW NUMBER DEFAULT 0,
	CONTENT_DATE DATE NOT NULL
);


--도메인 인덱스 생성 (LIKE 검색 필요)
--CREATE INDEX I_CONTENT_TABLE_D1 ON CONTENT_TABLE(CONTENT_TEXT) INDEXTYPE IS CTXSYS.CONTEXT;
--DROP INDEX I_CONTENT_TABLE_D1; -- 도메인 인덱스 삭제 (인덱스 필요 없음)
-- 아래 코드 실행
ALTER TABLE CONTENT_TABLE MODIFY CONTENT_TEXT CLOB;


DELETE FROM USER_TABLE;
DELETE FROM CONTENT_TABLE;
COMMIT;



DROP TABLE SCRAP_TABLE PURGE;
-- 관심/찜/공유 한 게시글 정보 테이블 --
CREATE TABLE SCRAP_TABLE (
    USER_IDX NUMBER CONSTRAINT FK_SCRAP REFERENCES USER_TABLE(USER_IDX) NOT NULL,
    CONTENT_IDX NUMBER CONSTRAINT FK2_SCRAP REFERENCES CONTENT_TABLE(CONTENT_IDX) NOT NULL
    --USER_IDX NUMBER NOT NULL,
    --CONTENT_IDX NUMBER NOT NULL
);



DROP TABLE LOGIN_LOG_TABLE PURGE;
-- 로그인,로그아웃 로그 저장 테이블 --
CREATE TABLE LOGIN_LOG_TABLE (
    USER_IDX NUMBER CONSTRAINT FK_LOGIN_LOG REFERENCES USER_TABLE(USER_IDX) NOT NULL,
    --USER_IDX NUMBER NOT NULL,
    LOGIN_START_DATE DATE,
    LOGIN_END_DATE DATE,
    LOGIN_IP_ADDR VARCHAR2(20)
);


SELECT * FROM USER_TABLE;
SELECT * FROM CONTENT_TABLE;
SELECT * FROM CATEGORY_INFO_TABLE;
SELECT * FROM CATEGORY_SELECT_TABLE;
SELECT * FROM SCRAP_TABLE;

--제약조건 삭제
ALTER TABLE CONTENT_TABLE DROP CONSTRAINT FK_CONTENT;
ALTER TABLE SCRAP_TABLE DROP CONSTRAINT FK_SCRAP;
ALTER TABLE SCRAP_TABLE DROP CONSTRAINT FK2_SCRAP;


-- USER_TABLE 에 등급 칼럼 추가 --
ALTER TABLE USER_TABLE ADD (USER_CONTENT_COUNT NUMBER DEFAULT 1);

DROP TABLE GRADE_TABLE PURGE;
-- 등급 테이블 --
CREATE TABLE GRADE_TABLE(
    GRADE_IDX NUMBER CONSTRAINT PK_GRADE PRIMARY KEY,
    GRADE_LOW NUMBER,
    GRADE_HIGH NUMBER
);

-- 등급 테이블에 데이터 추가 --
INSERT INTO GRADE_TABLE VALUES(1, 1, 50);
INSERT INTO GRADE_TABLE VALUES(2, 51, 150);
INSERT INTO GRADE_TABLE VALUES(3, 151, 300);
INSERT INTO GRADE_TABLE VALUES(4, 301, 600);
INSERT INTO GRADE_TABLE VALUES(5, 601, 1200);
INSERT INTO GRADE_TABLE VALUES(6, 1201, 99999);

SELECT * FROM GRADE_TABLE;
COMMIT;

/* 20240904_회원가입 시 첫 가입일 저장할 칼럼 추가 */
-- USER_TABLE 에 가입날짜 칼럼 추가 --
ALTER TABLE USER_TABLE ADD (USER_FIRST_JOIN DATE DEFAULT SYSDATE);


