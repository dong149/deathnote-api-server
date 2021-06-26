package com.rest.api.entity.summoner;

import com.rest.api.dto.result.SummonerMatchDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Column
    private String matchAccountId;

    @Column
    private int matchRank;

    @Column
    private boolean matchWin;

    @Column
    private int matchChampion;

    @Column
    private int matchKills;

    @Column
    private int matchDeaths;

    @Column
    private int matchAssists;

    @Column
    private int matchDealRank;

    @Column
    private int matchTankRank;

    @Column
    private int matchKdaScoreRank;

    @Column
    private int matchTowerDealRank;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Match(String matchAccountId,int matchRank, boolean matchWin, int matchChampion, int matchKills, int matchDeaths, int matchAssists, int matchDealRank, int matchTankRank, int matchKdaScoreRank, int matchTowerDealRank) {
        this.matchAccountId = matchAccountId;
        this.matchRank = matchRank;
        this.matchWin = matchWin;
        this.matchChampion = matchChampion;
        this.matchKills = matchKills;
        this.matchDeaths = matchDeaths;
        this.matchAssists = matchAssists;
        this.matchDealRank = matchDealRank;
        this.matchTankRank = matchTankRank;
        this.matchKdaScoreRank = matchKdaScoreRank;
        this.matchTowerDealRank = matchTowerDealRank;
    }



}
