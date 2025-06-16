package com.camoutech.enotesapiservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailRequest {

    private String to;

    private String subject;

    private String title;

    private String message;

}