document.addEventListener("DOMContentLoaded", function () {

    const email = document.getElementById("email");
    const password = document.getElementById("password");
    const btn = document.getElementById("login-btn");
    const errorMsg = document.getElementById("error-msg");
    const form = document.querySelector("form");

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

        // Validation simple du mot de passe test
        if (password.value !== "1234") {
            errorMsg.textContent = "❌ Mot de passe incorrect. Essayez '1234'";
            errorMsg.classList.add("show");
            return false;
        }

        errorMsg.textContent = "";
        errorMsg.classList.remove("show");
        alert("✓ Connexion réussie !");
        return false;
    }

    /* Lier le formulaire */
    if (form) {
        form.addEventListener("submit", handleLogin);
    }

});