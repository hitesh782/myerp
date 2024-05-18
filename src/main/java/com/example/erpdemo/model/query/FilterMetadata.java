package com.example.erpdemo.model.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterMetadata {
	private Object value;
	private MatchMode  matchMode;
}
