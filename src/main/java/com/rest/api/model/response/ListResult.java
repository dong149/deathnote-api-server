package com.rest.api.model.response;


import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResult<T> extends CommonResult {

    private List<T> list;
}
