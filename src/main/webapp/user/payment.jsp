<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - ABC Cinema</title>
    <link href="../css/payment.css" rel="stylesheet"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://js.stripe.com/v3/"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body class="bg-black text-white">
    <div class="container-fluid">
        <div class="row">
            <div class="col-1"></div>
            
            <div class="col-10">
                <div class="row justify-content-center mt-4">
                    <div class="col-lg-8 col-md-10 col-sm-12">
                        <div class="card payment-gradient">
                            <div class="card-body p-4">
                                <h3 class="gradient-text text-center fw-bold mb-4">Complete Your Payment</h3>
                                
                                <div class="timer-container text-center mb-4">
                                    <i class="fas fa-clock me-2 text-white"></i>
                                    <span class="timer-label">Time remaining: </span>
                                    <span id="payment-timer" class="timer-value">10:00</span>
                                </div>

                                <div class="details-section order-details mb-4">
                                    <h5><i class="fas fa-ticket-alt me-2"></i>Order Details</h5>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Movie:</span>
                                        <span class="value">${sessionScope.selectedMovie}</span>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Show Time:</span>
                                        <span class="value">${sessionScope.selectedShowTime}</span>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Date:</span>
                                        <span class="value">${sessionScope.selectedDate}</span>
                                    </div>
                                </div>

                                <div class="details-section price-breakdown mb-4">
                                    <h5><i class="fas fa-money-bill-wave me-2"></i>Price Details</h5>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Ticket Price:</span>
                                        <span class="value">${sessionScope.ticketPrice} LKR</span>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Number of Tickets:</span>
                                        <span class="value">${sessionScope.numTickets}</span>
                                    </div>
                                    <div class="d-flex justify-content-between">
                                        <span class="label">Service Fee:</span>
                                        <span class="value">${sessionScope.serviceFee} LKR</span>
                                    </div>
                                    <hr class="divider">
                                    <div class="d-flex justify-content-between total-amount">
                                        <span class="label">Total:</span>
                                        <span class="value highlight-price">${sessionScope.totalAmount} LKR</span>
                                    </div>
                                </div>

                                <div class="details-section payment-methods mb-4">
                                    <h5><i class="fas fa-credit-card me-2"></i>Payment Method</h5>
                                    <div class="form-check">
                                        <input type="radio" class="form-check-input" name="paymentMethod" value="card" checked>
                                        <label class="form-check-label">Credit/Debit Card</label>
                                    </div>
                                </div>

                                <form id="payment-form">
                                    <div id="card-element" class="mb-3"></div>
                                    <div id="card-errors" class="text-danger mb-3"></div>
                                    
                                    <div class="form-check mb-3">
                                        <input type="checkbox" class="form-check-input" id="saveCard">
                                        <label class="form-check-label">Save card for future payments</label>
                                    </div>

                                    <div class="d-grid gap-2">
                                        <button id="submit-button" class="btn btn-primary btn-lg">
                                            <i class="fas fa-lock me-2"></i>Pay Now
                                        </button>
                                        <button type="button" class="btn btn-outline-secondary btn-lg" onclick="cancelPayment()">
                                            <i class="fas fa-times me-2"></i>Cancel Payment
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="col-1"></div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const userId = '${sessionScope.user.userId}';
        sessionStorage.setItem('userId', userId);
        sessionStorage.setItem('userEmail', '${sessionScope.user.email}');
        
        const STRIPE_PUBLIC_KEY = '${stripePublicKey}';
        const BOOKING_ID = '${sessionScope.currentBookingId}';
        const TOTAL_AMOUNT = '${sessionScope.totalAmount}';
        
        // Store in session storage
        sessionStorage.setItem('totalAmount', TOTAL_AMOUNT);
    </script>
    <script src="../js/payment.js"></script>
</body>
</html>
