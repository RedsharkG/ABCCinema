<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Bookings - ABC Cinema</title>
    <link href="../css/home.css" rel="stylesheet"/>
    <link href="../css/mybookings.css" rel="stylesheet"/>
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
                                        <a class="nav-link active text-white mx-2" href="${pageContext.request.contextPath}/user/bookings">My Bookings</a>
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
                                        <li><a class="dropdown-item" href="#"><i class="fas fa-ticket-alt"></i> My Bookings</a></li>
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/user/bookings"><i class="fas fa-heart"></i> Favorites</a></li>
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
                

                <!-- Main Content -->
                <div class="container mt-5">
                    <div class="booking-header">
                        <h1>My Movie Bookings</h1>
                    </div>
                    
                    <c:if test="${empty bookings}">
                        <div class="empty-bookings">
                            <i class="fas fa-ticket-alt fa-3x mb-3"></i>
                            <h3>No Bookings Found</h3>
                            <p>You haven't made any bookings yet. Start exploring our movies!</p>
                            <a href="${pageContext.request.contextPath}/user/movies" class="btn btn-primary mt-3">
                                Browse Movies
                            </a>
                        </div>
                    </c:if>
                    
                    <div class="row">
                        <c:forEach items="${bookings}" var="booking">
                            <div class="col-md-6 mb-4">
                                <div class="booking-card">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h4 class="mb-0">${booking.movieTitle}</h4>
                                        <span class="booking-status ${booking.paymentStatus == 'COMPLETED' ? 'status-completed' : 'status-pending'}">
                                            ${booking.paymentStatus}
                                        </span>
                                    </div>
                                    <div class="booking-info">
                                        <i class="fas fa-calendar-alt"></i>
                                        <span>Date: ${booking.bookingDate}</span>
                                    </div>
                                    <div class="booking-info">
                                        <i class="fas fa-clock"></i>
                                        <span>Show Time: ${booking.showTime}</span>
                                    </div>
                                    <div class="booking-info">
                                        <i class="fas fa-ticket-alt"></i>
                                        <span>Tickets: ${booking.numTickets}</span>
                                    </div>
                                    <div class="booking-info">
                                        <i class="fas fa-money-bill-wave"></i>
                                        <span>Total Amount: LKR ${booking.totalAmount}</span>
                                    </div>
                                    <div class="mt-3">
                                        <small class="text-muted">Booking ID: ${booking.bookingId}</small>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>
    <script>
        document.querySelector('.dropdown-item.text-danger').addEventListener('click', function(e) {
            e.preventDefault();
            const logoutLink = this.href;
            
            // Add visual feedback
            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging out...';
            
            // Fade out effect
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
