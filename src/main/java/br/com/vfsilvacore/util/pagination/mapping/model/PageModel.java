package br.com.vfsilvacore.util.pagination.mapping.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Classe responsável em manter os dados de paginação dos métodos de seleção.
 *
 * @param <T>
 * @author Patrick Francis Gomes Rocha
 */
@AllArgsConstructor
@Getter
@Setter
public class PageModel<T> {

    private List<T> data;
    private Long totalElements;
    private Integer page;
    private Integer pageSize;
    private Integer pageTotal;

}
