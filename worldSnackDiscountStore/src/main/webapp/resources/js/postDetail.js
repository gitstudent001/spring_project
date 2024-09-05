document.addEventListener("DOMContentLoaded", function () {
  // moment.js가 올바르게 로드되었는지 확인
  if (typeof moment === 'undefined') return;
  
  // 서버로부터 받은 작성 날짜를 읽어와서 moment.js를 사용하여 상대 시간으로 변환
  const postDateElement = document.getElementById('postDate');
  if (postDateElement) {
    const createdAt = moment(postDateElement.dataset.date, "YYYY-MM-DDTHH:mm:ss");
    document.getElementById('timeAgo').textContent = createdAt.fromNow();
  }

  // Toast UI Viewer 초기화 (텍스트 게시글인 경우)
  const viewerElement = document.getElementById('viewer');
  if (viewerElement) {
    new toastui.Editor.factory({
      el: viewerElement,
      initialValue: viewerElement.dataset.content
    });
  }

  // 초기 댓글 목록 가져오기
  fetchComments(0, 10); // 처음에는 10개의 댓글을 가져옴

  // 스크롤 이벤트 추가 (무한 스크롤링)
  window.addEventListener('scroll', handleScroll);
});

let isFetching = false;  // 현재 데이터를 가져오는 중인지 여부를 추적
let offset = 0;  // 시작 오프셋
const limit = 10;  // 한 번에 가져올 댓글 수
const rootPath = root;

function handleScroll() {
  // 페이지 끝에 도달했을 때 추가 댓글을 로드
  if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100 && !isFetching) {
    offset += limit;  // 다음 오프셋으로 증가
    fetchComments(offset, limit);  // 다음 댓글 가져오기
  }
}

