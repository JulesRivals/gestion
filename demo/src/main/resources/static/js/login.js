
document.addEventListener("DOMContentLoaded", function () {

    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const btn = document.getElementById("login-btn");
    const errorMsg = document.getElementById("error-msg");

    /* Activer bouton si champs remplis */
    function checkInputs() {
        if (email.value.trim() !== "" && password.value.trim() !== "") {
            btn.disabled = false;
            btn.classList.add("active");
        } else {
            btn.disabled = true;
            btn.classList.remove("active");
        }
    }

    email.addEventListener("input", checkInputs);
    password.addEventListener("input", checkInputs);

    /* Fonction login */
    function handleLogin(event) {
        event.preventDefault();

        // mot de passe test
        if (password.value !== "1234") {
            errorMsg.textContent = "Mot de passe incorrect";
            return;
        }

        errorMsg.textContent = "";
        alert("Connexion réussie !");
    }

    /* Lier le bouton ou le formulaire */
    document.getElementById("login-form").addEventListener("submit", handleLogin);

});