package com.rest.api.repository;

import com.rest.api.entity.report.Report;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.profiles.active:local")
class ReportJpaRepoTest {

    @Autowired
    private ReportJpaRepo reportJpaRepo;


    @BeforeEach
    void beforeEach() {
        Report report1 = new Report(true, "hideonbush", "이사람은 진짜입니다.");
        Report report2 = new Report(true, "hideonbush", "이사람은 진짜인가.");
        Report report3 = new Report(false, "hideonbush", "너무 좋아요.");
        Report report4 = new Report(false, "hideonbush", "너무 좋아요.");

        reportJpaRepo.save(report1);
        reportJpaRepo.save(report2);
        reportJpaRepo.save(report3);
        reportJpaRepo.save(report4);
    }


    @Test
    @DisplayName("Report를 잘 찾아오는지 테스트")
    void findAllBySummonerName() {
        String summonerName = "hideonbush";
        List<Report> findAllBySummonerName = reportJpaRepo.findAllBySummonerName(summonerName);
        Assertions.assertEquals(4, findAllBySummonerName.size());
    }
}