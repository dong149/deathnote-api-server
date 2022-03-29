package com.rest.api.service.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active:local")
class DeathnoteServiceTest {


    @Autowired
    private DeathnoteService deathnoteService;


    @BeforeEach
    void beforeEach() {
//        Summoner

//    @Test
//    @DisplayName("Report를 잘 찾아오는지 테스트")
//    void findAllBySummonerName() {
//        String summonerName = "hideonbush";
//        List<Report> findAllBySummonerName = reportJpaRepo.findAllBySummonerName(summonerName);
//        Assertions.assertEquals(4, findAllBySummonerName.size());
    }


}