let originalComments = {};
let isFetching = false;  // 현재 데이터를 가져오는 중인지 여부를 추적
let offset = 0;  // 시작 오프셋
const limit = 20;  // 한 번에 가져올 댓글 수
const root = rootPath;
let userNickname;
let noMoreData = false;  // 더 이상 데이터가 없는지 여부 추적

document.addEventListener("DOMContentLoaded", function () {
	
	tinymce.init({
    selector: '#comment-editor',
    menubar: false,
    plugins: 'image link code lists',
    toolbar: 'fontsize fontfamily| bold italic | alignleft aligncenter alignright | bullist numlist | image link | code',
    height: 300,
	  content_css: [
      'https://fonts.googleapis.com/css2?family=Dotum',
      'https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap',
 		 	'https://fonts.googleapis.com/css2?family=Nanum+Myeongjo&display=swap',
      'https://fonts.googleapis.com/css2?family=Noto+Sans+KR',
      'https://fonts.googleapis.com/css2?family=Poppins',
      'https://fonts.googleapis.com/css2?family=Montserrat',
      'https://fonts.googleapis.com/css2?family=Roboto'
    	],
	  font_family_formats: `
	    나눔고딕=Nanum Gothic;
	    나눔명조=Nanum Myeongjo;
	    돋움=Dotum;
	    Noto Sans KR=Noto Sans KR;
	    Roboto=Roboto,sans-serif;
	    Poppins=Poppins,sans-serif;
	    Montserrat=Montserrat,sans-serif;
	    Helvetica=Helvetica,Arial,sans-serif;
	    Verdana=Verdana,Geneva,sans-serif;
	    Times New Roman=times new roman,times,serif;
	    sans-serif
	  	`,
  	// 디폴트 폰트와 스타일을 지정하는 부분
  	content_style: `
	    body { 
	      font-family: 'Nanum Gothic', Noto Sans KR; 
	      font-size: 14pt; 
	    }
	    span { font-family: inherit ; }
  	`,
  
	  fontsize_formats: '8pt 10pt 12pt 14pt 16pt 18pt 20pt',
    setup: function (editor) {
      editor.on('init', function () {
        // TinyMCE 에디터의 부모 컨테이너를 숨깁니다.
        document.getElementById('comment-editor_ifr').style.display = 'none';
      });
    }
  });
	
	const textEditorBtn = document.getElementById('text-editor-btn');
  const imageEditorBtn = document.getElementById('image-editor-btn');
  const newCommentText = document.getElementById('new-comment-text');
  
  // 텍스트 에디터 토글 기능
  if (textEditorBtn) {
    textEditorBtn.addEventListener('click', toggleTextEditor);
  }
  
  // 이미지 에디터 토글 기능
  if (imageEditorBtn) {
    imageEditorBtn.addEventListener('click', toggleImageEditor);
  }

  // moment.js를 사용하여 상대 시간으로 변환
  if (typeof moment !== 'undefined') {
    const postDateElement = document.getElementById('postDate');
    if (postDateElement) {
      const createdAt = moment(postDateElement.dataset.date, "YYYY-MM-DDTHH:mm:ss");
      document.getElementById('timeAgo').textContent = createdAt.fromNow();
    }
  }

   // Toast UI Viewer 초기화
   initializeToastUIViewer();

   // 초기 댓글 목록 가져오기
   fetchComments(0, 20);
 
   // 스크롤 이벤트 추가 (무한 스크롤링)
   window.addEventListener('scroll', handleScroll);
   
});

function toggleTextEditor() {
  const editorIframe = document.getElementById('comment-editor_ifr');
  const newCommentText = document.getElementById('new-comment-text');
  if (editorIframe.style.display === 'none' || !editorIframe.style.display) {
    tinymce.get('comment-editor').show();
    editorIframe.style.display = 'block';
    newCommentText.style.display = 'none';
  } else {
    tinymce.get('comment-editor').hide();
    editorIframe.style.display = 'none';
    newCommentText.style.display = 'block';
  }
}

