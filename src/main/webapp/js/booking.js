document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('bookingForm');
    const numTicketsInput = document.querySelector('input[name="numTickets"]');
    const totalAmountDisplay = document.getElementById('displayAmount');
    const totalAmountInput = document.getElementById('totalAmount');
    
    function updateTotal() {
        const numTickets = parseInt(numTicketsInput.value) || 0;
        const total = numTickets * TICKET_PRICE;
        totalAmountDisplay.textContent = total.toFixed(2);
        totalAmountInput.value = total;
    }
    
    numTicketsInput.addEventListener('input', updateTotal);
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const date = new Date(form.bookingDate.value);
        const today = new Date();
        
        if (date < today) {
            showAlert('Please select a future date', 'error');
            return;
        }
        
        this.submit();
    });
    
    // Initialize with default values
    updateTotal();
});

function incrementTickets() {
    const input = document.querySelector('input[name="numTickets"]');
    if (input.value < 10) {
        input.value = parseInt(input.value) + 1;
        input.dispatchEvent(new Event('input'));
    }
}

function decrementTickets() {
    const input = document.querySelector('input[name="numTickets"]');
    if (input.value > 1) {
        input.value = parseInt(input.value) - 1;
        input.dispatchEvent(new Event('input'));
    }
}

function showAlert(message, type) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.querySelector('.booking-form-card').insertBefore(alertDiv, document.getElementById('bookingForm'));
    
    setTimeout(() => alertDiv.remove(), 3000);
}
