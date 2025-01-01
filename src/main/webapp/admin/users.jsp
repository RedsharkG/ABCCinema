<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>User Management - ABC Cinema</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>
    <div class="container">
        <jsp:include page="includes/sidebar.jsp">
            <jsp:param name="page" value="users"/>
        </jsp:include>
        
        <div class="main-content">
            <div class="page-header">
                <h1><i class="fas fa-users"></i> User Management</h1>
                <div class="header-actions">
                    <button class="btn btn-primary" onclick="exportUserData()">
                        <i class="fas fa-download"></i> Export Data
                    </button>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <table id="usersTable" class="table table-hover">
                        <thead>
                            <tr>
                                <th>Profile</th>
                                <th>Username</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Gender</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>
                                        <div class="user-avatar">
                                            <c:choose>
                                                <c:when test="${not empty user.profileImageUrl}">
                                                    <img src="${user.profileImageUrl}" alt="Profile">
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-user"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </td>
                                    <td>${user.username}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>${user.gender}</td>
                                    <td>
                                        <span class="status-badge ${user.status == 'active' ? 'active' : 'inactive'}">
                                            ${user.status}
                                        </span>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <button class="btn btn-icon" onclick="viewUser('${user.userId}')">
                                                <i class="fas fa-eye"></i>
                                            </button>
                                            <button class="btn btn-icon" onclick="toggleUserStatus('${user.userId}')">
                                                <i class="fas fa-power-off"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- User Details Modal -->
    <div class="modal fade" id="userDetailsModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">User Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <!-- Modal content will be dynamically populated -->
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        function viewUser(userId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/users',
                type: 'GET',
                data: { action: 'view', userId: userId },
                success: function(response) {
                    $('#userDetailsModal').modal('show');
                    // Populate modal with user details
                }
            });
        }

        function toggleUserStatus(userId) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/users',
                type: 'POST',
                data: { 
                    action: 'toggleStatus', 
                    userId: userId 
                },
                success: function(response) {
                    if (response.success) {
                        location.reload();
                    }
                }
            });
        }

        $(document).ready(function() {
            $('#usersTable').DataTable({
                pageLength: 10,
                order: [[1, 'asc']],
                responsive: true
            });
        });
    </script>
</body>
</html>
