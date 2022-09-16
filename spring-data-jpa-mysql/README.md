# Spring Data JPA for MySQL

## Run MySQL

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
-e="TZ=Asia/Shanghai" \
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

# verify timezone
select now();
```

## Create Database and Tables

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

### Default CRUD method
- Default methods in repository
   - See `CrudRepository` interface
  
### Derived Query
- Derived query:
   - equals to : `findBy<Column>` (`WHERE column = ?`)
   - like: `findBy<Column>Like` (`WHERE column like ?`)
   - containing: `findBy<Column>Containing` (`WHERE column like '%?%'`)
   - in: `findBy<Column>In` (`WHERE column IN('aa', 'bb')`)
   - greater than: `findBy<Column>GreaterThan` (`WHERE column > ?`)
   - less than: `findBy<Column>LessThan` (`WHERE column < ?`)
   - between: `findBy<Column>Between` (`WHERE column BETWEEN ? AND ?`)
- Derived count query
   - `countByXXX`
- Derived delete
   - `deleteByXXX`
   - `removeByXXX`
- Derived update
  - Currently, doesn't support derived update
  
References
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.subject

  
### Sorting
- Static Sorting
   - `findByDeletedFalseOrderByPublisherNameAsc`
- Dynamic Sorting
```java
    public List<Publisher> findAllPublishersSorted(String sort, String direction) {
        Sort dynamicSort = Sort.by(Sort.Direction.fromOptionalString(direction).orElse(Sort.Direction.ASC),
                PublisherSort.valueOfSortOptional(sort).orElse(PublisherSort.CREATED).getField());
        return PublisherMapper.MAPPER.toPublisherList(
                publisherRepository.findByDeletedFalse(dynamicSort));
    }
```
  
  
### Pagination
- Pagination
```java
    public Page<Publisher> findAllPublishersPageable(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), Math.min(size, 10),
                Sort.by(PublisherSort.ONBOARD.getField()));
        return PublisherMapper.MAPPER.toPublisherPage(
                publisherRepository.findByDeletedFalse(pageable));
    }
```

Customize page dto:
- See `PageDto.java`

Slice (faster pagination with less information):
- TBD

  
References
- https://stackoverflow.com/questions/46186864/mapstruct-return-type
- https://github.com/mapstruct/mapstruct/issues/607
- https://stackoverflow.com/questions/63176297/how-do-i-make-a-dto-for-the-page-interface

### Customize query with @Query
- Customize query with JPQL
```java
    @Query("SELECT p FROM PublisherJpaEntity p WHERE p.deleted = false AND p.id = ?1")
    Optional<PublisherJpaEntity> findPublisherById(Long id);
```
- Customize update with Native SQL, and use named parameter instead of positional parameter
```java
    @Modifying
    @Query(value = "UPDATE PublisherJpaEntity p SET p.deleted = :deleted, p.updateTime = :updateTime WHERE p.id = :id")
    @Transactional
    void updateDeletedAndUpdateTimeById(@Param("deleted") boolean deleted,
                                        @Param("updateTime") LocalDateTime updateTime,
                                        @Param("id") Long id);
```
- Cutomize query with Native SQL
```java
    @Query(
        value = "SELECT p.* FROM t_publisher p WHERE p.is_deleted = 0 ORDER BY p.publisher_name ASC",
        nativeQuery = true
    )
    List<PublisherJpaEntity> findAllPublishers();
```

### Customize query with @NamedQuery

TBC

## Simple CRUD REST API test

Create a publisher:
```bash
echo '{"publisherName": "精华出版社", "city": "Guangzhou", "onboardDate": "2009-12-01"}' | http POST :8080/publishers
echo '{"publisherName": "滨海出版社", "city": "Xiamen", "onboardDate": "2012-11-21"}' | http POST :8080/publishers
echo '{"publisherName": "青春出版社", "city": "Guangzhou", "onboardDate": "2013-06-05"}' | http POST :8080/publishers
echo '{"publisherName": "征途出版社", "city": "Guangzhou", "onboardDate": "2013-06-05"}' | http POST :8080/publishers
echo '{"publisherName": "星辰出版社", "city": "Guangzhou", "onboardDate": "2020-02-02"}' | http POST :8080/publishers
echo '{"publisherName": "哈尼出版社", "city": "Guangzhou", "onboardDate": "2021-04-12"}' | http POST :8080/publishers
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

