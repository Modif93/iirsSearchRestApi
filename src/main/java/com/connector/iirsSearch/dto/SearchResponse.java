package com.connector.iirsSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchResponse {
    private String respCode;
    //@JsonProperty
    private SearchResponseRespData respData;
}