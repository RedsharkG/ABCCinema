<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/error.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <div class="error-container">
        <div class="error-card">
            <i class="fas fa-exclamation-circle"></i>
            <h1>Error Occurred</h1>
            <p>${error}</p>
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/admin" class="btn-primary">
                    <i class="fas fa-home"></i> Return to Dashboard
                </a>
            </div>
        </div>
    </div>
</body>
</html>
