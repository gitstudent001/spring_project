$(document).ready(function () {
  const categories = {
	  text: [
	    { value: 'free', text: '자유' },
	    { value: 'question', text: '질문' }
	  ],
	  image: [
	    { value: 'free', text: '자유' },
	    { value: 'question', text: '질문' },
	    { value: 'review', text: '리뷰' }
	  ],
	  ranking: [{ value: 'my_ranking', text: '마이랭킹' }],
	  promotion: [{ value: 'promotion', text: '가게홍보' }]
	};
	
  // 드롭다운 메뉴 초기화 함수
  function initializeDropdowns() {
    document.querySelectorAll('.category-dropdown').forEach(function (dropdown) {
      const categoryMenu = dropdown.querySelector('.dropdown-menu');
      const selectedCategory = dropdown.querySelector('.selectedCategory');
      const hiddenCategoryInput = dropdown.closest('form').querySelector('input[name="community_category"]');

      // 드롭다운 클릭 시 메뉴 표시/숨기기
      dropdown.addEventListener('click', function (e) {
        e.stopPropagation();
        document.querySelectorAll('.dropdown-menu').forEach(function (menu) {
          if (menu !== categoryMenu) {
            menu.style.display = 'none';
          }
        });
        categoryMenu.style.display = categoryMenu.style.display === 'block' ? 'none' : 'block';
      });

      // 메뉴 항목 클릭 시 선택 처리
      categoryMenu.addEventListener('click', function (e) {
        e.stopPropagation();
        if (e.target.tagName === 'LI') {
          selectedCategory.textContent = e.target.textContent;
          hiddenCategoryInput.value = e.target.getAttribute('data-value');
          categoryMenu.style.display = 'none';
        }
      });
    });

    // 메뉴 외부 클릭 시 모든 드롭다운 메뉴 숨기기
    document.addEventListener('click', function () {
      document.querySelectorAll('.dropdown-menu').forEach(function (menu) {
        menu.style.display = 'none';
      });
    });
  }
  
  // 탭 및 드롭다운 초기화 함수
  function initializeTabs() {
    const hiddenTypeInput = $('#hidden_type');
    const activeTab = $('#postTypeTabs .nav-link.active');
    const initialTabId = activeTab.length > 0 ? activeTab.attr('href').replace('#', '').toUpperCase() : 'TEXT';
    
		updateCategoryOptions(initialTabId);
    setHiddenTypeValue(initialTabId);

    $('#postTypeTabs a').on('click', function (e) {
      e.preventDefault();
      $(this).tab('show');

      const selectedTabId = $(this).attr('href').replace('#', '').toUpperCase();
      console.log("Selected Tab:", selectedTabId);
      setHiddenTypeValue(selectedTabId);
      updateCategoryOptions(selectedTabId);
    });

    function setHiddenTypeValue(tabId) {
      const hiddenInput = $(`#hidden_type_${tabId.toLowerCase()}`);
      if (hiddenInput.length) {
        hiddenInput.val(tabId);
      }
    }

		function updateCategoryOptions(tabId) {
		  const dropdownMenu = $('.category-dropdown .dropdown-menu');
		  const categoryDropdown = $('.category-dropdown .selectedCategory');
		  const hiddenCategoryInput = $('input[name="community_category"]');
		
		  // 카테고리 목록을 가져옵니다.
		  const categoryList = categories[tabId.toLowerCase()];
		
		  if (!categoryList) {
		    console.warn(`categories[${tabId}]가 정의되지 않았습니다.`);
		    return;
		  }
		
		  // 드롭다운 항목을 업데이트합니다.
		  dropdownMenu.hide().empty().html(
		    categoryList.map(category => `<li data-value="${category.value}">${category.text}</li>`).join('')
		  );
		
	    // 선택된 카테고리를 유지하거나 첫 번째 항목을 기본값으로 선택
		  const currentCategory = hiddenCategoryInput.val() || categoryList[0].value;
		  let selectedCategory = categoryList.find(category => category.value === currentCategory);
		
		  // 선택된 카테고리가 없으면 첫 번째 카테고리로 기본값 설정
		  if (!selectedCategory) {
		    selectedCategory = categoryList[0];
  		}
		
		  // 드롭다운과 hidden input 업데이트
		  categoryDropdown.text(selectedCategory.text);
		  hiddenCategoryInput.val(selectedCategory.value);
		
		}
  }

  // 랭킹 항목 드래그 앤 드롭 및 옵션 추가/제거 초기화
  function initializeRankingOptions() {
    const rankingOptions = $('#ranking-options');

    rankingOptions.sortable({
      handle: '.sortable-handle',
      placeholder: 'ui-state-highlight',
      update: updateOptionIndexes
    });

    $('#add-option').on('click', function () {
      const newOptionIndex = rankingOptions.children().length + 1;
      rankingOptions.append(`
        <li>
          <div class="form-group centered-content">
            <i class="fas fa-bars sortable-handle"></i>
            <label class="option-label">${newOptionIndex}.</label>
            <input type="text" class="form-control" name="options" placeholder="Option ${newOptionIndex}" required />
            <button type="button" class="btn btn-danger btn-sm remove-option"><i class="fas fa-trash"></i></button>
          </div>
        </li>
      `);
      updateOptionIndexes();
    });

    rankingOptions.on('click', '.remove-option', function () {
      $(this).closest('li').remove();
      updateOptionIndexes();
    });

    updateOptionIndexes();
  }

  // 옵션 인덱스를 업데이트하는 함수
  function updateOptionIndexes() {
    $('#ranking-options li').each(function (index) {
      $(this).find('.option-label').text(`${index + 1}.`);
      $(this).find('input').attr('placeholder', `Option ${index + 1}`);
    });
  }

  // 드래그 앤 드롭 초기화 함수
  function initializeDragAndDrop() {
    const dropZoneInputs = document.querySelectorAll('.drop-zone__input');

    dropZoneInputs.forEach(inputElement => {
      const dropZoneElement = inputElement.closest('.drop-zone');

      dropZoneElement.addEventListener('click', (e) => {
        if (e.target !== inputElement) {
          e.preventDefault();
          inputElement.click();
        }
      });

      inputElement.addEventListener('change', () => {
        if (inputElement.files.length) updateThumbnail(dropZoneElement, inputElement.files[0]);
      });

      dropZoneElement.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZoneElement.classList.add('drop-zone--over');
      });

      ['dragleave', 'dragend'].forEach((type) => {
        dropZoneElement.addEventListener(type, () => {
          dropZoneElement.classList.remove('drop-zone--over');
        });
      });

      dropZoneElement.addEventListener('drop', (e) => {
        e.preventDefault();
        if (e.dataTransfer.files.length) {
          inputElement.files = e.dataTransfer.files;
          updateThumbnail(dropZoneElement, e.dataTransfer.files[0]);
        }
        dropZoneElement.classList.remove('drop-zone--over');
      });
    });

    function updateThumbnail(dropZoneElement, file) {
      let thumbnailElement = dropZoneElement.querySelector('.drop-zone__thumb');

      if (dropZoneElement.querySelector('.drop-zone__prompt')) {
        dropZoneElement.querySelector('.drop-zone__prompt').remove();
      }

      if (!thumbnailElement) {
        thumbnailElement = document.createElement('div');
        thumbnailElement.classList.add('drop-zone__thumb');
        dropZoneElement.appendChild(thumbnailElement);
      }

      if (file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          thumbnailElement.style.backgroundImage = `url('${reader.result}')`;
        };
      } else {
        thumbnailElement.style.backgroundImage = null;
      }
    }
  }
  
	// 에디터 초기화
	const editorElement = document.querySelector('#editor');
	if (editorElement) {
    const editor = initializeEditor(editorElement, communityText);
    bindEditorToForm(editor, 'form', '#hidden_text');
  }
	
	// 폼 유효성 검사 함수
	function validateForm() {
	  const hiddenCategoryInput = $('input[name="community_category"]');
	  const categoryDropdown = $('.category-dropdown .selectedCategory');
	  
	  // 카테고리 값이 비어 있으면 경고 및 폼 제출 중단
	  if (!hiddenCategoryInput.val()) {
	    console.warn('카테고리 입력이 비어 있습니다.');
	    alert('카테고리를 선택해주세요.');
	    return false;  // 카테고리 값이 없으면 폼을 제출하지 않음
	  }
	
	  // 탭 타입 가져오기
	  const activeTab = $('#postTypeTabs .nav-link.active').attr('href').replace('#', '').toUpperCase();
	  const hiddenTypeValue = $(`#hidden_type_${activeTab.toLowerCase()}`).val();
	
	  // 탭 타입이 설정되지 않았으면 경고 및 폼 제출 중단
	  if (!hiddenTypeValue) {
	    console.warn('탭 타입이 설정되지 않았습니다.');
	    alert('유효한 탭을 선택해주세요.');
	    return false;
	  }
	
	  return true;  // 모든 유효성 검사가 통과하면 true를 반환
	}


  // 초기화 함수 호출
  initializeDropdowns();
  initializeTabs();
  initializeRankingOptions();
  initializeDragAndDrop();
});
