# QueryCat :: Spring3MVC-JSP-Dao-SQL Server
Spring3 MVC + JDBC (MS SQL Server) + JSP

# Database Installation
- Require MS SQL Express local installation
- Run company-ddl and dll.sql scripts
# Installation
- 
- Download JDBC Driver(sqljdbc4.jar) for SQL Server (http://go.microsoft.com/fwlink/?LinkId=245496)
- Place the sqljdbc4.jar file to your [tomcat installation]/lib directory
- Clone this source project into your workspace and import it to your Eclipse as Existing Maven Project
- In Eclipse, add the sqljdbc4.jar into your project build path (external jar)
- Open ConnectionURL.java file in Eclipse and modify the URL to your setting (hostname, port, dbname, user, password)
- Maven clean - Install
- Run the ConnectionURL.java to test your JDBC connection to SQL Server

# Test
- http://localhost:8080/QueryCat/rest/query/
- 
