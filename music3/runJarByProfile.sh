# for Linux/Mac
# run app or web case using DB's profile, from jar of all dependencies
# i.e., not using mvn and its ability to access its library repository
# be sure to load DB before using this the first time, have current jar built
# Usage: runJarByProfile.sh oracle|mysql|h2 web|SystemTest|UserApp|AdminApp
# This makes "active" one of the profiles (oracle or ...) defined in src/main/resources
# which partially overrides the setup in application.properties
java -jar -Dspring.profiles.active=$1 target/music3-1.jar $2

