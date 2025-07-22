function showDog() {
    const img = document.getElementById('dogImage');
    const message = document.getElementById('message');
    const error = document.getElementById('error');

    if (!img || !message || !error) return;

    img.classList.add('hidden');
    error.classList.add('hidden');
    message.classList.remove('hidden');
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
            img.classList.remove('hidden');
            message.classList.add('hidden');
        })
        .catch(() => {
            message.classList.add('hidden');
            error.textContent = 'Oops! Something went wrong. Please try again.';
            error.classList.remove('hidden');
        });
}
