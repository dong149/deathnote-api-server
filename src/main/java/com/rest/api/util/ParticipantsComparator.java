package com.rest.api.util;

import com.rest.api.entity.StatInfo;

import java.util.Comparator;

public class ParticipantsComparator implements Comparator<StatInfo> {

    @Override
    public int compare(StatInfo a,StatInfo b){
        if(a.stat>b.stat) return 1;
        if(a.stat<=b.stat) return -1;
        return 0;
    }

}
