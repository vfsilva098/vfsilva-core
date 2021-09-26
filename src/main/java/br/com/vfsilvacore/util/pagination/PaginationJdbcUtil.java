package br.com.vfsilvacore.util.pagination;

import br.com.vfsilvacore.util.pagination.mapping.model.PageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationJdbcUtil<T> {

    public PageModel<T> generatePagination(final int page, final int pageSize, final List<T> list) {
        final int pageHandler = page > 0 ? page - 1 : 0;
        final int pageSizeHandler = pageSize > 0 ? (Math.min(pageSize, 1000)) : 50;
        if (list == null) {
            return null;
        }

        final List<T> retorno = list.parallelStream()
            .skip(pageHandler * pageSizeHandler)
            .limit(pageSizeHandler)
            .collect(Collectors.toCollection(ArrayList::new));
        final Integer pageTotal = (list.size() % pageSizeHandler) > 0 ? (list.size() / pageSizeHandler) + 1 : list.size() / pageSizeHandler;
        final Long size = (long) list.size();
        return new PageModel<>(retorno, size, pageHandler + 1, pageSizeHandler, pageTotal);
    }

    public PageModel<T> generatePagination(final int page, final int pageSize, final List<T> list, final int totalElements) {
        final int pageHandler = page > 0 ? page - 1 : 0;
        final int pageSizeHandler = pageSize > 0 ? (Math.min(pageSize, 1000)) : 50;
        if (list == null) {
            return null;
        }

        final Integer pageTotal = (totalElements % pageSizeHandler) > 0 ? (totalElements / pageSizeHandler) + 1 : totalElements / pageSizeHandler;
        final Long size = (long) totalElements;
        return new PageModel<>(list, size, pageHandler + 1, pageSizeHandler, pageTotal);
    }
}
