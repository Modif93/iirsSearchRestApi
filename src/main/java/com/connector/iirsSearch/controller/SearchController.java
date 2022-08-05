package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.SearchRequest;
import com.connector.iirsSearch.dto.SearchResponse;
import com.connector.iirsSearch.service.FtpPutService;
import com.connector.iirsSearch.service.SearchResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/searchReq")
public class SearchController {

    private final SearchResponseService searchResponseService;
    private final FtpPutService ftpPutService;

    @PostMapping("/cid")
    public ResponseEntity<SearchResponse> cid(@RequestBody SearchRequest searchRequest) {
        // 요청 메시지 유효성 검증

        // 위치 추정

        // 응답
        return ResponseEntity.ok(
            searchResponseService.cidResponse(searchRequest)
        );
    }

    @PostMapping("/cid_series")
    public ResponseEntity<SearchResponse> cidSeries(@RequestBody SearchRequest searchRequest) throws IOException {

        // 요청 메시지 유효성 검증

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

        // 위치추정

        // 조난발생 시뮬레이터 결과 전송(FTP Put)
        ftpPutService.FTPUploader(new File("C:\\myPrivateWork\\putty\\ecid_test.txt"));

        // 응답
        return ResponseEntity.ok(
            searchResponseService.ecidResponse(searchRequest)
        );
    }
}