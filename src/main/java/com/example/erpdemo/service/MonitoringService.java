package com.example.erpdemo.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.example.erpdemo.model.MonitoringUrl;
import com.example.erpdemo.repository.MonitoringUrlRepo;

import jakarta.annotation.PostConstruct;

import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class MonitoringService {

    @Autowired
    private MonitoringUrlRepo monitoringUrlRepo;

    @Autowired
    private TaskScheduler taskScheduler;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new HashMap<>();

    @PostConstruct
    public void scheduledInitialTasks() {
        List<MonitoringUrl> monitoringUrls = monitoringUrlRepo.findAll();
        for (MonitoringUrl monitoringUrl : monitoringUrls) {
            scheduleTask(monitoringUrl);
        }
    }

    public List<MonitoringUrl> getAllMonitoringUrls() {
        return monitoringUrlRepo.findAll();
    }

    public MonitoringUrl saveMonitoringUrl(MonitoringUrl monitoringUrl) {
        MonitoringUrl savedMonitoringUrl = monitoringUrlRepo.save(monitoringUrl);
        scheduleTask(savedMonitoringUrl);
        return savedMonitoringUrl;
    }

    public void deleteMonitoringUrl(String id) {
        MonitoringUrl monitoringUrl = monitoringUrlRepo.findById(id).orElse(null);
        if (monitoringUrl != null) {
            ScheduledFuture<?> scheduledTask = scheduledTasks.remove(monitoringUrl.getId());
            if (scheduledTask != null) {
                scheduledTask.cancel(true);
            }
            monitoringUrlRepo.deleteById(id);
        }
    }

    private void scheduleTask(MonitoringUrl monitoringUrl) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(monitoringUrl.getId());
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
        }

        Runnable task = () -> {
            String status = checkPortalStatus(monitoringUrl.getUrl());
            System.out.println("-------------------------------------------------");
            System.out.println(monitoringUrl.getId() + " " + monitoringUrl.getUrl() + " "
                    + monitoringUrl.getCronSchedule() + " " + status);
            System.out.println("-------------------------------------------------");
        };
        scheduledTask = taskScheduler.schedule(task, new CronTrigger(monitoringUrl.getCronSchedule()));
        scheduledTasks.put(monitoringUrl.getId(), scheduledTask);
    }

    public static String checkPortalStatus(String portalUrl) {
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

    private static String getSSLCertificateDetails(String portalUrl) {
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
        } catch (IOException e) {
            result.append("Failed to verify SSL certificate: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }
}
