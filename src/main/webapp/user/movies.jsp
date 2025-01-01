<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Movies - ABC Cinema</title>
    <link href="../css/home.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <style>
        .movie-card {
    transition: transform 0.3s;
    height: 100%;
}

.movie-card:hover {
    transform: translateY(-5px);
}

.card-img-top {
    height: 400px;
    object-fit: cover;
}

.price-badge {
    background: #2d2d2d;
    padding: 5px 10px;
    border-radius: 4px;
    font-weight: 500;
    color: #fff;
}

.price-badge i {
    margin-right: 5px;
    color: #e50914;
}


    </style>
</head>
<body class="bg-black text-white">
    <div class="container-fluid">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10">
                <header>
                    <nav class="navbar navbar-expand-lg my-3">
                        <div class="container-fluid">
                            <div class="logo">
                                <p>ABC CINEMA</p>
                            </div>
                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                <ul class="navbar-nav d-flex ms-auto mb-lg-0">
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/home2.jsp">Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link active text-white mx-2" href="${pageContext.request.contextPath}/user/movies">Movies</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/bookings">My Bookings</a>

                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/feedback">Feedback</a>
                                    </li>
                                </ul>
                                
                                <div class="dropdown">
                                    <div class="profile-icon" data-bs-toggle="dropdown" aria-expanded="false">
                                        <div class="avatar-circle">
                                            <c:choose>
                                                <c:when test="${not empty sessionScope.user.profileImageUrl}">
                                                    <img src="${sessionScope.user.profileImageUrl}" alt="Profile" class="profile-image">
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-user"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="user-info">
                                            <span class="username">
                                                ${fn:substring(sessionScope.user.username, 0, 1).toUpperCase()}${fn:substring(sessionScope.user.username, 1, -1).toLowerCase()}
                                            </span>
                                            <i class="fas fa-chevron-down"></i>
                                        </div>
                                    </div>
                                    <ul class="dropdown-menu dropdown-menu-end animate slideIn">
                                        <li class="dropdown-header">
                                            <div class="d-flex align-items-center">
                                                <div class="avatar-circle-large">
                                                    <c:choose>
                                                        <c:when test="${not empty sessionScope.user.profileImageUrl}">
                                                            <img src="${sessionScope.user.profileImageUrl}" alt="Profile">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fas fa-user"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="ms-3">
                                                    <h6 class="mb-0">${sessionScope.user.username}</h6>
                                                    <small class="text-muted">${sessionScope.user.email}</small>
                                                </div>
                                            </div>
                                        </li>
                                        
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/profile.jsp"><i class="fas fa-user-circle"></i> My Profile</a></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/bookings"><i class="fas fa-ticket-alt"></i> My Bookings</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-heart"></i> Favorites</a></li>
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Settings</a></li>
                                        <li><hr class="dropdown-divider"></li>
                                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/user/auth?action=logout">
                                            <i class="fas fa-sign-out-alt"></i> Logout
                                        </a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </nav>
                </header>

                <div class="container mt-5">
                    <h1 class="gradient-text fw-bolder text-center mb-5">Latest Movies</h1>
                    
                    <div class="row row-cols-1 row-cols-md-3 g-4">
                        <c:forEach items="${movies}" var="movie">
                            <div class="col">
                                <div class="movie-card">
                                    <img src="${movie.posterUrl}" class="card-img-top" alt="${movie.title}">
                                    <div class="card-body">
                                        <h5 class="card-title">${movie.title}</h5>
                                        <p class="card-text">${fn:substring(movie.description, 0, 100)}...</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <span class="badge bg-primary">${movie.status}</span>
                                            <span class="price-badge">
                                                <i class="fas fa-ticket-alt"></i> 
                                                LKR ${moviePrices[movie.movieId]}
                                            </span>
                                        </div>
                                        <a href="${pageContext.request.contextPath}/user/booking?movieId=${movie.movieId}" class="btn btn-primary w-100 mt-2">Book Now</a>
                                    </div>
                                </div>
                                <!-- <div class="movie-popup">
                                    <h4>${movie.title}</h4>
                                    <div class="movie-details">
                                        <div class="d-flex align-items-center mb-3">
                                            <span class="rating-badge">
                                                <i class="fas fa-star"></i> ${movie.rating}
                                            </span>
                                            <span class="duration-badge">
                                                <i class="fas fa-clock"></i> ${movie.duration} mins
                                            </span>
                                        </div>
                                        <p>${movie.description}</p>
                                        <p><strong>Release Date:</strong> ${movie.releaseDate}</p>
                                        <p><strong>Status:</strong> ${movie.status}</p>
                                        <a href="booking?movieId=${movie.movieId}" class="btn btn-primary w-100">Book Now</a>
                                    </div>
                                </div> -->
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>

    <script>
        // Movie popup functions
        function showPopup(container, movieId) {
            const popup = container.querySelector('.movie-popup');
            const card = container.querySelector('.movie-card');
            const rect = card.getBoundingClientRect();
            
            popup.style.display = 'block';
            
            const spaceRight = window.innerWidth - rect.right;
            if (spaceRight >= 420) {
                popup.style.left = '100%';
                popup.style.top = '0';
            } else {
                popup.style.right = '100%';
                popup.style.top = '0';
            }
        }
    
        function hidePopup(container) {
            const popup = container.querySelector('.movie-popup');
            popup.style.display = 'none';
        }
    
        // Logout transition
        document.querySelector('.dropdown-item.text-danger').addEventListener('click', function(e) {
            e.preventDefault();
            const logoutLink = this.href;
            
            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging out...';
            
            document.body.style.opacity = '1';
            document.body.style.transition = 'opacity 0.5s ease';
            
            setTimeout(() => {
                document.body.style.opacity = '0';
            }, 100);
    
            fetch(logoutLink)
                .then(response => response.json())
                .then(data => {
                    setTimeout(() => {
                        window.location.href = data.redirect;
                    }, 500);
                });
        });
    </script>
    
</body>
</html>
