package com.rest.api.model.entity.summoner;

import com.rest.api.model.entity.BaseEntity;
import com.rest.api.model.entity.note.Note;
import com.rest.api.model.entity.report.Report;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summoner extends BaseEntity {

    @Id
    private String accountId;
    private String summonerName;
    private String summonerDecodedName;
    private String summonerId;
    private int profileIconId;
    private long summonerLevel;
    private int trollerScore;
    private int matchCount;
    private int matchWin;
    private int matchLose;
    private int matchWinningRate;
    private String summonerTier;
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
        List<Match> matches) {
        this.summonerName = summonerName;
        this.summonerDecodedName = summonerDecodedName;
        this.summonerRank = summonerRank;
        this.summonerTier = summonerTier;
        this.trollerScore = trollerScore;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.matchCount = matchCount;
        this.matchWin = matchWin;
        this.matchLose = matchLose;
        this.matchWinningRate = matchWinningRate;
        this.matches = matches;
    }
}