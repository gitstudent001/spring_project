// editPost.js
document.addEventListener("DOMContentLoaded", function() {
  // 탭 활성화
  let activeTabId = '';
  if (postType === 'TEXT') {
    activeTabId = '#text';
  } else if (postType === 'IMAGE') {
    activeTabId = '#image';
  } else if (postType === 'RANKING') {
    activeTabId = '#ranking';
  } else if (postType === 'PROMOTION') {
    activeTabId = '#promotion';
  }
  
  // 선택된 탭을 활성화
  if (activeTabId) {
    $('#postTypeTabs a[href="' + activeTabId + '"]').tab('show');
  }
	
	// 카테고리 드롭다운 초기화 (각 탭에 맞는 카테고리 선택)
  const categoryDropdown = document.querySelector('.selectedCategory');
  categoryDropdown.textContent = {
    'free': '자유',
    'question': '질문',
    'review': '리뷰',
    'ranking': '랭킹',
    'promotion': '프로모션'
  }[postCategory] || '자유';
  
  // 숨겨진 카테고리 값 설정
  const hiddenCategoryInput = document.querySelector('input[name="community_category"]');
  if (hiddenCategoryInput) {
    hiddenCategoryInput.value = postCategory;
  }
	
	const editorElement = document.querySelector('#editor');
	const editor = initializeEditor(editorElement, communityText);  // 에디터 초기화
	bindEditorToForm(editor, 'form', '#hidden_text');  // 폼 제출 시 에디터 내용 바인딩
	
//  // Toast UI Editor 초기화
//  const editorElement = document.querySelector('#editor');
//  if (editorElement) {
//    // 에디터가 이미 초기화된 경우, 재초기화하지 않음
//    if (!editor) {
//      editor = new toastui.Editor({
//        el: editorElement,
//        height: '400px',
//        initialEditType: 'wysiwyg',
//        previewStyle: 'vertical',
//      });
//
//      // 게시글 내용이 비어있지 않으면 설정
//      if (communityText && communityText.trim() !== '') {
//        editor.setHTML(communityText);  // HTML로 설정
//      }
//
//      console.log(communityText);
//
//      // 폼 제출 시 에디터 내용을 hidden 필드에 저장
//      document.querySelector('form').addEventListener('submit', function() {
//        if (editor) {
//          document.getElementById('hidden_text').value = editor.getHTML();  // 에디터 내용을 숨겨진 필드에 저장
//        }
//
//        // 디버깅: 폼 제출 시 hidden 필드 값 확인
//        console.log("Submitting form with content:", document.getElementById('hidden_text').value);
//      });
//    }
//  } 

  // 랭킹 옵션이 존재하면 인덱스 업데이트
  if (document.getElementById('ranking-options')) {
    updateOptionIndexes();
  }
  
  // 랭킹 옵션 인덱스 업데이트 함수 (이 함수를 정의해야 함)
  function updateOptionIndexes() {
    const rankingOptions = document.querySelectorAll('#ranking-options li');
    rankingOptions.forEach((option, index) => {
      // 옵션 번호 라벨을 업데이트
      const optionLabel = option.querySelector('.option-label');
      optionLabel.textContent = (index + 1) + '.';
    });
  }
	
  // 탭 변경 시 URL 해시 값도 변경되도록 처리
  $('#postTypeTabs a').on('click', function() {
    window.location.hash = this.hash;
  });
  
});


