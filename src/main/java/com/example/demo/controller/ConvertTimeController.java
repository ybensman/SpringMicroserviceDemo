package com.example.demo.controller;

import com.example.demo.model.ConvertTimeRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@RestController
@RequestMapping("time")
public class ConvertTimeController {

    @GetMapping("/now")
    String convertTime(@Valid @RequestBody ConvertTimeRequest convertTimeRequest) {
        ZoneId tz = TimeZone.getTimeZone(convertTimeRequest.timeZone).toZoneId();

        return OffsetDateTime.now(tz)
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
