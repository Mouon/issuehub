
![logo](https://github.com/Mouon/issuehub/assets/137624597/d13cca72-39b7-4be1-beea-78c6c24e6d58)

# Issuehub

### Introduction
Issuehub는 개인 프로젝트로 개발자들이 인기 있는 오픈 소스 프로젝트의 이슈를 쉽게 확인할 수 있는 플랫폼입니다. Kafka의 강력한 메시지 스트리밍 기능과 Spring Boot의 스케줄링 기능을 활용하여, 
Issuehub는 GitHub 저장소로부터 지속적인 업데이트를 제공합니다. [ 스프링부트와 백엔드 개발을 연습하기 위한 프로젝트입니다.]
<img width="1624" alt="스크린샷 2024-04-21 오후 4 12 26" src="https://github.com/Mouon/issuehub/assets/137624597/2d552f9e-6106-4e38-b438-c92caf893480">
<img width="1624" alt="스크린샷 2024-04-21 오후 4 12 38" src="https://github.com/Mouon/issuehub/assets/137624597/4999084b-94c2-48f1-8175-45594b684b1b">

<img width="1624" alt="스크린샷 2024-04-21 오후 4 13 14" src="https://github.com/Mouon/issuehub/assets/137624597/85ee4bc2-6705-46ef-ae86-aeeb7c2bca52">



### Features
- **실시간 업데이트**:  저장소의 최신 이슈를 계속해서 업데이트합니다.
- **Kafka 통합**: 데이터 피드를 처리하는 고성능 및 확장 가능한 분산 스트리밍 플랫폼을 활용합니다.
- **Spring Boot 스케줄링**: GitHub에서의 최신 데이터가 항상 사용 가능하도록 데이터 가져오기 작업을 스캐줄링 해둡니다.

### Dependencies
- Spring Boot
- Apache Kafka
- GitHub API

# Operation

Issuehub의 주요 목표는 GitHub 저장소에서의 이슈에 대한 실시간 업데이트를 개발자들에게 제공하는 것입니다. 이는 Kafka와 Spring Boot 스케줄링을 결합하여 달성됩니다.

## 목표
Issuehub의 주요 목표는 스프링 부트의 스케줄러를 사용하여 GitHub 데이터를 주기적으로 업데이트하고, Kafka 프로듀서를 통해 해당 데이터를 Kafka 토픽에 발행한 후, 이를 통해 개발자들은 GitHub 저장소의 이슈를 실시간으로 추적하고 업데이트된 정보에 즉각적으로 접근할 수 있습니다.

### 구성 요소
#### Scheduler 설정 (DataUpdateScheduler 클래스)
`@Scheduled(cron = "0 * * * * ?")`: 이 설정은 매 분마다 GitHub 데이터(이슈와 README 파일)를 주기적으로 가져오는 스케줄러를 구성합니다. 이 데이터는 `GitHubDataService`를 통해 처리되며, 이는 데이터의 실시간 추적과 업데이트를 가능하게 합니다.

#### Kafka Producer 설정 (KafkaProducerConfig 클래스)
Kafka Producer는 Kafka에 데이터를 보내는 설정을 담당합니다. `KafkaTemplate`를 사용하여 Kafka 토픽에 데이터를 발행합니다.

주요 설정은 다음과 같습니다:
- `BOOTSTRAP_SERVERS_CONFIG`: Kafka 서버 주소 (`localhost:9092`)
- `KEY_SERIALIZER_CLASS_CONFIG`, `VALUE_SERIALIZER_CLASS_CONFIG`: 데이터 직렬화 방식
- `TRANSACTIONAL_ID_CONFIG`: 트랜잭션을 위한 설정, 데이터의 일관성 유지

이 Producer는 GitHub에서 가져온 데이터를 `github-issues` 및 `github-readme` 토픽으로 보내는 데 사용됩니다.

#### Kafka Consumer 설정 (KafkaConsumerConfig 클래스)
Kafka Consumer는 Kafka 토픽을 구독하고 메시지를 처리합니다. 이는 안정적인 메시지 처리를 위한 중요한 구성 요소입니다.

주요 설정은 다음과 같습니다:
- `BOOTSTRAP_SERVERS_CONFIG`: Kafka 서버 주소 (`localhost:9092`)
- `GROUP_ID_CONFIG`: Consumer 그룹 아이디 (`github-data-group`)

`ConcurrentKafkaListenerContainerFactory`는 이러한 메시지를 처리하는 데 사용됩니다.



### Architecture
<img width="1382" alt="스크린샷 2024-04-21 오후 5 03 19" src="https://github.com/Mouon/issuehub/assets/137624597/49927611-6c41-46b8-b5ac-f6a813813bb6">







