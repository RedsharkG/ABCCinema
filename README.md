# Cinema Booking Admin Panel

A web-based Movie ticket booking application based on JavaServlets, JSP, HTML, CSS, JavaScripts

## Prerequisites

### Required Software
- Apache Tomcat 9
- Visual Studio Code
- JDK 17 or higher (suggested Jdk 21)
- Maven

### VS Code Extensions
- Prettier
- Java Extension Pack
- Maven for Java
- Extension Pack for Java

## Installation & Setup

1. **Clone the Repository**

https://github.com/RedsharkG/ABCCinema.git

## Database Importing

- Extract the cinema_db.zip file
- Open your mysql workbench
- In servers tab go to data Import
- Browse your extracted file to the Import from Dump Project Folder
- Select all Tables
- Click on start Import
- Open project's folder in VS code
- go to util --> DBConnection.java
- Check mysql DBConnection string's password and username.
- Change your password and username as same as your mysql local instance password and username
- Do the step 4


2. Open Project in VS Code

code .

3. Verify Maven is installed on your computer
- mvn --version

4. Build the Project

mvn clean install
mvn clean package

5. Tomcat Setup

#### Start Tomcat server
- Go to tomcat(version)/bin
- Open command prompt
- On windows run : ./startup.bat
- On mac run : ./startup.sh

#### Setup

- In VS Code go to servers
- Add New Server
- Server on Local Machine
- open Tomcat 9 Folder (Catalina Home)
- Rename it as "Cinema_Server"
- Click finish
- On Servers, right click on Cinema_Server an click 'Add a Deployment'
- Browse your Maven project file's target folder and deploy .war file
- Do the step 4 again
- After every change run 'mvn package'


#### Run the Application

Open your Web browser

6. Accessing the Application
- **URL**: [http://localhost:8080](http://localhost:8080)
- **Default Credentials for manager-gui**:
  - Username: `admin`
  - Password: `admin`


### View Running applications

-click on application name.

## Application Architecture

- **Web Server**: Tomcat (Port 8080)
- **Database**: MySQL (Port 3306)
