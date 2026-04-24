FROM openjdk:24-rc-oraclelinux8 as builder

WORKDIR /app/

COPY . .


RUN ./gradlew :web-api:shadowJar --stacktrace
RUN ./gradlew :modules:kenerator:sql:shadowJar --stacktrace

FROM openjdk:24-rc-oraclelinux8 as parser

WORKDIR /app/

COPY --from=builder /app/jars/IRDBBackend-parser-*.jar parser.jar

COPY IRDB IRDB

ENV IR_FOLDER_PATH="./IRDB/database"
ENV FBACKEND_DB_TYPE="H2"
ENV DB_FULL_PATH="./output/database"

RUN java -jar parser.jar

FROM openjdk:24-rc-oraclelinux8

COPY --from=builder /app/jars/IRDBBackend-web-*.jar web.jar
COPY --from=parser /app/output/database.mv.db database.mv.db

COPY IRDB IRDB

ENV IR_FOLDER_PATH="./IRDB/database"
ENV FBACKEND_DB_TYPE="H2"
ENV DB_FULL_PATH="./database"
ENV FBACKEND_PORT=8080

CMD java -jar web.jar