// 게시글 업다운보트
function vote(type, postId) {
  
  $.ajax({
    url: `${rootPath}article/vote`,
    type: 'POST',
    data: {
      id: postId,
      voteType: type
    },
    success: function(response) {
      if (response.success) {
        updatePostVoteCount(postId, response.newVoteCount);
      } else {
        alert('Error: ' + response.message);
      }
    },
    error: function() {
      alert('서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  });
}

function updatePostVoteCount(postId, newVoteCount) {
  $(`#vote-count-${postId}`).text(newVoteCount);  // 새 투표 수로 UI 업데이트
}

// 댓글 목록 가져오기 (페이징 적용)
function fetchComments(offset, limit) {
  isFetching = true;  // 데이터 가져오는 중으로 설정
  const postId = post.community_idx;

  $.ajax({
    url: `${rootPath}comments/post/${postId}/page?offset=${offset}&limit=${limit}`, // 페이징 URL 설정
    type: 'GET',
    success: function (comments) {
      if (comments && comments.length > 0) {
        comments.forEach(function (comment) {
          renderComment(comment);
        });
      } else if (offset === 0) {
        // 처음 로드 시 댓글이 없는 경우 메시지 표시
        $('#comments-list').append('<p class="text-muted">댓글이 없습니다.</p>');
      }
      isFetching = false;  // 데이터 가져오기 완료
    },
    error: function () {
      showErrorMessage('댓글을 불러오는 데 실패했습니다. 잠시 후 다시 시도해주세요.')();
      isFetching = false;  // 에러 발생 시에도 상태를 완료로 변경
    }
  });
}

// 댓글 HTML 렌더링
function renderComment(comment) {
  const isAuthor = comment.user_idx == post.user_idx; // 댓글 작성자인지 확인
  const commentHtml = `
    <div class="comment-item mt-2 p-2 border rounded " id="comment-${comment.comment_idx}">
      <div class="align-items-center">
      	<strong>${comment.user_nickname}</strong>
      	<small class="text-muted ml-2 align-items-center">•${moment(comment.comment_date).fromNow()}</small>
    	</div>
    	<div class="mt-2 mb-2">
      	${comment.comment_text}
      </div>
      <div class="comment-actions d-flex" style="height:30px;">
        <!-- 업보트/다운보트 -->
          <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
					  <!-- 업보트 버튼 -->
					  <button type="button" id="comment_upVote"class="rounded-circle btn-comment-vote up-vote" onclick="voteComment(${comment.comment_idx}, 'upvote')">
					    <i class="fa-regular fa-thumbs-up"></i>
					  </button>
					  <!-- 추천수 -->
						<span id="vote-count-${comment.comment_idx}" class="mx-2">${comment.upvote_count - comment.downvote_count}</span>
					  <!-- 다운보트 버튼 -->
					  <button type="button" id="comment_downVote" class="rounded-circle btn-comment-vote down-vote" onclick="voteComment(${comment.comment_idx}, 'downvote')">
					    <i class="fa-regular fa-thumbs-down"></i>
					  </button>
					</div>
        <!-- 대댓글 버튼 -->
        <button class="btn btn-comment btn-sm mr-2" onclick="replyToComment(${comment.comment_idx})">
          <i class="fa fa-reply"></i> 댓글
        </button>
        ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="editComment(${comment.comment_idx}, '${comment.comment_text}')">수정</button>` : ''}
        ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="deleteComment(${comment.comment_idx}, '${comment.comment_text}')">삭제</button>` : ''}
      </div>
      <div class="reply-section" id="reply-section-${comment.comment_idx}">
        <!-- 대댓글 목록이 여기에 추가됩니다 -->
      </div>
    </div>
  `;
  $('#comments-list').append(commentHtml);
}

// 댓글 추가
function addComment() {
  const postId = post.community_idx;
  const commentText = $('#new-comment-text').val();
  const userId = post.user_idx;

  if (!commentText) {
    alert('댓글을 입력해주세요.');
    return;
  }

  if (!userId) {
    alert('유효한 사용자 ID가 필요합니다.');
    return;
  }

  $.ajax({
    url: `${rootPath}comments`,
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({ post_id: postId, comment_text: commentText, user_idx: userId }),
    success: function (response) {
      if (response.success) {
        $('#new-comment-text').val(''); // 입력창 비우기
        $('#comments-list').empty(); // 댓글 목록 초기화
        offset = 0;  // 초기 오프셋으로 재설정
        fetchComments(0, 10); // 첫 페이지 댓글 목록 다시 로드
      } else {
        console.error('댓글을 추가하는 데 실패했습니다. 서버 메시지: ' + response.message);
      }
    },
    error: function () {
      showErrorMessage('댓글을 작성하는 데 문제가 발생했습니다. 잠시 후 다시 시도해주세요.')();
    }
  });
}

// 댓글 수정 버튼 클릭 시 호출되는 함수
function editComment(commentId, originalText) {
  const editFormHtml = `
    <div class="edit-form mt-2">
      <textarea class="form-control mb-2" id="edit-text-${commentId}" placeholder="댓글을 수정하세요.">${originalText}</textarea>
      <button class="btn btn-primary btn-sm" onclick="saveEditedComment(${commentId})">저장</button>
      <button class="btn btn-secondary btn-sm" onclick="cancelEdit(${commentId}, '${originalText}')">취소</button>
    </div>
  `;
  $(`#comment-${commentId}`).html(editFormHtml);
}

// 수정된 댓글 저장
function saveEditedComment(commentId) {
  const editedText = $(`#edit-text-${commentId}`).val();
  const currentUserId = post.user_idx;
	
	if (!editedText || commentId == null) {
    alert('댓글 내용이나 ID가 올바르지 않습니다.');
    return;
  }
  
  if (!currentUserId || isNaN(currentUserId)) {
    alert('유효한 사용자 ID가 필요합니다. 다시 로그인해주세요.');
    return;
  }
  
  $.ajax({
    url: `${rootPath}comments/${commentId}`,
    type: 'PUT',
    contentType: 'application/json',
    data: JSON.stringify({
      comment_text: editedText,
      currentUserId: currentUserId
    }),
    success: function (response) {
      if (response.success && response.updatedComment) {
        const updatedComment = response.updatedComment;
        const isAuthor = updatedComment.author;
				
        if (updatedComment) {
          // 댓글 영역을 원래의 텍스트 보기 상태로 변경
          $(`#comment-${commentId}`).html(`
            <div class="align-items-center">
              <strong>${updatedComment.user_nickname}</strong>
              <small class="text-muted ml-2 align-items-center">•${moment(updatedComment.comment_date).fromNow()}</small>
            </div>
            <div class="mt-2 mb-2">
              ${updatedComment.comment_text}
            </div>
            <div class="comment-actions d-flex" style="height:30px;">
              <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
                <button type="button" id="comment_upVote" class="rounded-circle btn-comment-vote up-vote" onclick="voteComment(${updatedComment.comment_idx}, 'upvote')">
                  <i class="fa-regular fa-thumbs-up"></i>
                </button>
                <span id="vote-count-${updatedComment.comment_idx}" class="mx-2">${updatedComment.upvote_count - updatedComment.downvote_count}</span>
                <button type="button" id="comment_downVote" class="rounded-circle btn-comment-vote down-vote" onclick="voteComment(${updatedComment.comment_idx}, 'downvote')">
                  <i class="fa-regular fa-thumbs-down"></i>
                </button>
              </div>
              <button class="btn btn-comment btn-sm mr-2" onclick="replyToComment(${updatedComment.comment_idx})">
                <i class="fa fa-reply"></i> 댓글
              </button>
              ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="editComment(${updatedComment.comment_idx}, '${updatedComment.comment_text}')">수정</button>` : ''}
              ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="deleteComment(${updatedComment.comment_idx}, '${updatedComment.comment_text}')">삭제</button>` : ''}
            </div>
          `);
        } else {
          alert('댓글 업데이트 데이터가 잘못되었습니다. 서버 응답을 확인하세요.');
        }
      } else {
        alert('댓글 수정에 실패했습니다. 서버 메시지: ' + response.message);
      }
    },
    error: function () {
      alert('댓글 수정에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  });
}


// 댓글 수정 취소
function cancelEdit(commentId) {
   // 수정 취소 시 원래의 댓글 텍스트를 다시 표시
  $(`#comment-${commentId}`).html(`
    <div class="align-items-center">
      	<strong>${comment.user_nickname}</strong>
      	<small class="text-muted ml-2 align-items-center">•${moment(comment.comment_date).fromNow()}</small>
    	</div>
    	<div class="mt-2 mb-2">
      	${comment.comment_text}
      </div>
      <div class="comment-actions d-flex" style="height:30px;">
        <!-- 업보트/다운보트 -->
          <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
					  <!-- 업보트 버튼 -->
					  <button type="button" id="comment_upVote"class="rounded-circle btn-comment-vote up-vote" onclick="voteComment(${comment.comment_idx}, 'upvote')">
					    <i class="fa-regular fa-thumbs-up"></i>
					  </button>
					  <!-- 추천수 -->
						<span id="vote-count-${comment.comment_idx}" class="mx-2">${comment.upvote_count - comment.downvote_count}</span>
					  <!-- 다운보트 버튼 -->
					  <button type="button" id="comment_downVote" class="rounded-circle btn-comment-vote down-vote" onclick="voteComment(${comment.comment_idx}, 'downvote')">
					    <i class="fa-regular fa-thumbs-down"></i>
					  </button>
					</div>
        <!-- 대댓글 버튼 -->
        <button class="btn btn-comment btn-sm mr-2" onclick="replyToComment(${comment.comment_idx})">
          <i class="fa fa-reply"></i> 댓글
        </button>
        ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="editComment(${comment.comment_idx}, '${comment.comment_text}')">수정</button>` : ''}
        ${isAuthor ? `<button class="btn btn-comment btn-sm mr-2" onclick="deleteComment(${comment.comment_idx}, '${comment.comment_text}')">삭제</button>` : ''}
      </div>
  `);
}

// 댓글 삭제
function deleteComment(commentId) {

  if (confirm("댓글을 삭제하시겠습니까?")) {
    $.ajax({
      url: `${rootPath}comments/${commentId}`,
      type: 'DELETE',
      success: function (response) {
        if (response.success) {
           $(`#comment-${commentId}`).remove();		// 댓글 삭제 후 UI에서 해당 댓글을 제거
        } else {
          alert('댓글 삭제에 실패했습니다. 서버 메시지: ' + response.message);
        }
      },
      error: function () {
        alert('댓글 삭제에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
      }
    });
  }
}

// 댓글 투표 (업보트/다운보트)
function voteComment(commentId, voteType) {
  const rootPath = root;
  const userId = post.user_idx;
	
	if (!userId) {
    console.error('User ID가 존재하지 않습니다. 로그인 여부를 확인해주세요.');
    return;
  }
	
  $.ajax({
    url: `${rootPath}comments/vote/${commentId}/${voteType}`, // 서버에 보낼 URL
    type: 'POST',
    data: { userId: userId },
    success: function (response) {
      if (response.success) {
        updateVoteCount(commentId, voteType);
      } else {
        alert('투표에 실패했습니다. 서버 메시지: ' + response.message);
      }
    },
    error: function () {
      alert('투표에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  });
}

// 투표 수 업데이트
function updateVoteCount(commentId, voteType) {
  // 현재 업보트 및 다운보트 수를 가져옴
  const voteCountElement = $(`#vote-count-${commentId}`);
  let currentVoteCount = parseInt(voteCountElement.text());

  // 투표 타입에 따라 카운트 업데이트
  if (voteType === 'upvote') {
    currentVoteCount += 1;
  } else if (voteType === 'downvote') {
    currentVoteCount -= 1;
  }

  // 업데이트된 값을 다시 설정
  voteCountElement.text(currentVoteCount);
}

// 대댓글 작성 폼 표시
function replyToComment(commentId) {
  const replyFormHtml = `
    <div class="reply-form mt-2">
      <textarea class="form-control mb-2" id="reply-text-${commentId}" placeholder="상대에게 상처주는 말은 피해주세요."></textarea>
      <button class="btn btn-primary btn-sm" onclick="addReply(${commentId})">댓글 달기</button>
      <button class="btn btn-secondary btn-sm" onclick="cancelReply(${commentId})">취소</button>
    </div>
  `;
  $(`#reply-section-${commentId}`).html(replyFormHtml);
}

// 대댓글 추가
function addReply(commentId) {
  const replyText = $(`#reply-text-${commentId}`).val();
  const rootPath = root;

  $.ajax({
    url: `${rootPath}comments/reply`,
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({
      parent_comment_idx: commentId,
      comment_text: replyText,
      user_idx: post.user_idx,
      post_id: post.community_idx
    }),
    success: function (response) {
      if (response.success) {
        fetchComments(0, $('#comments-list .comment-item').length); // 현재 페이지 댓글 목록 갱신
      } else {
        alert('대댓글 작성에 실패했습니다. 서버 메시지: ' + response.message);
      }
    },
    error: function () {
      alert('대댓글 작성에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  });
}

// 대댓글 작성 취소
function cancelReply(commentId) {
  $(`#reply-section-${commentId}`).empty();
}



// 오류 메시지 표시
function showErrorMessage(message) {
  return function () {
    $('#error-message').text(message).show();
  };
}
