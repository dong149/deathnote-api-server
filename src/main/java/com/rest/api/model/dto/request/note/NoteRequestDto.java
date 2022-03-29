package com.rest.api.model.dto.request.note;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
