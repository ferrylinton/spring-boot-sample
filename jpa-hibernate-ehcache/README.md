# CRUD

curl -s -D "/dev/stderr"  http://localhost:8080/books/1 | json_pp
curl -s -D "/dev/stderr" http://localhost:8080/books | json_pp
curl -X DELETE http://localhost:8080/books/1