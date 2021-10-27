## Endpoints and Payload

Please use exported collection for Postman in the root project directory
`School.postman_collection.json`

## Infrastructure

Please install and use docker compose.

Run:
```
cd infrastructure
docker-compose up -d
```

## Build the project

```
mvn clean install
```

## Run the project
```
 java -jar ./target/school-0.0.1-SNAPSHOT.jar
```

## Database migration
We have flyway database migration framework integrated into the project.
Please add new migration in the directory `src/main/resources/db/migration`