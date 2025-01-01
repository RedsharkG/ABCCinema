<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Feedback Management - ABC Cinema</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
</head>
<body>
    <div class="container">
        <jsp:include page="includes/sidebar.jsp">
            <jsp:param name="page" value="feedback"/>
        </jsp:include>
        
        <div class="main-content">
            <div class="page-header">
                <h1><i class="fas fa-comments"></i> Feedback Management</h1>
            </div>

            <div class="card">
                <div class="card-body">
                    <table id="feedbackTable" class="table table-hover">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Rating</th>
                                <th>Message</th>
                                <th>Date</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${feedbacks}" var="feedback">
                                <tr>
                                    <td>${feedback.username}</td>
                                    <td>
                                        <div class="rating-stars">
                                            <c:forEach begin="1" end="5" var="star">
                                                <i class="fas fa-star ${star <= feedback.rating ? 'active' : ''}"></i>
                                            </c:forEach>
                                        </div>
                                    </td>
                                    <td>${feedback.message}</td>
                                    <td><fmt:formatDate value="${feedback.createdAt}" pattern="MMM dd, yyyy HH:mm"/></td>
                                    <td>
                                        <div class="action-buttons">
    <button class="btn btn-icon" onclick="editFeedback('${feedback.feedbackId}')">
        <i class="fas fa-edit"></i>
    </button>
    <button class="btn btn-icon btn-danger" onclick="deleteFeedback('${feedback.feedbackId}')">
        <i class="fas fa-trash"></i>
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

    <!-- Edit Feedback Modal -->
    <div class="modal fade" id="editFeedbackModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Feedback</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="editFeedbackForm">
                        <input type="hidden" id="feedbackId" name="feedbackId">
                        <div class="mb-3">
                            <label for="rating" class="form-label">Rating</label>
                            <select class="form-control" id="rating" name="rating" required>
                                <option value="1">1 Star</option>
                                <option value="2">2 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="5">5 Stars</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="message" class="form-label">Message</label>
                            <textarea class="form-control" id="message" name="message" rows="3" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" 
                        style="width: 150px; height: 45px; background-color: #6c757d; color: white; font-weight: 500; border: none; border-radius: 8px;">
                        <i class="fas fa-times me-2"></i>Cancel
                    </button>
                    <button type="button" class="btn btn-primary" onclick="updateFeedback()" 
                        style="width: 150px; height: 45px; background-color: #1a237e; color: white; font-weight: 500; border: none; border-radius: 8px;">
                        <i class="fas fa-save me-2"></i>Save Changes
                    </button>
                </div>
                
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Previous HTML code remains the same until the script section -->

<script>
    $(document).ready(function() {
        $('#feedbackTable').DataTable({
            pageLength: 10,
            order: [[3, 'desc']],
            responsive: true
        });
    });
    
    function editFeedback(feedbackId) {
        $.ajax({
            url: '${pageContext.request.contextPath}/admin/feedback',
            type: 'GET',
            data: { 
                action: 'get', 
                id: feedbackId 
            },
            success: function(feedback) {
                $('#feedbackId').val(feedback.feedbackId);
                $('#rating').val(feedback.rating);
                $('#message').val(feedback.message);
                $('#editFeedbackModal').modal('show');
            }
        });
    }
    
    function updateFeedback() {
        var formData = {
            feedbackId: $('#feedbackId').val(),
            rating: $('#rating').val(),
            message: $('#message').val()
        };
    
        $.ajax({
            url: '${pageContext.request.contextPath}/admin/feedback',
            type: 'POST',
            data: {
                action: 'update',
                feedbackId: formData.feedbackId,
                rating: formData.rating,
                message: formData.message
            },
            success: function(response) {
                if(response.success) {
                    location.reload();
                }
            }
        });
    }
    
    function deleteFeedback(feedbackId) {
        if(confirm('Are you sure you want to delete this feedback?')) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin/feedback',
                type: 'POST',
                data: {
                    action: 'delete',
                    feedbackId: feedbackId
                },
                success: function(response) {
                    if(response.success) {
                        location.reload();
                    }
                }
            });
        }
    }
    </script>
    
</body>
</html>
