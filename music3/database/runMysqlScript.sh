# needs mysql program available, so works on topcat but not at home
# Usage; runMysqlScript createdb.sql|showdb.sql|dropdb.sql
mysql -u $MYSQL_USER -p -D ${MYSQL_USER}db < $1
