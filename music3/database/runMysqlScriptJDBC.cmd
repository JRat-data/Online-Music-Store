rem Use H2's runScript JDBC program to run scripts on mysql
rem Usage: runMysqlScriptJDBC <script>, for ex.,  runMysqlScriptJDBC showdb.sql 
java -cp lib-for-ant/h2.jar;lib-for-ant/mysql-connector-java-8.0.14.jar org.h2.tools.RunScript -url jdbc:mysql://%MYSQL_SITE%/%MYSQL_USER%db -user %MYSQL_USER% -password %MYSQL_PW% -script %1 -showResults
