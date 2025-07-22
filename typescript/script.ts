interface DogApiResponse {
    message: string;
    status: 'success' | 'error';
}

function showDog(): void {
    const img: HTMLImageElement = document.getElementById('dogImage') as HTMLImageElement;
    const message: HTMLParagraphElement = document.getElementById('message') as HTMLParagraphElement;
    const error: HTMLParagraphElement = document.getElementById('error') as HTMLParagraphElement;

    if (!img || !message || !error) return;

    img.classList.add('hidden');
    error.classList.add('hidden');
    message.classList.remove('hidden');
    message.textContent = 'Loading...';

    fetch('https://dog.ceo/api/breeds/image/random')
        .then((response: Response): Promise<DogApiResponse> => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then((data: DogApiResponse): void => {
            if (data.status === 'success') {
                img.src = data.message;
                img.style.display = 'block';
                message.style.display = 'none';
            } else {
                throw new Error('API returned error status');
            }
        })
        .catch((): void => {
            message.style.display = 'none';
            error.textContent = 'Oops! Something went wrong. Please try again.';
            error.style.display = 'block';
        });
}

