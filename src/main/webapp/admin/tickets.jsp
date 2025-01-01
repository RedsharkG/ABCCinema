<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ticket Management</title>
    <meta name="contextPath" content="${pageContext.request.contextPath}">
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/ticket-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="container">
        <jsp:include page="includes/sidebar.jsp">
            <jsp:param name="page" value="tickets"/>
        </jsp:include>
        
        <div class="main-content">
            <div class="ticket-management">
                <!-- Ticket Stats -->
                <div class="ticket-stats">
                    <div class="stat-card">
                        <i class="fas fa-ticket-alt"></i>
                        <h3>Available Tickets</h3>
                        <p>450</p>
                    </div>
                    <div class="stat-card">
                        <i class="fas fa-shopping-cart"></i>
                        <h3>Sold Today</h3>
                        <p>127</p>
                    </div>
                    <div class="stat-card">
                        <i class="fas fa-clock"></i>
                        <h3>Reserved</h3>
                        <p>45</p>
                    </div>
                </div>

                <!-- Ticket Actions -->
                <div class="ticket-actions">
                    <button class="action-btn" onclick="showAddModal()">
                        <i class="fas fa-plus"></i> Add New Tickets
                    </button>
                    <button class="action-btn" onclick="showPricingModal()">
                        <i class="fas fa-edit"></i> Modify Pricing
                    </button>
                    <button class="action-btn" onclick="showBulkDeleteModal()">
                        <i class="fas fa-trash"></i> Remove Tickets
                    </button>
                </div>

                <!-- Ticket List -->
                <div class="ticket-list">
                    <h2>Current Tickets</h2>
                    <table class="ticket-table">
                        <thead>
                            <tr>
                                <th>Ticket ID</th>
                                <th>Movie</th>
                                <th>Show Time</th>
                                <th>Price</th>
                                <th>Qty</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${tickets}" var="ticket">
                                <tr>
                                    <td>#${ticket.ticketId}</td>
                                    <td>${ticket.movieTitle}</td>
                                    <td>${ticket.showTime}</td>
                                    <td>${ticket.price}</td>
                                    <td>${ticket.quantity}</td>
                                    <td><span class="status ${fn:toLowerCase(ticket.status)}">${ticket.status}</span></td>
                                    <td class="action-buttons">
                                        <button class="btn-edit" onclick="editTicket('${ticket.ticketId}', '${ticket.price}', '${ticket.quantity}', '${ticket.status}')">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                        <button class="btn-delete" onclick="confirmDelete('${ticket.ticketId}')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Add Ticket Modal -->
                <div id="addModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <h2>Add New Ticket</h2>
                        <form id="addTicketForm" action="${pageContext.request.contextPath}/admin/tickets" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group">
                                <label>Movie:</label>
                                <select name="movieId" required>
                                    <c:forEach items="${movies}" var="movie">
                                        <option value="${movie.movieId}">
                                            ${movie.title} (${movie.status})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Price:</label>
                                <input type="number" step="0.01" name="price" required>
                            </div>
                            <div class="form-group">
                                <label>Quantity:</label>
                                <input type="number" name="quantity" required>
                            </div>
                            <div class="form-group">
                                <label>Status:</label>
                                <select name="status" required>
                                    <option value="AVAILABLE">Available</option>
                                    <option value="SOLD">Sold</option>
                                    <option value="RESERVED">Reserved</option>
                                </select>
                            </div>
                            <button type="submit" class="submit-btn">Add Ticket</button>
                        </form>
                    </div>
                </div>

                <!-- Edit Ticket Modal -->
                <div id="editModal" class="modal">
                    <div class="modal-content">
                        <span class="close">&times;</span>
                        <h2>Edit Ticket</h2>
                        <form id="editTicketForm" action="${pageContext.request.contextPath}/admin/tickets" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="ticketId" id="editTicketId">
                            <div class="form-group">
                                <label>Price:</label>
                                <input type="number" step="0.01" name="price" id="editPrice" required>
                            </div>
                            <div class="form-group">
                                <label>Quantity:</label>
                                <input type="number" name="quantity" id="editQuantity" required>
                            </div>
                            <div class="form-group">
                                <label>Status:</label>
                                <select name="status" id="editStatus" required>
                                    <option value="AVAILABLE">Available</option>
                                    <option value="SOLD">Sold</option>
                                    <option value="RESERVED">Reserved</option>
                                </select>
                            </div>
                            <button type="submit" class="submit-btn">Update Ticket</button>
                        </form>
                    </div>
                </div>

                <!-- Delete Confirmation Alert -->
                <div id="deleteConfirmModal" class="modal">
                    <div class="modal-content">
                        <h2><i class="fas fa-exclamation-triangle"></i> Confirm Delete</h2>
                        <p>Are you sure you want to delete this ticket?</p>
                        <div class="modal-actions">
                            <button onclick="executeDelete()" class="btn-delete">Delete</button>
                            <button onclick="closeDeleteModal()" class="btn-cancel">Cancel</button>
                        </div>
                    </div>
                </div>

                <!-- Delete Form -->
                <form id="deleteForm" method="post" style="display: none;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="ticketId" id="deleteTicketId">
                </form>
            </div>
        </div>
    </div>

    <script src="../js/ticket-management.js"></script>
</body>
</html>
