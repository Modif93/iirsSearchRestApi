package com.connector.iirsSearch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidTargetRequest {
    private int reqId;
    private String addr1;
    private String addr2;
    private String addr3;
}