"use strict";
const img = document.getElementById('dogImage');
const message = document.getElementById('message');
const error = document.getElementById('error');
function showDog() {
    if (!img || !message || !error)
        return;
    img.classList.add('hidden');
    error.classList.add('hidden');
    message.classList.remove('hidden');
    message.textContent = 'Loading...';
    fetch('https://dog.ceo/api/breeds/image/random')
        .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
        .then((data) => {
        if (data.status === 'success') {
            img.src = data.message;
            img.style.display = 'block';
            message.style.display = 'none';
        }
        else {
            throw new Error('API returned error status');
        }
    })
        .catch(() => {
        message.style.display = 'none';
        error.textContent = 'Oops! Something went wrong. Please try again.';
        error.style.display = 'block';
    });
}
