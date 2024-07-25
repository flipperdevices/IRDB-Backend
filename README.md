# IRDB-Backend

### Start server

```bash
# Run parser and fill DB
./gradlew :modules:kenerator:sql:run
# Run server
./gradlew :web-api:run
```

### Shadow server

Output jars will be located in [generated ./jars folder](./jars)

```bash
# Shadow parser
./gradlew :modules:kenerator:sql:shadowJar
# Shadow server
./gradlew :web-api:shadowJar
```

### Environment

```properties
# Path to https://github.com/flipperdevices/IRDB/tree/dev/database
IR_FOLDER_PATH="./IRDB/database"
FBACKEND_PORT=8080
# SQLite Section
# [H2, POSTGRES]
FBACKEND_DB_TYPE="H2"
# Only for H2
DB_FULL_PATH="./folder/DB_FILE"
# SQL Remote section
# Only for POSTGRES
DB_NAME=SOME_NAME
DB_HOST=192.168.0.1
DB_PORT=1234
DB_USER=ROOT
DB_PASSWORD=PASSWORD
```