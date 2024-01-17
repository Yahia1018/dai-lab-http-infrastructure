document.addEventListener('DOMContentLoaded', function () {
    // Fonction pour effectuer une requête GET
    function fetchData() {
        fetch('https://localhost/api/products') // Remplacez par l'URL correcte de votre API
            .then(response => response.json())
            .then(data => {
                // Affichez les résultats dans la div 'apiResults'
                document.getElementById('apiResults').innerHTML = JSON.stringify(data);
            })
            .catch(error => console.error('Error:', error));
    }
 
    // Appeler fetchData au chargement de la page
    fetchData();
 
    // Optionnel : Effectuer la requête périodiquement
    setInterval(fetchData, 5000); // Répéter toutes les 5 secondes (ajustez selon vos besoins)
});