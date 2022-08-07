package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.SearchRequest;
import com.connector.iirsSearch.dto.SearchRequestRegInfo;
import com.connector.iirsSearch.dto.SearchRequestRegInfoStations;
import com.connector.iirsSearch.dto.SearchResponse;
import com.connector.iirsSearch.exception.RequestException;
import com.connector.iirsSearch.service.FtpPutService;
import com.connector.iirsSearch.service.SearchResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.connector.iirsSearch.exception.RequestExceptCode.MANDATORY_PARAM_ERROR;
import static com.connector.iirsSearch.exception.RequestExceptCode.INVALID_PARAM_ERROR;


@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/searchReq")
public class SearchController {
    private final SearchResponseService searchResponseService;
    private final FtpPutService ftpPutService;

    @PostMapping("/cid_series")
    public ResponseEntity<SearchResponse> cidSeries(@RequestBody SearchRequest searchRequest) throws IOException {

        // 요청 메시지 유효성 검증
        validateRescueDataRequest(searchRequest);

        // 위치추정

        // 조난발생 시뮬레이터 결과 전송(FTP Put)
        ftpPutService.FTPUploader(new File("C:\\myPrivateWork\\putty\\cid_test.txt"));

        // 응답
        return ResponseEntity.ok(
                searchResponseService.cidSeriesResponse(searchRequest)
        );
    }

    @PostMapping("/ecid")
    public ResponseEntity<SearchResponse> ecid(@RequestBody SearchRequest searchRequest) throws IOException {
        // 요청 메시지 유효성 검증
        validateRescueDataRequest(searchRequest);

        // 위치추정

        // 조난발생 시뮬레이터 결과 전송(FTP Put)
        ftpPutService.FTPUploader(new File("C:\\myPrivateWork\\putty\\ecid_test.txt"));

        // 응답
        return ResponseEntity.ok(
                searchResponseService.ecidResponse(searchRequest)
        );
    }

    public void validateRescueDataRequest(SearchRequest request) {
        if (request.getPhone() == null || request.getPhone().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "phone");
        if (request.getName() == null || request.getName().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "name");
        if (request.getHealthy() == null || request.getHealthy().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "healty");
        if (request.getAge() == null || request.getAge().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "age");
        if (request.getGender() == null || request.getGender().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "gender");
        if (request.getExperienced() == null || request.getExperienced().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "experienced");
        if (request.getTarget() == null || request.getTarget().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "target");
        if (request.getEnterDtm() == null || request.getEnterDtm().toString().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "enterDtm");
        if (request.getCompany() == null || request.getCompany().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "company");
        if (request.getService() == null || request.getService().trim().isEmpty())
            throw new RequestException(MANDATORY_PARAM_ERROR, "service");
        if (request.getRegInfo().size() == 0)
            throw new RequestException(MANDATORY_PARAM_ERROR, "regInfo");
        for (SearchRequestRegInfo item : request.getRegInfo()) {
            if (item.getTime() == null)
                throw new RequestException(MANDATORY_PARAM_ERROR, "regInfo-time");
            if (item.getStations().size() == 0)
                throw new RequestException(MANDATORY_PARAM_ERROR, "regInfo-stations");
            for(SearchRequestRegInfoStations stations : item.getStations()) {
                if (stations.getCallSign() == null || stations.getCallSign().trim().isEmpty())
                    throw new RequestException(MANDATORY_PARAM_ERROR, "reginfo-stations-callsign");
            }
        }

        if (Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", request.getPhone()) == false)
            throw new RequestException(INVALID_PARAM_ERROR, "phone");
        if (Pattern.matches("^[상중하]$", request.getHealthy()) == false)
            throw new RequestException(INVALID_PARAM_ERROR, "healthy");
        if (Pattern.matches("^[1-7]0대$", request.getAge()) == false)
            throw new RequestException(INVALID_PARAM_ERROR, "age");
        if (Pattern.matches("^[상중하]$", request.getExperienced()) == false)
            throw new RequestException(INVALID_PARAM_ERROR, "experienced");
        if (request.getWeather() < 0 && request.getWeather() > 6)
            throw new RequestException(INVALID_PARAM_ERROR, "wheather");
        if (request.getReqType() < 1 && request.getReqType() > 3)
            throw new RequestException(INVALID_PARAM_ERROR, "reqType");
    }
}