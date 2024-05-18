package com.example.erpdemo.config;

import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.example.erpdemo.Exception.SearchException;
import com.example.erpdemo.model.query.FilterMetadata;
import com.example.erpdemo.model.query.SearchEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryBuilder {
	public static Query buildSearchQuery(SearchEvent search,Pageable pageable) throws SearchException{
		Query query = new Query();
		
		Map<String,FilterMetadata> filters = search.getFilters();
		
		if(!Objects.isNull(filters)) {
			for(Entry<String,FilterMetadata> entry: filters.entrySet()) {
				String key = entry.getKey();
				FilterMetadata value = entry.getValue();
				try {
					buildQuery(key,value,query);
				}
				catch(ParseException e) {
					log.info(e.getMessage());
					throw new SearchException(e.getMessage());
				}
			}
		}
		
		if(search.getSortField()!=null && !search.getSortField().isEmpty()) {
			 Sort.Direction direction = (search.getSortOrder() == 1) ? Sort.Direction.DESC : Sort.Direction.ASC;
	            query.with(Sort.by(direction, search.getSortField()));
		}
		
		if(Objects.nonNull(pageable)) {
			query.with(pageable);
		}
		log.info("builded query is: {}",query.toString());
		return query;
	}
	
	private static Query buildQuery(String key,FilterMetadata value,Query query) throws ParseException{
			
		switch(value.getMatchMode()) {
		case CONTAINS: 
			query.addCriteria(Criteria.where(key).regex(
					Pattern.compile(Pattern.quote(String.valueOf(value.getValue())),
							Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
			break;
		case GTE:
			query.addCriteria(Criteria.where(key)
					.gte(Double.valueOf(String.valueOf(value.getValue()))));
			break;
		case LTE:
			query.addCriteria(Criteria.where(key)
					.lte(Double.valueOf(String.valueOf(value.getValue()))));
			break;
		case GT:
			query.addCriteria(Criteria.where(key)
					.gt(Double.valueOf(String.valueOf(value.getValue()))));
			break;
		case LT:
			query.addCriteria(Criteria.where(key)
					.lt(Double.valueOf(String.valueOf(value.getValue()))));
			break;
			
			default:
				log.error("matchmode is not defined");
				break;
		}
		
		return query;
	}
	
}
