package com.rest.api.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="summoner")
public class Summoner {

    /**
     * Summoner 한 명당 정보
     **/
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    // Encrypted account ID
    @Column
    private String accountId;

    // Encrypted summoner ID
    @Column
    private String summonerId;

    @Column
    private int profileIconId;

    @Column
    private long summonerLevel;

    @CreationTimestamp
    private LocalDateTime createdAt;
}