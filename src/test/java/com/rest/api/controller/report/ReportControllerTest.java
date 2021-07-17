package com.rest.api.controller.report;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rest.api.controller.note.NoteController;
import com.rest.api.entity.summoner.Summoner;
import com.rest.api.repository.SummonerJpaRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("report 생성 성공 테스트")
    void createReportSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode content = objectMapper.createObjectNode();
        content.put("accountId", ACCOUNT_ID);
        content.put("isReport", IS_REPORT);
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
                .andExpect(jsonPath("$.data").value(1));
    }

//    @Test
//    @Order(2)
//    @DisplayName("report 조회 성공 테스트")
//    void




}
