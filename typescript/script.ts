interface DogApiResponse {
    message: string;
    status: 'success' | 'error';
}

document.addEventListener('DOMContentLoaded', () => {
    const img: HTMLImageElement = document.getElementById('dogImage') as HTMLImageElement;
    const message: HTMLParagraphElement = document.getElementById('message') as HTMLParagraphElement;
    const error: HTMLParagraphElement = document.getElementById('error') as HTMLParagraphElement;
    const button: HTMLButtonElement = document.querySelector('button')!;

    function showDog(this: HTMLButtonElement, _event: MouseEvent): void {
        img.style.display = 'none';
        error.style.display = 'none';
        message.style.display = 'block';
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
            .catch((err: unknown): void => {
                console.error('Error fetching dog image:', err);
                message.style.display = 'none';
                error.textContent = 'Oops! Something went wrong. Please try again.';
                error.style.display = 'block';
            });
    }

    button.addEventListener('click', showDog);
});
