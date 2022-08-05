package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.*;
import com.connector.iirsSearch.exception.RequestException;
import com.connector.iirsSearch.service.ValidResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.connector.iirsSearch.exception.RequestExceptCode.MANDATORY_PARAM_ERROR;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/validReq")
public class ValidController {
    private final ValidResponseService validResponseService;
    @PostMapping("/target")
    public ResponseEntity<ValidTargetResponse> target(@RequestBody ValidTargetRequest validTargetRequest) {

        // 요청 메시지 유효성 검사
        validateTarget(validTargetRequest);

        // DB 조회

        // 응답
        return ResponseEntity.ok(
            validResponseService.targetResponse(validTargetRequest)
        );
    }

    @PostMapping("/stations")
    public ResponseEntity<ValidStationResponse> stations(@RequestBody ValidStationRequest validStationRequest) {
        // 요청 메시지 유효성 검사

        // DB 조회

        // 응답
        return ResponseEntity.ok(
            validResponseService.stationsResponse(validStationRequest)
        );
    }

    public void validateTarget(ValidTargetRequest request) throws RequestException {
        if (request.getAddr1() == null || request.getAddr1().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "addr1");
        if (request.getAddr2() == null || request.getAddr2().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "addr2");
        if (request.getAddr3() == null || request.getAddr3().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "addr3");
    }
}