function toggleImageEditor() {
  const editorIframe = document.getElementById('comment-editor_ifr');
  const newCommentText = document.getElementById('new-comment-text');
  if (editorIframe.style.display === 'none' || !editorIframe.style.display) {
    tinymce.get('comment-editor').show();
    editorIframe.style.display = 'block';
    newCommentText.style.display = 'none';
    tinymce.activeEditor.insertContent('<img src="your-image-path.jpg" alt="이미지 설명" />');
  } else {
    tinymce.get('comment-editor').hide();
    editorIframe.style.display = 'none';
    newCommentText.style.display = 'block';
  }
}

function initializeToastUIViewer() {
  const viewerElement = document.getElementById('viewer');
  if (viewerElement) {
    new toastui.Editor.factory({
      el: viewerElement,
      initialValue: viewerElement.dataset.content
    });
  }
}

function handleScroll() {
  if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100 && !isFetching && !noMoreData) {
    offset += limit;
    fetchComments(offset, limit);
  }
}

function showLoader() {
  document.getElementById('loader').style.display = 'block';
}

function hideLoader() {
  document.getElementById('loader').style.display = 'none';
}

// 게시글 업다운보트
async function vote(type, postId) {
  try {
    const response = await axios.post(
      `${root}article/vote?id=${postId}&voteType=${type}`
    );

    if (response.data.success) {
      updatePostVoteCount(postId, response.data.newVoteCount);
    } else {
      showErrorMessage('Error: ' + response.data.message);
    }
  } catch (error) {
    console.error('투표 오류:', error);
    showErrorMessage('서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
  }
}

function updatePostVoteCount(postId, newVoteCount) {
  document.getElementById(`vote-count-${postId}`).textContent = newVoteCount;
  document.getElementById('top-vote-count').textContent = `추천 수: ${newVoteCount}`;
}

// 댓글 목록 가져오기 (페이징 적용)
async function fetchComments(offset, limit) {
  isFetching = true;
  showLoader();
  const postId = post.community_idx;

  try {
    const response = await axios.get(`${root}comments/post/${postId}/page?offset=${offset}&limit=${limit}`);
    let comments = response.data;

    if (comments && comments.length > 0) {
      // 부모 댓글을 시간 순으로 정렬 (오래된 순)
      comments.sort((a, b) => new Date(a.comment_date) - new Date(b.comment_date));

      const fragment = document.createDocumentFragment();
      const commentMap = new Map();

      // 먼저 모든 댓글을 맵에 저장
      comments.forEach(comment => {
        commentMap.set(comment.comment_idx, comment);
      });

      // 부모 댓글만 먼저 렌더링
      comments.filter(comment => !comment.parent_comment_idx).forEach(comment => {
        const commentElement = renderComment(comment);
        fragment.appendChild(commentElement);
        
        // 대댓글 렌더링
        const replies = comments.filter(reply => reply.parent_comment_idx === comment.comment_idx);
        if (replies.length > 0) {
          // 대댓글을 시간 순으로 정렬 (오래된 순)
          replies.sort((a, b) => new Date(a.comment_date) - new Date(b.comment_date));
          const replySection = commentElement.querySelector('.reply-section');
          replies.forEach(reply => {
            const replyElement = renderComment(reply, true);
            replySection.appendChild(replyElement);
          });
        }
      });

      // 기존 댓글 목록을 지우고 새로운 댓글들을 추가
      const commentsList = document.getElementById('comments-list');
      if (offset === 0) {
        commentsList.innerHTML = '';
      }
      commentsList.appendChild(fragment);
    } else if (offset === 0) {
      document.getElementById('comments-list').innerHTML = '<p class="text-muted">댓글이 없습니다.</p>';
    } else {
      noMoreData = true;
    }
  } catch (error) {
    console.error('댓글을 불러오는 데 실패했습니다:', error);
    showErrorMessage('댓글을 불러오는 데 실패했습니다. 잠시 후 다시 시도해주세요.');
  } finally {
    hideLoader();
    isFetching = false;
  }
}

// 댓글 HTML 렌더링
function renderComment(comment, isReply = false) {
  const commentDiv = document.createElement('div');
  commentDiv.className = `comment-item mt-2 p-2 border rounded ${isReply ? 'ml-4' : ''}`;
  commentDiv.id = `comment-${comment.comment_idx}`;
  commentDiv.innerHTML = generateCommentHTML(comment, isReply);
  return commentDiv;
}

function generateCommentHTML(comment, isReply = false) {
  const isAuthor = comment.user_idx == post.user_idx;
  let sanitizedCommentText = DOMPurify.sanitize(comment.comment_text || '', {
    ALLOWED_TAGS: ['p', 'span', 'strong', 'em', 'u', 'ol', 'ul', 'li', 'br'],
    ALLOWED_ATTR: ['style']
  });
  sanitizedCommentText = sanitizedCommentText.replace(/font-family:\s*'([^']+)'/g, function(match, fontName) {
    fontName = fontName.replace(/['"""]/g, '').trim();
    return `font-family: ${fontName}, sans-serif`;
  });
  return `
    <div class="align-items-center">
      ${isReply ? '<span class="badge badge-secondary mr-1">답글</span>' : ''}
      <strong>${escapeHtml(comment.user_nickname)}</strong>
      <small class="text-muted ml-2 align-items-center">•${moment(comment.comment_date).fromNow()}</small>
    </div>
    <div class="mt-2 mb-2">
      ${sanitizedCommentText}
    </div>
    <div class="comment-actions d-flex" style="height:30px;">
      <div class="btn-fills d-flex justify-content-between align-items-center mr-3">
        <button type="button" class="rounded-circle btn-comment-vote up-vote" onclick="voteComment(${comment.comment_idx}, 'upvote')">
          <i class="fa-regular fa-thumbs-up"></i>
        </button>
        <span id="vote-count-${comment.comment_idx}" class="mx-2">${comment.upvote_count - comment.downvote_count}</span>
        <button type="button" class="rounded-circle btn-comment-vote down-vote" onclick="voteComment(${comment.comment_idx}, 'downvote')">
          <i class="fa-regular fa-thumbs-down"></i>
        </button>
      </div>
      ${!isReply ? `
        <button class="btn btn-comment btn-sm mr-2" onclick="toggleReplyForm(${comment.comment_idx})">
          <i class="fa fa-reply"></i> 답글
        </button>
      ` : ''}
      ${isAuthor ? `
        <button class="btn btn-comment btn-sm mr-2" onclick="editComment(${comment.comment_idx})">수정</button>
        <button class="btn btn-comment btn-sm mr-2" onclick="deleteComment(${comment.comment_idx})">삭제</button>
      ` : ''}
    </div>
    ${!isReply ? `
      <div class="reply-form-container" id="reply-form-container-${comment.comment_idx}" style="display: none;"></div>
      <div class="reply-section" id="reply-section-${comment.comment_idx}"></div>
    ` : ''}
  `;
}

