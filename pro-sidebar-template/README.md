# Projects

## Run With Embedded Server

```
mvn spring-boot:run
mvn spring-boot:run -Dspring-boot.run.profiles=prod
mvn spring-boot:run -Drun.jvmArguments='-Dserver.port=8762'
java -jar target/pro-sidebar-template.jar --server.port=8762 -Dspring.profiles.active=dev
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
scp target/pro-sidebar-template.jar root@192.227.166.217:/loyalty
```

### Run
```
nohup mvn spring-boot:run &
nohup java -jar target/pro-sidebar-template.jar &
```