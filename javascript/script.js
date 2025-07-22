function showDog() {
    fetch('https://dog.ceo/api/breeds/image/random')
        .then(response => response.json())
        .then(data => {
            const img = document.getElementById('dogImage');
            img.src = data.message;
            img.style.display = 'block';
        })
        .catch(error => {
            console.error('Error fetching dog image:', error);
        });
}