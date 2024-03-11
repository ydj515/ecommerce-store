## kafka test
1번 shell에서 메시지를 쓰고 2번 shell에서 확인

#### terminal 2개 접속
```sh
#1 docker exec -it kafka sh
#2 docker exec -it kafka sh
```
#### message publish
```sh
#1 cd /opt/kafka/bin
#1 kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic exam-topic
#1 kafka-console-producer.sh --topic exam-topic --broker-list localhost:9092
```

#### message subscribe confirm
```sh
#2 kafka-console-consumer.sh --topic exam-topic --bootstrap-server localhost:9092 --from-beginning
```
#### 현재 topic 확인
```sh
cd /opt/kafka/bin
./opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```


## kafaka connect

#### kafka server.properties 수정
```sh
docker exec -it kafka sh
vi /opt/kafka/config/server.properties

listeners=PLAINTEXT://:9092 # 주석으로 되어있는 부분 해제
advertised.listeners=PLAINTEXT://{ip}:9092
delete.topic.enable=true
auto.create.topics.enable=true
```


#### connector 확인
```sh
curl localhost:8083/connectors
```
결과로 빈 리스트가 넘어온다.

#### jdbc connector plugin 확인
![downlaod](https://github.com/ydj515/record-study/assets/32935365/52b6dde7-cf39-4ea2-81f8-709b2f899974)
`https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc`에서 다운 받아서 lib/하위의 jar 파일들을 lib폴더하위로 복사한다.
```sh
curl http://localhost:8083/connector-plugins
```



#### source connector 생성
토픽 이름은 `topic_prefix + table.whitelist`
```sh
curl --location 'localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
  "name" : "mariadb-source-connector", // 커넥터이름
  "config" : {
    "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
    "connection.url" : "jdbc:mariadb://kafka-mariadb:3306/test", // db url
    "connection.user" : "root", // db user
    "connection.password" : "root", // db password
    "mode": "incrementing", //insert와 update 감지
    "incrementing.column.name" : "id", // insert를 감지할 컬럼
    "table.whitelist" : "users", // table name
    "topic.prefix" : "user_topic_", // topic prefix
    "tasks.max" : "1" // 
  }
}
'
```
- 확인
```sh
curl http://localhost:8083/connectors/mariadb-source-connector
curl http://localhost:8083/connectors/mariadb-source-connector/status
```



#### sink connector 생성

{
    "name":"mariadb-sink-connector",
    "config":{
        "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
        "connection.url":"jdbc:mariadb://kafka-mariadb:3306/test",
        "connection.user":"root",
        "connection.password":"root",
        "auto.create":"true",
        "auto.evolve":"true",
        "delete.enabled":"false",
        "tasks.max":"1",
        "topics":"user_topic_users" // source connector에서 지정한 토픽 이름은 `topic_prefix + table.whitelist`을 기입
    }
}

#### connector 삭제
```sh
curl --location --request DELETE 'http://localhost:8083/connectors/mariadb-source-connector'
```


#### kafka ui

https://devocean.sk.com/blog/techBoardDetail.do?ID=163980

https://velog.io/@shinmj1207/Apache-Kafka-Docker-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-Spring-boot-Kafka-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0



https://velog.io/@shinmj1207/Apache-Kafka-Docker-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C-Spring-boot-Kafka-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0
https://velog.io/@shinmj1207/Apache-Kafka-Kafka-Spring-Boot-%EC%97%B0%EB%8F%99-%EC%98%A4%EB%A5%98-%EB%AA%A8%EC%9D%8C

