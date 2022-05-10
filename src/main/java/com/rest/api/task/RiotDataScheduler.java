package com.rest.api.task;


import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.rest.api.controller.v1.riot.RiotAPIController;
import com.rest.api.model.dto.mldata.DataRankDto;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RiotDataScheduler {

    private static RiotAPIController riotAPIController;
    /*
     * 유저의 닉네임을 입력하여, gameId를 얻습니다.
     */
//    public static void main(String[] args) throws InterruptedException {
//        Scanner sc = new Scanner(System.in);
//        StringBuilder sb = new StringBuilder();
//        List<Long> matchIdList = new ArrayList<>();
//        while(true){
//            String summonerName = sc.nextLine();
//            List<Long> temp = riotAPIController.getMatchListForML(summonerName);
//            matchIdList.addAll(temp);
//            Thread.sleep(5000);
//            if(summonerName.equals("END")){
//                break;
//            }
//        }
//
//        for(long cur:matchIdList){
//            sb.append(cur).append('\n');
//        }
//        System.out.print(sb);
//    }


    /*
     * gameId로, 각각의 데이터를 모두 출력한다.
     */
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<DataRankDto> resList = new ArrayList<>();
        int cnt = 0;
        while (true) {
            String gameId = sc.nextLine();
            if (gameId.equals("0")) {
                break;
            }
            List<DataRankDto> tempList = riotAPIController.getMatchInfoForML(gameId);
            System.out.println("현재 cnt : " + cnt);
            cnt++;
            Thread.sleep(10000);
            resList.addAll(tempList);
        }
        exportCSV(resList);

    }

    public static void exportCSV(List<DataRankDto> list) throws Exception {
        // set file name and content type
        String filename = "src/main/resources/data.csv";
        Path path = Paths.get(filename);

        try (var writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            StatefulBeanToCsv<DataRankDto> beanToCsv = new StatefulBeanToCsvBuilder<DataRankDto>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
            beanToCsv.write(list);
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
}
