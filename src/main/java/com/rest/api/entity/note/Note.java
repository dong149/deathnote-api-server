package com.rest.api.entity.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @Column
    private String noteAccountId;

    @Column
    private Boolean isGood;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Long noteMatchId;

    @Column
    private int upCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    public Report(String reportAccountId, boolean isReport, String summonerName, String content) {
//        this.reportAccountId = reportAccountId;
//        this.isReport = isReport;
//        this.summonerName = summonerName;
//        this.content = content;
//    }
}




