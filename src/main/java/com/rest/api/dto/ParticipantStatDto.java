package com.rest.api.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantStatDto {
    private boolean firstInhibitorKill;
    private long physicalDamageTaken;
    private int nodeNeutralizeAssist;
    private int totalPlayerScore;
    private int champLevel;
    private long damageDealtToObjectives;
    private long totalDamageTaken;
    private long neutralMinionsKilled;
    private int deaths;
    private int tripleKills;
    private int wardsKilled;
    private int pentaKills;
    private boolean firstTowerKill;
    private int totalScoreRank;
    private long totalDamageDealt;
    private long totalDamageDealtToChampions;
    private int totalMinionsKilled;
    private boolean firstInhibitorAssist;
    private int kills;
    private boolean firstTowerAssist;
    private int inhibitorKills;
    private int turretKills;
    private int participantId;
    private long trueDamageTaken;
    private boolean firstBloodAssist;
    private int assists;
    private int goldSpent;
    private long damageDealtToTurrets;
    private boolean win;
    private long totalHeal;
    private long visionScore;
    private boolean firstBloodKill;
    private long trueDamageDealtToChampions;
    private int doubleKills;
    private long trueDamageDealt;
    private int quadraKills;
    private int playerScore0;
    private int playerScore1;
    private int playerScore2;
    private int playerScore3;
    private int playerScore4;
    private int playerScore5;
    private int playerScore6;
    private int playerScore7;
    private int playerScore8;
    private int playerScore9;
}
