1. redis cache
post http://localhost:8080/api/cache/products

get http://localhost:8080/api/cache/id
get http://localhost:8080/api/cache/products

put http://localhost:8080/api/cache/id&newname

delete http://localhost:8080/api/cache/id

3. docker => 
- redis/redis-stack:latest
port:6379
port:8001
- mariadb:latest
port:3306