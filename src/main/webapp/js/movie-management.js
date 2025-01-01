document.addEventListener('DOMContentLoaded', function() {
    initializeFileUpload();
    initializeFormHandling();
});

const contextPath = document.querySelector('meta[name="contextPath"]').content;

function initializeFileUpload() {
    const fileInput = document.getElementById('moviePoster');
    const preview = document.getElementById('posterPreview');

    fileInput.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                preview.innerHTML = `<img src="${e.target.result}" alt="Preview">`;
            }
            reader.readAsDataURL(file);
        }
    });
}

function showAddMovieModal() {
    const form = document.getElementById('movieForm');
    form.reset();
    form.setAttribute('data-mode', 'add');
    document.getElementById('modalTitle').textContent = 'Add New Movie';
    document.getElementById('posterPreview').innerHTML = '';
    document.getElementById('movieModal').style.display = 'block';
}

function editMovie(movieId) {
    fetch(`${contextPath}/admin/movies/${movieId}`)
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(movie => {
            const form = document.getElementById('movieForm');
            form.setAttribute('data-mode', 'edit');
            form.elements['movieId'].value = movie.movieId;
            form.elements['title'].value = movie.title;
            form.elements['description'].value = movie.description;
            form.elements['duration'].value = movie.duration;
            form.elements['rating'].value = movie.rating;
            form.elements['releaseDate'].value = formatDate(movie.releaseDate);
            form.elements['status'].value = movie.status;
            
            if (movie.posterUrl) {
                document.getElementById('posterPreview').innerHTML = 
                    `<img src="${movie.posterUrl}" alt="Current Poster">`;
            }
            
            document.getElementById('movieModal').style.display = 'block';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load movie details');
        });
}

function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const mode = form.getAttribute('data-mode');
    
    formData.append('action', mode === 'edit' ? 'update' : 'add');
    
    const url = mode === 'edit' 
        ? `${contextPath}/admin/movies/${formData.get('movieId')}`
        : `${contextPath}/admin/movies`;
        
    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to save movie');
        window.location.reload();
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}

function deleteMovie(movieId) {
    if (!confirm('Are you sure you want to delete this movie?')) return;
    
    fetch(`${contextPath}/admin/movies/${movieId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) throw new Error('Failed to delete movie');
        window.location.reload();
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message);
    });
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
}

function closeModal() {
    document.getElementById('movieModal').style.display = 'none';
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('movieModal');
    if (event.target == modal) {
        closeModal();
    }
}

document.getElementById('movieForm').addEventListener('submit', handleFormSubmit);
