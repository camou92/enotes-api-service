package com.camoutech.enotesapiservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {
    private List<NotesDto> notes;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isFirst;
    private Boolean isLast;
}
