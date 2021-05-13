package com.rest.api.task;


import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rest.api.controller.RiotAPIController;
import com.rest.api.dto.SummonerDto;
import com.rest.api.dto.mldata.DataRankDto;
import com.rest.api.util.DataRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class RiotDataScheduler {

    private static final RiotAPIController riotAPIController = new RiotAPIController();

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
        while(true){
            String gameId = sc.nextLine();
            if(gameId.equals("0"))
                break;
            List<DataRankDto> tempList = riotAPIController.getMatchInfoForML(gameId);
            Thread.sleep(10000);
            resList.addAll(tempList);
        }
        exportCSV(resList);

    }

    public static void exportCSV(List<DataRankDto> list) throws Exception {
        // set file name and content type
        String filename = "src/main/resources/data.csv";
        Path path = Paths.get(filename);


        try( var writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            StatefulBeanToCsv<DataRankDto> beanToCsv = new StatefulBeanToCsvBuilder<DataRankDto>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(list);
        }catch ( Exception ex){
            System.out.println("error");
        }




    }

}
