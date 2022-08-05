package com.connector.iirsSearch.controller;


import com.connector.iirsSearch.dto.*;
import com.connector.iirsSearch.exception.RequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.connector.iirsSearch.exception.RequestExceptCode.MANDATORY_PARAM_ERROR;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/validReq")
public class ValidController {
    @PostMapping("/target")
    public ValidTargetResponse target(@RequestBody ValidTargetRequest validTargetRequest) {

        validateTarget(validTargetRequest);

        JSONObject target1 = new JSONObject();
        JSONObject target2 = new JSONObject();
        JSONObject target3 = new JSONObject();
        target1.put("0", "지리산");
        target2.put("1", "설악산");
        target3.put("2", "백두산");

        JSONArray targetArr = new JSONArray();
        targetArr.add(target1);
        targetArr.add(target2);
        targetArr.add(target3);

        ValidTargetResponseRespData respData = new ValidTargetResponseRespData();
        respData.setRespId(validTargetRequest.getReqId());
        respData.setTarget(targetArr);

        return ValidTargetResponse.builder()
                .respCode("OK-200")
                .respData(respData)
                .build();
    }

    @PostMapping("/stations")
    public ValidStationResponse stations(@RequestBody ValidStationRequest validStationRequest) {
        ValidStationResponseRespData respData = new ValidStationResponseRespData();
        List<ValidStationResponseRespDataStations> stations = new ArrayList<ValidStationResponseRespDataStations>();
        if (validStationRequest.getType() == 1) {
            for(int i = 0; i < validStationRequest.getStations().size(); i++)
            {
                ValidStationResponseRespDataStations station = ValidStationResponseRespDataStations.builder()
                        .callSign(validStationRequest.getStations().get(i).getCallSign())
                        .service("4G")
                        .build();
                stations.add(station);
            }
            respData.setStations(stations);
            respData.setRespId(validStationRequest.getReqId());
            respData.setCompany(validStationRequest.getCompany());
        }
        else {
            // type=2 (지번주소), type=3 (법정동주소)
        }

        return ValidStationResponse.builder()
                .respCode("OK-200")
                .respData(respData)
                .build();
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