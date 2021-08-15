package com.rest.api.repository;

import com.rest.api.entity.summoner.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface MatchJpaRepo extends JpaRepository<Match,Long> {
    @Transactional
    void deleteAllByMatchAccountId(String summonerAccountId);
}


// Http, Https 차이 => 모의면접 SSL , CA 공개키 비밀키 ( 블로그에 있어서 ), 근데 보안 백엔드 전문가가 들어왔거든
// HashMap 구조 -> Separate Chanining , 0.75 load factor bucket -> 75% 2배로 늘려준다.
// readtimeout, http request 에서 , connectiontimeout 모릅니다.
// 인덱스에서 Btree 에서 왜 Btree쓰는지 HashMap 같은거 왜 안쓰는지 O(1) 모릅니다.
// RDB , NoSQL차이
// AWS 비용 얼마 쓰는지 ( 이거 구조 다 알고있는지 ) , 프로젝트에 얼마나 진심인지
// 돈 쓰는거 아깝지 않냐

// http 에 관련된거 중요하게 봐야될듯

