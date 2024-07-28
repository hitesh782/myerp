package com.example.erpdemo.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.erpdemo.service.impl.TenantPoolServiceImpl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeanListener {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        getAllBeans();
        getEnvironmentVariables();
        getResources();
    }

    public void getAllBeans() {
        String[] beans = applicationContext.getBeanDefinitionNames();
        log.info("total bean count is: {}", beans.length);
        for (String bean : beans) {
            log.info("bean is: {}", bean);
        }

        Object tenantPoolServiceImplBean = applicationContext.getBean("tenantPoolServiceImpl");
        log.info("tenant pool service impl bean is: {}", tenantPoolServiceImplBean.getClass());
    }

    public void getEnvironmentVariables() {
        String propertyValue = applicationContext.getEnvironment().getProperty("server.port");
        log.info("property Value is: {}", propertyValue);
    }

    public void getResources() {
        // Resource resource = applicationContext.getResource("classpath:banner.txt");
        Resource resource = applicationContext.getResource("classpath:logback-spring.xml");
        try {
            // File file = resource.getFile();
            // log.info("file content is: {}", file.getAbsolutePath());

            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String line;
            while ((line = reader.readLine()) != null) {
                log.info("line is: {}", line);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
