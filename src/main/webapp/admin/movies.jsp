<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Movie Management</title>
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/movie-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="container">
        <jsp:include page="includes/sidebar.jsp">
            <jsp:param name="page" value="movies"/>
        </jsp:include>
        
        <div class="main-content">
            <div class="movie-management">
                <!-- Movie Stats -->
                <div class="movie-stats">
                    <div class="stat-card">
                        <i class="fas fa-film"></i>
                        <h3>Total Movies</h3>
                        <p>${movies.size()}</p>
                    </div>
                    <div class="stat-card">
                        <i class="fas fa-play-circle"></i>
                        <h3>Now Showing</h3>
                        <p>${nowShowingCount}</p>
                    </div>
                    <div class="stat-card">
                        <i class="fas fa-clock"></i>
                        <h3>Coming Soon</h3>
                        <p>${comingSoonCount}</p>
                    </div>
                </div>

                <!-- Add Movie Button -->
                <div class="ticket-actions">
                    <button class="action-btn" onclick="showAddMovieModal()">
                        <i class="fas fa-plus"></i> Add New Movie
                    </button>
                </div>

                <!-- Movie Grid -->
                <div class="movie-grid">
                    <c:forEach items="${movies}" var="movie">
                        <div class="movie-card">
                            <div class="movie-poster">
                                <img src="${movie.posterUrl}" alt="${movie.title}">
                                <div class="movie-actions">
                                    <button onclick="editMovie('${movie.movieId}')" class="btn-edit">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button onclick="deleteMovie('${movie.movieId}')" class="btn-delete">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="movie-info">
                                <h3>${movie.title}</h3>
                                <span class="badge ${movie.status.toLowerCase()}">${movie.status}</span>
                                <p class="duration"><i class="far fa-clock"></i> ${movie.duration} mins</p>
                                <p class="rating"><i class="fas fa-star"></i> ${movie.rating}/5.0</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <!-- Add/Edit Movie Modal -->
    <div id="movieModal" class="modal">
        <div class="modal-content compact">
            <span class="close">&times;</span>
            <h2 id="modalTitle"><i class="fas fa-film"></i> Add New Movie</h2>
            <form id="movieForm" enctype="multipart/form-data">
                <input type="hidden" name="movieId" id="movieId">
                
                <div class="form-group">
                    <label>Title</label>
                    <input type="text" name="title" id="movieTitle" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" id="movieDescription" required></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group half">
                        <label>Duration (minutes)</label>
                        <input type="number" name="duration" id="movieDuration" required>
                    </div>
                    <div class="form-group half">
                        <label>Rating</label>
                        <input type="number" step="0.1" min="0" max="5" name="rating" id="movieRating" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group half">
                        <label>Release Date</label>
                        <input type="date" name="releaseDate" id="movieReleaseDate" required>
                    </div>
                    <div class="form-group half">
                        <label>Status</label>
                        <select name="status" id="movieStatus" required>
                            <option value="NOW_SHOWING">Now Showing</option>
                            <option value="COMING_SOON">Coming Soon</option>
                            <option value="ENDED">Ended</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label>Movie Poster</label>
                    <div class="file-upload">
                        <input type="file" name="poster" id="moviePoster" accept="image/*">
                        <label for="moviePoster">
                            <i class="fas fa-cloud-upload-alt"></i>
                            <span>Choose a file</span>
                        </label>
                    </div>
                    <div id="posterPreview" class="preview-image"></div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="modal-btn primary">
                        <i class="fas fa-save"></i> Save Movie
                    </button>
                    <button type="button" class="modal-btn secondary" onclick="closeModal()">
                        <i class="fas fa-times"></i> Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="../js/movie-management.js"></script>
</body>
</html>
