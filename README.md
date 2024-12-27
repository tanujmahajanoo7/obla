# Online Booking Library API #
## The project is build using Java SpringBoot and for the database we are using MySql ##
- ####  How to setup and run application locally ####

1. Download the zip file
   - Extract the zip file
2. Open Eclipse and import this project as existing maven project
   - Wait till the project dependencies are installed
   - Create a database named “library_dbˮ
   - Open the “application.propertiesˮ file which is present in the “src/main/resourcesˮ folder and then provide the user and password as per your system that means the MySql credentials.
3. Run LibraryApplication.java file
   - Database schema will be created
4. Execute the queries in mysql
   - Open the queries.txt which is which is present in the “src/main/resourcesˮ folder
   - Copy and paste the queries in mysql.
5. Open the Postman application - Test order API
   - Select the HTTP post method
   - Provide the url: ```http://localhost:8080/order?userId=_&title=_```
   - Make sure to put the userId and title as per the data present in the database table.
   - Click on Send Button
     - If the userId and title are matching with the conditions that are specified in the business logic then it return a SUCCESS message
     - And if the conditions are not satisfied then it will return a error message with the appropriate reason.
6. Open the Postman application  Test return API
   -  Select the HTTP post method
   -   Provide the url:  ```http://localhost:8080/return?userId=_&title=_```
   -   Make sure to put the userId and title as per the data present in the database table.
   -   Click on Send Button.
       -  If the userId and title are matching with the conditions that are specified in the business logic then it return a SUCCESS message
       -  And if the conditions are not satisfied then it will return a error message with the appropriate reason.
7.  To execute the JUnit test
    - Open the folder src/test/java then right click on “LibraryApplicationTests.javaˮ file and run as junit test.
