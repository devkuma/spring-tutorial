Spring Cloud는 **클라우드 네이티브 어플리케이션을 만들기 위한 편리한 도구**이다. 그럼 **클라우드 네이티브** 무엇인가? 클라우드에서 이용하여 설계된 시스템이나 서비스하는 것을 말한다. 결국 여러 작은 서비스를 결합하여 큰 시스템을 구축하는데 필요한 유용한 도구이라고 볼 수 있다.


## 전체 구성
SpringCloud 기능의 일부인, ServiceDelivery가 실현되도록 구현해 보려고 한다. 구현할 어플리케이션은 DiscoveryServer, GatewayServer, BackendService이 있다. 각 서버의 역할을 아래와 같다.

- DiscoveryServer는 각 어플리케이션의 정보가 등록되어, 로드 밸런서 역할을 한다.
- GatewayServer는 모든 요청이 거쳐가는 진입로가 된다.
- BackendService는 실제 호출에 대한 응답을 하게 된다.

## DiscoveryServer 구현
Discovery Server는 Netflix의 Eureka를 사용하여 만들어 보도록 하겠다. Spring-boot 어플리케이션에 `@EnableEurekaServer` 어노테이션을 선언하는 것만으로 손쉽게 Eureka 서버를 만들 수 있다.

### 프로젝트 생성
아래와 같이 `curl` 명령으로 간단히 Spring-boot 초기 프로젝트를 만들자. Windows인 경우에는 Bash로도 만들 수 있다.

```
curl https://start.spring.io/starter.tgz  \
-d bootVersion=2.4.5 \
-d dependencies=spring-discovery-server \
-d baseDir=spring-eureka-server \
-d artifactId=discovery-server \
-d packageName=com.devkuma.cloud.discovery.server \
-d applicationName=DiscoveryServerApplication \
-d packaging=jar \
-d javaVersion=1.8 \
-d type=gradle-project | tar -xzvf -
```

### 소스 코드 구현
작성된 DiscoveryServerApplication  클래스에 `@EnableEurekaServer` 어노테이션을 선언한다.
```
package com.devkuma.cloud.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}

}
```

그리고, 설정 정보를 기재하는 `application.yml`를 생성한다. 여기 포트를 설정하고, 레지스트리에 자기 자신을 등록할 필요도 없고 조회를 할 필요도 없기에 관련된 설정을 모두 `false`로 지정한다.
```
server:
  port: 8761
  
eureka:
  client:
    # 유레카에 등록할지 여부. 자기 자신을 등록할 필요가 없다.
    registerWithEureka: false
    # 유레카에서 조회할지 여부. 조회해서 로직을 수행할 일이 없다.
    fetchRegistry: false
```
이것만으로 완성이 되었다.

### 서버 구동
이제 구동을 해보자. 브라우저에서 아래 URL로 접속해 보자.
```
http://localhost:8761/
```
화면이 표시가 되었다면 제대로 구동이 되는 거다.


## GatewayServer 생성
이번에는 GatewayServer를 만들어 보도록 하겠다. 이 서버의 역할은 클라이언트와의 통신, BackendService에 대한 리버스 프록시(Reverse Proxy)을 하게 된다.


### 프로젝트 생성
위에서와 마찬가지로 `curl` 명령어를 이용하여 spring-boot 프로젝트를 만든다. 이번에는 Dependencies에 "cloud-eureka,cloud-gateway"을 포함한다.

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


### 소스 코드 구현
이 서버에는 Application 클래스에 두개의 어노테이션을 선언한다.

- `@EnableEurekaClient`을 선언하는 것으로, DiscoveryServer에 `Activate`가 된다.

```
package com.devkuma.cloud.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

}
```
설정 정보는 application.yml에 작성한다.
```
server:
  port: 8001

spring:
  application:
    name: gateway-server
  cloud:
    gateway:
      routes:
        - id: backend-service
          uri: lb://backend-service
          predicates:
            - Path=/backend/**
          filters:
            - RewritePath=/backend/(?<path>.*),/$\{path}

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
  instance:
    preferIpAddress: true
```
- `spring.application.name`에 설정한 이름대로 DiscoveryServer에 서비스가 등록된다.
- `eureka.client.serviceUrl.defaultZone` 에 DiscoveryServer을 지정한다.
- Gateway의 라우트에 Path로 `/backend/**`을 지정하여 요청이 들어오면 BackendService에 전달되도록 설정하였다.



## BackendService 생성
실제 서비스

### 프로젝트 생성

이번에도  `curl` 명령어를 이용하여 spring-boot 프로젝트를 만든다. 이번에는 Dependencies에 "cloud-eureka"을 포함한다.

```
curl https://start.spring.io/starter.tgz  \
-d bootVersion=2.4.5 \
-d dependencies=cloud-eureka \
-d baseDir=spring-backend-service \
-d artifactId=backend-service \
-d packageName=com.devkuma.cloud.backend.service \
-d applicationName=BackendServiceApplication \
-d packaging=jar \
-d javaVersion=1.8 \
-d type=gradle-project | tar -xzvf -
```

### 소스 코드 구현
소스 2개에, 설정 파일 1개 만들 것이다.

먼저, Application 클래스에 BackendService가 기동되면서 DiscoverySever에 등록되도록 `@EnableEurekaClient` 어노테이션을 선언한다.
```
package com.devkuma.cloud.backend.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BackendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceApplication.class, args);
	}

}
```

이제 실질적으로 호출에 응답할 REST API을 구현한다.
```
package com.devkuma.cloud.backend.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello backend service";
    }
}
```

포트, 어플리케이션 이름, 유레카 설정을 포함한 `application.yml`을 작성한다.
```
server:
  port: 8002

spring:
  application:
    name: backend-service

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
  instance:
    preferIpAddress: true
```

### 서버 구동
아래 URL로 접속해 보자.
```
http://localhost:8002/hello
```

화면이 표시되면 잘 구동이 된 거다.


## 마무리

### 전체 구동
자, 이제 모든 어플리케이션이  완성 되었다. 이제 모든 서버를 기동하여 아래 URL 접속해 보자
```
http://localhost:8001/backend/hello
```

화면이 표시되었다면, 제대로 동작하는 것이다.

이는 각 어플리케이션이 모두 연결이 되었다는 것을 뜻한다.

### 웹 페이지
위에 대한 내용은 [devkuma 웹 페이지](http://www.devkuma.com/pages/1522)에도 설명되어 있다.


