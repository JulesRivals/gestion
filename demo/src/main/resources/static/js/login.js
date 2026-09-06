document.addEventListener("DOMContentLoaded", function () {
    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const btn = document.getElementById("login-btn");

    function checkInputs() {
        if (email && password && email.value.trim() !== "" && password.value.trim() !== "") {
            btn.disabled = false;
            btn.classList.add("active");
        } else {
            btn.disabled = true;
            btn.classList.remove("active");
        }
    }

    if (email) email.addEventListener("input", checkInputs);
    if (password) password.addEventListener("input", checkInputs);

    // Ne pas intercepter submit ici — laisser le navigateur faire le POST vers /login
});