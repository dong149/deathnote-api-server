package com.rest.api.model.dto.response.note;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NoteListResponseDto {

    private List<NoteResponseDto> noteList;
}
