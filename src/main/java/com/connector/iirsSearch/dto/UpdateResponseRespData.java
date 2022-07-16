package com.connector.iirsSearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class UpdateResponseRespData {
    @JsonProperty
    private int respId;
    @JsonProperty
    private LocalDate previous;
    @JsonProperty
    private LocalDate current;
    @JsonProperty
    private LocalDate latest;
}