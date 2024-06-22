package com.example.erpdemo.model;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.erpdemo.model.bo.BOWithId;
import com.example.erpdemo.model.bo.ImsService;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tenant_pool")
public class TenantPool extends BOWithId {

    @NotBlank(message = "Tenant Pool Name cannot be empty")
    private String name;

    @NotBlank(message = "Tenant Pool description cannot be empty")
    private String description;

    private Map<ImsService, Object> serviceConfigs;

    private Map<String, Object> metadata;

}
