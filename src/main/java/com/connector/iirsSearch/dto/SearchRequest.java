package com.connector.iirsSearch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SearchRequest {
    private int reqId;
    private String phone;
    private String name;
    private String healthy;
    private String age;
    private String gender;
    private String experienced;
    private String enterDnr;
    private String target;
    private String exitDnr;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enterDtm;
    private int weather;
    private String info;
    private String company;
    private String service;
    private int reqType;
    @JsonProperty
    private List<SearchRequestRegInfo> regInfo;
}