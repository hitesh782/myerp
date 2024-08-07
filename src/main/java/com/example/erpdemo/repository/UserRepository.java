package com.example.erpdemo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.erpdemo.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
