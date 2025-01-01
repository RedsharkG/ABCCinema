<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Movie Tickets</title>
    <link href="../css/booking.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-dark text-white">
    <div class="container mt-5">
        <div class="booking-container">
            <div class="row g-4">
                <!-- Movie Details Section -->
                <div class="col-md-6">
                    <div class="movie-details-card">
                        <div class="movie-header">
                            <div class="movie-title-wrapper">
                                <h2 class="movie-title gradient-text">${movie.title}</h2>
                                <div class="movie-meta">
                                    <span class="badge bg-primary"><i class="fas fa-clock"></i> ${movie.duration} mins</span>
                                    <span class="badge bg-success"><i class="fas fa-star"></i> ${movie.rating}/10</span>
                                    <span class="badge bg-info"><i class="fas fa-film"></i> Now Showing</span>
                                </div>
                            </div>
                        </div>
                        <div class="poster-container">
                            <img src="${movie.posterUrl}" class="movie-poster" alt="${movie.title}">
                            <div class="movie-info">
                                <p><i class="fas fa-clock"></i> Duration: ${movie.duration} mins</p>
                                <p><i class="fas fa-star"></i> Rating: ${movie.rating}/10</p>
                            </div>
                        </div>
                        <div class="movie-description mt-3">
                            <h5>Synopsis</h5>
                            <p>${movie.description}</p>
                        </div>
                    </div>
                </div>

                <!-- Booking Form Section -->
                <div class="col-md-6">
                    <div class="booking-form-card">
                        <h3 class="text-center mb-4">Book Your Tickets</h3>
                        <form id="bookingForm" action="${pageContext.request.contextPath}/user/booking" method="post">
                            <input type="hidden" name="movieId" value="${movie.movieId}">
                            <input type="hidden" name="totalAmount" id="totalAmount">
                            
                            <div class="form-group mb-4">
                                <label><i class="fas fa-calendar"></i> Show Date</label>
                                <input type="date" name="bookingDate" class="form-control custom-input" required>
                            </div>
                            
                            <div class="form-group mb-4">
                                <label><i class="fas fa-clock"></i> Show Time</label>
                                <select name="showTime" class="form-control custom-select" required>
                                    <option value="" disabled selected>Select show time</option>
                                    <option value="8:30 AM-10:30 AM">8:30 AM - 10:30 AM</option>
                                    <option value="12:30 PM-2:30 PM">12:30 PM - 2:30 PM</option>
                                    <option value="4:30 PM-6:30 PM">4:30 PM - 6:30 PM</option>
                                    <option value="8:30 PM-10:30 PM">8:30 PM - 10:30 PM</option>
                                </select>
                            </div>
                            
                            <div class="form-group mb-4">
                                <label><i class="fas fa-ticket-alt"></i> Number of Tickets</label>
                                <div class="input-group">
                                    <button type="button" class="btn btn-outline-light btn-sm" onclick="decrementTickets()">-</button>
                                    <input type="number" name="numTickets" min="1" max="10" value="1" class="form-control custom-input text-center" required readonly>
                                    <button type="button" class="btn btn-outline-light btn-sm" onclick="incrementTickets()">+</button>
                                </div>
                            </div>
                            
                            <div class="price-summary mb-4">
                                <div class="d-flex justify-content-between">
                                    <span>Ticket Price:</span>
                                    <span>${ticketPrice} LKR</span>
                                </div>
                                <div class="d-flex justify-content-between total-amount">
                                    <h5>Total Amount:</h5>
                                    <h5><span id="displayAmount">0.00</span> LKR</h5>
                                </div>
                            </div>
                            
                            <button type="submit" class="btn btn-primary w-100 booking-btn">
                                Continue to Seat Selection <i class="fas fa-arrow-right"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        const TICKET_PRICE = parseFloat("${ticketPrice}");
    </script>
    <script src="../js/booking.js"></script>
</body>
</html>
