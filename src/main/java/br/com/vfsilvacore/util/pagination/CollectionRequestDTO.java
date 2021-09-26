package br.com.vfsilvacore.util.pagination;

import br.com.vfsilvacore.util.pagination.mapping.BindingColumn;
import br.com.vfsilvacore.util.pagination.mapping.model.Attribute;
import br.com.vfsilvacore.util.pagination.mapping.model.OrderType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


public abstract class CollectionRequestDTO<MODEL> implements Serializable {

    private int page;
    private int pageSize;
    private List<String> order;
    private List<String> fields;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getOrder() {
        if (order != null) {
            return Collections.unmodifiableList(order);
        }

        return order;
    }

    public void setOrder(final List<String> order) {
        this.order = order;
    }

    public List<String> getFields() {
        if (fields != null) {
            return Collections.unmodifiableList(fields);
        }

        return fields;
    }

    public void setFields(final List<String> fields) {
        this.fields = fields;
    }

    public abstract Example<MODEL> generateWhere();


    public Map<String, Object> generateQueryFields() {

        final Map<String, Object> queryFields = new HashMap<>();
        final BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(this);

        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())))
                .forEach(field -> {

                    final Object value = wrapper.getPropertyValue(field.getName());

                    if (value != null) {
                        queryFields.put(field.getName(), value);
                    }
                });

        return queryFields;
    }

    public Pageable generatePage(final Class<?> clazz) {
        final int pageHandler = page > 0 ? page - 1 : 0;
        final int pageSizeHandler = pageSize > 0 ? Math.min(pageSize, 1000) : 50;

        final List<Order> orders = generateOrderJPA(order, generateBindingColumns(clazz));

        if (!CollectionUtils.isEmpty(order)) {
            return PageRequest.of(pageHandler, pageSizeHandler, Sort.by(orders));
        } else {
            return PageRequest.of(pageHandler, pageSizeHandler);
        }
    }

    private List<OrderType> generateOrder(final List<String> ordination) {

        final List<OrderType> orders = new ArrayList<>();

        if (!CollectionUtils.isEmpty(ordination)) {
            for (String value : ordination) {
                final OrderType orderType = generateOrderType(value);

                if (orderType != null) {
                    orders.add(orderType);
                }
            }
        }

        return orders;
    }

    private OrderType generateOrderType(final String instruction) {

        if (StringUtils.isNotEmpty(instruction)) {
            final char prefix = instruction.charAt(0);

            if ('-' == prefix) {
                return new OrderType(false, instruction.substring(1));
            } else {
                return new OrderType(true, instruction);
            }

        }

        return null;
    }

    private List<Order> generateOrderJPA(final List<String> ordination, final List<BindingColumn> bindingColumns) {
        final List<Order> orders = new ArrayList<>();

        for (OrderType type : generateOrder(ordination)) {
            final BindingColumn column = bindingColumns
                    .parallelStream()
                    .filter(p -> type.getAttribute().equalsIgnoreCase(p.getApiColmn())).findFirst().orElse(null);

            if (column != null) {
                if (type.getAsc()) {
                    orders.add(Order.asc(column.getPrefix() != null ? column.getPrefix() + "." + column.getModelColumn()
                            : column.getModelColumn()));
                } else {
                    orders.add(
                            Order.desc(column.getPrefix() != null ? column.getPrefix() + "." + column.getModelColumn()
                                    : column.getModelColumn()));
                }
            }
        }

        return orders;
    }

    private List<BindingColumn> generateBindingColumns(final Class<?> clazz) {

        final List<BindingColumn> bindings = new ArrayList<>();

        final Field[] atributos = clazz.getDeclaredFields();

        for (Field field : atributos) {
            final Attribute fieldss = field.getDeclaredAnnotation(Attribute.class);

            if (fieldss != null) {
                final String modelColumn = fieldss.field();
                final String apiColumn = field.getName();
                final String prefix = fieldss.prefix();

                final BindingColumn bindingColumn = "".equals(prefix) ? new BindingColumn(modelColumn, apiColumn) : new BindingColumn(prefix, modelColumn, apiColumn);

                bindings.add(bindingColumn);
            }
        }

        return bindings;
    }
}
