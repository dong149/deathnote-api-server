package com.rest.api.controller.note;

import com.rest.api.dto.request.note.NoteRequestDto;
import com.rest.api.dto.response.BaseResponseDto;
import com.rest.api.dto.response.note.NoteListResponseDto;
import com.rest.api.dto.response.note.NoteResponseDto;
import com.rest.api.service.note.NoteService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"4. Note"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1/note")
public class NoteController {


    private final NoteService noteService;

    @ApiOperation(value = "note", notes = "note 생성", response = NoteResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "note 생성 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @PostMapping
    public ResponseEntity<BaseResponseDto> createNote(@RequestBody NoteRequestDto noteRequestDto) {
        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.CREATED.value(), "note 생성 성공", noteService.createNote(noteRequestDto)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "note", notes = "note 조회", response = NoteResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "note 조회 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @GetMapping
    public ResponseEntity<BaseResponseDto> getNoteByAccountId(@ApiParam(value = "accountId", required = true) @RequestParam String accountId){
        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "note 조회 성공", noteService.findNoteListWithAccountId(accountId)), HttpStatus.OK);
    }


    @ApiOperation(value = "note", notes = "최근 note 조회", response = NoteResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "최근 note 조회 성공"),
            @ApiResponse(code = 500, message = "서버 에러"),
    })
    @GetMapping(value="/recent")
    public ResponseEntity<BaseResponseDto> getNoteRecent(){
        return new ResponseEntity<>(new BaseResponseDto(HttpStatus.OK.value(), "최근 note 조회 성공", NoteListResponseDto.builder().noteList(noteService.findNoteListRecent()).build()), HttpStatus.OK);
    }




}
