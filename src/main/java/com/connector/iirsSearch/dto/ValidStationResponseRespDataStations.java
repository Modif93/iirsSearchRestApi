package com.connector.iirsSearch.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidStationResponseRespDataStations {
    private String callSign;
    private String service;
}