package com.sport.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {


    private int pageNO;
    private int pageSize;
    private int total;

    //시작 페이지 번호
    private int start;
    //끝 페이지 번호
    private int end;

    //이전 페이지의 존재 여부
    private boolean prev;
    //다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        if (total <= 0) {
            this.dtoList = new ArrayList<>();
            return;
        }

        this.pageNO = pageRequestDTO.getPageNo();
        this.pageSize = pageRequestDTO.getPageSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int) (Math.ceil(this.pageNO / 10.0)) * 10;

        this.start = this.end - 9;

        int last = (int) (Math.ceil((total / (double) pageSize)));

        this.end = end > last ? last : end;

        this.prev = this.start > 1;

        this.next = total > this.end * this.pageSize;
    }
}
