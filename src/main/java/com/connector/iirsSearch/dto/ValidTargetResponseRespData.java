package com.connector.iirsSearch.dto;

import lombok.*;
import org.json.simple.JSONArray;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidTargetResponseRespData {
    private int respId;
    private JSONArray target;
}