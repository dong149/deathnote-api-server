package com.rest.api.util;

import com.rest.api.dto.StatInfoDto;

import java.util.Comparator;


/**
 * Participants의 스탯 정보를 정렬하기 위한 함수입니다.
 **/
public class ParticipantsComparator implements Comparator<StatInfoDto> {
    @Override
    public int compare(StatInfoDto a, StatInfoDto b){
        if(a.stat>b.stat)
            return 1;
        else if(a.stat==b.stat)
            return 0;
        else
            return -1;
    }
}
