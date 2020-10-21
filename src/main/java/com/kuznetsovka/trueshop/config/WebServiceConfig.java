package com.kuznetsovka.trueshop.config;

import com.kuznetsovka.trueshop.endpoint.GreetingEndpoint;
import com.kuznetsovka.trueshop.endpoint.UsersEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "greeting")
    public DefaultWsdl11Definition defaultWsdl11Definition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GreetingPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(GreetingEndpoint.NAMESPACE_URL);
        wsdl11Definition.setSchema(xsdGreetingSchema());
        return wsdl11Definition;
    }

    @Bean("greetingSchema")
    public XsdSchema xsdGreetingSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/greeting.xsd"));
    }


    @Bean(name = "users")
    public DefaultWsdl11Definition usersWsdlDefinition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UsersPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(UsersEndpoint.NAMESPACE_URL);
        wsdl11Definition.setSchema(xsdUsersSchema());
        return wsdl11Definition;
    }

    @Bean("usersSchema")
    public XsdSchema xsdUsersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ws/users.xsd"));
    }


}
