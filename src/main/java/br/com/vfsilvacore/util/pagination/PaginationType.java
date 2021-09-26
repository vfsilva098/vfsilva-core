package br.com.vfsilvacore.util.pagination;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
public class PaginationType<T> {

    private Example<T> where;
    private Pageable page;

}
