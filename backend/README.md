Run this command to start the MySQL container:

docker run --name mysql-desafioawer -e MYSQL_ROOT_PASSWORD=root123 -e MYSQL_DATABASE=desafioawer_db -p 3306:3306 -d mysql:8.0

Run this to execute the SQL script to create the database and tables:

docker exec -i mysql-desafioawer \
mysql -uroot -proot123 desafioawer_db \
< src/main/resources/schema.sql

Run this to execute the SQL script to insert the data into the tables:

docker exec -i mysql-desafioawer \
mysql -uroot -proot123 desafioawer_db \
< src/main/resources/data.sql