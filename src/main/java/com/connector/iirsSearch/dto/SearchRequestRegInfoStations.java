package com.connector.iirsSearch.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchRequestRegInfoStations {
    private String callSign;
    private int rsrp;
    private int ta;
    private int callType;
    private int cellType;
}