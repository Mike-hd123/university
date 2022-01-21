package com.mike.conctrol.info.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    String query;
    int pagenum;
    int pagesize;
}
