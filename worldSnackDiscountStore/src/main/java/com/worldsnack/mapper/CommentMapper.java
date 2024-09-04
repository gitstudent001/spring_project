package com.worldsnack.mapper;

import com.worldsnack.dto.CommentDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    // 댓글 추가
    @Insert("INSERT INTO COMMENT_TABLE (COMMENT_IDX, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, POST_ID, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT) " +
            "VALUES (COMMENT_SEQ.NEXTVAL, #{user_idx}, #{parent_comment_idx, jdbcType=NUMERIC}, #{comment_text}, SYSDATE, #{post_id}, #{comment_level}, 'N', 0, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "comment_idx", keyColumn = "COMMENT_IDX")
    void insertComment(CommentDTO commentDto);

    // 댓글 조회
    @Select("SELECT COMMENT_IDX, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, POST_ID, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT " +
            "FROM COMMENT_TABLE WHERE COMMENT_IDX = #{comment_idx}")
    CommentDTO getCommentById(Long comment_idx);

    // 모든 댓글 조회
    @Select("SELECT COMMENT_IDX, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, POST_ID, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT " +
            "FROM COMMENT_TABLE WHERE IS_DELETED = 'N'")
    List<CommentDTO> getAllComments();

    // 특정 게시물의 모든 댓글 조회 (계층 구조로 조회)
    @Select("SELECT C.COMMENT_IDX, C.USER_IDX, C.PARENT_COMMENT_IDX, C.COMMENT_TEXT, C.COMMENT_DATE, C.COMMENT_UPDATE_DATE, C.POST_ID, C.COMMENT_LEVEL, C.IS_DELETED, C.UPVOTE_COUNT, C.DOWNVOTE_COUNT, U.USER_NICKNAME " +
        "FROM COMMENT_TABLE C " +
        "JOIN USER_TABLE U ON C.USER_IDX = U.USER_IDX " +
        "WHERE C.POST_ID = #{post_id} AND C.IS_DELETED = 'N' " +
        "START WITH C.PARENT_COMMENT_IDX IS NULL CONNECT BY PRIOR C.COMMENT_IDX = C.PARENT_COMMENT_IDX " +
        "ORDER SIBLINGS BY C.COMMENT_DATE ASC")
    List<CommentDTO> selectCommentsByPostId(Long post_id);

    // 특정 게시물의 댓글 조회 (페이징)
    @Select("SELECT * FROM ( " +
            "  SELECT a.*, ROWNUM rnum FROM ( " +
            "    SELECT C.COMMENT_IDX, C.POST_ID, C.USER_IDX, C.PARENT_COMMENT_IDX, C.COMMENT_TEXT, C.COMMENT_DATE, C.COMMENT_UPDATE_DATE, " +
            "           C.COMMENT_LEVEL, C.IS_DELETED, C.UPVOTE_COUNT, C.DOWNVOTE_COUNT, U.USER_NICKNAME " +
            "    FROM COMMENT_TABLE C " +
            "    JOIN USER_TABLE U ON C.USER_IDX = U.USER_IDX " + // USER_TABLE과 JOIN
            "    WHERE C.POST_ID = #{postId} AND C.IS_DELETED = 'N' " +
            "    ORDER BY C.COMMENT_DATE DESC " +
            "  ) a " +
            "  WHERE ROWNUM <= #{offset} + #{limit} " +
            ") " +
            "WHERE rnum > #{offset}")
    List<CommentDTO> selectCommentsByPostIdWithPagination(@Param("postId") Long postId, @Param("offset") int offset, @Param("limit") int limit);



    // 특정 댓글의 대댓글(답글)만 조회
    @Select("SELECT COMMENT_IDX, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, POST_ID, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT " +
            "FROM COMMENT_TABLE WHERE PARENT_COMMENT_IDX = #{parent_comment_idx} AND IS_DELETED = 'N' " +
            "ORDER BY COMMENT_DATE ASC")
    List<CommentDTO> selectRepliesByCommentId(Long parent_comment_idx);

    // 댓글 업데이트
    @Update("UPDATE COMMENT_TABLE SET COMMENT_TEXT = #{comment_text}, COMMENT_UPDATE_DATE = SYSDATE WHERE COMMENT_IDX = #{comment_idx} AND IS_DELETED = 'N'")
    void updateComment(CommentDTO commentDto);

    // 댓글 삭제 (논리 삭제)
    @Update("UPDATE COMMENT_TABLE SET IS_DELETED = 'Y', COMMENT_TEXT = '[삭제된 댓글입니다]' WHERE COMMENT_IDX = #{comment_idx}")
    void deleteComment(Long comment_idx);

    // 댓글 숨기기
    @Update("UPDATE COMMENT_TABLE SET IS_HIDDEN = 'Y' WHERE COMMENT_IDX = #{comment_idx}")
    void hideComment(Long comment_idx);

    // 댓글 보이기
    @Update("UPDATE COMMENT_TABLE SET IS_HIDDEN = 'N' WHERE COMMENT_IDX = #{comment_idx}")
    void unhideComment(Long comment_idx);

    // 댓글 신고
    @Update("UPDATE COMMENT_TABLE SET REPORTED_COUNT = REPORTED_COUNT + 1 WHERE COMMENT_IDX = #{comment_idx}")
    void reportComment(Long comment_idx);

    // 댓글 업보트 증가
    @Update("UPDATE COMMENT_TABLE SET UPVOTE_COUNT = UPVOTE_COUNT + 1 WHERE COMMENT_IDX = #{comment_idx}")
    void upvoteComment(Long comment_idx);

    // 댓글 다운보트 증가
    @Update("UPDATE COMMENT_TABLE SET DOWNVOTE_COUNT = DOWNVOTE_COUNT + 1 WHERE COMMENT_IDX = #{comment_idx}")
    void downvoteComment(Long comment_idx);

    // 댓글 업보트 취소
    @Update("UPDATE COMMENT_TABLE SET UPVOTE_COUNT = UPVOTE_COUNT - 1 WHERE COMMENT_IDX = #{comment_idx} AND UPVOTE_COUNT > 0")
    void cancelUpvote(Long comment_idx);

    // 댓글 다운보트 취소
    @Update("UPDATE COMMENT_TABLE SET DOWNVOTE_COUNT = DOWNVOTE_COUNT - 1 WHERE COMMENT_IDX = #{comment_idx} AND DOWNVOTE_COUNT > 0")
    void cancelDownvote(Long comment_idx);

    // 특정 게시물의 댓글 조회 (정렬: 최신순)
    @Select("SELECT COMMENT_IDX, POST_ID, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT " +
            "FROM COMMENT_TABLE WHERE POST_ID = #{post_id} AND IS_DELETED = 'N' " +
            "ORDER BY COMMENT_DATE DESC")
    List<CommentDTO> selectCommentsByPostIdSortedByDate(Long post_id);

    // 특정 게시물의 댓글 조회 (정렬: 업보트순)
    @Select("SELECT COMMENT_IDX, POST_ID, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT " +
            "FROM COMMENT_TABLE WHERE POST_ID = #{post_id} AND IS_DELETED = 'N' " +
            "ORDER BY UPVOTE_COUNT DESC")
    List<CommentDTO> selectCommentsByPostIdSortedByUpvotes(Long post_id);
    
    // 특정 게시물의 댓글 조회 (정렬: 추천순, 업보트-다운보트 순)
    @Select("SELECT COMMENT_IDX, POST_ID, USER_IDX, PARENT_COMMENT_IDX, COMMENT_TEXT, COMMENT_DATE, COMMENT_UPDATE_DATE, COMMENT_LEVEL, IS_DELETED, UPVOTE_COUNT, DOWNVOTE_COUNT, (UPVOTE_COUNT - DOWNVOTE_COUNT) AS RECOMMEND_COUNT " +
            "FROM COMMENT_TABLE WHERE POST_ID = #{post_id} AND IS_DELETED = 'N' " +
            "ORDER BY RECOMMEND_COUNT DESC")
    List<CommentDTO> selectCommentsByPostIdSortedByRecommend(Long post_id);
}
