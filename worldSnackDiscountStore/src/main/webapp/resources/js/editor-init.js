// editor-init.js
function initializeEditor(editorElement, initialContent = '') {
  // 에디터 초기화
  if (editorElement) {
    const editor = new toastui.Editor({
      el: editorElement,
      height: '400px',
      initialEditType: 'wysiwyg',
      previewStyle: 'vertical',
      toolbarItems: [
        ['heading', 'bold', 'italic', 'strike'],
        ['hr', 'quote'],
        ['ul', 'ol', 'task'],
        ['code', 'codeblock'],
        ['scrollSync']
      ]
    });

    // 에디터에 초기 내용 설정 (수정하는 게시글인 경우에 사용)
    if (initialContent && initialContent.trim() !== '') {
      editor.setHTML(initialContent);
    }

    return editor;
  }
  return null;
}

function bindEditorToForm(editor, formSelector, hiddenFieldSelector) {
  const form = document.querySelector(formSelector);
  const hiddenField = document.querySelector(hiddenFieldSelector);

  if (form && editor && hiddenField) {
    form.addEventListener('submit', function () {
      hiddenField.value = editor.getHTML();  // 에디터 내용을 숨겨진 필드에 저장
    });
  }
}
