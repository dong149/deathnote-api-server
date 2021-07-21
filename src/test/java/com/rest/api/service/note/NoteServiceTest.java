package com.rest.api.service.note;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.rest.api.dto.request.note.NoteRequestDto;
import com.rest.api.dto.response.note.NoteResponseDto;
import com.rest.api.entity.note.Note;
import com.rest.api.repository.NoteJpaRepo;
import com.rest.api.repository.SummonerJpaRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    private static NoteRequestDto noteRequestDtoTest;
    private static List<Note> noteList;


    private static final String ACCOUNT_ID = "1h5Vq0Xx7zmKTwXA2Jo58dLZ82myKYsciS9MouhDJLe6";
    private static final String ACCOUNT_ID_2 = "123455Xx7zmKTwXA2Jo58dLZ82myKYsciS9MouhDJLe6";
    private static final String SUMMONER_NAME = "Hide on Bush";
    private static final String SUMMONER_TIER = "CHALLENGER";
    private static final String SUMMONER_RANK = "1";
    private static final Boolean IS_GOOD = Boolean.FALSE;
    private static final String TITLE = "여기를 봐주세요 이것은 테스트입니다.";
    private static final String CONTENT = "이 사람은 정말 잘해서 신고합니다. 여기좀 봐주세요!!";
    private static final String TITLE_2 = "여기를 봐주세요 이것은 테스트입니다.!!!";
    private static final String CONTENT_2 = "이 사람은 정말 잘해서 신고합니다. 여기좀 봐주세요!!!!!";

    @InjectMocks
    NoteService noteService;

    @Mock
    NoteJpaRepo noteJpaRepo;

    @Mock
    SummonerJpaRepo summonerJpaRepo;


    @BeforeAll
    static void setup() {
        noteRequestDtoTest = NoteRequestDto.builder()
                .accountId(ACCOUNT_ID)
                .isGood(IS_GOOD)
                .title(TITLE)
                .content(CONTENT)
                .build();


        noteList = new ArrayList<>();
        Note note1 = Note.builder()
                .title(TITLE)
                .content(CONTENT)
                .isGood(false)
                .noteAccountId(ACCOUNT_ID)
                .upCount(0)
                .updatedAt(LocalDateTime.now())
                .build();
        Note note2 = Note.builder()
                .title(TITLE_2)
                .content(CONTENT_2)
                .isGood(false)
                .noteAccountId(ACCOUNT_ID)
                .upCount(0)
                .updatedAt(LocalDateTime.now())
                .build();
        Note note3 = Note.builder()
                .title(TITLE_2)
                .content(CONTENT_2)
                .isGood(false)
                .noteAccountId(ACCOUNT_ID_2)
                .upCount(0)
                .updatedAt(LocalDateTime.now())
                .build();


        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
    }

    @Test
    @DisplayName("노트 생성 성공 테스트")
    void createNoteSuccessTest() {
        //given

        //when
        NoteResponseDto noteResponseDto = noteService.createNote(noteRequestDtoTest);

        //then
        assertEquals(CONTENT, noteResponseDto.getContent());
        assertEquals(TITLE, noteResponseDto.getTitle());
    }

    @Test
    @DisplayName("노트 리스트 조회 성공 테스트")
    void findNoteListWithAccountIdSuccessTest() {
        //given
        doReturn(new ArrayList<>(Arrays.asList(noteList.get(0), noteList.get(1))))
                .when(noteJpaRepo).findAllByNoteAccountId(ACCOUNT_ID);
        doReturn(new ArrayList<>(Collections.singletonList(noteList.get(2))))
                .when(noteJpaRepo).findAllByNoteAccountId(ACCOUNT_ID_2);
        //when
        List<NoteResponseDto> noteResponseDtoList = noteService.findNoteListWithAccountId(ACCOUNT_ID);
        List<NoteResponseDto> noteResponseDtoList2 = noteService.findNoteListWithAccountId(ACCOUNT_ID_2);
        //then
        assertEquals(2, noteResponseDtoList.size());
        assertEquals(TITLE, noteResponseDtoList.get(0).getTitle());
        assertEquals(TITLE_2, noteResponseDtoList.get(1).getTitle());
        assertEquals(1, noteResponseDtoList2.size());
        assertEquals(TITLE_2, noteResponseDtoList2.get(0).getTitle());
    }


}
