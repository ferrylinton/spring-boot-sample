# OpenID ConnectServer


## JVM
```
-Xmx1024m -Xms256m
```

## Docker

### MySql
```
docker run --name mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=oic -e MYSQL_USER=oic -e MYSQL_PASSWORD=oic -p 3306:3306 -d mysql --default-authentication-plugin=mysql_native_password
```

### Postgresql
```
docker run --name postgres -p 5432:5432 -e POSTGRES_DB=oic -e POSTGRES_USER=oic -e POSTGRES_PASSWORD=oic -d postgres
```

## Run

### Hsql
```
mvn jetty:run -Dspring.profiles.active=hsql,db
mvn jetty:run -Dspring.profiles.active=hsql
mvn jetty:run -Dspring.profiles.active=hsql -DshowSql=true -DdisableCache=true
```

### MySql
```
mvn jetty:run -Dspring.profiles.active=mysql,db
mvn jetty:run -Dspring.profiles.active=mysql
mvn jetty:run -Dspring.profiles.active=mysql -DshowSql=true -DdisableCache=true
```

### Postgresql
```
mvn jetty:run -Dspring.profiles.active=postgresql,db
mvn jetty:run -Dspring.profiles.active=postgresql
mvn jetty:run -Dspring.profiles.active=postgresql -DshowSql=true -DdisableCache=true
```