// 대댓글 폼 토글 함수
function toggleReplyForm(commentId) {
  const replyFormContainer = document.getElementById(`reply-form-container-${commentId}`);
  const isFormVisible = replyFormContainer.style.display !== 'none';

  if (isFormVisible) {
    replyFormContainer.style.display = 'none';
    replyFormContainer.innerHTML = '';
  } else {
    replyFormContainer.style.display = 'block';
    replyFormContainer.innerHTML = `
      <div class="mt-2">
        <textarea class="form-control" id="reply-text-${commentId}" rows="3" placeholder="답글을 입력하세요..."></textarea>
        <button class="btn btn-primary btn-sm mt-2" onclick="addReply(${commentId})">답글 작성</button>
        <button class="btn btn-secondary btn-sm mt-2" onclick="toggleReplyForm(${commentId})">취소</button>
      </div>
    `;
  }
}

// 특수 문자를 이스케이프하는 함수
function escapeHtml(unsafe) {
	if (!unsafe) return '';
  return unsafe
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

// 댓글 추가
async function addComment() {
  const postId = post.community_idx;
  const userId = post.user_idx;
  
  let commentContent = tinymce.get('comment-editor').getContent().trim();
  if (!commentContent) {
    commentContent = document.getElementById('new-comment-text').value.trim();
  }
  
  if (!commentContent) {
    showErrorMessage('댓글을 입력해주세요.');
    return;
  }

  if (!userId) {
    showErrorMessage('유효한 사용자 ID가 필요합니다.');
    return;
  }

  const commentData = {
    post_id: postId,
    user_idx: userId,
    parent_comment_idx: null,
    comment_text: commentContent,
    comment_level: 0
  };
  
  const optimisticComment = {
    ...commentData,
    comment_idx: 'temp_' + Date.now(),
    user_nickname: window.currentUserNickname || '익명',
    comment_date: new Date().toISOString(),
    is_deleted: 'N',
    is_hidden: 'N',
    upvote_count: 0,
    downvote_count: 0
  };
  
  const tempCommentElement = renderComment(optimisticComment);
  document.getElementById('comments-list').prepend(tempCommentElement);
  document.getElementById('new-comment-text').value = '';
  tinymce.get('comment-editor').setContent('');
  updateCommentCount(1);

  try {
    const response = await axios.post(`${root}comments`, commentData);
    if (response.data && response.data.success) {
      const serverComment = response.data.comment;
      const actualCommentElement = renderComment(serverComment);
      const commentsList = document.getElementById('comments-list');
      commentsList.appendChild(actualCommentElement);
      // 임시 요소 제거
      tempCommentElement.remove();
      // 새 댓글로 스크롤
      actualCommentElement.scrollIntoView({ behavior: 'smooth', block: 'center' })
      // 시각적 피드백 추가
      actualCommentElement.style.backgroundColor = '#f0f8ff';
      setTimeout(() => {
        actualCommentElement.style.backgroundColor = '';
      }, 3000);
    } else {
      throw new Error(response.data.message || '서버에서 오류 응답을 받았습니다.');
    }
  } catch (error) {
    tempCommentElement.remove();
    updateCommentCount(-1);
    
    let errorMessage = '댓글을 작성하는 데 문제가 발생했습니다. ';
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage += error.response.data.message;
    } else {
      errorMessage += '잠시 후 다시 시도해주세요.';
    }
    showErrorMessage(errorMessage);
  }
}

