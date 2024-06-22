package com.example.erpdemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.erpdemo.config.AppConstant;
import com.example.erpdemo.service.KafkaServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaServices {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean updateLocation(String message) {
        this.kafkaTemplate.send(AppConstant.KAFKA_TOPIC_NAME, message);

        return true;
    }

}
