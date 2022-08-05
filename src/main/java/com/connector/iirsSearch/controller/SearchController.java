package com.connector.iirsSearch.controller;

import com.connector.iirsSearch.dto.SearchRequest;
import com.connector.iirsSearch.dto.SearchResponse;
import com.connector.iirsSearch.dto.SearchResponseRespData;
import com.connector.iirsSearch.service.FtpPutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/searchReq")
public class SearchController {

    private final FtpPutService ftpPutService;

    @PostMapping("/cid")
    public SearchResponse cid(@RequestBody SearchRequest searchRequest) {
        JSONObject left = new JSONObject();
        JSONObject top = new JSONObject();
        JSONObject right = new JSONObject();
        JSONObject bottom = new JSONObject();
        left.put("left", 126.123456);
        top.put("top", 37.123456);
        right.put("right", 127.123456);
        bottom.put("bottom", 36.123456);

        JSONArray mapBoundaryArr = new JSONArray();
        mapBoundaryArr.add(left);
        mapBoundaryArr.add(top);
        mapBoundaryArr.add(right);
        mapBoundaryArr.add(bottom);

        JSONObject file0 = new JSONObject();
        JSONObject file1 = new JSONObject();
        file0.put("0", "example.kml");
        file1.put("1", "example.png");
        JSONArray filesArr = new JSONArray();
        filesArr.add(file0);
        filesArr.add(file1);

        SearchResponseRespData searchResponseRespData = SearchResponseRespData.builder()
                .respId(searchRequest.getReqId())
                .phone(searchRequest.getPhone())
                .reqType(searchRequest.getReqType())
                .mapBoundary(mapBoundaryArr)
                .files(filesArr)
                .build();

        return SearchResponse.builder()
                .respCode("OK-200")
                .respData(searchResponseRespData)
                .build();
    }

    @PostMapping("/cid_series")
    public SearchResponse cidSeries(@RequestBody SearchRequest searchRequest) throws IOException {

        // 조난발생 시뮬레이터 결과 전송(FTP Put)
        ftpPutService.FTPUploader(new File("C:\\myPrivateWork\\putty\\cid_test.txt"));

        JSONObject left = new JSONObject();
        JSONObject top = new JSONObject();
        JSONObject right = new JSONObject();
        JSONObject bottom = new JSONObject();
        left.put("left", 126.123456);
        top.put("top", 37.123456);
        right.put("right", 127.123456);
        bottom.put("bottom", 36.123456);

        JSONArray mapBoundaryArr = new JSONArray();
        mapBoundaryArr.add(left);
        mapBoundaryArr.add(top);
        mapBoundaryArr.add(right);
        mapBoundaryArr.add(bottom);
        System.out.println(mapBoundaryArr.toJSONString());

        JSONObject file1 = new JSONObject();
        JSONObject file2 = new JSONObject();
        JSONObject file3 = new JSONObject();
        JSONObject file4 = new JSONObject();
        JSONObject file5 = new JSONObject();
        JSONObject file6 = new JSONObject();
        file1.put("0", "example1.kml");
        file2.put("1", "example1.png");
        file3.put("2", "example2.kml");
        file4.put("3", "example2.png");
        file5.put("4", "example3.kml");
        file6.put("5", "example3.png");
        JSONArray filesArr = new JSONArray();
        filesArr.add(file1);
        filesArr.add(file2);
        filesArr.add(file3);
        filesArr.add(file4);
        filesArr.add(file5);
        filesArr.add(file6);

        SearchResponseRespData searchResponseRespData = SearchResponseRespData.builder()
                .respId(searchRequest.getReqId())
                .phone(searchRequest.getPhone())
                .reqType(searchRequest.getReqType())
                .mapBoundary(mapBoundaryArr)
                .files(filesArr)
                .build();

        return SearchResponse.builder()
                .respCode("OK-200")
                .respData(searchResponseRespData)
                .build();
    }

    @PostMapping("/ecid")
    public SearchResponse ecid(@RequestBody SearchRequest searchRequest) throws IOException {

        // 조난발생 시뮬레이터 결과 전송(FTP Put)
        ftpPutService.FTPUploader(new File("C:\\myPrivateWork\\putty\\ecid_test.txt"));

        // CDB 검색을 통한 위치추정
        //searchResult = getLastPosition(searchRequest);

        // Response Mock Data
        JSONObject left = new JSONObject();
        JSONObject top = new JSONObject();
        JSONObject right = new JSONObject();
        JSONObject bottom = new JSONObject();
        left.put("left", 126.123456);
        top.put("top", 37.123456);
        right.put("right", 127.123456);
        bottom.put("bottom", 36.123456);

        JSONArray mapBoundaryArr = new JSONArray();
        mapBoundaryArr.add(left);
        mapBoundaryArr.add(top);
        mapBoundaryArr.add(right);
        mapBoundaryArr.add(bottom);

        JSONObject file0 = new JSONObject();
        JSONObject file1 = new JSONObject();
        file0.put("0", "example.kml");
        file1.put("1", "example.png");
        JSONArray filesArr = new JSONArray();
        filesArr.add(file0);
        filesArr.add(file1);

        SearchResponseRespData searchResponseRespData = SearchResponseRespData.builder()
                .respId(searchRequest.getReqId())
                .phone(searchRequest.getPhone())
                .reqType(searchRequest.getReqType())
                .mapBoundary(mapBoundaryArr)
                .files(filesArr)
                .build();

        return SearchResponse.builder()
                .respCode("OK-200")
                .respData(searchResponseRespData)
                .build();
    }
}