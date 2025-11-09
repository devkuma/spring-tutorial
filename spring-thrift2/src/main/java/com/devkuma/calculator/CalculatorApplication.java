package com.devkuma.calculator;

import javax.servlet.http.HttpServlet;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @Bean
    public TProtocolFactory tProtocolFactory() {
        //We will use binary protocol, but it's possible to use JSON and few others as well
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> stateServlet(TProtocolFactory protocolFactory, CalculatorServiceHandler handler) {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new TServlet(new TCalculatorService.Processor<>(handler), protocolFactory));
        servRegBean.addUrlMappings("/calculator/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
}
