# SpringBatch Toy Project
spring batch maven 설정부터 배치 개발, aws, jenkins 배포까지 

* Spring Batch    
 https://blog.naver.com/spell2126/222111871644    
 https://blog.naver.com/spell2126/222111898121    
 https://blog.naver.com/spell2126/222111901631    

* [Springboot] 설정파일(properties, yml) 암호화 적용방법
 https://blog.naver.com/spell2126/222135191503

* aws ec2 인스턴스 생성 & jdk & ftp & 젠킨스 설치 및 초기 설정
 https://blog.naver.com/spell2126/222149717999

*	JENKINS 타임존 변경 및 스프링배치 작업 구성
 https://blog.naver.com/spell2126/222157104815

## rabbit mq
배치 실행에서 open api를 호출하여 일정이상의 데이터의 경우 사용자 알림 이메일 발송을 위해 rabbitmq 메세지 브로커를 사용.
배치에서는 producer(발행역할)을 수행한다.

## schema

![A](imgs/rabbitmq_schema.PNG)


## 메세지 시스템(rabbit mq) 사용의 이점
* 애플리케이션/시스템 간의 의존성 분리
front => api 의 구조에서 대령의 요청으로 인해 api서버에 문제가 생긴다면 서비스 전체에 영향을 미친다.
front => mq => api 처럼 중간에 message 브로커가 있다면 대량의 요청으로 인해 mq서버에 문제가 생기더라도 서비스 전체에 영향이 미치지는 않는다.

#### rabbitmq vs kafka

`rabbitmq`<br>
* exchange의 개념은 rabbitmq에 존재 kafka에는 없음

`kafka`<br>
* 파티션의 개념은 kafka에 존재 rabbitmq에는 없음
* 메세지 보존은 정책을 기반으로한다.
사용자가 설정할 수 있는 특정 기간까지 디스크에 저장됩니다.
ex)7