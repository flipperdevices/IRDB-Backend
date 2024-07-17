FROM openjdk:24-slim as builder

WORKDIR /app/

COPY . .


RUN ./gradlew :web-api:shadowJar
RUN ./gradlew :modules:kenerator:sql:shadowJar

FROM openjdk:24-slim as parser

WORKDIR /app/

COPY --from=builder /app/jars/IfrBackend-parser-*.jar parser.jar

COPY IRDB IRDB

ENV IR_FOLDER_PATH="./IRDB/database"
ENV FBACKEND_DB_TYPE="H2"
ENV DB_FULL_PATH="./output/database"

RUN java -jar parser.jar

FROM openjdk:24-slim

COPY --from=builder /app/jars/IfrBackend-web-*.jar web.jar
COPY --from=parser /app/output/database.mv.db database.mv.db

COPY IRDB IRDB

ENV IR_FOLDER_PATH="./IRDB/database"
ENV FBACKEND_DB_TYPE="H2"
ENV DB_FULL_PATH="./database"
ENV FBACKEND_PORT=8080

CMD java -jar web.jar