// 댓글 수정 버튼 클릭 시 호출되는 함수
async function editComment(commentId) {
  try {
    const comment = await getCommentData(commentId);
    if (!comment) return;

    const editFormHtml = `
      <div class="edit-form mt-2">
        <textarea class="form-control mb-2" id="edit-text-${comment.comment_idx}">${escapeHtml(comment.comment_text)}</textarea>
        <button class="btn btn-primary btn-sm" onclick="saveEditedComment(${comment.comment_idx})">저장</button>
        <button class="btn btn-secondary btn-sm" onclick="cancelEdit(${comment.comment_idx})">취소</button>
      </div>
    `;
    document.getElementById(`comment-${comment.comment_idx}`).innerHTML = editFormHtml;
  } catch (error) {
    showErrorMessage('댓글 수정 준비 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
  }
}

// 수정된 댓글 저장 (Optimistic UI 적용)
async function saveEditedComment(commentId) {
  const editedText = document.getElementById(`edit-text-${commentId}`).value;
  const currentUserId = post.user_idx;
  
  if (!editedText || commentId == null) {
    showErrorMessage('댓글 내용이나 ID가 올바르지 않습니다.');
    return;
  }
  
  if (!currentUserId || isNaN(currentUserId)) {
    showErrorMessage('유효한 사용자 ID가 필요합니다. 다시 로그인해주세요.');
    return;
  }

  const optimisticComment = {
    comment_idx: commentId,
    comment_text: editedText,
    user_idx: currentUserId,
  };

  updateCommentContent(optimisticComment);
  
  try {
    const response = await axios.put(`${root}comments/${commentId}`, {
      comment_text: editedText,
      currentUserId: currentUserId
    });

    if (response.data.success && response.data.updatedComment) {
      updateCommentContent(response.data.updatedComment);
    } else {
      cancelEdit(commentId);
      showErrorMessage('댓글 수정에 실패했습니다. 서버 메시지: ' + response.data.message);
    }
  } catch (error) {
    cancelEdit(commentId);
    showErrorMessage('댓글 수정에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
  }
}

// 댓글 수정 취소
async function cancelEdit(commentId) {
  try {
    const comment = await getCommentData(commentId);
    updateCommentContent(comment);
  } catch (error) {
    showErrorMessage('댓글 수정 취소 중 오류가 발생했습니다.');
  }
}

// 댓글 삭제 (Optimistic UI 적용)
async function deleteComment(commentId) {
  if (confirm("댓글을 삭제하시겠습니까?")) {
    const commentElement = document.getElementById(`comment-${commentId}`);
    commentElement.style.display = 'none';
    
    try {
      const response = await axios.delete(`${root}comments/${commentId}`);
      
      if (response.data.success) {
        commentElement.remove();
        updateCommentCount(-1);
      } else {
        commentElement.style.display = '';
        showErrorMessage('댓글 삭제에 실패했습니다. 서버 메시지: ' + response.data.message);
      }
    } catch (error) {
      commentElement.style.display = '';
      showErrorMessage('댓글 삭제에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
    }
  }
}

// 댓글 투표 (업보트/다운보트) (Optimistic UI 적용)
async function voteComment(commentId, voteType) {
  const userId = post.user_idx;
  
  if (!userId) {
    showErrorMessage('로그인이 필요합니다.');
    return;
  }

  // Optimistic UI
  updateVoteCount(commentId, voteType);
  
  try {
    const response = await axios.post(
      `${rootPath}comments/vote/${commentId}/${voteType}?userId=${userId}`
    );

    if (!response.data.success) {
      updateVoteCount(commentId, voteType === 'upvote' ? 'downvote' : 'upvote');
      showErrorMessage('투표에 실패했습니다. 서버 메시지: ' + response.data.message);
    }
  } catch (error) {
    console.error('투표 오류:', error);
    updateVoteCount(commentId, voteType === 'upvote' ? 'downvote' : 'upvote');
    showErrorMessage('투표에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
  }
}

// 투표 수 업데이트
function updateVoteCount(commentId, voteType) {
  const voteCountElement = document.getElementById(`vote-count-${commentId}`);
  let currentVoteCount = parseInt(voteCountElement.textContent);

  if (voteType === 'upvote') {
    currentVoteCount += 1;
  } else if (voteType === 'downvote') {
    currentVoteCount -= 1;
  }

  voteCountElement.textContent = currentVoteCount;
}

// 대댓글 작성 폼 표시
function showReplyForm(commentId) {
  const replyFormHtml = `
    <div class="reply-form mt-2" id="reply-form-${commentId}">
      <textarea class="form-control mb-2" id="reply-text-${commentId}" placeholder="상대방을 존중하는 댓글을 작성해주세요."></textarea>
      <button class="btn btn-primary btn-sm" onclick="addReply(${commentId})">댓글 달기</button>
      <button class="btn btn-secondary btn-sm" onclick="hideReplyForm(${commentId})">취소</button>
    </div>
  `;
  document.getElementById(`reply-section-${commentId}`).innerHTML = replyFormHtml;
  document.getElementById(`reply-text-${commentId}`).focus();
}

// 대댓글 작성 폼 숨기기
function hideReplyForm(commentId) {
  const replyForm = document.getElementById(`reply-form-${commentId}`);
  if (replyForm) {
    replyForm.remove();
  }
}

/// 대댓글 추가 (Optimistic UI 적용)
async function addReply(commentId) {
  const replyText = document.getElementById(`reply-text-${commentId}`);
  if (!replyText) {
    showErrorMessage('답글 입력 필드를 찾을 수 없습니다.');
    return;
  }

  const replyTextValue = replyText.value.trim();
  const userId = post.user_idx;
  const postId = post.community_idx;

  if (!replyTextValue) {
    showErrorMessage('댓글 내용을 입력해주세요.');
    return;
  }

  if (!userId) {
    showErrorMessage('로그인이 필요합니다.');
    return;
  }

  const submitButton = document.querySelector(`#reply-form-container-${commentId} button.btn-primary`);
  if (!submitButton) {
    showErrorMessage('답글 제출 버튼을 찾을 수 없습니다.');
    return;
  }

  const originalButtonText = submitButton.textContent;
  submitButton.disabled = true;
  submitButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 처리중...';

  const optimisticReply = {
    comment_idx: 'temp_' + Date.now(),
    parent_comment_idx: commentId,
    comment_text: replyTextValue,
    user_idx: userId,
    user_nickname: window.currentUserNickname || '익명',
    upvote_count: 0,
    downvote_count: 0,
    comment_date: new Date()
  };

  const replyElement = renderComment(optimisticReply, true);
  const replySection = document.getElementById(`reply-section-${commentId}`);
  if (replySection) {
    replySection.appendChild(replyElement);
    toggleReplyForm(commentId);
  } else {
    showErrorMessage('답글을 추가할 섹션을 찾을 수 없습니다.');
    return;
  }

  try {
    const response = await axios.post(`${root}comments/reply`, {
      parent_comment_idx: commentId,
      comment_text: replyTextValue,
      user_idx: userId,
      post_id: postId,
      community_idx: post.community_idx
    });

    if (response.data && response.data.success) {
      const serverReply = response.data.comment || response.data.reply;
      if (serverReply && serverReply.comment_idx) {
        // 서버에서 받은 대댓글로 optimistic reply 교체
        const newReplyElement = renderComment(serverReply, true);
        replyElement.replaceWith(newReplyElement);
        updateCommentCount(1);
        
        // 새 대댓글로 스크롤
        newReplyElement.scrollIntoView({ behavior: 'smooth', block: 'center' });
        // 새 대댓글에 시각적 피드백 추가
        newReplyElement.style.backgroundColor = '#f0f8ff';  // 연한 파란색 배경
        setTimeout(() => {
          newReplyElement.style.backgroundColor = '';  // 1초 후 배경색 제거
        }, 1000);
      } else {
        throw new Error('서버 응답에 유효한 댓글 데이터가 없습니다.');
      }
    } else {
      throw new Error(response.data.message || '서버 오류가 발생했습니다.');
    }
  } catch (error) {
    console.error('대댓글 추가 중 오류 발생:', error);
    replyElement.remove();
    toggleReplyForm(commentId);
    replyText.value = replyTextValue;
    let errorMessage = '대댓글 작성 중 오류가 발생했습니다. ';
    if (error.response && error.response.data && error.response.data.message) {
      errorMessage += error.response.data.message;
    } else {
      errorMessage += error.message || '잠시 후 다시 시도해주세요.';
    }
    showErrorMessage(errorMessage);
  } finally {
    if (submitButton) {
      submitButton.disabled = false;
      submitButton.textContent = originalButtonText;
    }
  }
}

// 댓글 내용 업데이트 함수
function updateCommentContent(comment, isReply = false) {
  const commentElement = document.getElementById(`comment-${comment.comment_idx}`);
  if (commentElement) {
    commentElement.innerHTML = generateCommentHTML(comment, isReply);
  }
}

// 댓글 수 업데이트
function updateCommentCount(change) {
  const commentCountElements = document.querySelectorAll('.comment-count');

  if (commentCountElements.length > 0) {
    commentCountElements.forEach(element => {
      let currentCount = parseInt(element.textContent.replace(/[^0-9]/g, ''), 10);
      element.textContent = `댓글수: ${currentCount + change}`;
    });
  } 
}

// 오류 메시지 표시
function showErrorMessage(message) {
  const errorDiv = document.getElementById('error-message');
  errorDiv.textContent = message;
  errorDiv.classList.remove('d-none');
  errorDiv.classList.add('alert', 'alert-danger');
  setTimeout(() => {
    errorDiv.classList.add('d-none');
    errorDiv.classList.remove('alert', 'alert-danger');
  }, 5000);
}

async function getCommentData(commentId) {
  try {
    const response = await axios.get(`${root}comments/detail/${commentId}`);
    if (response.data) {
      return response.data; // CommentDTO를 직접 반환
    } else {
      throw new Error('댓글 데이터를 가져오는데 실패했습니다.');
    }
  } catch (error) {
    console.error('댓글 데이터를 가져오는데 실패했습니다:', error);
    showErrorMessage('댓글 데이터를 가져오는데 실패했습니다. 잠시 후 다시 시도해주세요.');
    return null;
  }
}

const PostDetailShareModal = {
  root: rootPath,

  init: function() {
    this.bindEvents();
  },

  bindEvents: function() {
    document.addEventListener('click', (event) => {
      if (event.target.classList.contains('shareBtn')) {
        event.preventDefault();
        event.stopPropagation();
        this.openShareModal(event);
      } else if (event.target.classList.contains('modal-share')) {
        this.closeModal(event.target);
      } else if (event.target.classList.contains('close')) {
        event.preventDefault();
        const modal = event.target.closest('.modal-share');
        this.closeModal(modal);
      }
    });

    document.addEventListener('click', (event) => {
      if (event.target.closest('.modal-content')) {
        event.stopPropagation();
      }
    });

    document.addEventListener('click', (event) => {
      if (event.target.id.startsWith('copyLinkBtn_')) {
        this.copyLink(event);
      }
    });
  },

  openShareModal: function(event) {
    const postId = event.target.getAttribute('data-post-id');
    const modal = document.getElementById(`shareModal_${postId}`);

    if (modal) {
      const mouseX = event.clientX;
      const mouseY = event.clientY;
      modal.style.visibility = 'hidden';
      modal.style.display = 'block';

      const modalContent = modal.querySelector('.modal-content');
      const modalWidth = modalContent.offsetWidth;
      const modalHeight = modalContent.offsetHeight;

      const viewportHeight = window.innerHeight;
      const viewportWidth = window.innerWidth;

      let topPosition = mouseY - modalHeight / 2;
      let leftPosition = mouseX - modalWidth / 2;

      topPosition = Math.max(10, Math.min(topPosition, viewportHeight - modalHeight - 10));
      leftPosition = Math.max(10, Math.min(leftPosition, viewportWidth - modalWidth - 10));

      modalContent.style.position = 'absolute';
      modalContent.style.top = `${topPosition}px`;
      modalContent.style.left = `${leftPosition}px`;

      modal.style.visibility = 'visible';
      modal.setAttribute('aria-hidden', 'false');
      modal.classList.add('show');

      this.setupShareButtons(modal, postId);

      console.log(`Modal shown for post ID: ${postId} at (${mouseX}, ${mouseY})`);
    } else {
      console.error('Modal not found for post:', postId);
    }
  },

  closeModal: function(modal) {
    modal.classList.remove('show');
    modal.style.display = 'none';
    console.log('Modal closed');
  },

  setupShareButtons: function(modal, postId) {
    const postUrl = `${window.location.origin}${this.root}board/post/${postId}`;

    const facebookBtn = modal.querySelector('button:nth-child(1)');
    const twitterBtn = modal.querySelector('button:nth-child(2)');
    const emailBtn = modal.querySelector('button:nth-child(3)');

    facebookBtn.onclick = () => this.shareFacebook(postUrl);
    twitterBtn.onclick = () => this.shareTwitter(postUrl);
    emailBtn.onclick = () => this.shareEmail(postUrl);
  },

  shareFacebook: function(url) {
    const facebookUrl = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(url)}`;
    window.open(facebookUrl, '_blank');
  },

  shareTwitter: function(url) {
    const twitterUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(url)}`;
    window.open(twitterUrl, '_blank');
  },

  shareEmail: function(url) {
    const subject = encodeURIComponent('게시물 공유');
    const body = encodeURIComponent(`이 링크를 확인하세요: ${url}`);
    window.location.href = `mailto:?subject=${subject}&body=${body}`;
  },

  copyLink: function(event) {
    const postId = event.target.id.split('_')[1];
    const baseUrl = window.location.origin;
    const postUrl = `${baseUrl}${this.root}board/post/${postId}`;

    navigator.clipboard.writeText(postUrl)
      .then(() => {
        alert('링크가 복사되었습니다.');
      })
      .catch(err => {
        console.error('링크 복사 실패:', err);
      });
  }
};

// 초기화 함수를 호출합니다.
document.addEventListener('DOMContentLoaded', function() {
  PostDetailShareModal.init();
});

