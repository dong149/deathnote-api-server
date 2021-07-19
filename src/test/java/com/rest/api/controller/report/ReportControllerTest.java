package com.rest.api.controller.report;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.api.entity.summoner.Summoner;
import com.rest.api.repository.SummonerJpaRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = "spring.profiles.active:local")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReportControllerTest {


    private static final String ACCOUNT_ID = "1h5Vq0Xx7zmKTwXA2Jo58dLZ82myKYsciS9MouhDJLe6";
    private static final String SUMMONER_NAME = "Hide on Bush";
    private static final String SUMMONER_TIER = "CHALLENGER";
    private static final String SUMMONER_RANK = "1";
    private static final Boolean IS_REPORT = Boolean.TRUE;
    private static final String CONTENT = "이 사람은 정말 잘해서 신고합니다. 여기좀 봐주세요!!";
    private static final String CONTENT_UPDATE = "수정된 글입니다!";

    private MockMvc mockMvc;

    @Autowired
    private SummonerJpaRepo summonerJpaRepo;

    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeAll
    void setup() {
        Summoner summoner = new Summoner(ACCOUNT_ID, SUMMONER_NAME, SUMMONER_TIER, SUMMONER_RANK);
        summonerJpaRepo.save(summoner);
    }

    @Test
    @Order(1)
    @DisplayName("1.report 생성 성공 테스트")
    void createReportSuccessTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode content = objectMapper.createObjectNode();
        content.put("accountId", ACCOUNT_ID);
        content.put("report", IS_REPORT);
        content.put("content", CONTENT);
        content.put("summonerName", SUMMONER_NAME);


        ResultActions result = mockMvc.perform(
                post("/api/v1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );


        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(handler().handlerType(ReportController.class))
                .andExpect(handler().methodName("createReport"))
                .andExpect(jsonPath("$.data.content").value(CONTENT));
    }

    @Test
    @Order(2)
    @DisplayName("2.report 조회 성공 테스트")
    void getReportByAccountIdSuccessTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/api/v1/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("accountId", ACCOUNT_ID)
        );


        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ReportController.class))
                .andExpect(handler().methodName("getReportByAccountId"))
                .andExpect(jsonPath("$.reportCount").value(1));


    }

    @Test
    @Order(3)
    @DisplayName("3.report 수정 성공 테스트")
    void updateReportByReportIdSuccessTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode content = objectMapper.createObjectNode();
        content.put("content", CONTENT_UPDATE);

        ResultActions result = mockMvc.perform(
                put("/api/v1/report/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
        );


        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ReportController.class))
                .andExpect(handler().methodName("updateReportByReportId"))
                .andExpect(jsonPath("$.data").value(true));


    }
//
//    @Test
//    @Order(4)
//    @DisplayName("4.report 삭제 성공 테스트")
//    void deleteReportByReportIdSuccessTest() throws Exception {
//
//
//
//        ResultActions deleteResult = mockMvc.perform(
//                delete("/api/v1/report/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//
//        deleteResult.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(handler().handlerType(ReportController.class))
//                .andExpect(handler().methodName("deleteReportByReportId"))
//                .andExpect(jsonPath("$.data").value(true));
//
//
//        ResultActions getResult = mockMvc.perform(
//                get("/api/v1/report")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .param("accountId", ACCOUNT_ID)
//        );
//
//
//        getResult.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(handler().handlerType(ReportController.class))
//                .andExpect(handler().methodName("getReportByAccountId"))
//                .andExpect(jsonPath("$.reportCount").value(0));
//
//    }


}
