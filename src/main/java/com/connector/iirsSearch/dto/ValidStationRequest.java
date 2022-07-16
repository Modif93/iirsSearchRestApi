package com.connector.iirsSearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidStationRequest {
    private int reqId;
    private int type;
    private String company;
    private List<ValidStationRequestStations> stations;
    private String address;
}