package com.rest.api.dto.request.report;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportUpdateRequestDto {
    @NotBlank
    @Size(max = 200)
    private String content;
}
