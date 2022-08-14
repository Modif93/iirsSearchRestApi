package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.UpdateRequest;
import com.connector.iirsSearch.dto.UpdateResponse;
import com.connector.iirsSearch.exception.RequestException;
import com.connector.iirsSearch.service.CdbUpdateResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.connector.iirsSearch.exception.RequestExceptCode.INVALID_PARAM_ERROR;
import static com.connector.iirsSearch.exception.RequestExceptCode.MANDATORY_PARAM_ERROR;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/cdbUpdate")
@PropertySource("classpath:extern.properties")
public class CdbUpdateController {
    @Value("${url}")
    private String target;
    private final CdbUpdateResponseService cdbUpdateResponseService;

    @GetMapping("/Alarm")
    public void alarm() throws IOException {
        URL url = new URL(target);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("PUT");

        String jsonInputStr = "{\"current\": \"2022-07-01\", \"latest\": \"2022-08-01\"}";
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputStr.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        } else {
            System.out.println("Put Response is OK");
        }

        conn.disconnect();
    }

    // TestCode
    @PutMapping("/Alarm")
    public void alarmTest(@RequestBody String request) throws IOException {
        System.out.println("[TEST] Alarm Received...");
        System.out.println(request);
    }

    @PostMapping("/Command")
    public ResponseEntity<UpdateResponse> command(
            @RequestBody UpdateRequest updateRequest) throws ParseException {
        // 요청 메시지 검증
        validateCommand(updateRequest);

        // CDB 업데이트 실행
        // 실행 결과 오래걸리는데 응답은 어떻게...

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.commandResponse(updateRequest)
        );
    }

    @PostMapping("/Recovery")
    public ResponseEntity<UpdateResponse> recovery(
            @RequestBody UpdateRequest updateRequest) throws ParseException {
        // 요청 메시지 검증
        validateRecovery(updateRequest);

        // CDB 복구 실행

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.recoveryResponse(updateRequest)
        );
    }

    @PostMapping("/OK")
    public ResponseEntity<UpdateResponse> Okay(
            @RequestBody UpdateRequest updateRequest) throws ParseException {
        // 요청 메시지 검증
        validateOkay(updateRequest);

        // CDB 업데이트 확정

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.OkayResponse(updateRequest)
        );
    }

    public void validateCommand(UpdateRequest request) throws RequestException, ParseException {
        if (request.getCurrent() == null)
            throw new RequestException(MANDATORY_PARAM_ERROR, "current");
        if (request.getLatest() == null)
            throw new RequestException(MANDATORY_PARAM_ERROR, "latest");

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        Date current = dateFormat.parse(request.getCurrent().toString());
        Date latest = dateFormat.parse(request.getLatest().toString());
        if (current.before(latest) == false) {
            throw new RequestException(INVALID_PARAM_ERROR, "업데이트 버전(latest)은 현재 버전(current) 보다 최근 날짜 이어야 합니다.");
        }
    }

    public void validateRecovery(UpdateRequest request) throws RequestException, ParseException {
        if (request.getCurrent() == null)
            throw new RequestException(MANDATORY_PARAM_ERROR, "current");
        if (request.getPrevious() == null)
            throw new RequestException(MANDATORY_PARAM_ERROR, "previous");

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        Date current = dateFormat.parse(request.getCurrent().toString());
        Date previous = dateFormat.parse(request.getPrevious().toString());
        if (previous.before(current) == false) {
            throw new RequestException(INVALID_PARAM_ERROR, "복구 버전(previous)은 현재 버전(current) 보다 이전 날짜 이어야 합니다.");
        }
    }

    public void validateOkay(UpdateRequest request) throws RequestException, ParseException {
        if (request.getCurrent() == null)
            throw new RequestException(MANDATORY_PARAM_ERROR, "current");
    }
}