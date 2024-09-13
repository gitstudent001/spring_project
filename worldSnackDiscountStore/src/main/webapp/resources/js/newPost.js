$(document).ready(function () {
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
    var urlParams = new URLSearchParams(window.location.search);
    var type = urlParams.get('type');
    if (type) {
      $('#postTypeTabs a[href="#' + type.toLowerCase() + '"]').tab('show');
    }

    // 탭 클릭 시 드롭다운 카테고리 동기화
    $('#postTypeTabs a').on('click', function (e) {
      e.preventDefault();
      $(this).tab('show');

      const selectedTabId = $(this).attr('href').replace('#', '');
      const categoryDropdown = $('.category-dropdown .selectedCategory');
      const hiddenCategoryInput = $('input[name="community_category"]');

      if (selectedTabId === 'text') {
        categoryDropdown.text('자유');
        hiddenCategoryInput.val('free');
      } else if (selectedTabId === 'image') {
        categoryDropdown.text('리뷰');
        hiddenCategoryInput.val('review');
      } else if (selectedTabId === 'ranking') {
        categoryDropdown.text('마이랭킹');
        hiddenCategoryInput.val('my_ranking');
      } else if (selectedTabId === 'promotion') {
        categoryDropdown.text('가게홍보');
        hiddenCategoryInput.val('promotion');
      }
    });
  }

  // 랭킹 항목 드래그 앤 드롭 및 옵션 추가/제거 초기화
  function initializeRankingOptions() {
    $("#ranking-options").sortable({
      handle: ".sortable-handle",
      placeholder: "ui-state-highlight",
      update: function () {
        updateOptionIndexes();
      }
    });

    function updateOptionIndexes() {
      $('#ranking-options li').each(function (index) {
        $(this).find('.option-label').text((index + 1) + '.');
        $(this).find('input').attr('placeholder', 'Option ' + (index + 1));
      });
    }

    $('#add-option').on('click', function () {
      var newOptionIndex = $('#ranking-options li').length + 1;
      $('#ranking-options').append(`
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

    $(document).on('click', '.remove-option', function () {
      $(this).closest('li').remove();
      updateOptionIndexes();
    });

    // 기본 인덱스 업데이트 실행
    updateOptionIndexes();
  }

  // 드래그 앤 드롭 초기화 함수
  function initializeDragAndDrop() {
    document.querySelectorAll(".drop-zone__input").forEach((inputElement) => {
      const dropZoneElement = inputElement.closest(".drop-zone");

      dropZoneElement.addEventListener("click", (e) => {
        if (e.target !== inputElement) {
          e.preventDefault();
          inputElement.click();
        }
      });

      inputElement.addEventListener("change", (e) => {
        if (inputElement.files.length) {
          updateThumbnail(dropZoneElement, inputElement.files[0]);
        }
      });

      dropZoneElement.addEventListener("dragover", (e) => {
        e.preventDefault();
        e.stopPropagation();
        dropZoneElement.classList.add("drop-zone--over");
      });

      ["dragleave", "dragend"].forEach((type) => {
        dropZoneElement.addEventListener(type, (e) => {
          e.preventDefault();
          e.stopPropagation();
          dropZoneElement.classList.remove("drop-zone--over");
        });
      });

      dropZoneElement.addEventListener("drop", (e) => {
        e.preventDefault();
        e.stopPropagation();

        if (e.dataTransfer.files.length) {
          inputElement.files = e.dataTransfer.files;
          updateThumbnail(dropZoneElement, e.dataTransfer.files[0]);
        }

        dropZoneElement.classList.remove("drop-zone--over");
      });
    });

    function updateThumbnail(dropZoneElement, file) {
      let thumbnailElement = dropZoneElement.querySelector(".drop-zone__thumb");

      if (dropZoneElement.querySelector(".drop-zone__prompt")) {
        dropZoneElement.querySelector(".drop-zone__prompt").remove();
      }

      if (!thumbnailElement) {
        thumbnailElement = document.createElement("div");
        thumbnailElement.classList.add("drop-zone__thumb");
        dropZoneElement.appendChild(thumbnailElement);
      }

      if (file.type.startsWith("image/")) {
        const reader = new FileReader();

        reader.readAsDataURL(file);
        reader.onload = () => {
          thumbnailElement.style.backgroundImage = `url('${reader.result}')`;
          thumbnailUrl = reader.result;
        };
      } else {
        thumbnailElement.style.backgroundImage = null;
        thumbnailUrl = '';
      }
    }
  }

  // Toast UI Editor 초기화
  let editor;
  function initializeEditor() {
    editor = new toastui.Editor({
      el: document.querySelector('#editor'),
      height: '400px',
      initialEditType: 'wysiwyg',
      previewStyle: 'vertical',
      placeholder: '글을 작성해주세요',
      toolbarItems: [
        ['heading', 'bold', 'italic', 'strike'],
        ['hr', 'quote'],
        ['ul', 'ol', 'task'],
        ['code', 'codeblock'],
        ['scrollSync'],
      ],
    });
  }

  // 폼 제출 시 에디터의 내용을 숨겨진 필드에 설정하는 함수
  window.handleFormSubmit = function() {
    try {
      if (!editor) {
        console.error('Editor not initialized');
        return false;
      }

      const editorContent = editor.getHTML();
      const hiddenTextElement = document.getElementById('hidden_text');

      if (hiddenTextElement) {
        hiddenTextElement.value = editorContent;
      } else {
        console.error('Hidden text element not found.');
        return false;
      }

      const activeTab = $('#postTypeTabs .active').attr('href').replace('#', '');
      const hiddenCategoryInput = $('input[name="community_category"]');

      if (activeTab === 'text') {
        hiddenCategoryInput.val('free');
      } else if (activeTab === 'image') {
        hiddenCategoryInput.val('review');
      } else if (activeTab === 'ranking') {
        hiddenCategoryInput.val('my_ranking');
      } else if (activeTab === 'promotion') {
        hiddenCategoryInput.val('promotion');
      }

      console.log('Editor Content:', editorContent); // 디버깅: 콘솔에 에디터 내용 출력
      return true;
    } catch (error) {
      console.error('Error during form submission:', error);
      return false; // 에러가 발생한 경우 폼 제출을 중단
    }
  };

  // 초기화 함수 호출
  initializeDropdowns();
  initializeTabs();
  initializeRankingOptions();
  initializeDragAndDrop();
  initializeEditor();
});
