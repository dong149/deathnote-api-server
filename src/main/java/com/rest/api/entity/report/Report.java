package com.rest.api.entity.report;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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

    public void update(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();

    }
}
