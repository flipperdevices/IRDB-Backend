# IRDB-Backend

### Start server

```bash
# Run parser and fill DB
./gradlew :modules:parser:run
# Run server
./gradlew :web-api:run
```

### Shadow server

Output jars will be located in [generated ./jars folder](./jars)

```bash
# Shadow parser
./gradlew :modules:parser:shadowJar
# Shadow server
./gradlew :web-api:shadowJar
```

### Environment

```properties
# Path to https://github.com/flipperdevices/IRDB/tree/dev/database
IR_FOLDER_PATH="./IRDB/database"
FBACKEND_PORT=8080
# H2 Section
FBACKEND_DB_TYPE="H2"
DB_FULL_PATH="./folder/DB_FILE"
```