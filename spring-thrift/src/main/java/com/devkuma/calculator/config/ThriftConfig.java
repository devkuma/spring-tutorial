package com.devkuma.calculator.config;

import javax.servlet.http.HttpServlet;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.devkuma.calculator.TCalculatorService;
import com.devkuma.calculator.handler.CalculatorServiceHandler;

@Configuration
public class ThriftConfig {

    @Bean
    public TProtocolFactory tProtocolFactory() {
        //We will use binary protocol, but it's possible to use JSON and few others as well
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean <HttpServlet> stateServlet(TProtocolFactory tProtocolFactory, CalculatorServiceHandler handler) {
        ServletRegistrationBean <HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new TServlet(new TCalculatorService.Processor <>(handler), tProtocolFactory));
        servRegBean.addUrlMappings("/calculator/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }
}
