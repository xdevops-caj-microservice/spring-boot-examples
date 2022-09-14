# Spring Data JPA for MySQL

## MySQL

MySQL softwares and tools:
- MySQL 8 with container
- MySQL Client (Command Line Tool)
- MySQL Client GUI
    - MySQL Workbench
    - Navicat
    - IDE Plugins

References:
- https://www.javatpoint.com/mysql-tutorial

### Run MySQL with container

```bash
# create folders
mkdir -p /Users/william/data/mysql/mysql-data
mkdir -p /Users/william/data/mysql/config/conf.d


# run mysql with container
docker run \
-d \
--name=local-mysql \
-e="MYSQL_ROOT_PASSWORD=admin123" \
-p 6603:3306 \
-v=/Users/william/data/mysql/config/conf.d:/etc/mysql/conf.d \
-v=/Users/william/data/mysql/mysql-data:/var/lib/mysql \
mysql:latest
```

References:
- https://hevodata.com/learn/docker-mysql
- https://hub.docker.com/_/mysql

### Access MySQL

Install MySQL client if necessary:
```bash
# install mysql client on MacOS
brew install mysql-client

# add to PATH env var
echo 'export PATH="/usr/local/opt/mysql-client/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc

# verify
mysql --version
```

Access MySQL in the host machine:
```bash
mysql -uroot -padmin123 -P6603 -h127.0.0.1
```

Verify by below commands:
```bash
show databases;
```

### Create Database

```sql
CREATE DATABASE test_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Verify:
```bash
show databases;
```

### Create Database User

Create an admin user `admin` with enabling remote access:
```sql
CREATE USER 'admin'@'%' IDENTIFIED BY 'dba123' PASSWORD EXPIRE NEVER;
ALTER USER 'admin'@'%' IDENTIFIED WITH mysql_native_password BY 'dba123';
GRANT ALL ON test_db.* TO 'admin'@'%';
FLUSH PRIVILEGES;
```

Create a normal user `test` with enabling remote access:
```sql
CREATE USER 'test'@'%' IDENTIFIED BY 'test123' PASSWORD EXPIRE NEVER;
ALTER USER 'test'@'%' IDENTIFIED WITH mysql_native_password BY 'test123';
GRANT SELECT, INSERT, UPDATE, DELETE ON test_db.* TO 'test'@'%';
FLUSH PRIVILEGES;
```

Notes:
- You have to enable remote access here, so that you can access containerized MySQL from "remote" host machine

Verify:
```sql
SELECT user, host, account_locked, password_expired FROM mysql.user; 
```


### Create Tables

Access MySQL with admin user:
```bash
mysql -uadmin -pdba123 -P6603 -h127.0.0.1
```

Switch to the database:
```bash
use test_db;
```

Create table SQL as below:
- `src/main/resources/db/migration/V0.1__create_tables.sql`


## Spring Data JPA CRUD

### Database Connection and JPA Settings

See `application.yaml`

Notes:
- Disable auto create/update database schema: `spring.jpa.hiernate.ddl-auto=none` (by default it is `none`)
- Print SQL to help debug: `spring.jpa.show-sql=true`


### Entity class

See `PublisherJpaEntity.java`.

### Repository

See `PublisherRepository.java`.

1. Default methods in repository
2. Auto `findByXXX` method
3. Customize query with JPQL
```java
    @Query("SELECT p FROM PublisherJpaEntity p WHERE p.deleted = false AND p.id = ?1")
    Optional<PublisherJpaEntity> findPublisherById(Long id);
```
4. Customize update with JPQL
```java
    @Modifying
    @Query("UPDATE PublisherJpaEntity p SET p.deleted = true WHERE p.id = ?1")
    @Transactional
    void deletePublisherById(Long id);
```
5. Cutomize query with Native SQL
```java
    @Query(
        value = "SELECT p.* FROM t_publisher p WHERE p.is_deleted = 0 ORDER BY p.publisher_name ASC",
        nativeQuery = true
    )
    List<PublisherJpaEntity> findAllPublishers();
```

## Simple CRUD REST API test

Create a publisher:
```bash
echo '{"publisherName": "精华出版社"}' | http POST :8080/publishers
```

Find a valid publisher:
```bash
http :8080/publishers/1
```

Update a publisher:
```bash
echo '{"publisherName": "梦想出版社"}' | http PUT :8080/publishers/1
```

Delete a publisher (soft delete):
```bash
http DELETE :8080/publishers/1
```

Add another publisher:
```bash
echo '{"publisherName": "新世纪出版社"}' | http POST :8080/publishers
```

Find all valid publishers:
```bash
http :8080/publishers
```

## JPA Many to Many
TBC

## JPA One to Many
TBC

## References

- https://spring.io/guides/gs/accessing-data-mysql/
  
- https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/
- https://www.bezkoder.com/spring-jpa-query/
- https://www.bezkoder.com/jpa-many-to-many/
  

- https://www.baeldung.com/spring-data-jpa-modifying-annotation
- https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
- https://stackoverflow.com/questions/10394857/how-to-use-transactional-with-spring-data