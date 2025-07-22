"use strict";
document.addEventListener('DOMContentLoaded', () => {
    const img = document.getElementById('dogImage');
    const message = document.getElementById('message');
    const error = document.getElementById('error');
    const button = document.querySelector('button');
    function showDog(_event) {
        img.style.display = 'none';
        error.style.display = 'none';
        message.style.display = 'block';
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
            .catch((err) => {
            console.error('Error fetching dog image:', err);
            message.style.display = 'none';
            error.textContent = 'Oops! Something went wrong. Please try again.';
            error.style.display = 'block';
        });
    }
    button.addEventListener('click', showDog);
});
