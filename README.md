# carros_springboot
API dos Carros no SpringBoot





## Usage with docker database

Contents 

- Mysql 5.7.30
- Adminer php

Run 

```sh
docker-compose up
```


Open browser to `http://localhost:8000/` to access adminer interface to mysql
user :root
password : LOOK docker-compose file

### Restoring data

1 - create the database :

```sql
CREATE DATABASE `carros` COLLATE 'utf8_bin';
```

2 - in `livro` database click on *SQL command*

Paste the contents of file `backup_carros.sql.txt`.
Now paste the contents of `03-user.txt`.


## Run service

Run 

```sh
./mvnw spring-boot:run
```

URLs:

http://localhost:8080/api/v1/carros/
http://localhost:8080/api/v1/carros/tipo/esportivos
