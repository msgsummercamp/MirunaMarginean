"use strict";
function showDog() {
    fetch('https://dog.ceo/api/breeds/image/random')
        .then((response) => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
        .then((data) => {
        const img = document.getElementById('dogImage');
        if (img) {
            img.src = data.message;
            img.style.display = 'block';
        }
        else {
            console.error('Image element with id "dogImage" not found.');
        }
    })
        .catch((error) => {
        console.error('Error fetching dog image:', error);
    });
}
document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('showDogBtn');
    button === null || button === void 0 ? void 0 : button.addEventListener('click', showDog);
});
