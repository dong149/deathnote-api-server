package com.rest.api.entity;


import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report") // 'report' 테이블과 매핑됨을 명시
public class Report {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean isReport;

    @Column(nullable = false, length = 50)
    private String summonerName;

    @Column(nullable = false, length = 50)
    private String content;

    private LocalDateTime createdDate;


    @Builder
    public Report(boolean isReport, String summonerName, String content) {
        this.isReport = isReport;
        this.summonerName = summonerName;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }
}
