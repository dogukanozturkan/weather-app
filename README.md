Weather Forecast App with heroku-springmvc-hibernate-mysql
========================

Quickstart for a Spring MVC Cloud oriented (Heroku) Weather Forecast Web Application backed by Hibernate, deployable to Heroku. 

* You can check Weather Forecast using Wunderground Weather Forecast API. 
* Admin Role can create/edit/update or delete users, adding new cities.
* Users can forecast the wheater on listed cities. 
* Administrator and User roles can access logs of people using the weather service.

During application startup a default "root" user is created in the database - the password is "root"  respectively (the same as the username). 

You can use the root account to login to the application and also you can build your own database  with given weather-app.sql file under the "resources" directory.

The purpose of Weather Forecast app is to demonstrate how to integrate, in a robust manner, at least these technologies together in a single-page web application:

 - Bootstrap;
 - Spring MVC;
 - Spring Security;
 - Hibernate
 - MySQL;
 - Heroku;

## Build

    mvn package
    

## Run

Running requires:

* You must have Java 1.8 installed.
* You must have Maven 3.x installed.


### Run Via Command Line (Java+Tomcat)

    java -jar target/dependency/webapp-runner.jar target/*.war


### Run Via Eclipse WTP

1. Project::Right-Click::Properties::Project Facts tab
2. Check: Dynamic Web Module (3.0+), Java  (1.6+), JavaScript 
3. Add to web container (Tomcat, VMWare TCServer, etc.) 
4. Configure MySQL properties if you want to work on your local otherwhise application will work on remote test db.

## Verify

Use a browser to access:

* http://localhost:8080/weather-app/
* http://localhost:8080/weather-app/login

