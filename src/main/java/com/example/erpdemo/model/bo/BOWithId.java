package com.example.erpdemo.model.bo;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BOWithId {
	private String id;
	
	@CreatedDate
	@Field("created_Date")
	private Date createdDate;
	
	@LastModifiedDate
	@Field("updated_Date")
	private Date updatedDate;
}
