package br.com.vfsilvacore.util.pagination.mapping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class OrderType {

    private Boolean asc;
    private String attribute;

}
