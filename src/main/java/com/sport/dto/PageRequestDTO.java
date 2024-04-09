package com.sport.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int pageNo = 1;

    @Builder.Default
    private int pageSize = 10;

    private String searchOption;

    private String search;

    private String category;

    private String type;

    public String getSearchOption() {
        if(searchOption == null || searchOption.isEmpty()){
            return null;
        }
        return searchOption;
    }
    public Pageable getPageable(String...props) {
        return PageRequest.of(this.pageNo -1, this.pageSize, Sort.by(props).descending());
    }

    private String link;

    public String getLink() {

        if(link == null){
            StringBuilder builder = new StringBuilder();

            builder.append("pageNo=" + this.pageNo);

            builder.append("&pageSize=" + this.pageSize);

            if(searchOption != null){
                builder.append("&searchOption=" + searchOption);
            }

            if(search != null){
                try {
                    builder.append("&search=" + URLEncoder.encode(search,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }

        return link;
    }
}
