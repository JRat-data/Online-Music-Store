rem for Windows
rem run webapp or SystemTest or other client app using mvn
rem be sure to load DB before using this the first time
rem Usage: runMvnByProfile oracle|mysql|h2file web|SystemTest|UserApp|AdminApp
rem This makes "active" one of the profiles (oracle or ...) defined in src/main/resources
rem   which partially override the setup in application.properties
mvn spring-boot:run -Dspring.profiles.active=%1 -Dspring-boot.run.arguments=%2
