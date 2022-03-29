package com.rest.api.model.dto.response.rank;

import com.rest.api.model.dto.TrollerRankerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;



@Builder
@Getter
@Setter
public class TrollerRankerResponseDto {
    private List<TrollerRankerDto> rankList;
}
