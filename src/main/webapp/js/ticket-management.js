let contextPath = '';
let ticketToDelete = null;

document.addEventListener('DOMContentLoaded', function() {
    contextPath = document.querySelector('meta[name="contextPath"]').getAttribute('content');
    initializeModalHandlers();
    initializeFormValidation();
});

function showAddModal() {
    document.getElementById('addModal').style.display = 'block';
}

function editTicket(ticketId, price, quantity, status) {
    document.getElementById('editTicketId').value = ticketId;
    document.getElementById('editPrice').value = price;
    document.getElementById('editQuantity').value = quantity;
    document.getElementById('editStatus').value = status;
    document.getElementById('editModal').style.display = 'block';
}

function confirmDelete(ticketId) {
    ticketToDelete = ticketId;
    document.getElementById('deleteConfirmModal').style.display = 'block';
}

function executeDelete() {
    if (ticketToDelete) {
        const form = document.getElementById('deleteForm');
        document.getElementById('deleteTicketId').value = ticketToDelete;
        form.submit();
    }
}

function closeDeleteModal() {
    document.getElementById('deleteConfirmModal').style.display = 'none';
    ticketToDelete = null;
}

function validateTicketForm(formId) {
    const form = document.getElementById(formId);
    const price = parseFloat(form.querySelector('[name="price"]').value);
    const quantity = parseInt(form.querySelector('[name="quantity"]').value);
    
    if (isNaN(price) || price <= 0) {
        alert('Price must be greater than 0');
        return false;
    }
    
    if (isNaN(quantity) || quantity <= 0) {
        alert('Quantity must be greater than 0');
        return false;
    }
    
    return true;
}

function initializeModalHandlers() {
    window.onclick = function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = 'none';
        }
    }

    document.querySelectorAll('.close').forEach(button => {
        button.onclick = function() {
            this.closest('.modal').style.display = 'none';
        }
    });
}

function initializeFormValidation() {
    const addForm = document.getElementById('addTicketForm');
    if (addForm) {
        addForm.addEventListener('submit', function(e) {
            if (!validateTicketForm('addTicketForm')) {
                e.preventDefault();
            }
        });
    }

    const editForm = document.getElementById('editTicketForm');
    if (editForm) {
        editForm.addEventListener('submit', function(e) {
            if (!validateTicketForm('editTicketForm')) {
                e.preventDefault();
            }
        });
    }
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'LKR'
    }).format(amount);
}

function formatDate(dateString) {
    return new Date(dateString).toLocaleString();
}