Search examples with `findBy`:
```bash
# findByCity
http :8080/publishers/search/city city==Guangzhou

# findByCityLike
http :8080/publishers/search/citylike city==Guang

# findByCityContaining
http :8080/publishers/search/citycontaining city==ang

# findByCityIn
http :8080/publishers/search/cityin city1==Guangzhou city2==Xiamen

# findByOnboardDateGreaterThan
http :8080/publishers/search/onboardgreaterthan onboard==2010-10-10

# findByOnboardDateLessThan
http :8080/publishers/search/onboardlessthan onboard==2010-10-10

# findByOnboardDateBetween
http :8080/publishers/search/onboardbetween start==2010-10-10 end==2013-05-05
```

Sorting:
```bash
http :8080/publishers/search/sorting sort==created direction==asc
http :8080/publishers/search/sorting sort==updated direction==asc
http :8080/publishers/search/sorting sort==name direction==asc
```

Pagination:
```bash
http :8080/publishers/search/paging page==1 size==3
```

Paging with cutomize pagedto
```bash
http :8080/publishers/search/pagingpagedto page==1 size==3
```

Customized pagedto example:
```json
{
    "content": [
        {
            "city": "Guangzhou",
            "id": 1,
            "onboardDate": "2009-12-01",
            "publisherName": "精华出版社"
        },
        {
            "city": "Xiamen",
            "id": 2,
            "onboardDate": "2012-11-21",
            "publisherName": "滨海出版社"
        },
        {
            "city": "Guangzhou",
            "id": 3,
            "onboardDate": "2013-06-05",
            "publisherName": "青春出版社"
        }
    ],
    "page": {
        "pageNumber": 1,
        "pageSize": 3,
        "totalElements": 6,
        "totalPages": 2
    }
}
```

## JPA One to One

有两种方式实现1-1关系：
1. 外键 （适用于有1-1关联关系的两个表）
2. 共享主键 （适用于1-1的主表和明细表）

现在一般不主张使用外键，而是在应用层面实现外键概念。
因此本例子不在数据库表上建立外键，也不使用JPA自带的`@OneToOne`相关注解。

本例做法：
1. 将表A的id作为表B的1-1关联字段，并在表B中设置该字段为唯一索引，但是不将该字段作为外键。

Sample code:
- Entity Layer
  - `BookJpaEntity.java`
  - `BookDetailJpaEntity.java`
- Repository Layer
  - `BookRepository.java`
  - `BookDetailRepository.java`
- Service Layer
  - `BookService.java`


References:
- https://www.baeldung.com/jpa-one-to-one
- 阿里Java开发规范

## JPA One to One test

### Create a book
API:
```bash
POST /books
```

Request body:
```json
{
  "bookName": "乡土中国",
  "isbn": "978654321",
  "price": 22.8,
  "publishDate": "2019-10-01",
  "pageCount": 208,
  "wordCount": 1000000,
  "paperFormat": "32开",
  "paperType": "胶版纸",
  "packageType": "平装-胶订"
}
```

## JPA One to Many
TBC

## JPA Many to Many
TBC


## JPA Transaction

Default behaviour:
- Enable Transaction Management by default
- The default `propagation` in `@Tranactional` is `REQUIRED`
- The fefault isolation level is `DEFAULT` (same as database ?)

Pitfalls:
- Any **self-invocation** calls will NOT start any transaction
- Only **public methods** should be annotated with `@Transactional`

Usage
- Use `@Transactional` at class level or method level
- Notes only public method will work

References:
- https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
- https://thorben-janssen.com/transactions-spring-data-jpa/
- https://www.baeldung.com/spring-transactional-propagation-isolation




## References

Spring Docs:
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
- https://spring.io/guides/gs/accessing-data-mysql/
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
- https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.subject

  
- https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/
- https://www.bezkoder.com/spring-jpa-query/
- https://www.bezkoder.com/jpa-many-to-many/
  

- https://www.baeldung.com/spring-data-jpa-modifying-annotation
- https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
- https://stackoverflow.com/questions/10394857/how-to-use-transactional-with-spring-data

- https://www.baeldung.com/spring-data-jpa-query

- https://attacomsian.com/blog/spring-data-jpa-query-annotation
- https://attacomsian.com/blog/derived-query-methods-spring-data-jpa
- https://attacomsian.com/blog/spring-data-jpa-sorting
- https://github.com/attacomsian/code-examples/tree/master/spring-data-jpa