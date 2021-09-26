package br.com.vfsilvacore.util.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaginationResponseDTO {

    private Integer page;
    private Integer pageSize;
    private Integer pageTotal;
    private Long totalElements;

}
