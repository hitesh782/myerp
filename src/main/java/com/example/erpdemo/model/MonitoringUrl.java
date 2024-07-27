package com.example.erpdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "monitoring_urls")
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringUrl {

    @Id
    private String id;

    private String url;

    private String cronSchedule;

    private String email;
}
