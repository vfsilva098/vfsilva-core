package br.com.vfsilvacore.util.abstracao;

import br.com.vfsilvacore.util.message.ErrorMessage;

import java.util.List;

public abstract class ServiceUtil<MODEL> {

    protected abstract void validacao(MODEL model);

    protected abstract MODEL construirAtualizacao(final MODEL saved, final MODEL toModifier);

    protected ErrorMessage generateErrorMessage(final String error) {
        return ErrorMessage.builder()
                .title("Atenção")
                .error(error)
                .build();
    }

    protected ErrorMessage generateErrorMessage(final String error, final List<ErrorMessage> details) {
        return ErrorMessage.builder()
                .title("Atenção")
                .error(error)
                .details(details)
                .build();
    }
}
