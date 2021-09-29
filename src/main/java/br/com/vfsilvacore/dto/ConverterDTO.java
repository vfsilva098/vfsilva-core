package br.com.vfsilvacore.dto;


import br.com.vfsilvacore.util.message.stack.MessageStack;
import br.com.vfsilvacore.util.message.translator.IMessageTranslator;
import br.com.vfsilvacore.util.pagination.CollectionRequestDTO;
import br.com.vfsilvacore.util.pagination.CollectionResponseDTO;
import br.com.vfsilvacore.util.pagination.mapping.model.PageModel;

import java.util.ArrayList;
import java.util.List;

public abstract class ConverterDTO<DTO extends SimpleResponseDTO, MODEL> {

    private final MessageStack messageStack;
    private final List<String> attributes;
    private final IMessageTranslator translator;

    public ConverterDTO(final MessageStack messageStack, final CollectionRequestDTO<?> request, final IMessageTranslator translator) {
        this.messageStack = messageStack;
        this.translator = translator;
        this.attributes = request != null ? request.getFields() : new ArrayList<>();
    }

    public ConverterDTO(final MessageStack messageStack, final IMessageTranslator translator) {
        this.messageStack = messageStack;
        this.translator = translator;
        this.attributes = new ArrayList<>();
    }

    protected Boolean isAttribute(final String attribute) {
        if (this.attributes != null) {
            return this.attributes.isEmpty() || this.attributes.contains(attribute);
        }

        return Boolean.TRUE;
    }

    protected abstract DTO converterDTO(final MODEL model);

    public List<DTO> convertManyDTOs(final List<MODEL> models) {
        final var array = new ArrayList<DTO>();

        if (models != null) {
            models.forEach((model) -> array.add(this.converterDTO(model)));
        }

        return array;
    }

    public DTO toDTO(final MODEL model) {
        final DTO handler = converterDTO(model);

        if (messageStack.isMessage()) {
            translator.translateMessages(messageStack.getMessages());
            handler.setMessages(messageStack.getMessages());
        }


        return handler;
    }

    public CollectionResponseDTO<DTO> convertManyCollectionResponse(final PageModel<MODEL> page) {
        final var dtos = this.convertManyDTOs(page.getData());
        return new CollectionResponseDTO<>(dtos, page.getPage(), page.getPageSize(), page.getTotalElements(), page.getPageTotal());
    }
}
