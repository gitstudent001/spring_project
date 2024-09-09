package com.worldsnack.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.worldsnack.dto.CommDTO;

public interface CommMapper {

    // 카테고리에 따른 게시글 목록 조회 (정렬 및 뷰 타입 추가)
		@Select("<script> "
      + "  SELECT c.*, u.user_nickname AS community_nickname "
      + "  FROM COMMUNITY_TABLE c "
      + "  LEFT JOIN USER_TABLE u ON c.community_writer_idx = u.user_idx "
      + "  <if test='category != null and category != \"\"'> "
      + "    WHERE c.COMMUNITY_CATEGORY = #{category} "
      + "  </if> "
      + "  ORDER BY "
      + "  <choose> "
      + "    <when test='sortOrder == \"latest\"'> "
      + "      c.COMMUNITY_DATE DESC "
      + "    </when> "
      + "    <otherwise> "
      + "      c.COMMUNITY_VIEW DESC "
      + "    </otherwise> "
      + "  </choose> "
      + "</script>")
    
    List<CommDTO> findPostsByCategory(
            @Param("category") String category,
            @Param("sortOrder") String sortOrder,
            @Param("viewType") String viewType
    );

    // 게시글 ID에 따른 게시글 조회
    @Select("SELECT c.*, u.user_nickname AS community_nickname " +
        "FROM COMMUNITY_TABLE c " +
        "LEFT JOIN USER_TABLE u ON c.community_writer_idx = u.user_idx " + 
        "WHERE c.community_idx = #{id}")
    CommDTO findPostById(@Param("id") int id);
    
    @SelectKey(statement = "SELECT COMMUNITY_SEQ.NEXTVAL FROM DUAL", 
    keyProperty = "community_idx", before = true, resultType = int.class)
    
    //게시글 저장
    @Insert("INSERT INTO COMMUNITY_TABLE (community_idx, community_subject, community_text, community_file, community_writer_idx, "
    																	 	 + "community_category, community_url, community_date, community_thumb) "
        + "VALUES (#{community_idx}, #{community_subject}, #{community_text, jdbcType=VARCHAR}, #{community_file, jdbcType=VARCHAR}, "
        + "#{community_writer_idx}, #{community_category}, #{community_url, jdbcType=VARCHAR}, SYSDATE, #{community_thumb, jdbcType=VARCHAR})")
    void savePost(CommDTO post);


    // 텍스트 게시물 저장 (카테고리 필드를 TEXT로 설정)
    default void saveTextPost(CommDTO post) {
        post.setCommunity_category("TEXT");
        savePost(post);
    }

    // 이미지 게시물 저장 (카테고리 필드를 IMAGE로 설정)
    default void saveImagePost(CommDTO post) {
        post.setCommunity_category("IMAGE");
        savePost(post);
    }

    // 랭킹 게시물 저장 (카테고리 필드를 RANKING로 설정)
    default void saveRankingPost(CommDTO post) {
        post.setCommunity_category("RANKING");
        savePost(post);
    }

    // 프로모션 게시물 저장 (카테고리 필드를 PROMOTION로 설정)
    default void savePromotionPost(CommDTO post) {
        post.setCommunity_category("PROMOTION");
        savePost(post);
    }
    
    // 게시글 업데이트
    @Update("UPDATE COMMUNITY_TABLE SET "
    		+ "            COMMUNITY_SUBJECT = #{community_subject},  COMMUNITY_TEXT = #{community_text, jdbcType=VARCHAR}, "
    		+ "            COMMUNITY_WRITER_IDX = #{community_writer_idx}, COMMUNITY_FILE = #{community_file, jdbcType=VARCHAR}, "
    		+ "            COMMUNITY_DATE = SYSDATE, COMMUNITY_CATEGORY = #{community_category}, COMMUNITY_URL = #{community_url, jdbcType=VARCHAR}, "
    		+ "						 COMMUNITY_THUMB = #{community_thumb, jdbcType=VARCHAR} "
    		+ "            WHERE COMMUNITY_IDX = #{community_idx}")
    void updatePost(CommDTO post);
    
    // 게시글 삭제------(희만 수정)
    @Delete({"BEGIN",
    				 "	DELETE FROM COMMENT_TABLE WHERE POST_ID = #{community_idx}; ",
    				 "	DELETE FROM COMMUNITY_TABLE WHERE COMMUNITY_IDX = #{community_idx}; ",
      
    				 "	COMMIT; ",
    				 "EXCEPTION ",
    				 "	WHEN OTHERS THEN ",
    				 "		ROLLBACK; ",
    				 "END;"})
    void deletePost(@Param("community_idx") int community_idx);
    
    // 조회수 증가
    @Update("UPDATE COMMUNITY_TABLE SET COMMUNITY_VIEW = COMMUNITY_VIEW + 1 WHERE COMMUNITY_IDX = #{id}")
    void increaseViewCount(@Param("id") int id);

    // 업보트 수 증가
    @Update("UPDATE COMMUNITY_TABLE SET COMMUNITY_UPVOTES = COMMUNITY_UPVOTES + 1 WHERE COMMUNITY_IDX = #{id}")
    void incrementUpvotes(@Param("id") int id);

    // 다운보트 수 증가
    @Update("UPDATE COMMUNITY_TABLE SET COMMUNITY_DOWNVOTES = COMMUNITY_DOWNVOTES + 1 WHERE COMMUNITY_IDX = #{id}")
    void incrementDownvotes(@Param("id") int id);

    // 업보트 - 다운보트의 차이를 반환
    @Select("SELECT (COMMUNITY_UPVOTES - COMMUNITY_DOWNVOTES) FROM COMMUNITY_TABLE WHERE COMMUNITY_IDX = #{id}")
    int getNetVotes(@Param("id") int id);
    
    
}
