# Projects

## Run With Embedded Server

```
mvn spring-boot:run
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```
## Deploy

### Minify HTML/CSS/JS
```
mvn generate-resources
```

### Build war
```
mvn clean package -DskipTests=false
```

### Copy To Server
```
scp target/loyalty-0.0.1-SNAPSHOT.war root@192.227.166.217:/loyalty
```

### Run
```
nohup mvn spring-boot:run &
nohup java -jar target/loyalty-0.0.1-SNAPSHOT.war &
```