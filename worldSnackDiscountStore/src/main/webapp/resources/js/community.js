// community.js
(function() {
  'use strict';

  const CommunityApp = {
    root: rootPath,
    defaultThumbnailUrl: `${rootPath}images/default-thumbnail.png`,
    lastCommunityId: 0,
    isLoading: false,
    noMoreData: false,

    init: function() {
      this.initializeMoment();
      this.initializeInfiniteScroll();
      this.updateThumbnails();
      this.initializeShareButtons();
      this.initializeScrapButtons();
      this.bindEvents();
    },

    getCommData: function(postId) {
      return $.ajax({
        url: `${this.root}api/post/${postId}`,
        type: 'GET',
        dataType: 'json'
      }).then(function(response) {
        if (response.success) {
          return response.post;
        } else {
          throw new Error(response.message);
        }
      });
    },

    updateSortAndView: function() {
      const urlParams = new URLSearchParams(window.location.search);
      urlParams.set('sortOrder', $('#sortOrder').val());
      urlParams.set('viewType', $('#viewType').val());
      const newUrl = `${this.root}board/community?${urlParams.toString()}`;
      window.location.href = newUrl;
    },

    vote: function(type, postId) {
      $.ajax({
        url: `${this.root}article/vote`,
        type: 'POST',
        data: { id: postId, voteType: type },
        success: (response) => {
          if (response.success) {
            this.updateVoteCount(postId, response.newVoteCount);
          } else {
            alert(`Error: ${response.message}`);
          }
        },
        error: (xhr, status, error) => {
          console.error('AJAX Error:', status, error);
          console.error('Response Text:', xhr.responseText);
          alert('서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
      });
    },

    updateVoteCount: function(postId, newVoteCount) {
      $(`#vote-count-${postId}`).text(newVoteCount);
    },

    updateThumbnails: function() {
      $('.post-thumbnail').each(function () {
        const $this = $(this);
        const thumbnailUrl = $this.data('thumbnail');
        const $img = $this.find('img');

        if (thumbnailUrl && thumbnailUrl.trim() !== '' && $img.attr('src') !== thumbnailUrl) {
          $img.attr('src', thumbnailUrl);
        } else if ($img.attr('src') !== CommunityApp.defaultThumbnailUrl) {
          $img.attr('src', CommunityApp.defaultThumbnailUrl);
        }
      });
    },

    initializeShareButtons: function() {
		  // 공유 버튼 클릭 시 모달 열기
		  $(document).on('click', '.shareBtn', function (event) {
		    event.preventDefault();
		    event.stopPropagation();
		    
		    const postId = $(this).data('post-id');
		    const modal = $(`#shareModal_${postId}`);
		    
		   if (modal.length) {
			    // 마우스 클릭 위치를 가져오기 (스크롤이 포함된 전체 좌표)
			    const mouseX = event.clientX;
			    const mouseY = event.clientY;
			    modal.css({ visibility: 'hidden', display: 'block' });
			    
			    // modal-content의 너비와 높이를 가져오기
			    const modalContent = modal.find('.modal-content');
			    const modalWidth = modalContent.outerWidth();
			    const modalHeight = modalContent.outerHeight();
			    console.log('Modal height:', modalHeight);
			    
			    // 현재 브라우저의 뷰포트(화면) 크기 가져오기
			    const viewportHeight = $(window).height();
			    const viewportWidth = $(window).width();
			    
			    // modal-content의 위치를 마우스 클릭 위치로 설정, 모달 크기 반만큼 보정
			    // 화면 밖으로 나가지 않도록 상한/하한 값 적용
			    let topPosition = mouseY - modalHeight / 2;
			    let leftPosition = mouseX - modalWidth / 2;
			
			    // 모달이 화면 위로 넘어가지 않도록 보정
			    if (topPosition < 0) topPosition = 10; // 최소 상단 여백
			    if (topPosition + modalHeight > viewportHeight) {
			      topPosition = viewportHeight - modalHeight - 10; // 하단 여백 보정
			    }
			
			    // 모달이 화면 좌우로 넘어가지 않도록 보정
			    if (leftPosition < 0) leftPosition = 10; // 최소 좌측 여백
			    if (leftPosition + modalWidth > viewportWidth) {
			      leftPosition = viewportWidth - modalWidth - 10; // 우측 여백 보정
			    }
			    
			    modalContent.css({
			      position: 'absolute',
			      top: topPosition + 'px',
			      left: leftPosition + 'px'
			    });
			    modal.css({ visibility: 'visible', display: 'none' });
			    // 모달 표시
			    modal.attr('aria-hidden', 'false');
			    modal.addClass('show');
			    modal.css({ display: 'block' });
			    console.log(`Modal shown for post ID: ${postId} at (${mouseX}, ${mouseY})`);
			  } else {
			    console.error('Modal not found for post:', postId);
			  }
			});

		  // 모달 배경 클릭 시 모달 닫기
		  $(document).on('click', '.modal-share', function (event) {
		    if ($(event.target).hasClass('modal-share')) {
		      $(this).removeClass('show').hide();
		      console.log('Modal closed by clicking outside');
		    }
		  });
		
		  // 모달 내용 클릭 시 이벤트 전파 막기
		  $(document).on('click', '.modal-content', function (event) {
		    event.stopPropagation();
		  });
		
		  // 'X' 버튼 클릭 시 모달 닫기
		  $(document).on('click', '.modal-share .close', function(event) {
		    event.preventDefault();
		    $(this).closest('.modal-share').removeClass('show').hide();
		    console.log('Modal closed via close button');
		  });
		  
		  // 링크 복사 버튼 클릭 시 링크 복사
		  $(document).on('click', '[id^=copyLinkBtn_]', function () {
		    const postId = this.id.split('_')[1];
		    const baseUrl = window.location.origin;
		    const postUrl = `${baseUrl}${CommunityApp.root}board/post/${postId}`;
		    navigator.clipboard.writeText(postUrl).then(() => {
		      alert('링크가 복사되었습니다.');
		    }).catch(err => {
		      console.error('링크 복사 실패:', err);
		    });
		  });
		},


    initializeMoment: function() {
      if (typeof moment === 'undefined') {
        console.error('Moment.js is not loaded.');
        return;
      }
      moment.locale('ko');
      $('.postDate').each(function () {
        const $this = $(this);
        const dateString = $this.data('date');
        if (dateString) {
          const createdAt = moment(dateString, "YYYY-MM-DDTHH:mm:ss");
          $this.text(createdAt.fromNow());
        }
      });
    },

    initializeInfiniteScroll: function() {
      $(window).on('scroll', () => {
        if (!this.isLoading && !this.noMoreData && 
            $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
          this.loadMoreData();
        }
      });
    },

    loadMoreData: function() {
      if (this.isLoading || this.noMoreData) return;
      
      this.isLoading = true;
      const urlParams = new URLSearchParams(window.location.search);
      
      $.ajax({
        url: `${this.root}board/community/posts`,
        type: 'GET',
        data: {
          lastCommunityId: this.lastCommunityId,
          category: urlParams.get('category') || 'default',
          sortOrder: urlParams.get('sortOrder') || 'latest',
          viewType: urlParams.get('viewType') || 'compact'
        },
        beforeSend: () => {
          $('#loader').show();
        },
        success: (response) => {
					console.log('Scrap toggle response:', response);
          if (response.posts && response.posts.length > 0) {
            this.appendPosts(response.posts);
            this.lastCommunityId = response.posts[response.posts.length - 1].communityId;
          } else {
            this.noMoreData = true;
            $('#post-container').append('<p>더 이상 표시할 게시물이 없습니다.</p>');
          }
        },
        error: (jqXHR, textStatus, errorThrown) => {
          console.error("AJAX request failed: ", textStatus, errorThrown);
        },
        complete: () => {
          $('#loader').hide();
          this.isLoading = false;
        }
      });
    },

    appendPosts: function(posts) {
      const container = $('#post-container');
      const modalsContainer = $('body');
      
      posts.forEach(post => {
        this.getCommData(post.community_idx).then(postData => {
          const postHtml = this.createPostHtml(postData);
          container.append(postHtml);
          
          const shareModalHtml = this.createShareModal(post.community_idx);
          modalsContainer.append(shareModalHtml);
        }).catch(error => {
          console.error('Error fetching post data:', error);
        });
      });

      this.initializeMoment();
      this.updateThumbnails();
      this.initializeShareButtons();
    },

    createPostHtml: function(post) {
      return PostTemplates.postTemplate.replace(/\{\{(\w+)\}\}/g, (match, p1) => {
        if (p1 === 'vote_count') {
          return post.community_upvotes - post.community_downvotes;
        }
        return post.hasOwnProperty(p1) ? post[p1] : match;
      });
    },

    createShareModal: function(postId) {
      return PostTemplates.shareModalTemplate.replace(/\{\{community_idx\}\}/g, postId);
    },

  bindEvents: function() {
	$('#sortOrder, #viewType').on('change', () => this.updateSortAndView());
  
  // 투표 버튼 이벤트 핸들러
  $(document).on('click', '.btn-vote', (event) => {
    event.preventDefault();
    event.stopPropagation();
    const $button = $(event.currentTarget);
    const type = $button.data('vote-type');
    const postId = $button.data('post-id');
    this.vote(type, postId);
    });

  // 공유 버튼 이벤트 핸들러
  $(document).on('click', '.shareBtn', function(event) {
  event.preventDefault();
  event.stopPropagation();
  
  const postId = $(this).data('post-id');
  const modal = $(`#shareModal_${postId}`);
  
  if (modal.length) {
    const postUrl = `${window.location.origin}${CommunityApp.root}board/post/${postId}`;

    // 페이스북 공유
    modal.find('button:contains("페이스북")').off('click').on('click', function() {
      const facebookUrl = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(postUrl)}`;
      window.open(facebookUrl, '_blank');
    });

    // 트위터 공유
    modal.find('button:contains("트위터")').off('click').on('click', function() {
      const twitterUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(postUrl)}`;
      window.open(twitterUrl, '_blank');
    });

    // 이메일 공유
    modal.find('button:contains("이메일")').off('click').on('click', function() {
      const emailSubject = encodeURIComponent('게시물 공유');
      const emailBody = encodeURIComponent(`이 링크를 확인하세요: ${postUrl}`);
      window.location.href = `mailto:?subject=${emailSubject}&body=${emailBody}`;
    });

    // 모달 표시
    modal.addClass('show');
    modal.css({ display: 'block' });
	} else {
    console.error('Modal not found for post:', postId);
  	}
	});

  // 스크랩 버튼 이벤트 핸들러 (이벤트 위임 사용)
  $(document).on('click', '.btn-scrap', (event) => {
    event.preventDefault();
    event.stopPropagation();
    this.toggleScrap(event.currentTarget);
  });
    
  // 숨기기 버튼 이벤트 핸들러
  $(document).on('click', '.btn-hide', function(event) {
    event.preventDefault();
    event.stopPropagation();
    const postId = $(this).data('post-id');
    CommunityApp.hidePost(postId);
  });

  // 신고 버튼 이벤트 핸들러
  $(document).on('click', '.btn-report', function(event) {
    event.preventDefault();
    event.stopPropagation();
    const postId = $(this).data('post-id');
    CommunityApp.reportPost(postId);
  });

  // 확장 버튼 이벤트 핸들러
  $(document).on('click', '.btn-expand', function(event) {
    event.preventDefault();
    event.stopPropagation();
    const postId = $(this).data('post-id');
    console.log(`확장 기능: 게시물 ${postId}`);
    // 여기에 확장 기능 구현
  });

  // 게시물 클릭 이벤트 핸들러 (상세 페이지로 이동)
  $(document).on('click', '.compact-post', (event) => {
      if (!$(event.target).closest('.post-actions').length) {
        const postId = $(event.currentTarget).data('post-id');
        window.location.href = `${this.root}board/post/${postId}`;
      }
  });
},
	
	initializeScrapButtons: function() {
    console.log('Initializing scrap buttons...');
    $('.btn-scrap').each((index, button) => {
        const $button = $(button);
        const postId = $button.data('post-id');
        const userIdx = $button.data('user-idx');
        const isScraped = $button.attr('data-is-scraped') === 'true';

        console.log(`Post ${postId}: userIdx = ${userIdx}, isScraped = ${isScraped}`); // 로그 추가
        this.updateScrapButtonUI($button, isScraped);
    });
	},

  updateScrapButtonUI: function($button, isScraped) {
	  const postId = $button.data('post-id');
	  console.log(`Updating UI for post ${postId}: isScraped = ${isScraped}`);
	  if (isScraped) {
	      $button.html('<i class="fa-solid fa-bookmark"></i> 취소');
	  } else {
	      $button.html('<i class="fa-regular fa-bookmark"></i> 스크랩');
	  }
	  $button.data('is-scraped', isScraped);
	  $button.attr('data-is-scraped', isScraped); // Update the HTML attribute
	},
	
	// 스크랩 토글 함수 추가
	toggleScrap: function(button) {
    const $button = $(button);
    const postId = $button.data('post-id');
    const userIdx = $button.data('user-idx');
    const isScraped = $button.data('is-scraped') === 'true'; // 문자열 'true'를 boolean으로 변환

    console.log(`Toggling scrap for post ${postId}: current state = ${isScraped}`);

    if (!userIdx) {
        alert('로그인이 필요한 서비스입니다.');
        return;
    }

    $.ajax({
        url: `${this.root}board/scrap-async`,
        type: 'POST',
        data: {
          community_idx: postId,
          user_idx: userIdx
        },
        success: (response) => {
          console.log('Scrap toggle response:', response);
          if (response.success) {
              this.updateScrapButtonUI($button, response.scraped);
              alert(response.message);
          } else {
              alert('스크랩 처리 중 오류가 발생했습니다: ' + response.message);
          }
        },
        error: (xhr, status, error) => {
          console.error('AJAX Error:', status, error);
          console.error('Response Text:', xhr.responseText);
          alert('서버와의 통신 중 오류가 발생했습니다. 자세한 내용은 콘솔을 확인해주세요.');
        }
    	});
    },
	
	// 게시물 숨기기 함수 추가
	hidePost: function(postId) {
	  // 여기에 숨기기 기능 구현
	  console.log(`게시물 숨기기: ${postId}`);
	  // AJAX 요청을 통해 서버에 숨기기 요청
	  // 성공 시 UI에서 게시물 제거
	},
	
	// 게시물 신고 함수 추가
	reportPost: function(postId) {
	  // 여기에 신고 기능 구현
	  console.log(`게시물 신고: ${postId}`);
	  // 신고 모달 표시 또는 신고 페이지로 이동
	},
};

  // 전역 객체에 CommunityApp 추가
  window.CommunityApp = CommunityApp;

  // DOM이 로드된 후 초기화
  $(document).ready(() => {
	  CommunityApp.init();
	});

})();