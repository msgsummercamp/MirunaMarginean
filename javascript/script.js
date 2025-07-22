function showDog() {
    const img = document.getElementById('dogImage');
    const message = document.getElementById('message');
    const error = document.getElementById('error');

    img.style.display = 'none';
    error.style.display = 'none';
    message.style.display = 'block';
    message.textContent = 'Loading...';

    fetch('https://dog.ceo/api/breeds/image/random')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not OK');
            }
            return response.json();
        })
        .then(data => {
            img.src = data.message;
            img.style.display = 'block';
            message.style.display = 'none';
        })
        .catch(err => {
            console.error('Error fetching dog image:', err);
            message.style.display = 'none';
            error.textContent = 'Oops! Something went wrong. Please try again.';
            error.style.display = 'block';
        });
}
