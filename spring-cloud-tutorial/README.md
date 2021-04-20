Spring Cloud는 "클라우드 네이티브 응용 프로그램을 만들기 위한 편리한 툴" 이다. 그럼 "클라우드 네이티브" 무엇인가? 클라우드에서 이용하여 설계된 시스템이나 서비스하는 것을 말한다. 결국 여러 작은 서비스를 결합하여 큰 시스템을 구축하는데 필요한 유용한 툴이라고 볼 수 있다.


## 전체 구성
SpringCloud 기능의 일부이지만 ServiceDelivery을 실현하는 부분을 해보려고 한다.



## discovery-server 구현
Spring-boot 응용 프로그램에 `@EnableEurekaServer` 어노테이션을 선언하는 것만으로 서비스 레지스트리로 Eureka 서버를 만들 수 있다.

### 프로젝트 생성
curl 명령으로 간단히 프로젝트를 만든다. Windows의 경우는 Bash로 만들 수 있다.

```
curl https://start.spring.io/starter.tgz  \
-d bootVersion=2.4.5 \
-d dependencies=cloud-eureka-server \
-d baseDir=spring-eureka-server \
-d artifactId=discovery-server \
-d packageName=com.devkuma.cloud.discovery.server \
-d applicationName=DiscoveryServerApplication \
-d packaging=jar \
-d javaVersion=1.8 \
-d type=gradle-project | tar -xzvf -
```

### 구현
```
package  com.example.eurekaserver ; 

import  org.springframework.boot.SpringApplication ; 
import  org.springframework.boot.autoconfigure.SpringBootApplication ; 
import  org.springframework.cloud.netflix.eureka.server.EnableEurekaServer ; 

@EnableEurekaServer 
@SpringBootApplication 
public  class  EurekaServerApplication  { 

    public  static  void  main ( String []  args )  { 
        SpringApplication . run ( EurekaServerApplication . class , args ); 
    } 
}
```
작성된 클래스에 `@EnableEurekaServer` 어노테이션을 선언한다.

그리고, 설정 정보를 기재하는 `application.yml`를 생성하고, 포트와 자신을 레지스트리에 등록하지 않으므로 설정을 설명하고 있다.
```
server:
  port: 8761
eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
```
이것만으로 완성이 되었다.


## 구동
아래 URL로 화면 표시되면 제대로 구동이 되는거다.
```
http://localhost:8761/
```


## GatewayServer 생성
이번에는 GatewayServer를 만들어 보도록 하자. 여기의 역할은 클라이언트와의 통신, BackendService에 대한 리버스 프록시(Reverse Proxy)이다.


### 프로젝트 생성
아까와 마찬가지로 curl 명령어를 이용하여 프로젝트를 만든다. 이번에는 Dependencies에 "cloud-eureka,cloud-gateway"을 포함할 거다.

```
curl https://start.spring.io/starter.tgz  \
-d bootVersion=2.4.5 \
-d dependencies=cloud-eureka,cloud-gateway \
-d baseDir=spring-gateway-server \
-d artifactId=gateway-server \
-d packageName=com.devkuma.cloud.gateway.server \
-d applicationName=GatewayServerApplication \
-d packaging=jar \
-d javaVersion=1.8 \
-d type=gradle-project | tar -xzvf -
```


### 구현
여기에서는 두개의 어노테이션을 선언한다.

- `@EnableEurekaClient`을 선언하는 것으로, EurekaServer에 `Activate`가 된다.
- `@EnableZuulProxy`을 선언하는 것으로, Zuul를 사용하여 리버스 프록시로 동작시킬 수 있다
```
package  com.example.webservice ; 

import  org.springframework.boot.SpringApplication ; 
import  org.springframework.boot.autoconfigure.SpringBootApplication ; 
import  org.springframework.cloud.netflix.eureka.EnableEurekaClient ; 
import  org.springframework.cloud.netflix.zuul .EnableZuulProxy ; 

@EnableEurekaClient 
@EnableZuulProxy 
@SpringBootApplication 
public  class  WebServiceApplication  { 

    public  static  void  main ( String []  args )  { 
        SpringApplication .run ( WebServiceApplication . class ,  args ); 
    } 
}
```
설정 정보는 application.yml에 작성한다.
```
spring : 
  application : 
    name :  web - service 
server : 
  port :  8001 
eureka : 
  client : 
    serviceUrl : 
      defaultZone :  http : // localhost : 8761 / eureka 
  instance : 
    preferIpAddress :  true 
zuul : 
  ignored - services :  '*' 
  routes : 
    backend - service :  ' api / ** '
```
- `spring.application.name` 설정한 이름으로 EurekaServer에 서비스를 등록한다.
- `eureka.client.service.url.defaultZone` 에 EurekaServer을 지정한다.
- zuul의 `api/**`에 대한 요청을 BackendService에 전달하도록 하고 있다.



## BackendService 생성

### 준비
지금까지처럼 start.spring.ioからひな形을 만듭니다.
WebService뿐만 아니라 "Eureka Discovery"를 선택해야합니다.

```
curl https://start.spring.io/starter.tgz  \
-d bootVersion=2.4.5 \
-d dependencies=cloud-eureka,cloud-gateway \
-d baseDir=spring-backend-service \
-d artifactId=backend-service \
-d packageName=com.devkuma.cloud.backend.service \
-d applicationName=BackendServiceApplication \
-d packaging=jar \
-d javaVersion=1.8 \
-d type=gradle-project | tar -xzvf -
```

구현
소스 2 개에 설정 파일 1 개 만듭니다.

더 이상 작법.
```
@EnableEurekaClient 
@SpringBootApplication 
public  class  WebServiceApplication  { 

    public  static  void  main ( String []  args )  { 
        SpringApplication . run ( WebServiceApplication . class ,  args ); 
    } 
}
```
RestAPI을 구현
```
package  com.example.backendservice.controller ; 

import  org.springframework.web.bind.annotation.RequestMapping ; 
import  org.springframework.web.bind.annotation.RestController ; 

@RestController 
public  class  BackendController  { 

    @RequestMapping ( "/ hello" ) 
    public  String  hello ()  { 
        return  "Hello from EurekaClient!" ; 
    } 
}
```
지금까지 설명한 것을 제외하고 포함되지 않기 때문에 생략
```
spring : 
  application : 
    name :  backend - service 
server : 
  port :  8002 
eureka : 
  client : 
    serviceUrl : 
      defaultZone :  http : // localhost : 8761 / eureka 
  instance : 
    preferIpAddress :  true
```

## 기동
그 그러면 여기까지 구현이 끝나면 나머지는 시작하고 연결 확인한다.

image

오~ 무사히 연결되었다.

## 정리
Gralde 설정 등도 start.spring.io에서 프로젝트를 만들 수있어 대부분 자동으로 만들어 버렸습니다.
또한 Spring Cloud에서 할 수있는 부분 만 아직해라 있지 않기 때문에, 점점 여러가지 일을 해보고 싶습니다.