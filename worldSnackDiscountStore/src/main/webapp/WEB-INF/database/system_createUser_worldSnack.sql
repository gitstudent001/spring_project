-- system 에서 세계과자프로젝트 유저 생성 --
CREATE USER worldSnack IDENTIFIED BY 1234;

-- 세션 접속 권한 부여 --
GRANT CREATE SESSION TO worldSnack;

-- 테이블 생성 권한 부여 --
GRANT CREATE TABLE TO worldSnack;

-- tablespace 할당량 권한 부여 --
ALTER USER worldSnack DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;


/* 아래 코드 추가 했습니다 아래 코드만 SYSTEM계정에서 실행해 주세요 */
-- 기본 사용자 권한 부여 --
GRANT RESOURCE, CONNECT, DBA TO worldSnack;

-- 시퀀스, 프로시져 생성 권한 부여 --
GRANT CREATE SEQUENCE, CREATE PROCEDURE TO worldSnack;