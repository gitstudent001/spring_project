// post_templates.js

const PostTemplates = {
  postTemplate: `
    <div class="compact-post d-flex p-2 border-bottom post-link" data-post-id="{{community_idx}}" style="cursor: pointer;">
      <!-- 썸네일 이미지 -->
      <div class="thumbnail mr-3 post-thumbnail" style="width: 100px; height: 100px; overflow: hidden;" data-thumbnail="{{community_thumb}}">
        <img id="imgThumb_{{community_idx}}" class="imgThumb" src="" alt="썸네일" class="img-thumbnail" style="width: 100%; height: 100%; object-fit: cover;">
      </div>

      <!-- 게시글 내용 -->
      <div class="post-content flex-grow-1">
        <!-- 작성자와 작성 시간 -->
        <div class="d-flex justify-content-between align-items-center mb-1">
          <small class="text-muted">{{community_nickname}}</small>
          <small class="postDate text-muted" data-date="{{community_date}}"></small>
        </div>
        
        <!-- 제목 -->
        <h5 class="mb-1"><a href="${rootPath}board/post/{{community_idx}}" class="text-dark">{{community_subject}}</a></h5>
        
        <!-- 하단 기능 버튼들 -->
        <div class="post-actions d-flex align-items-center">
          <!-- 확장 -->
          <button type="button" class="btn-expand btn-fills mr-2" data-post-id="{{community_idx}}">
            <i class="fa-solid fa-up-right-and-down-left-from-center"></i>
          </button>
          
          <!-- 업보트/다운보트 -->
          <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
            <button type="button" class="rounded-circle btn-vote up-vote" data-vote-type="upvote" data-post-id="{{community_idx}}">
              <i class="fa-regular fa-thumbs-up"></i>
            </button>
            <span id="vote-count-{{community_idx}}" class="mx-2 text-center">{{vote_count}}</span>
            <button type="button" class="rounded-circle btn-vote down-vote" data-vote-type="downvote" data-post-id="{{community_idx}}">
              <i class="fa-regular fa-thumbs-down"></i>
            </button>
          </div>
                    
          <!-- 조회수 -->
          <small class="mr-3 text-muted">조회수: {{community_view}}</small>

          <!-- 댓글 수 -->
          <a href="${rootPath}board/post/{{community_idx}}#comments-section" class="mr-3 comment-count" style="color: black; font-size:15px; ">댓글 수: {{community_comment}}</a>
          
          <!-- 공유 버튼 -->
          <button type="button" class="shareBtn btn btn-custom btn-sm mr-2" aria-label="공유" data-post-id="{{community_idx}}">
            <i class="fas fa-share"></i> 공유
          </button>
          
          <!-- 저장 버튼 -->
          <button type="button" class="btn-scrap btn btn-custom btn-sm mr-2" data-post-id="{{community_idx}}">
            <i class="fa-regular fa-bookmark"></i> 스크랩
          </button>
          
          <!-- 숨기기 버튼 -->
          <button type="button" class="btn-hide btn btn-custom btn-sm mr-2" data-post-id="{{community_idx}}">
            <i class="fas fa-eye-slash"></i> 숨기기
          </button>
          
          <!-- 신고 버튼 -->
          <button type="button" class="btn-report btn btn-custom btn-sm" data-post-id="{{community_idx}}">
            <i class="fas fa-flag"></i> 신고
          </button>
        </div>
      </div>
    </div>
  `,

  shareModalTemplate: `
    <div id="shareModal_{{community_idx}}" class="modal-share" role="dialog" aria-labelledby="shareModalTitle_{{community_idx}}" aria-hidden="true">
      <div class="modal-content">
        <div class="d-flex justify-content-between align-items-center">
          <h3 id="shareModalTitle_{{community_idx}}">공유하기</h3>
		      <h4 class="close position-absolute top-0 end-0 p-2 mr-1" aria-label="닫기">&times;</h4>
        </div>
        <div class="mt-3">
          <button type="button">페이스북</button>
          <button type="button">트위터</button>
          <button type="button">이메일</button>
          <button type="button" id="copyLinkBtn_{{community_idx}}">링크 복사</button>
        </div>
      </div>
    </div>
  `
};