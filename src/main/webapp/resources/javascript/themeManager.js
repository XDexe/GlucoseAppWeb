document.addEventListener('DOMContentLoaded', function () {
    const themeToggleLink = document.getElementById('themeToggleLink');
    const themeButton = document.getElementById('themeToggleTextAdminPage'); // Le bouton dans le dropdown
    const themeToggleText = document.getElementById('themeToggleText');
    const themeToggleIcon = document.getElementById('themeToggleIcon'); // L'icône dans le dropdown
    const htmlElement = document.documentElement;

    function applyTheme(theme) {
        htmlElement.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);

        if (themeToggleText && themeToggleIcon) {
            if (theme === 'dark') {
                themeToggleText.textContent = 'Passer en mode clair';
                themeToggleIcon.classList.remove('fa-moon');
                themeToggleIcon.classList.add('fa-sun');
            } else {
                themeToggleText.textContent = 'Passer en mode sombre';
                themeToggleIcon.classList.remove('fa-sun');
                themeToggleIcon.classList.add('fa-moon');
            }
        }
    }

    // Charger le thème sauvegardé ou 'light' par défaut
    const savedTheme = localStorage.getItem('theme') || 'light';
    applyTheme(savedTheme);


    themeButton.addEventListener('click', function (event) {
        console.log('Click');
        event.preventDefault(); // Empêche le comportement par défaut du lien <a>
        const currentTheme = htmlElement.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        applyTheme(newTheme);
    });

});