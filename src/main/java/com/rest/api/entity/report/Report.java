package com.rest.api.entity.report;


import lombok.*;
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
@Table(name = "reports")
public class Report {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column
    private String reportAccountId;

    @Column
    private boolean isReport;

    @Column
    @NotNull
    private String summonerName;

    @Column
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Report(String reportAccountId, boolean isReport, String summonerName, String content) {
        this.reportAccountId = reportAccountId;
        this.isReport = isReport;
        this.summonerName = summonerName;
        this.content = content;
    }
}
