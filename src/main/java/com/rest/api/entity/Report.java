package com.rest.api.entity;


import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report") // 'report' 테이블과 매핑됨을 명시
public class Report {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column
    private boolean isReport;

    //어떤 소환사에 정보가 저장되었는지 나타낸다.
    @Column(nullable = false, length= 50)
    private String summonerName;

    @Column(nullable = false, length = 50)
    private String description;
    private LocalDateTime createdDate;


    @Builder
    public Report(boolean isReport,String summonerName,String description){
        this.createdDate = LocalDateTime.now();
        this.isReport = isReport;
        this.summonerName = summonerName;
        this.description = description;
    }
}
