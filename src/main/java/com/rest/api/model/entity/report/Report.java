package com.rest.api.model.entity.report;


import com.rest.api.model.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reports")
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private String reportAccountId;
    private boolean isReport;
    private String summonerName;
    private String content;

    public Report(String reportAccountId, boolean isReport, String summonerName, String content) {
        this.reportAccountId = reportAccountId;
        this.isReport = isReport;
        this.summonerName = summonerName;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
