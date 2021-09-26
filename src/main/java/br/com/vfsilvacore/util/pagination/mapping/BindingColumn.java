package br.com.vfsilvacore.util.pagination.mapping;

import lombok.Getter;

@Getter
public class BindingColumn {

    private String prefix;
    private String modelColumn;
    private String apiColmn;

    public BindingColumn(final String modelColumn, final String apiColmn) {
        this.modelColumn = modelColumn;
        this.apiColmn = apiColmn;
    }

    public BindingColumn(final String prefix, final String modelColumn, final String apiColmn) {
        this.prefix = prefix;
        this.modelColumn = modelColumn;
        this.apiColmn = apiColmn;


    }
}
