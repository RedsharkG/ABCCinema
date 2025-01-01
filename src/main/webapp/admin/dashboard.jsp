<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Cinema Admin</title>
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="container">
        <jsp:include page="includes/sidebar.jsp">
            <jsp:param name="page" value="dashboard"/>
        </jsp:include>
        
        <div class="main-content">
            <!-- Header Banner -->
            <div class="header-banner">
                <img src="../images/banner.png" alt="Cinema Banner" class="banner-img">
            </div>

            <!-- Quick Stats -->
            <div class="stats-container">
                <div class="stat-card">
                    <i class="fas fa-ticket-alt"></i>
                    <h3>Total Tickets</h3>
                    <p>1,234</p>
                </div>
                <div class="stat-card">
                    <i class="fas fa-users"></i>
                    <h3>Active Users</h3>
                    <p>567</p>
                </div>
                <div class="stat-card">
                    <i class="fas fa-film"></i>
                    <h3>Movies</h3>
                    <p>89</p>
                </div>
                <div class="stat-card">
                    <i class="fas fa-chart-line"></i>
                    <h3>Revenue</h3>
                    <p>$12,345</p>
                </div>
            </div>

            <!-- Dashboard Grid -->
            <div class="dashboard-grid">
                <!-- Recent Bookings -->
                <div class="dashboard-card">
                    <h3><i class="fas fa-clock"></i> Recent Bookings</h3>
                    <div class="booking-list">
                        <div class="booking-item">
                            <div class="booking-info">
                                <h4>John Doe</h4>
                                <p>Dune Part II - 2 tickets</p>
                                <span class="status confirmed">Confirmed</span>
                            </div>
                            <div class="booking-actions">
                                <button class="btn-view"><i class="fas fa-eye"></i></button>
                                <button class="btn-edit"><i class="fas fa-edit"></i></button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Theater Status -->
                <div class="dashboard-card">
                    <h3><i class="fas fa-video"></i> Theater Status</h3>
                    <div class="theater-grid">
                        <div class="theater-item">
                            <h4>Theater 1</h4>
                            <div class="occupancy-bar">
                                <div class="occupancy-fill" style="width: 75%;">75%</div>
                            </div>
                            <p>Now Playing: Dune Part II</p>
                            <p>Next Show: 7:30 PM</p>
                        </div>
                    </div>
                </div>

                <!-- Revenue Chart -->
                <div class="dashboard-card">
                    <h3><i class="fas fa-chart-bar"></i> Revenue Analytics</h3>
                    <div class="chart-container">
                        <canvas id="revenueChart"></canvas>
                    </div>
                </div>

                <!-- Popular Movies -->
                <div class="dashboard-card">
                    <h3><i class="fas fa-star"></i> Top Movies</h3>
                    <div class="movie-grid">
                        <div class="movie-item">
                            <img src="../images/dune.png" alt="Movie">
                            <div class="movie-details">
                                <h4>Dune Part II</h4>
                                <div class="rating">
                                    <i class="fas fa-star"></i>
                                    <span>4.8/5</span>
                                </div>
                                <div class="sales">
                                    <i class="fas fa-ticket-alt"></i>
                                    <span>1,234 tickets</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <!-- <script src="../js/dashboard.js"></script> -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            console.log('Page loaded with modal:', !!document.getElementById('logoutModal'));
        });
    </script>
</body>
</html>
