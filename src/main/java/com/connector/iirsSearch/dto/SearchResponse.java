package com.connector.iirsSearch.dto;

import lombok.*;

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