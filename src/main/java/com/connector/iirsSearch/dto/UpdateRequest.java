package com.connector.iirsSearch.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateRequest {
    private int reqId;
    private LocalDate current;
    private LocalDate latest;
    private LocalDate previous;
}