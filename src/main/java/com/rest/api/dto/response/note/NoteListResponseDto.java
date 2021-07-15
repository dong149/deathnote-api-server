package com.rest.api.dto.response.note;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;




@Builder
@Getter
@Setter
public class NoteListResponseDto {

    private List<NoteResponseDto> noteList;

}
