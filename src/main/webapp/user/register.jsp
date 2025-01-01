<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/auth-styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            background: url('https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?q=80&w=2070&auto=format&fit=crop') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            height: 100vh;
        }
        .auth-container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: rgba(0, 0, 0, 0.6);
            padding: 20px;
        }
        .auth-card {
            background: rgba(255, 255, 255, 0.9);
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
            width: 600px;
        }
        .form-group {
            margin-bottom: 25px;
        }
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: white;
            font-size: 14px;
        }
        button {
            width: 100%;
            padding: 14px;
            background: #e50914;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 20px;
            font-weight: bold;
        }
        button:hover {
            background: #b2070f;
            transition: background 0.3s ease;
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 35px;
            font-size: 28px;
        }
        .error-message {
            background: #ffebee;
            color: #c62828;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 25px;
        }
        a {
            color: #e50914;
            text-decoration: none;
            font-weight: 500;
        }
        a:hover {
            text-decoration: underline;
        }
        .form-row {
            display: flex;
            gap: 30px;
            margin-bottom: 25px;
        }
        .form-row .form-group {
            flex: 1;
            margin-bottom: 0;
        }
        .input-group {
            margin-bottom: 35px;
        }
    </style>
</head>
<body>
    <div class="auth-container">
        <div class="auth-card">
            <h2><i class="fas fa-film"></i> Cinema Registration</h2>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/user/auth" method="post">
                <input type="hidden" name="action" value="register">
                
                <div class="input-group">
                    <div class="form-row">
                        <div class="form-group">
                            <label><i class="fas fa-user"></i> Username</label>
                            <input type="text" name="username" required placeholder="Choose a username">
                        </div>
                        <div class="form-group">
                            <label><i class="fas fa-envelope"></i> Email</label>
                            <input type="email" name="email" required placeholder="Enter your email">
                        </div>
                    </div>
                </div>
                
                <div class="input-group">
                    <div class="form-row">
                        <div class="form-group">
                            <label><i class="fas fa-phone"></i> Phone Number</label>
                            <input type="tel" name="phoneNumber" required placeholder="Enter phone number">
                        </div>
                        <div class="form-group">
                            <label><i class="fas fa-id-card"></i> NIC</label>
                            <input type="text" name="nic" required placeholder="Enter NIC number">
                        </div>
                    </div>
                </div>
                
                <div class="input-group">
                    <div class="form-group">
                        <label><i class="fas fa-venus-mars"></i> Gender</label>
                        <select name="gender" required>
                            <option value="">Select gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="OTHER">Other</option>
                        </select>
                    </div>
                </div>
                
                <div class="input-group">
                    <div class="form-row">
                        <div class="form-group">
                            <label><i class="fas fa-lock"></i> Password</label>
                            <input type="password" name="password" required placeholder="Create password">
                        </div>
                        <div class="form-group">
                            <label><i class="fas fa-lock"></i> Confirm Password</label>
                            <input type="password" name="confirmPassword" required placeholder="Confirm password">
                        </div>
                    </div>
                </div>
                
                <button type="submit">Register</button>
            </form>
            <p style="text-align: center; margin-top: 25px;">Already have an account? <a href="${pageContext.request.contextPath}/user/login.jsp">Login here</a></p>
        </div>
    </div>
</body>
</html>
