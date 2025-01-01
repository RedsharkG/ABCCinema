<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Feedback - ABC Cinema</title>
    <link href="../css/home.css" rel="stylesheet"/>
    <link href="../css/feedback.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-black text-white">
    <div class="container-fluid">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10">
                <!-- Navigation Bar -->
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
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/movies">Movies</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/bookings">My Bookings</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link active text-white mx-2" href="${pageContext.request.contextPath}/user/feedback">Feedback</a>
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

                <!-- Feedback Content -->
                <div class="container mt-5">
                    <div class="feedback-container">
                        <h1 class="gradient-text text-center mb-5">Share Your Experience</h1>
                        
                        <c:if test="${not empty success}">
                            <div class="alert alert-success">${success}</div>
                        </c:if>
                        
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>
                        
                        <form action="${pageContext.request.contextPath}/user/feedback" method="post" class="feedback-form" id="feedbackForm" onsubmit="return validateForm()">
                            <div class="rating-container mb-4">
                                <label class="mb-3">Rate Your Experience</label>
                                <div class="stars">
                                    <input type="radio" id="star5" name="rating" value="5">
                                    <label for="star5"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="star4" name="rating" value="4">
                                    <label for="star4"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="star3" name="rating" value="3">
                                    <label for="star3"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="star2" name="rating" value="2">
                                    <label for="star2"><i class="fas fa-star"></i></label>
                                    <input type="radio" id="star1" name="rating" value="1">
                                    <label for="star1"><i class="fas fa-star"></i></label>
                                </div>
                                <div id="ratingError" class="error-message mt-2" style="display: none; color: #e50914;">
                                    Please select a rating
                                </div>
                            </div>
                            
                            <div class="form-group mb-4">
                                <label class="mb-2">Your Feedback</label>
                                <textarea class="form-control" id="message" name="message" rows="5" placeholder="Tell us about your experience..." required></textarea>
                            </div>
                            
                            <button type="submit" class="btn btn-primary w-100">Submit Feedback</button>
                        </form>
                    </div>

                    <!-- User Feedback Display Section -->
                    <div class="feedback-list mt-5">
                        <h2 class="gradient-text text-center mb-4">What Our Users Say</h2>
                        
                        <div class="row">
                            <c:forEach items="${feedbacks}" var="feedback">
                                <div class="col-md-6 mb-4">
                                    <div class="feedback-card">
                                        <div class="feedback-header">
                                            <div class="user-info">
                                                <div class="avatar-circle-small">
                                                    <i class="fas fa-user"></i>
                                                </div>
                                                <div>
                                                    <h5 class="mb-0">${feedback.username}</h5>
                                                    <small class="text-muted">
                                                        <fmt:formatDate value="${feedback.createdAt}" pattern="MMM dd, yyyy HH:mm"/>
                                                    </small>
                                                </div>
                                            </div>
                                            <div class="rating">
                                                <c:forEach begin="1" end="5" var="star">
                                                    <i class="fas fa-star ${star <= feedback.rating ? 'active' : ''}"></i>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <div class="feedback-content mt-3">
                                            <p>${feedback.message}</p>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>

    <script>
        // Form validation
        function validateForm() {
            const ratingInputs = document.querySelectorAll('input[name="rating"]');
            const ratingError = document.getElementById('ratingError');
            let isRatingSelected = false;
    
            ratingInputs.forEach(input => {
                if (input.checked) {
                    isRatingSelected = true;
                }
            });
    
            if (!isRatingSelected) {
                ratingError.style.display = 'block';
                return false;
            }
    
            ratingError.style.display = 'none';
            return true;
        }
    
        document.querySelectorAll('input[name="rating"]').forEach(input => {
            input.addEventListener('change', () => {
                document.getElementById('ratingError').style.display = 'none';
            });
        });
    
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
