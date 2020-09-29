package com.geodan.cloud.demo.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactAdminJsonPlaceholderFilter {

    private String q;

    private String order;

    private int start;

    private int end;

    private String sort;

}
