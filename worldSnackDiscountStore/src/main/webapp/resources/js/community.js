$(document).ready(function() {
  // Moment.js를 사용하여 작성 날짜를 상대 시간으로 변환
  initializeMoment();

  // 무한 스크롤 기능 초기화
  initializeInfiniteScroll();

  function initializeMoment() {
    if (typeof moment === 'undefined') {
      console.error('Moment.js is not loaded.');
      return;
    }

    // Moment.js 로케일 설정을 한국어로
    moment.locale('ko');

    // 모든 postDate 요소를 찾습니다.
    $('.postDate').each(function() {
      var dateString = $(this).data('date');
      if (dateString) {
        // Moment.js를 사용하여 상대 시간으로 변환
        var createdAt = moment(dateString, "YYYY-MM-DDTHH:mm:ss");
        $(this).text(createdAt.fromNow());
      } else {
        console.error('Post date not found.');
      }
    });
  }

  function initializeInfiniteScroll() {
    var page = 1; // 시작 페이지
    var isLoading = false; // 데이터 로딩 중인지 여부
    var noMoreData = false; // 더 이상 데이터가 없을 때

    $(window).scroll(function() {
      if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
        if (!isLoading && !noMoreData) {
          isLoading = true; // 로딩 상태 설정
          page++; // 다음 페이지
          loadMoreData(page); // 데이터 로드
        }
      }
    });

    function loadMoreData(page) {
      $.ajax({
        url: `${root}board/communityData`, // 서버에서 데이터 불러올 URL
        type: 'GET',
        data: {
          page: page,
          category: `${param.category}`,
          sortOrder: `${param.sortOrder}`,
          viewType: `${param.viewType}`
        },
        beforeSend: function() {
          $('#loader').show(); // 로딩 스피너 표시
        },
        success: function(data) {
          if (data.trim().length > 0) { // 데이터가 있을 때
            $('#post-container').append(data); // 추가 데이터 렌더링
            initializeMoment(); // 새로 추가된 데이터에도 moment 적용
            
            // 썸네일 이미지 업데이트
            updateThumbnails();

            isLoading = false; // 로딩 상태 해제
          } else { // 데이터가 더 이상 없는 경우
            noMoreData = true; // 더 이상 로딩하지 않음
            $('#loader').hide(); // 로딩 스피너 숨기기
          }
        },
        error: function() {
          console.error('데이터를 불러오는 데 실패했습니다.');
        },
        complete: function() {
          $('#loader').hide(); // 로딩 스피너 숨기기
        }
      });
    }
  }
  
  // 썸네일을 업데이트하는 함수
  function updateThumbnails() {
	  $('.post-thumbnail').each(function() {
	    var thumbnailUrl = $(this).data('thumbnail');
	    if (thumbnailUrl && thumbnailUrl.trim() !== '') {
	      $(this).css('background-image', `url(${thumbnailUrl})`);
	    } else {
				console.log('Using default thumbnail for:', this);
	      $(this).css('background-image', `url(${defaultThumbnailUrl})`);
	    }
	  });
	}
  
  // 초기 썸네일 설정
  updateThumbnails();
});
