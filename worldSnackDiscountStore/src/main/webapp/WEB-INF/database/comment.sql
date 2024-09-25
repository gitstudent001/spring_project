-- COMMENT_TABLE의 시퀀스
DROP SEQUENCE COMMENT_SEQ;
CREATE SEQUENCE COMMENT_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0
CACHE 20
NOCYCLE;

-- COMMENT_VOTE_TABLE의 시퀀스
DROP SEQUENCE COMMENT_VOTE_SEQ;
CREATE SEQUENCE COMMENT_VOTE_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 0
CACHE 20
NOCYCLE;

-- 댓글 테이블 (COMMENT_TABLE)
DROP TABLE COMMENT_TABLE PURGE;
CREATE TABLE COMMENT_TABLE (
    COMMENT_IDX NUMBER CONSTRAINT PK_COMMENT PRIMARY KEY,
    POST_ID NUMBER CONSTRAINT FK_COMMENT_POST REFERENCES COMMUNITY_TABLE(COMMUNITY_IDX) ON DELETE CASCADE,  -- 게시물 ID
    USER_IDX NUMBER CONSTRAINT FK_COMMENT_USER REFERENCES USER_TABLE(USER_IDX) NOT NULL,
    PARENT_COMMENT_IDX NUMBER CONSTRAINT FK_COMMENT_PARENT REFERENCES COMMENT_TABLE(COMMENT_IDX),
    COMMENT_TEXT CLOB NOT NULL,
    COMMENT_IMAGE VARCHAR2(255),  -- 댓글에 첨부된 이미지 URL
    COMMENT_DATE DATE DEFAULT SYSDATE NOT NULL,
    COMMENT_UPDATE_DATE DATE,
    
    COMMENT_LEVEL NUMBER DEFAULT 0,  -- 댓글의 계층 구조 레벨 (0: 최상위 댓글, 1: 1단계 대댓글, ...)
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  -- 댓글 삭제 여부 ('Y' 또는 'N')
    IS_HIDDEN CHAR(1) DEFAULT 'N' CHECK (IS_HIDDEN IN ('Y', 'N')),    -- 댓글 숨기기 여부 ('Y' 또는 'N')
    UPVOTE_COUNT NUMBER DEFAULT 0,    -- 업보트 수
    DOWNVOTE_COUNT NUMBER DEFAULT 0,  -- 다운보트 수 
    REPORTED_COUNT NUMBER DEFAULT 0   -- 댓글이 신고된 수
);

-- 댓글 투표 테이블 (COMMENT_VOTE_TABLE)
DROP TABLE COMMENT_VOTE_TABLE PURGE;
CREATE TABLE COMMENT_VOTE_TABLE (
    VOTE_IDX NUMBER CONSTRAINT PK_VOTE PRIMARY KEY,  -- 각 투표의 고유 ID
    COMMENT_IDX NUMBER CONSTRAINT FK_VOTE_COMMENT REFERENCES COMMENT_TABLE(COMMENT_IDX) ON DELETE CASCADE NOT NULL,  -- 투표가 달린 댓글 ID
    USER_IDX NUMBER CONSTRAINT FK_VOTE_USER REFERENCES USER_TABLE(USER_IDX) NOT NULL,  -- 투표를 한 사용자 ID
    VOTE_TYPE NUMBER(1) NOT NULL,  -- 1: 업보트, -1: 다운보트
    LAST_VOTE_ACTION NUMBER(1),  -- 마지막 투표 액션 (1: 업보트, -1: 다운보트, 0: 투표 취소)
    VOTE_DATE DATE DEFAULT SYSDATE NOT NULL  -- 투표가 이루어진 날짜
);

-- 댓글 작성 날짜를 기준으로 최신순으로 정렬하기 위해 COMMENT_DATE 컬럼에 대한 내림차순 인덱스 생성
CREATE INDEX IDX_COMMENT_DATE ON COMMENT_TABLE (COMMENT_DATE DESC);
-- 특정 게시물에 속한 모든 댓글을 조회할 때 성능을 향상시키기 위해 POST_ID 컬럼에 인덱스 생성
CREATE INDEX IDX_COMMENT_POST_ID ON COMMENT_TABLE (POST_ID);
-- 특정 댓글의 하위 댓글(대댓글)을 조회할 때 성능을 높이기 위해 PARENT_COMMENT_IDX 컬럼에 인덱스 생성
CREATE INDEX IDX_COMMENT_PARENT_ID ON COMMENT_TABLE (PARENT_COMMENT_IDX);
-- 댓글을 추천(업보트) 수 기준으로 정렬하여 조회할 때 성능을 최적화하기 위해 UPVOTE_COUNT 컬럼에 대한 내림차순 인덱스 생성
CREATE INDEX IDX_COMMENT_UPVOTE ON COMMENT_TABLE (UPVOTE_COUNT DESC);
-- 댓글을 비추천(다운보트) 수 기준으로 정렬하여 조회할 때 성능을 최적화하기 위해 DOWNVOTE_COUNT 컬럼에 대한 내림차순 인덱스 생성
CREATE INDEX IDX_COMMENT_DOWNVOTE ON COMMENT_TABLE (DOWNVOTE_COUNT DESC);
-- 특정 사용자가 작성한 댓글을 빠르게 조회하기 위해 USER_IDX 컬럼에 인덱스 생성
CREATE INDEX IDX_COMMENT_USER_ID ON COMMENT_TABLE (USER_IDX);

-- 특정 댓글에 대한 모든 투표를 조회할 때 성능을 향상시키기 위해 COMMENT_IDX 컬럼에 인덱스 생성
CREATE INDEX IDX_VOTE_COMMENT_ID ON COMMENT_VOTE_TABLE (COMMENT_IDX);
-- 특정 사용자가 한 투표를 조회할 때 성능을 향상시키기 위해 USER_IDX 컬럼에 인덱스 생성
CREATE INDEX IDX_VOTE_USER_ID ON COMMENT_VOTE_TABLE (USER_IDX);


SELECT * FROM COMMENT_TABLE ORDER BY COMMENT_IDX;
SELECT * FROM COMMENT_VOTE_TABLE ORDER BY VOTE_IDX;  

commit;