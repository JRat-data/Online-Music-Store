# needs sqlplus program available, so works on topcat but not at home
# Usage runOracleScript createdb.sql|showdb.sql|dropdb.sql
sqlplus ${ORACLE_USER}/${ORACLE_PW}@//dbs3.cs.umb.edu/dbs3 < $1
