package com.rest.api.dto.request.note;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequestDto {


    @NotBlank
    private String accountId;

    private Boolean isGood;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 300)
    private String content;

    private Long matchId;


}
