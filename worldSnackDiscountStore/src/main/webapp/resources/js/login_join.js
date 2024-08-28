const signUpBtn = document.getElementById("signUp");
const signInBtn = document.getElementById("signIn");
const container = document.querySelector(".container");

signUpBtn.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});
signInBtn.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});


// 에러가 있는 경우 자동으로 회원가입 양식을 보이게 함
window.addEventListener("load", () => {
  const errorMessage = document.getElementById("errorMessage");
  if (errorMessage) {
    container.classList.add("right-panel-active");
  }
});


