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
    </style>
</head>
<body class="bg-black text-white">
    <!-- Include the same navbar as home2.jsp -->
    
    <div class="container mt-5">
        <h1 class="gradient-text fw-bolder text-center mb-5">Latest Movies</h1>
        
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <c:forEach items="${movies}" var="movie">
                <div class="col">
                    <div class="card text-bg-dark movie-card">
                        <img src="${movie.posterUrl}" class="card-img-top" alt="${movie.title}">
                        <div class="card-body">
                            <h5 class="card-title">${movie.title}</h5>
                            <p class="card-text">${fn:substring(movie.description, 0, 100)}...</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <span class="badge bg-primary">${movie.status}</span>
                                <a href="booking?movieId=${movie.movieId}" class="btn btn-primary">Book Now</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
