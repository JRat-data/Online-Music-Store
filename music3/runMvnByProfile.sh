# for Linux/Mac
# run webapp or SystemTest or other client app using mvn
# be sure to load DB before using this the first time
# Usage: runMvnByProfile oracle|mysql|h2file web|SystemTest|UserApp|AdminApp
# This makes "active" one of the profiles (oracle or ...) defined in src/main/resources
#   which partially override the setup in application.properties
mvn spring-boot:run -Dspring.profiles.active=$1 -Dspring-boot.run.arguments=$2
