ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';

grant all on *.* to 'root'@'%';

flush privileges;
