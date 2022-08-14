package com.connector.iirsSearch.service;

import com.connector.iirsSearch.dto.SearchRequest;
import com.connector.iirsSearch.dto.SearchResponse;
import com.connector.iirsSearch.dto.SearchResponseRespData;
import com.connector.iirsSearch.model.Entitypos;
import com.connector.iirsSearch.repository.EntityposRepository;
import com.connector.iirsSearch.util.UtilMappUtm;
import com.connector.iirsSearch.util.yxUtmVo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.osgeo.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.connector.iirsSearch.util.UtilUtmToWgs84.transform;

@RequiredArgsConstructor
@Service
public class SearchResponseService {
    private final FtpPutService ftpPutService;
    private final EntityposRepository entityposRepository;
    private double marginOffset = 0.1;
    private String kmlPath = "c:\\temp\\";
    private String kmlExt = ".kml";

    public SearchResponse cidSeriesResponse(SearchRequest searchRequest) {

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
        file1.put("0", "example1.kml");
        file2.put("1", "example1.png");
        JSONArray filesArr = new JSONArray();
        filesArr.add(file1);
        filesArr.add(file2);

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

    public SearchResponse ecidResponse(SearchRequest searchRequest)
            throws IOException, ParserConfigurationException, TransformerException {
        // CDB 검색을 통한 위치추정
        // temporary for last simulation_time
        long lastTime = 0;
        List<Entitypos> entityposList = entityposRepository.findAll();
        entityposList.get(0).getSimulationTime();
        for (Entitypos entitypos : entityposList) {
            if (lastTime < entitypos.getSimulationTime()) {
                lastTime = entitypos.getSimulationTime();
            }
        }
        System.out.println("lastTime : " + lastTime);

        String fileName = getFileName(searchRequest.getReqId());
        List<String> respFileList = new ArrayList<String>();
        respFileList.add(fileName + kmlExt);
        //respFileList.add(fileName + ".png");

        // make KML
        makeKml(fileName, entityposList);

        // FTP Put
        ftpPutService.FTPUploader(new File(kmlPath + fileName + kmlExt));

        // find Margin
        double[] margin = findPosMargin(
                entityposRepository.findAllBySimulationTime(lastTime)
        );

        // Response
        return SearchResponse.builder()
                .respCode("OK-200")
                .respData(setRespMsg(margin, respFileList, searchRequest))
                .build();
    }

    private String getFileName(int _reqId) {
        return _reqId + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    private double[] findPosMargin(List<Entitypos> entityPosList) {

        double []margin = {1000, 0, 0, 1000};

        for (Entitypos entitypos : entityPosList)
        {
            yxUtmVo temp = UtilMappUtm.idx_to_utm(
                    entitypos.getY_digit_1(), entitypos.getY_digit_0(),
                    entitypos.getX_digit_1(), entitypos.getX_digit_0()
            );

            ProjCoordinate trans = transform(temp.getX(), temp.getY());

            if (margin[0] > trans.x)    margin[0] = trans.x;
            if (margin[1] < trans.y)    margin[1] = trans.y;
            if (margin[2] < trans.x)    margin[2] = trans.x;
            if (margin[3] > trans.y)    margin[3] = trans.y;
        }

        return margin;
    }

    private SearchResponseRespData setRespMsg(
            double[] _margin, List<String> _respFileList, SearchRequest _searchRequest) {
        JSONObject left = new JSONObject();
        JSONObject top = new JSONObject();
        JSONObject right = new JSONObject();
        JSONObject bottom = new JSONObject();
        left.put("left", _margin[0] - marginOffset);
        top.put("top", _margin[1] + marginOffset);
        right.put("right", _margin[2] + marginOffset);
        bottom.put("bottom", _margin[3] - marginOffset);

        JSONArray mapBoundaryArr = new JSONArray();
        mapBoundaryArr.add(left);
        mapBoundaryArr.add(top);
        mapBoundaryArr.add(right);
        mapBoundaryArr.add(bottom);

        JSONArray filesArr = new JSONArray();
        for (int i = 0; i < _respFileList.size(); i++) {
            JSONObject temp = new JSONObject();
            temp.put(i, _respFileList.get(i));
            filesArr.add(temp);
        }

        return SearchResponseRespData.builder()
                .respId(_searchRequest.getReqId())
                .phone(_searchRequest.getPhone())
                .reqType(_searchRequest.getReqType())
                .mapBoundary(mapBoundaryArr)
                .files(filesArr)
                .build();
    }

    private void makeKml(String fileName, List<Entitypos> _entityposList)
            throws ParserConfigurationException, TransformerException, FileNotFoundException {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        // New Document
        Document document = docBuilder.newDocument();
        Element root = document.createElement("kml");
        root.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
        document.appendChild(root);
        Element doc = document.createElement("Document");
        root.appendChild(doc);
        Element docName = document.createElement("name");
        doc.appendChild(docName);
        Element folder = document.createElement("Folder");
        doc.appendChild(folder);
        Element folderName = document.createElement("name");
        folderName.setTextContent("E-CID Searching");
        folder.appendChild(folderName);

        for(Entitypos entitypos : _entityposList) {
            yxUtmVo temp = UtilMappUtm.idx_to_utm(
                    entitypos.getY_digit_1(), entitypos.getY_digit_0(),
                    entitypos.getX_digit_1(), entitypos.getX_digit_0()
            );

            ProjCoordinate trans = transform(temp.getX(), temp.getY());

            Element placemark = document.createElement("Placemark");
            placemark.setAttribute("id", "id-name");
            folder.appendChild(placemark);
            Element placemarkName = document.createElement("name");
            placemarkName.setTextContent(entitypos.getEntity());
            placemark.appendChild(placemarkName);
            Element magnitude = document.createElement("magnitude");
            magnitude.setTextContent("2");
            placemark.appendChild(magnitude);
            Element point = document.createElement("Point");
            placemark.appendChild(point);
            Element coordinates = document.createElement("coordinates");
            coordinates.setTextContent(
                    "{0},{1},{2}".format(String.valueOf(trans.x), String.valueOf(trans.y), 0)
            );
            point.appendChild(coordinates);
        }

        // XML 문자열로 변환하기!
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(out);

        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = transFactory.newTransformer();

        // 출력 시 문자코드: UTF-8
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        // 들여 쓰기 있음
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

        System.out.println(new String(out.toByteArray(), StandardCharsets.UTF_8));

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(
                new FileOutputStream(
                        new File(kmlPath + fileName + kmlExt)
                )
        );
        transformer.transform(domSource, streamResult);
    }
}