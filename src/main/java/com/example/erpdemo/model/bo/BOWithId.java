package com.example.erpdemo.model.bo;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BOWithId {
	private String id;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date updatedDate;

	private boolean active = true;

	private boolean deleted = false;
}
