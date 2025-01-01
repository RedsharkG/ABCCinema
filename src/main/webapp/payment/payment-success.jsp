<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Successful - ABC Cinema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #0a0d14;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0;
            overflow: hidden;
        }
        .success-container {
            background: linear-gradient(135deg, #1e2433 0%, #2d3447 100%);
            border-radius: 20px;
            width: 90%;
            max-width: 1000px;
            padding: 2rem;
            box-shadow: 0 15px 30px rgba(0,0,0,0.3);
        }
        .success-icon {
            color: #4BB543;
            font-size: 5rem;
            animation: bounceIn 1s ease-out;
        }
        .success-title {
            background: linear-gradient(45deg, #FFD700, #FFA500);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 2.5rem;
            font-weight: bold;
            margin: 1rem 0;
        }
        .booking-info {
            background: rgba(255,255,255,0.05);
            border-radius: 15px;
            padding: 1.5rem;
            margin: 1.5rem 0;
        }
        .info-label {
            color: #FFD700;
            font-weight: 500;
        }
        .info-value {
            color: #ffffff;
        }
        .email-notice {
            color: #00ff87;
            font-size: 1.1rem;
            margin: 1rem 0;
        }
        .action-btn {
            padding: 12px 30px;
            border-radius: 50px;
            font-weight: 600;
            transition: all 0.3s ease;
            margin: 0 10px;
        }
        .btn-view {
            background: linear-gradient(45deg, #FFD700, #FFA500);
            border: none;
            color: #000;
        }
        .btn-browse {
            background: transparent;
            border: 2px solid #FFD700;
            color: #FFD700;
        }
        .action-btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        }
        @keyframes bounceIn {
            0% { transform: scale(0); opacity: 0; }
            60% { transform: scale(1.1); }
            100% { transform: scale(1); opacity: 1; }
        }
    </style>
</head>
<body>
    <div class="success-container text-center">
        <div class="success-icon mb-3">
            <i class="fas fa-check-circle"></i>
        </div>
        <h2 class="success-title">Payment Successful!</h2>
        <p class="text-white mb-4">Your booking has been confirmed.</p>
        
        <div class="booking-info">
            <div class="row">
                <div class="col-md-6 text-start mb-3">
                    <p><span class="info-label">Movie:</span> <span class="info-value">${sessionScope.selectedMovie}</span></p>
                    <p><span class="info-label">Date:</span> <span class="info-value">${sessionScope.selectedDate}</span></p>
                </div>
                <div class="col-md-6 text-start mb-3">
                    <p><span class="info-label">Show Time:</span> <span class="info-value">${sessionScope.selectedShowTime}</span></p>
                    <p><span class="info-label">Tickets:</span> <span class="info-value">${sessionScope.numTickets}</span></p>
                </div>
            </div>
        </div>

        <p class="email-notice">
            <i class="fas fa-envelope me-2"></i>
            Booking confirmation has been sent to your email
        </p>

        <div class="mt-4">
            <a href="../user/bookings" class="action-btn btn-view">
                <i class="fas fa-ticket-alt me-2"></i>View My Bookings
            </a>
            <a href="../user/movies" class="action-btn btn-browse">
                <i class="fas fa-film me-2"></i>Browse Movies
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
