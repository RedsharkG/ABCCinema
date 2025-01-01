# Cinema Booking Admin Panel

A web-based administration panel for managing cinema bookings, built with Java EE and Docker.

## Prerequisites

### Required Software
- Docker Desktop
- Visual Studio Code
- JDK 17 or higher
- Maven

### VS Code Extensions
- Docker
- Java Extension Pack
- Maven for Java
- Extension Pack for Java

## Installation & Setup

1. **Clone the Repository**

git clone https://github.com/yourusername/CinemaBookingAdminPanel.git
cd CinemaBookingAdminPanel


2. Open Project in VS Code

code .


3. Build the Project

mvn clean package


4. Verify Configuration Files
- **Dockerfile**: Container configuration
- **docker-compose.yml**: Multi-container setup
- **src/main/webapp/META-INF/context.xml**: Database connection

5. Docker Setup

#### Start Docker Desktop
- Launch Docker Desktop application
- Ensure Docker Engine is running

#### Build Docker Images

docker-compose build


#### Run the Application

docker-compose up


6. Accessing the Application
- **URL**: [http://localhost:8080](http://localhost:8080)
- **Default Credentials**:
  - Username: `admin`
  - Password: `admin123`

## Container Management

### View Running Containers

docker ps


### Monitor Application Logs

docker-compose logs -f


### Stop the Application

docker-compose down


### Additional Commands

#### Docker Commands

# Rebuild and restart containers
docker-compose up --build

# Remove volumes when stopping
docker-compose down -v

# Check container status
docker-compose ps


## Application Architecture

- **Web Server**: Tomcat (Port 8080)
- **Database**: MySQL (Port 3306)
- **Data Storage**: Docker volumes
