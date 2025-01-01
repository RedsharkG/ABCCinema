<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="../css/auth-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="auth-container" style="background: url('../images/admin.png') no-repeat center center fixed; background-size: cover;">
        <div class="auth-card">
            <h2><i class="fas fa-lock"></i> Admin Login</h2>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <form action="${pageContext.request.contextPath}/admin/auth" method="post">
                <input type="hidden" name="action" value="login">
                <div class="form-group">
                    <label><i class="fas fa-user"></i> Username</label>
                    <input type="text" name="username" required>
                </div>
                <div class="form-group">
                    <label><i class="fas fa-key"></i> Password</label>
                    <input type="password" name="password" required>
                </div>
                <button type="submit">Login</button>
            </form>
            <p>Don't have an account? Pleace Contact The Manager</p>
        </div>
    </div>
</body>
</html>
