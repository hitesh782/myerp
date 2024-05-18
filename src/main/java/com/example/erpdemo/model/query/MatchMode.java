package com.example.erpdemo.model.query;

public enum MatchMode {
	CONTAINS("contains"),
	
	GTE("gte"),
	
	LTE("lte"),
	
	GT("gt"),
	
	LT("lt");
	
	private final String value;
	
	MatchMode(String value){
		this.value = value;
	}
	
	public static MatchMode get(String matchModeCode) {
		for(MatchMode matchMode: values()) {
			if(matchMode.getValue().equals(matchModeCode)) {
				return matchMode;
			}
		}
		throw new IllegalArgumentException("No Matching Constant for ["+matchModeCode+"]");
	}
	
	public String getValue() {
		return this.value;
	}
}
