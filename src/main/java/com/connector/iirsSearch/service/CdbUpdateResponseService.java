package com.connector.iirsSearch.service;

import com.connector.iirsSearch.dto.UpdateRequest;
import com.connector.iirsSearch.dto.UpdateResponse;
import com.connector.iirsSearch.dto.UpdateResponseRespData;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CdbUpdateResponseService {

    public UpdateResponse commandResponse(UpdateRequest updateRequest) {
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

    public UpdateResponse recoveryResponse(UpdateRequest updateRequest) {
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

    public UpdateResponse OkayResponse(UpdateRequest updateRequest) {
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
