package com.example.erpdemo.model.query;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchEvent {
	private Integer first;
	
	private Integer rows;
	
	private String sortField;
	
	private Integer sortOrder;
	
	private SortMeta multiSortMeta;
	
	private Map<String,FilterMetadata> filters;
	
	private String globalFilter;
	
	private int size;
	
	private int page;
}
