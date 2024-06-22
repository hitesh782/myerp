package com.example.erpdemo.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.erpdemo.Exception.MpsManagerException;
import com.example.erpdemo.config.SecurityAuditWare;
import com.example.erpdemo.model.FileDetail;
import com.example.erpdemo.model.MyData;
import com.example.erpdemo.model.TenantPool;
import com.example.erpdemo.model.bo.ImsService;
import com.example.erpdemo.repository.TenantPoolRepo;
import com.example.erpdemo.service.KafkaServices;
import com.example.erpdemo.service.TenantPoolService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/tenantPool")
@Slf4j
public class TenantPoolController {

	@Autowired
	private TenantPoolService tenantPoolService;

	@Autowired
	private TenantPoolRepo tenantPoolRepo;

	@Autowired
	private KafkaServices kafkaServices;

	@GetMapping()
	// @PreAuthorize("hasAuthority('SCOPE_read:user')")
	public List<TenantPool> getAllTenantPool() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Jwt jwt = (Jwt) authentication.getPrincipal();

		// Retrieve email claim from JWT token
		String email = jwt.getClaim("https://medpharmservices.com/email");

		log.info("authentication is: " + authentication);
		log.info("authentication is: " + authentication.getPrincipal() + " " + authentication.getName() + " "
				+ authentication.getAuthorities() + " " + authentication.getDetails());
		log.info("email is: " + email);
		return tenantPoolService.getAllTenantPool();
	}

	@GetMapping("/{id}")
	public TenantPool getTenantPool(@PathVariable String id) throws MpsManagerException {

		log.info("current user is: " + SecurityAuditWare.getEmail());
		List<String> roles = SecurityAuditWare.getRoles();

		for (String a : roles) {
			log.info("role is: " + a);
		}

		String scope = SecurityAuditWare.getScopes();

		log.info("scope is: " + scope);

		Optional<TenantPool> tenantPoolOptional = tenantPoolRepo.findById(id);

		if (tenantPoolOptional.isPresent()) {
			return tenantPoolOptional.get();
		} else {
			throw new MpsManagerException("Tenant Pool with given is is not present");
		}
	}

	@GetMapping("/for-filter")
	public List<TenantPool> getTenantPoolWithFilter(@RequestParam(defaultValue = "true") String active) {
		boolean isActive;
		if ("true".equalsIgnoreCase(active) || "false".equalsIgnoreCase(active)) {
			log.info("active is " + active + " and parsed is: " + Boolean.parseBoolean(active));
			isActive = Boolean.parseBoolean(active);
		} else {
			throw new IllegalArgumentException("Invalid value for active parameter. Must be 'true' or 'false'.");
		}

		return tenantPoolRepo.findByActive(isActive);
	}

	@PostMapping()
	public TenantPool createTenantPool(@RequestBody final TenantPool tenantPool) throws MpsManagerException {
		return tenantPoolService.createTenantPool(tenantPool);
	}

	@DeleteMapping("/{id}")
	public void deleteTenantPool(@PathVariable String id) throws MpsManagerException {
		TenantPool tenantPool = tenantPoolRepo.findByIdAndActive(id, true);

		if (null == tenantPool) {
			throw new MpsManagerException("can not delete the tenant Pool");
		}

		tenantPool.setActive(false);
		tenantPoolRepo.save(tenantPool);
	}

	@PostMapping("/{id}")
	public Map<ImsService, Object> saveServiceConfig(@PathVariable String id, @RequestParam ImsService service,
			@RequestBody Object object) throws MpsManagerException {
		return tenantPoolService.saveServiceConfig(id, service, object);
	}

	@GetMapping("/csv")
	public void getCSVData(HttpServletResponse response) throws IOException {
		try {
			List<MyData> data = new ArrayList<>();
			data.add(new MyData("Hitesh", "Parmar", 20));
			data.add(new MyData("Karan", "Panchal", 22));
			String csvData = tenantPoolService.convertToCsv(data);

			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=data.csv");

			response.getWriter().write(csvData);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Error occurred while generating CSV data.");
		}
	}

	@GetMapping("/system-info")
	public String getSystemInfo() {
		return tenantPoolService.getSystemInfo();
	}

	@GetMapping("/get-file-info")
	public List<FileDetail> getFileInfo() {
		String directoryPath = "D:\\dummy_folder";
		return tenantPoolService.getFileInfo(directoryPath);
	}

	@GetMapping("/get-windows-services")
	public List<String> getServices(@RequestParam(defaultValue = "RUNNING") String state) throws IOException {
		if ("RUNNING".equalsIgnoreCase(state)) {
			return tenantPoolService.getRunningWindowsServices();
		} else {
			return tenantPoolService.getStoppedWindowsServices();
		}
	}

	@GetMapping("/async")
	public void startAsyncProcess() {
		tenantPoolService.asyncMethod();
	}

	@PostMapping("/update-user")
	public ResponseEntity<?> updateLocation() {

		// for (int i = 1; i <= 200000; i++) {
		this.kafkaServices.updateLocation(
				"( " + Math.round(Math.random() * 100) + " , " + Math.round(Math.random() * 100) + " " + ")");
		// }

		return new ResponseEntity<>(Map.of("message", "Location updated"), HttpStatus.OK);
	}
}
