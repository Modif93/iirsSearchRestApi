package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.UpdateRequest;
import com.connector.iirsSearch.dto.UpdateResponse;
import com.connector.iirsSearch.service.CdbUpdateResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/cdbUpdate")
public class CdbUpdateController {
    private final CdbUpdateResponseService cdbUpdateResponseService;

    @PutMapping("/Alarm")
    public void alarm() {
        UpdateRequest updateRequest = UpdateRequest.builder()
                .reqId(1)
                .current(LocalDate.parse("2022-07-01"))
                .latest(LocalDate.parse("2022-08-01"))
                .build();
    }

    @PostMapping("/Command")
    public ResponseEntity<UpdateResponse> command(@RequestBody UpdateRequest updateRequest) {
        // 요청 메시지 검증

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.commandResponse(updateRequest)
        );
    }

    @PostMapping("/Recovery")
    public ResponseEntity<UpdateResponse> recovery(@RequestBody UpdateRequest updateRequest) {
        // 요청 메시지 검증

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.recoveryResponse(updateRequest)
        );
    }

    @PostMapping("/OK")
    public ResponseEntity<UpdateResponse> Okay(@RequestBody UpdateRequest updateRequest) {
        // 요청 메시지 검증

        // 응답
        return ResponseEntity.ok(
                cdbUpdateResponseService.OkayResponse(updateRequest)
        );
    }
}