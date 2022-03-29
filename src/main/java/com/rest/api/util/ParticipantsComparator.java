package com.rest.api.util;

import com.rest.api.model.dto.StatInfoDto;
import java.util.Comparator;


public class ParticipantsComparator implements Comparator<StatInfoDto> {

    @Override
    public int compare(StatInfoDto a, StatInfoDto b) {
        if (a.stat > b.stat) {
            return 1;
        } else if (a.stat == b.stat) {
            return 0;
        } else {
            return -1;
        }
    }
}
