package com.connector.iirsSearch.controller;


import com.connector.iirsSearch.dto.UpdateRequest;
import com.connector.iirsSearch.dto.UpdateResponse;
import com.connector.iirsSearch.dto.UpdateResponseRespData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/cdbUpdate")
public class CdbUpdateController {

    @PutMapping("/Alarm")
    public void alarm() {
        UpdateRequest updateRequest = UpdateRequest.builder()
                .reqId(1)
                .current(LocalDate.parse("2022-07-01"))
                .latest(LocalDate.parse("2022-08-01"))
                .build();
    }

    @PostMapping("/Command")
    public UpdateResponse command(@RequestBody UpdateRequest updateRequest) {
        UpdateResponseRespData updateResponseRespData = UpdateResponseRespData.builder()
                .respId(updateRequest.getReqId())
                .current(updateRequest.getLatest())
                .previous(updateRequest.getCurrent())
                .build();

        return UpdateResponse.builder()
                .respcode("OK-200")
                .respData(updateResponseRespData)
                .build();

    }

    @PostMapping("/Recovery")
    public UpdateResponse recovery(@RequestBody UpdateRequest updateRequest) {
        UpdateResponseRespData updateResponseRespData = UpdateResponseRespData.builder()
                .respId(updateRequest.getReqId())
                .current(updateRequest.getPrevious())
                .latest(updateRequest.getCurrent())
                .build();

        return UpdateResponse.builder()
                .respcode("OK-200")
                .respData(updateResponseRespData)
                .build();
    }

    @PostMapping("/OK")
    public UpdateResponse Okay(@RequestBody UpdateRequest updateRequest) {
        UpdateResponseRespData updateResponseRespData = UpdateResponseRespData.builder()
                .respId(updateRequest.getReqId())
                .current(updateRequest.getCurrent())
                .build();

        return UpdateResponse.builder()
                .respcode("OK-200")
                .respData(updateResponseRespData)
                .build();
    }
}