package com.example.erpdemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.erpdemo.model.TenantPool;

public interface TenantPoolRepo extends MongoRepository<TenantPool, String> {

    List<TenantPool> findByActive(boolean active);

    TenantPool findByIdAndActive(String id, boolean active);

}
