document.addEventListener('DOMContentLoaded', function() {
    const userId = sessionStorage.getItem('userId');
    console.log('Current userId:', userId);

    if (!STRIPE_PUBLIC_KEY) {
        console.error('Stripe key missing');
        return;
    }

    const stripe = Stripe(STRIPE_PUBLIC_KEY);
    const elements = stripe.elements();
    
    const cardElement = elements.create('card', {
        hidePostalCode: true,
        style: {
            base: {
                color: '#ffd700',
                fontFamily: '"Inter", -apple-system, sans-serif',
                fontSmoothing: 'antialiased',
                fontSize: '16px',
                fontWeight: '500',
                '::placeholder': {
                    color: '#6b7280'
                },
                backgroundColor: '#ffffff',
                ':-webkit-autofill': {
                    color: '#ffd700'
                }
            },
            invalid: {
                color: '#ef4444',
                iconColor: '#ef4444'
            }
        }
    });
    
    cardElement.mount('#card-element');

    let timeLeft = 600;
    const timerDisplay = document.getElementById('payment-timer');
    
    const timer = setInterval(() => {
        const minutes = Math.floor(timeLeft / 60);
        const seconds = timeLeft % 60;
        timerDisplay.textContent = `${minutes}:${seconds.toString().padStart(2, '0')}`;
        
        if (timeLeft <= 180) {
            timerDisplay.style.color = '#ef4444';
        }
        
        if (timeLeft <= 0) {
            clearInterval(timer);
            cancelPayment();
        }
        timeLeft--;
    }, 1000);

    const form = document.getElementById('payment-form');
    const submitButton = document.getElementById('submit-button');
    const loadingSpinner = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Processing...';
    const buttonText = submitButton.innerHTML;

    form.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const selectedSeats = sessionStorage.getItem('selectedSeats');
        if (!selectedSeats) {
            showError('No seats selected');
            return;
        }

        try {
            const response = await fetch('/CinemaBookingAdminPanel/user/seats/store-session', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    selectedSeats: selectedSeats
                })
            });
            
            if (!response.ok) {
                throw new Error('Failed to store seats in session');
            }
            
            const result = await response.json();
            if (result.error) {
                throw new Error(result.error);
            }
        } catch (error) {
            showError('Failed to store seats: ' + error.message);
            return;
        }

        const userId = sessionStorage.getItem('userId');
        if (!userId) {
            showError('User session not found. Please login again.');
            window.location.href = '/CinemaBookingAdminPanel/user/login.jsp';
            return;
        }
        
        setLoading(true);

        try {
            const amount = sessionStorage.getItem('totalAmount');
            if (!amount || isNaN(amount)) {
                throw new Error('Invalid amount');
            }

            const paymentData = {
                bookingId: BOOKING_ID,
                amount: amount,
                saveCard: document.getElementById('saveCard').checked
            };
            
            console.log('Sending payment data:', paymentData);

            const response = await fetch('/CinemaBookingAdminPanel/stripe/create-payment-intent', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(paymentData)
            });

            if (!response.ok) {
                const errorData = await response.text();
                console.error('Payment intent creation failed:', errorData);
                throw new Error('Payment intent creation failed');
            }

            const {clientSecret} = await response.json();

            const result = await stripe.confirmCardPayment(clientSecret, {
                payment_method: {
                    card: cardElement,
                    billing_details: {
                        email: sessionStorage.getItem('userEmail')
                    }
                }
            });

            if (result.error) {
                showError(result.error.message);
                setLoading(false);
            } else {
                await savePaymentDetails(result.paymentIntent);
                showSuccessMessage();
                setTimeout(() => {
                    window.location.href = '/CinemaBookingAdminPanel/payment/payment-success.jsp';
                }, 1500);
            }
        } catch (error) {
            showError('Payment processing failed. Please try again.');
            setLoading(false);
        }
    });

    async function savePaymentDetails(paymentIntent) {
        const userId = sessionStorage.getItem('userId');
        if (!userId) {
            throw new Error('User ID not found in session');
        }

        console.log('Payment details being sent:', {
            bookingId: BOOKING_ID,
            userId: parseInt(userId),
            amount: sessionStorage.getItem('totalAmount'),
            paymentIntentId: paymentIntent.id
        });

        const response = await fetch('/CinemaBookingAdminPanel/stripe/save-payment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                bookingId: BOOKING_ID,
                userId: parseInt(userId),
                amount: sessionStorage.getItem('totalAmount'),
                paymentIntentId: paymentIntent.id
            })
        });
        
        if (!response.ok) {
            const errorData = await response.text();
            console.error('Save payment failed:', errorData);
            throw new Error('Failed to save payment details');
        }
        return response.json();
    }

    function setLoading(isLoading) {
        submitButton.disabled = isLoading;
        submitButton.innerHTML = isLoading ? loadingSpinner : buttonText;
        form.style.opacity = isLoading ? '0.7' : '1';
    }

    function showError(message) {
        const errorDiv = document.getElementById('card-errors');
        errorDiv.innerHTML = `<i class="fas fa-exclamation-circle me-2"></i>${message}`;
        errorDiv.style.display = 'block';
        setTimeout(() => {
            errorDiv.style.opacity = '0';
            setTimeout(() => {
                errorDiv.style.display = 'none';
                errorDiv.style.opacity = '1';
            }, 300);
        }, 5000);
    }

    function showSuccessMessage() {
        const successDiv = document.createElement('div');
        successDiv.className = 'alert alert-success text-center';
        successDiv.innerHTML = '<i class="fas fa-check-circle me-2"></i>Payment successful! Redirecting...';
        form.insertBefore(successDiv, form.firstChild);
    }
});

function cancelPayment() {
    const confirmed = confirm('Are you sure you want to cancel this payment? Your booking will be lost.');
    if (confirmed) {
        sessionStorage.removeItem('totalAmount');
        sessionStorage.removeItem('selectedSeats');
        window.location.href = '/user/movies';
    }
}
