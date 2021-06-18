package com.rest.api.entity.summoner;

import com.rest.api.entity.report.Report;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "matchAccountId")
    private List<Match> matches;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "reportAccountId")
    private List<Report> reports;

    @CreationTimestamp
    private LocalDateTime updatedAt;


}