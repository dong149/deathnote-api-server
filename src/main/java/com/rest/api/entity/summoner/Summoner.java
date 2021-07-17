package com.rest.api.entity.summoner;

import com.rest.api.entity.note.Note;
import com.rest.api.entity.report.Report;
import com.rest.api.util.NameFormatter;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "summoners")
public class Summoner {

    /**
     * Summoner
     **/
    // Encrypted account ID



    @Id
    private String accountId;

    @Column
    private String summonerName;
    // Decoded summonerName
    @Column
    private String summonerDecodedName;
    // Encrypted summoner ID
    @Column
    private String summonerId;

    @Column
    private int profileIconId;

    @Column
    private long summonerLevel;

    @Column
    private int trollerScore;

    @Column
    private int matchCount;

    @Column
    private int matchWin;

    @Column
    private int matchLose;

    @Column
    private int matchWinningRate;

    @Column
    private String summonerTier;

    @Column
    private String summonerRank;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "matchAccountId")
    private List<Match> matches;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reportAccountId")
    private List<Report> reports;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "noteAccountId")
    private List<Note> notes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    // for test
    public Summoner(String accountId, String summonerName, String summonerTier, String summonerRank) {
        this.accountId = accountId;
        this.summonerName = summonerName;
        this.summonerDecodedName = null;
        this.summonerId = null;
        this.summonerTier = summonerTier;
        this.summonerRank = summonerRank;
        this.profileIconId = 0;
        this.summonerLevel = 0;
        this.trollerScore = 0;
        this.matchCount = 0;
        this.matchWin = 0;
        this.matchLose = 0;
        this.matchWinningRate = 0;
    }

    public void reload(
            String summonerName,
            String summonerDecodedName,
            String summonerRank,
            String summonerTier,
            int trollerScore,
            int profileIconId,
            long summonerLevel,
            int matchCount,
            int matchWin,
            int matchLose,
            int matchWinningRate,
            List<Match> matches){
        this.summonerName = summonerName;
        this.summonerDecodedName = summonerDecodedName;
        this.summonerRank = summonerRank;
        this.summonerTier = summonerTier;
        this.trollerScore = trollerScore;
        this.profileIconId = profileIconId;
        this.summonerLevel= summonerLevel;
        this.matchCount = matchCount;
        this.matchWin = matchWin;
        this.matchLose = matchLose;
        this.matchWinningRate = matchWinningRate;
        this.matches = matches;

        this.updatedAt = LocalDateTime.now();
    }
}