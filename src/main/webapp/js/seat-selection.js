document.addEventListener('DOMContentLoaded', function() {
    const seatMap = document.getElementById('seatMap');
    const selectedSeatsText = document.getElementById('selectedSeatsText');
    const totalAmountSpan = document.getElementById('totalAmount');
    const proceedButton = document.getElementById('proceedToPayment');
    
    let selectedSeats = [];
    
    function createSeatMap() {
        for (let i = 0; i < 64; i++) {
            const seat = document.createElement('div');
            seat.className = 'seat available';
            seat.dataset.seatNumber = `${String.fromCharCode(65 + Math.floor(i/8))}${i%8 + 1}`;
            seat.textContent = seat.dataset.seatNumber;
            
            seat.addEventListener('click', () => toggleSeat(seat));
            seatMap.appendChild(seat);
        }
    }
    
    function toggleSeat(seat) {
        if (seat.classList.contains('occupied')) return;
        
        if (!seat.classList.contains('selected') && selectedSeats.length >= TICKET_COUNT) {
            showAlert(`You can only select ${TICKET_COUNT} seats`);
            return;
        }
        
        seat.classList.toggle('selected');
        const seatNumber = seat.dataset.seatNumber;
        
        if (seat.classList.contains('selected')) {
            selectedSeats.push(seatNumber);
        } else {
            selectedSeats = selectedSeats.filter(s => s !== seatNumber);
        }
        
        updateBookingSummary();
        updateProceedButton();
    }
    
    function updateBookingSummary() {
        selectedSeatsText.textContent = selectedSeats.join(', ') || 'None';
        totalAmountSpan.textContent = (selectedSeats.length * SEAT_PRICE).toFixed(2);
    }
    
    function updateProceedButton() {
        const isValidSelection = selectedSeats.length === TICKET_COUNT;
        proceedButton.disabled = !isValidSelection;
        
        if (!isValidSelection) {
            proceedButton.title = `Please select exactly ${TICKET_COUNT} seats`;
        } else {
            proceedButton.title = 'Proceed to payment';
        }
    }
    
    function showAlert(message) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-warning alert-dismissible fade show';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        document.querySelector('.container').insertBefore(alertDiv, document.querySelector('.screen-container'));
        
        setTimeout(() => alertDiv.remove(), 3000);
    }
    
    function loadReservedSeats() {
        const movieId = document.getElementById('movieId').value;
        const showTime = document.getElementById('showTime').value;
        const bookingDate = document.getElementById('bookingDate').value;
        
        if (!movieId || !showTime || !bookingDate) {
            console.error('Missing required parameters');
            return;
        }

        fetch(`/CinemaBookingAdminPanel/user/seats/reserved?movieId=${movieId}&showTime=${showTime}&bookingDate=${bookingDate}`)
            .then(response => {
                if (!response.ok) throw new Error('Failed to fetch reserved seats');
                return response.json();
            })
            .then(reservedSeats => {
                console.log('Reserved seats:', reservedSeats);
                // Parse the JSON array if it's a string
                const seatArray = typeof reservedSeats === 'string' ? JSON.parse(reservedSeats) : reservedSeats;
                
                seatArray.forEach(seatNumber => {
                    // Remove any quotes or brackets from the seat number
                    const cleanSeatNumber = seatNumber.replace(/[\[\]"]/g, '');
                    const seat = document.querySelector(`[data-seat-number="${cleanSeatNumber}"]`);
                    if (seat) {
                        seat.classList.remove('available');
                        seat.classList.add('occupied');
                        seat.setAttribute('title', 'Already booked');
                    }
                });
            })
            .catch(error => {
                console.error('Error loading reserved seats:', error);
                showAlert('Error loading seat availability');
            });
    }
    
    proceedButton.addEventListener('click', function() {
        const userId = sessionStorage.getItem('userId');
        if (selectedSeats.length === TICKET_COUNT) {
            sessionStorage.setItem('selectedSeats', selectedSeats.join(','));
            window.location.href = 'payment.jsp';
        }
    });
    
    createSeatMap();
    loadReservedSeats();
    updateProceedButton();
});
