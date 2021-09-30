package br.com.vfsilvacore.util.abstracao;

import br.com.vfsilvacore.util.message.ErrorMessage;

import java.util.List;

public abstract class ServiceUtilValidate<MODEL> {

    protected abstract void validacao(MODEL model);

    protected abstract MODEL construirAtualizacao(final MODEL saved, final MODEL toModifier);

    protected ErrorMessage generateErrorMessage(final String error) {
        return ErrorMessage.builder()
                .title("atencao")
                .error(error)
                .build();
    }

    protected ErrorMessage generateErrorMessage(final String error, final Object[] params) {
        return ErrorMessage.builder()
                .title("atencao")
                .error(error)
                .paramsError(params)
                .build();
    }

    protected ErrorMessage generateErrorMessage(final String error, final List<ErrorMessage> details) {
        return ErrorMessage.builder()
                .title("atencao")
                .error(error)
                .details(details)
                .build();
    }

    protected ErrorMessage generateErrorMessage(final String error, final List<ErrorMessage> details, final Object[] params) {
        return ErrorMessage.builder()
                .title("atencao")
                .error(error)
                .paramsError(params)
                .details(details)
                .build();
    }
}
