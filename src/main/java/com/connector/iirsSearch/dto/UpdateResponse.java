package com.connector.iirsSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateResponse {
    @JsonProperty
    private String respcode;
    @JsonProperty
    private UpdateResponseRespData respData;
}
