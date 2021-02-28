package com.rest.api.util;

import com.rest.api.entity.StatInfo;

import java.util.Comparator;

public class ParticipantsComparator implements Comparator<StatInfo> {

    /**
     * 스탯 정보를 정렬하기 위한 함수입니다.
     **/
    @Override
    public int compare(StatInfo a,StatInfo b){
        if(a.stat>b.stat) return 1;
        if(a.stat<=b.stat) return -1;
        return 0;
    }

}
