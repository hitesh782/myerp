package com.example.erpdemo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.erpdemo.Exception.MpsManagerException;
import com.example.erpdemo.config.Todo;
import com.example.erpdemo.model.FileDetail;
import com.example.erpdemo.model.MyData;
import com.example.erpdemo.model.TenantPool;
import com.example.erpdemo.model.bo.ImsService;

public interface TenantPoolService {

	List<TenantPool> getAllTenantPool();

	TenantPool createTenantPool(TenantPool tenantPool) throws MpsManagerException;

	Map<ImsService, Object> saveServiceConfig(String tenantPoolId, ImsService service, Object config)
			throws MpsManagerException;

	String getSystemInfo();

	List<FileDetail> getFileInfo(String directoryPath);

	List<String> getRunningWindowsServices() throws IOException;

	List<String> getStoppedWindowsServices() throws IOException;

	String convertToCsv(List<MyData> myData) throws Exception;

	void asyncMethod();

	Todo[] getAllTodosRestTemplate();

	Todo getTodoById(String todoId);

	void TestJavers();

}
