package com.rest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="summoner")
public class Summoner {

    /**
     * Summoner : 소환사 정보
     **/
    // Encrypted summoner ID
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long msrl;

    @Column(nullable = false)
    private String id;

    // Encrypted account ID
    @Column(nullable = false)
    private String accountId;

    // ID of the summoner icon associated with the summoner.
    @Column(nullable = false)
    private int profileIconId;

    // Date summoner was last modified specified as epoch milliseconds.
    // The following events will update this timestamp:
    // profile icon change, playing the tutorial or advanced tutorial, finishing a game, summoner name change
    @Column(nullable = false)
    private long revisionDate;

    // Summoner name
    @Column(nullable = false)
    private String name;

    // Encrypted PUUID
    @Column(nullable = false)
    private String puuid;

    // Summoner level associated with the summoner.
    @Column(nullable = false)
    private long summonerLevel;


}