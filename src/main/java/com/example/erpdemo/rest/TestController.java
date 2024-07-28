package com.example.erpdemo.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

	@GetMapping()
	String getName() {
		return "hitesh";
	}

	@GetMapping("/ipconfig")
	Object getIpConfig() {

		StringBuilder output = new StringBuilder();

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("ipconfig /all");

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;

			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}

			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	@GetMapping("/portal-status")
	String checkPortalStatus(@RequestParam String portalUrl) {
		// String portalUrl = "https://dev.medpharmservices.com";
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(portalUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int responseCode = connection.getResponseCode();
			result.append("Response Code: ").append(responseCode).append("\n");

			if (responseCode == 200) {
				result.append("Portal is UP\n");
				if (portalUrl.startsWith("https")) {
					result.append("SSL Certificate Details:\n");
					result.append(getSSLCertificateDetails(portalUrl));
				}
			} else {
				result.append("Portal is DOWN\n");
			}
		} catch (IOException e) {
			result.append("Error checking portal status: ").append(e.getMessage()).append("\n");
		}

		return result.toString();
	}

	String getSSLCertificateDetails(String portalUrl) {
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(portalUrl);
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) factory.createSocket(url.getHost(), 443);
			socket.startHandshake();

			Certificate[] serverCerts = socket.getSession().getPeerCertificates();
			for (Certificate cert : serverCerts) {
				if (cert instanceof X509Certificate) {
					X509Certificate x509Cert = (X509Certificate) cert;
					result.append("Subject: ").append(x509Cert.getSubjectDN().getName()).append("\n");
					result.append("Issuer: ").append(x509Cert.getIssuerDN().getName()).append("\n");
					result.append("Serial Number: ").append(x509Cert.getSerialNumber().toString()).append("\n");
					result.append("Valid From: ").append(x509Cert.getNotBefore()).append("\n");
					result.append("Valid To: ").append(x509Cert.getNotAfter()).append("\n");
				}
			}
			socket.close();
		} catch (SSLPeerUnverifiedException e) {
			result.append("Failed to verify SSL certificate: ").append(e.getMessage()).append("\n");
		} catch (IOException e) {
			result.append("Error retrieving SSL certificate: ").append(e.getMessage()).append("\n");
		}

		return result.toString();
	}
}
