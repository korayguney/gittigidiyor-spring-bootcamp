package dev.patika.patika0305.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.patika.patika0305.entity.ProcessStartTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequestMapping("/api/processStartTime")
public class ProcessController {
    private final static String PROCESS_ENDPOINT = "http://localhost:8080/actuator/metrics/process.start.time";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping
    public String getProcessTime(Model model) throws JsonProcessingException {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(PROCESS_ENDPOINT, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ProcessStartTime processStartTime = objectMapper.readValue(responseEntity.getBody(), ProcessStartTime.class);
        // LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) processStartTime.getMeasurements().get(0).getValue()), ZoneId.systemDefault());
        LocalDateTime date = Instant.ofEpochSecond((long) processStartTime.getMeasurements().get(0).getValue()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        model.addAttribute("processAge",date);

        return "process";
    }
}
