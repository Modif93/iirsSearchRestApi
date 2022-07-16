package com.connector.iirsSearch.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidStationResponseRespData {
    private int respId;
    private String company;
    private List<ValidStationResponseRespDataStations> stations;
}