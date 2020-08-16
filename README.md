# Product / Order CRUD REST API

### Requirements
* JDK 14
* docker and docker-compose
* bash

Note that this has been tested on linux only - Windows will require WSL or cygwin. OS X should work.

### Running
To launch the site with postgres simply run:
```shell script
> gradle bootRun
```
To run tests:
```shell script
> gradle test
```

To stop the running docker postgres instance:
```shell script
> docker-compose -f resources/postgresql.yml down
```

Note that postgres persisted data is stored in `./resources/postgres-data` with owner set to the current uid

### Notes
* The implementation is reactive with webflux and R2DBC
* MapStruct was not used to map between entity and DTO types to demonstrate avoiding unnecessary allocations when 
converting between types. This can cause GC stress in high throughput / large request situations.
<br>The Repository layer maps between the types during query/bind time, however this could easily be replaced with 
MapStruct conversions if performance is not a concern. However, as with any architecture, these aspects are always 
difficult to change later down the line (when performance issues arise).

### API
The api endpoints can be accessed as follows:
```shell script
> curl http://localhost:8080/api/products | jq
> curl http://localhost:8080/api/orders\?from\=2014-04-23T04:30:45.123Z\&to\=2021-04-23T04:30:45.123Z | jq
> curl -X POST -H "Content-Type: application/json" -d \
    '{"productSkus":["A1213","A12113","A1214"],"buyerEmail":"me@me.com"}' \
    http://localhost:8080/api/orders
```

Swagger UI can be launched via: http://localhost:8080/swagger-ui.html

The raw openapi is accessible at:
http://localhost:8080/v3/api-docs

### Troubleshooting
Should there be an issue with docker, you can completely reset your docker environment with:
```shell script
docker stop `docker ps -a -q`; docker rm `docker ps -a -q`; docker system prune -f
```