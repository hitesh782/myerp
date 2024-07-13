package com.example.erpdemo.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.example.erpdemo.Exception.MpsManagerException;
import com.example.erpdemo.config.TenantContext;
import com.example.erpdemo.config.TenantPoolCreationEvent;
import com.example.erpdemo.config.Todo;
import com.example.erpdemo.model.FileDetail;
import com.example.erpdemo.model.MyData;
import com.example.erpdemo.model.TenantPool;
import com.example.erpdemo.model.bo.ImsService;
import com.example.erpdemo.repository.TenantPoolRepo;
import com.example.erpdemo.service.TenantPoolService;
import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TenantPoolServiceImpl implements TenantPoolService {

	private static final DecimalFormat df = new DecimalFormat("0.00");

	// private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private TenantPoolRepo tenantPoolRepo;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Javers javers;

	@Override
	public List<TenantPool> getAllTenantPool() {

		String recipient = "komalparmar8320@gmail.com";
		String subject = "Hello, ${firstName}!";
		String template = "Hello, ${firstName}!\n\n" + "This is a message just for you, ${firstName} ${lastName}. "
				+ "We hope you're having a great day!\n\n" + "Best regards,\n" + "The Spring Boot Team";

		subject = subject.replace("${firstName}", "Karan");

		template = template.replace("${firstName}", "hitesh");
		template = template.replace("${lastName}", "parmar");

		String tenantId = TenantContext.getId();

		log.info("current Tenant Id is: {}", tenantId);

		// sendMail(recipient, subject, template);

		return tenantPoolRepo.findAll();
	}

	void sendMail(String to, String subject, String mess) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(mess);

		log.info("email is sending with configurations: {}", message);

		emailSender.send(message);
	}

	@Override
	public TenantPool createTenantPool(TenantPool tenantPool) throws MpsManagerException {
		validateTenantPool(tenantPool);

		TenantPool savedTenantPool = tenantPoolRepo.save(tenantPool);

		log.debug("debug log level");
		log.info("info log level");
		log.warn("warn log level");
		log.error("error log level");

		eventPublisher.publishEvent(new TenantPoolCreationEvent(savedTenantPool.getId()));

		return savedTenantPool;

		// return tenantPoolRepo.save(tenantPool);
	}

	void validateTenantPool(TenantPool tenantPool) throws MpsManagerException {
		try {
			Assert.isTrue(!tenantPool.getName().isEmpty(), "Tenant Pool name cannot be empty");
		} catch (Exception e) {
			throw new MpsManagerException(e.getMessage());
		}
	}

	@Override
	public Map<ImsService, Object> saveServiceConfig(String tenantPoolId, ImsService service, Object config)
			throws MpsManagerException {
		TenantPool tenantPool = tenantPoolRepo.findByIdAndActive(tenantPoolId, true);

		if (tenantPool == null) {
			throw new MpsManagerException("Tenant pool with given id is not present");
		}

		Map<ImsService, Object> configs = tenantPool.getServiceConfigs();

		if (configs == null) {
			configs = new HashMap<>();
		}

		configs.put(service, config);

		tenantPool.setServiceConfigs(configs);

		tenantPoolRepo.save(tenantPool);

		return configs;

	}

	@Override
	public String getSystemInfo() {
		StringBuilder sb = new StringBuilder();

		// Operating System
		OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
		sb.append("Operating System: ").append(osBean.getName()).append(" ").append(osBean.getVersion()).append(" ")
				.append(osBean.getArch()).append("\n");

		// CPU Load
		if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
			com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
			double cpuLoad = sunOsBean.getSystemCpuLoad() * 100;
			sb.append("CPU Load: ").append(df.format(cpuLoad)).append("%\n");
		} else {
			sb.append("CPU Load: Not available\n");
		}

		// Memory Usage
		Runtime runtime = Runtime.getRuntime();
		long totalMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		long usedMemory = totalMemory - freeMemory;

		sb.append("Total Memory: ").append(totalMemory / 1024 / 1024).append(" MB\n");
		sb.append("Used Memory: ").append(usedMemory / 1024 / 1024).append(" MB\n");
		sb.append("Free Memory: ").append(freeMemory / 1024 / 1024).append(" MB\n");

		// // Disk Usage
		// File root = new File("/");
		// long totalSpace = root.getTotalSpace();
		// long freeSpace = root.getFreeSpace();
		// long usableSpace = root.getUsableSpace();

		// sb.append("Total Disk Space: ").append(totalSpace / 1024 / 1024 /
		// 1024).append(" GB\n");
		// sb.append("Free Disk Space: ").append(freeSpace / 1024 / 1024 /
		// 1024).append(" GB\n");
		// sb.append("Usable Disk Space: ").append(usableSpace / 1024 / 1024 /
		// 1024).append(" GB\n");

		// Disk Usage
		File[] roots = File.listRoots();
		for (File root : roots) {
			long totalSpace = root.getTotalSpace();
			long freeSpace = root.getFreeSpace();
			long usableSpace = root.getUsableSpace();

			sb.append("Drive: ").append(root.getAbsolutePath()).append("\n");
			sb.append("  Total Space: ").append(totalSpace / 1024 / 1024 / 1024).append(" GB\n");
			sb.append("  Free Space: ").append(freeSpace / 1024 / 1024 / 1024).append(" GB\n");
			sb.append("  Usable Space: ").append(usableSpace / 1024 / 1024 / 1024).append(" GB\n");
		}

		// JVM Uptime
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		long uptime = runtimeMxBean.getUptime();
		sb.append("JVM Uptime: ").append(uptime / 1000).append(" seconds\n");

		// Thread Count
		ThreadMXBean threadMxBean = ManagementFactory.getThreadMXBean();
		int threadCount = threadMxBean.getThreadCount();
		sb.append("Thread Count: ").append(threadCount).append("\n");

		// IP Address
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			sb.append("Local IP Address: ").append(localHost.getHostAddress()).append("\n");
		} catch (UnknownHostException e) {
			sb.append("Error retrieving network information: ").append(e.getMessage()).append("\n");
		}

		sb.append(getIpConfigOutput());

		return sb.toString();
	}

	public String getIpConfigOutput() {
		StringBuilder sb = new StringBuilder();
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("ipconfig");
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			reader.close();
		} catch (Exception e) {
			sb.append("Error running ipconfig: ").append(e.getMessage()).append("\n");
		}
		return sb.toString();
	}

	@Override
	public List<FileDetail> getFileInfo(String directoryPath) {
		List<FileDetail> fileDetailsList = new ArrayList<>();

		File directory = new File(directoryPath);
		if (!directory.exists() || !directory.isDirectory()) {
			// Handle case where directory doesn't exist or is not a directory
			return fileDetailsList;
		}

		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					String type = getFileType(file);
					long lastModified = file.lastModified();
					FileDetail fileDetails = new FileDetail(file.getName(), file.length(), type, lastModified);
					fileDetailsList.add(fileDetails);
				}
			}
		}

		return fileDetailsList;
	}

	public List<String> getWindowsServiceNames() throws IOException {
		List<String> serviceNames = new ArrayList<>();

		// Execute 'sc query' command to list services
		ProcessBuilder processBuilder = new ProcessBuilder("sc", "query");
		Process process = processBuilder.start();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			String serviceName = null;

			while ((line = reader.readLine()) != null) {
				// Check if line contains "SERVICE_NAME"
				if (line.trim().startsWith("SERVICE_NAME")) {
					// Extract service name from the line (format: "SERVICE_NAME: <service_name>")
					serviceName = line.split(":")[1].trim();
				} else if (line.trim().startsWith("DISPLAY_NAME") && serviceName != null) {
					// Optionally extract display name if needed
					// String displayName = line.split(":")[1].trim();
					serviceNames.add(serviceName);
					serviceName = null; // Reset for next service
				}
			}
		}

		return serviceNames;
	}

	private String getFileType(File file) {
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".txt")) {
			return "Text File";
		} else if (fileName.endsWith(".pdf")) {
			return "PDF Document";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")) {
			return "Image";
		} else {
			return "Unknown";
		}
	}

	public List<String> getWindowsServices(String state) throws IOException {
		List<String> serviceNames = new ArrayList<>();

		// Execute 'wmic' command to list services based on state
		Process process = Runtime.getRuntime().exec("wmic service where state='" + state + "' get caption");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			// Skip header line
			reader.readLine();
			// Read service names
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					serviceNames.add(line.trim());
				}
			}
		}

		return serviceNames;
	}

	// Method to get currently running service names
	@Override
	public List<String> getRunningWindowsServices() throws IOException {
		return getWindowsServices("running");
	}

	// Method to get currently stopped service names
	@Override
	public List<String> getStoppedWindowsServices() throws IOException {
		return getWindowsServices("stopped");
	}

	@Override
	public String convertToCsv(List<MyData> data) throws Exception {
		StringWriter writer = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(writer);

		// Write header
		csvWriter.writeNext(new String[] { "First Name", "Last Name", "Age" });

		// Convert each MyData object to a CSV row
		for (MyData myData : data) {
			csvWriter.writeNext(
					new String[] { myData.getFirstName(), myData.getLastName(), String.valueOf(myData.getAge()) });
		}

		csvWriter.close();
		return writer.toString();
	}

	@Override
	@Async
	public void asyncMethod() {
		System.out.println("Start async method: " + Thread.currentThread().getName());
		try {
			Thread.sleep(15000); // Simulate a long-running task
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("End async method: " + Thread.currentThread().getName());
	}

	@Override
	public Todo[] getAllTodosRestTemplate() {

		// get for object
		// Todo[] todos =
		// restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos",
		// Todo[].class);
		// return todos;

		// get for entity
		ResponseEntity<Todo[]> todoList = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos",
				Todo[].class);
		System.out.println(todoList.getStatusCode());
		Todo[] todos = todoList.getBody();
		return todos;

	}

	@Override
	public Todo getTodoById(String todoId) {
		Todo todo = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/" + todoId, Todo.class);
		return todo;
	}

	@Override
	public void TestJavers() {
		Todo todo1 = new Todo("12", "23", "hello", false);
		Todo todo2 = new Todo("12", "57", "hello", true);

//		Javers javers = JaversBuilder.javers().build();

		Diff diff = javers.compare(todo1, todo2);

		log.info(diff.prettyPrint());
//		log.info("changes are " + diff.getChanges());
	}
}
