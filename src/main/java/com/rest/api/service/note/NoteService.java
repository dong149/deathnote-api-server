package com.rest.api.service.note;


import com.rest.api.dto.request.note.NoteRequestDto;
import com.rest.api.dto.response.note.NoteResponseDto;
import com.rest.api.entity.note.Note;
import com.rest.api.exception.summoner.SummonerNotFoundException;
import com.rest.api.repository.NoteJpaRepo;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.repository.SummonerToNoteFieldMapper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteJpaRepo noteJpaRepo;
    private final SummonerJpaRepo summonerJpaRepo;

    private static final int RECENT_NOTE_NUM = 8;
    private static final Pageable noteRecentPageable = PageRequest.of(
        0, RECENT_NOTE_NUM, Sort.by(Sort.Direction.DESC, "updatedAt"));

    public NoteResponseDto createNote(NoteRequestDto noteRequestDto) {

        noteJpaRepo.save(Note.builder()
                             .title(noteRequestDto.getTitle())
                             .content(noteRequestDto.getContent())
                             .isGood(false)
                             .noteAccountId(noteRequestDto.getAccountId())
                             .upCount(0)
                             .build());

        return NoteResponseDto.builder()
                              .content(noteRequestDto.getContent())
                              .title(noteRequestDto.getTitle())
                              .build();
    }


    public List<NoteResponseDto> findNoteListWithAccountId(String accountId) {

        List<Note> notes = noteJpaRepo.findAllByNoteAccountId(accountId);
        return NoteResponseDto.of(notes)
                              .stream()
                              .sorted(Comparator.comparing(NoteResponseDto::getUpdatedAt))
                              .collect(Collectors.toList());
    }


    public List<NoteResponseDto> findNoteListRecent() {

        List<Note> notes = noteJpaRepo.findNoteListRecent(noteRecentPageable);
        List<NoteResponseDto> noteResponseDtoList = new ArrayList<>();
        for (Note note : notes) {
            SummonerToNoteFieldMapper summoner = summonerJpaRepo.getByAccountId(note.getNoteAccountId())
                                                                .orElseThrow(() -> {
                                                                    throw new SummonerNotFoundException(
                                                                        "Summoner를 찾을 수 없습니다.");
                                                                });

            noteResponseDtoList.add(NoteResponseDto.of(note, summoner));
        }
        return noteResponseDtoList;
    }


}
