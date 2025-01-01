<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile - ABC Cinema</title>
    <link href="../css/home.css" rel="stylesheet"/>
    <link href="../css/profile.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body class="bg-black text-white">
    <div class="container-fluid">
        <div class="row">
            <div class="col-1"></div>
            <div class="col-10">
                <!-- Navigation Bar -->
                <header>
                    <nav class="navbar navbar-expand-lg my-3">
                        <div class="container-fluid">
                            <div class="logo">
                                <p>ABC CINEMA</p>
                            </div>
                            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                                <span class="navbar-toggler-icon"></span>
                            </button>
                            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                                <ul class="navbar-nav d-flex ms-auto mb-lg-0">
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/home2.jsp">Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/movies">Movies</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/bookings">My Bookings</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link text-white mx-2" href="${pageContext.request.contextPath}/user/feedback">Feedback</a>
                                    </li>
                                </ul>
                                
                                <!-- Profile Dropdown -->
                                <div class="dropdown">
                                    <!-- [Previous dropdown code remains the same] -->
                                </div>
                            </div>
                        </div>
                    </nav>
                </header>

                <!-- Profile Content -->
                <div class="container mt-5">
                    <div class="profile-container">
                        <c:if test="${param.updated == 'true'}">
                            <div class="alert alert-success">Profile updated successfully!</div>
                        </c:if>
                        
                        <!-- View Mode -->
                        <div class="profile-view" id="profileView">
                            <div class="profile-header">
                                <div class="profile-avatar">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user.profileImageUrl}">
                                            <img src="${sessionScope.user.profileImageUrl}" alt="Profile" class="profile-image">
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-user fa-3x"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <h2>${sessionScope.user.username}'s Profile</h2>
                            </div>
                            
                            <div class="profile-details">
                                <div class="detail-item">
                                    <i class="fas fa-envelope"></i>
                                    <div>
                                        <label>Email</label>
                                        <p>${sessionScope.user.email}</p>
                                    </div>
                                </div>
                                
                                <div class="detail-item">
                                    <i class="fas fa-phone"></i>
                                    <div>
                                        <label>Phone Number</label>
                                        <p>${sessionScope.user.phoneNumber}</p>
                                    </div>
                                </div>
                                
                                <div class="detail-item">
                                    <i class="fas fa-id-card"></i>
                                    <div>
                                        <label>NIC</label>
                                        <p>${sessionScope.user.nic}</p>
                                    </div>
                                </div>
                                
                                <div class="detail-item">
                                    <i class="fas fa-venus-mars"></i>
                                    <div>
                                        <label>Gender</label>
                                        <p>${sessionScope.user.gender}</p>
                                    </div>
                                </div>
                            </div>
                            
                            <button type="button" class="btn btn-primary mt-4" onclick="showEditMode()">Update Profile</button>
                        </div>

                        <!-- Edit Mode -->
                        <div class="profile-edit" id="profileEdit" style="display: none;">
                            <form action="${pageContext.request.contextPath}/user/profile/update" method="post" enctype="multipart/form-data">
                                <div class="profile-image-upload">
                                    <img src="${sessionScope.user.profileImageUrl != null ? sessionScope.user.profileImageUrl : '../images/default-avatar.png'}" 
                                         alt="Profile" class="current-profile-image">
                                    <input type="file" name="profileImage" accept="image/*" class="profile-image-input">
                                </div>
                                
                                <div class="profile-details">
                                    <div class="detail-item editable">
                                        <i class="fas fa-envelope"></i>
                                        <div>
                                            <label>Email</label>
                                            <input type="email" name="email" value="${sessionScope.user.email}" required>
                                        </div>
                                    </div>
                                    
                                    <div class="detail-item editable">
                                        <i class="fas fa-phone"></i>
                                        <div>
                                            <label>Phone Number</label>
                                            <input type="tel" name="phoneNumber" value="${sessionScope.user.phoneNumber}" required>
                                        </div>
                                    </div>
                                    
                                    <div class="detail-item editable">
                                        <i class="fas fa-id-card"></i>
                                        <div>
                                            <label>NIC</label>
                                            <input type="text" name="nic" value="${sessionScope.user.nic}" required>
                                        </div>
                                    </div>
                                    
                                    <div class="detail-item editable">
                                        <i class="fas fa-venus-mars"></i>
                                        <div>
                                            <label>Gender</label>
                                            <select name="gender" required>
                                                <option value="MALE" ${sessionScope.user.gender == 'MALE' ? 'selected' : ''}>Male</option>
                                                <option value="FEMALE" ${sessionScope.user.gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                                                <option value="OTHER" ${sessionScope.user.gender == 'OTHER' ? 'selected' : ''}>Other</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="button-group mt-4">
                                    <button type="submit" class="btn btn-primary">Save Changes</button>
                                    <button type="button" class="btn btn-secondary ms-2" onclick="cancelEdit()">Cancel</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-1"></div>
        </div>
    </div>

    <script>
        function showEditMode() {
            document.getElementById('profileView').style.display = 'none';
            document.getElementById('profileEdit').style.display = 'block';
        }

        function cancelEdit() {
            document.getElementById('profileEdit').style.display = 'none';
            document.getElementById('profileView').style.display = 'block';
        }

        // Logout transition
        document.querySelector('.dropdown-item.text-danger').addEventListener('click', function(e) {
            e.preventDefault();
            const logoutLink = this.href;
            
            this.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Logging out...';
            
            document.body.style.opacity = '1';
            document.body.style.transition = 'opacity 0.5s ease';
            
            setTimeout(() => {
                document.body.style.opacity = '0';
            }, 100);

            fetch(logoutLink)
                .then(response => response.json())
                .then(data => {
                    setTimeout(() => {
                        window.location.href = data.redirect;
                    }, 500);
                });
        });
    </script>
</body>
</html>
