package com.rest.api.dto.response.note;


import com.rest.api.entity.note.Note;
import com.rest.api.entity.summoner.Summoner;
import com.rest.api.repository.SummonerToNoteFieldMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
@Getter
@Setter
public class NoteResponseDto {

    private Long noteId;
    private String accountId;

    private String summonerName;
    private Integer profileIconId;
    private String summonerRank;
    private String summonerTier;


    private boolean isGood;
    private int upCount;
    private String title;
    private String content;
    private LocalDateTime updatedAt;



    public static NoteResponseDto of(Note note, SummonerToNoteFieldMapper summoner){
        return NoteResponseDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .isGood(note.getIsGood())
                .upCount(note.getUpCount())
                .updatedAt(note.getUpdatedAt())
                .accountId(note.getNoteAccountId())
                .summonerName(summoner.getSummonerName())
                .summonerRank(summoner.getSummonerRank())
                .profileIconId(summoner.getProfileIconId())
                .summonerTier(summoner.getSummonerTier())
                .build();

    }
    public static NoteResponseDto of(Note note){
        return NoteResponseDto.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .content(note.getContent())
                .accountId(note.getNoteAccountId())
                .isGood(note.getIsGood())
                .upCount(note.getUpCount())
                .updatedAt(note.getUpdatedAt())
                .build();

    }
    public static List<NoteResponseDto> of(List<Note> notes){
        return notes.stream()
                .map(NoteResponseDto::of)
                .collect(Collectors.toList());
    }
}
