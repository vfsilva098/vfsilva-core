package br.com.vfsilvacore.util.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CollectionResponseDTO<DTO> {

    private Collection<DTO> data;
    private PaginationResponseDTO pagination;

    public CollectionResponseDTO(final List<DTO> data, final Integer page, final Integer pageSize, final Long totalElements, final Integer pageTotal) {
        this.pagination = new PaginationResponseDTO();

        this.pagination.setPage(page);
        this.pagination.setPageSize(pageSize);
        this.pagination.setTotalElements(totalElements);
        this.pagination.setPageTotal(pageTotal);

        this.data = data;
    }
}
