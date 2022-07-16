package com.connector.iirsSearch.dto;

import lombok.*;
import org.json.simple.JSONArray;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchResponseRespData {
    private int respId;
    private String phone;
    private int reqType;
    private JSONArray mapBoundary;
    private JSONArray files;
}