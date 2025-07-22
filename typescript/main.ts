interface DogApiResponse {
    message: string;
    status: 'success';
}

function showDog(): void {
    fetch('https://dog.ceo/api/breeds/image/random')
        .then((response: Response): Promise<DogApiResponse> => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((data: DogApiResponse) => {
            const img = document.getElementById('dogImage') as HTMLImageElement | null;

            if (img) {
                img.src = data.message;
                img.style.display = 'block';
            } else {
                console.error('Image element with id "dogImage" not found.');
            }
        })
        .catch((error: unknown) => {
            console.error('Error fetching dog image:', error);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('showDogBtn') as HTMLButtonElement | null;
    button?.addEventListener('click', showDog);
});