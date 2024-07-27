package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.erpdemo.model.MonitoringUrl;

public interface MonitoringUrlRepo extends MongoRepository<MonitoringUrl, String> {

}
