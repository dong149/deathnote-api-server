package com.rest.api.service.deathnote.batch;


import com.rest.api.entity.summoner.Summoner;
import com.rest.api.repository.SummonerJpaRepo;
import com.rest.api.service.deathnote.DeathnoteService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeathnoteBatch {

    private final SummonerJpaRepo summonerJpaRepo;
    private final DeathnoteService deathnoteService;
    private static Logger logger = LoggerFactory.getLogger(DeathnoteBatch.class);

    public void doMatchUpdateBatch() {

        List<Summoner> summonerList = summonerJpaRepo.findAll();
        for (Summoner summoner : summonerList) {
            String curName = summoner.getSummonerName();
            try {
                Thread.sleep(5000);
                deathnoteService.getSummonerInfoDtoWithSummonerName(curName, true);
                logger.info(new StringBuilder()
                                .append(curName)
                                .append("님 Batch작업 완료")
                                .toString()
                           );
            } catch (Exception e) {
                logger.info(new StringBuilder()
                                .append(curName)
                                .append("님 작업시 에러 발생")
                                .toString()
                           );
                try {
                    Thread.sleep(120000);
                } catch (Exception e2) {

                }

                e.printStackTrace();
            }
        }
    }
}
