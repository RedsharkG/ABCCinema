// Handle responsive charts
window.addEventListener('resize', function() {
    const charts = document.querySelectorAll('canvas');
    charts.forEach(chart => {
        if (chart.chart) {
            chart.chart.resize();
        }
    });
});

// Mobile navigation handling
document.addEventListener('DOMContentLoaded', function() {
    const mobileWidth = 575;
    const contextPath = document.querySelector('meta[name="contextPath"]').getAttribute('content');
    
    // Test if modal exists
    console.log('Modal element:', document.getElementById('logoutModal'));
    
    // Test if logout functions are accessible
    console.log('Logout functions:', {
        confirmLogout: typeof confirmLogout !== 'undefined',
        executeLogout: typeof executeLogout !== 'undefined',
        closeLogoutModal: typeof closeLogoutModal !== 'undefined'
    });
    
    function handleMobileNav() {
        const nav = document.querySelector('.sidenav');
        if (window.innerWidth <= mobileWidth) {
            nav.classList.add('mobile-nav');
        } else {
            nav.classList.remove('mobile-nav');
        }
    }
    
    window.addEventListener('resize', handleMobileNav);
    handleMobileNav();
});

// Logout handling functions
function confirmLogout() {
    const logoutModal = document.getElementById('logoutModal');
    logoutModal.style.display = 'block';
}

function executeLogout() {
    const form = document.getElementById('logoutForm');
    console.log('Submitting form:', form);
    if(form) {
        const logoutBtn = document.querySelector('.btn-logout');
        logoutBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging out...';
        logoutBtn.disabled = true;
        form.submit();
    } else {
        console.error('Logout form not found');
        window.location.href = contextPath + '/admin/logout';
    }
}

function closeLogoutModal() {
    const logoutModal = document.getElementById('logoutModal');
    logoutModal.style.display = 'none';
}

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('logoutModal');
    if (event.target == modal) {
        closeLogoutModal();
    }
}

// Close modal on ESC key
document.addEventListener('keydown', function(event) {
    if (event.key === "Escape") {
        closeLogoutModal();
    }
});

// Make logout functions globally accessible
window.confirmLogout = confirmLogout;
window.executeLogout = executeLogout;
window.closeLogoutModal = closeLogoutModal;
