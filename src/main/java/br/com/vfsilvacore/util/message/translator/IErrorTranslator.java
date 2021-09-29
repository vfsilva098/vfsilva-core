package br.com.vfsilvacore.util.message.translator;

import br.com.vfsilvacore.util.message.ErrorMessage;

import java.util.List;
import java.util.Locale;

/**
 * Interface responsável pela prototipação do tradutor de erros.
 */
public interface IErrorTranslator {

    /**
     * Método responsável pela tradução de uma listagem de erros.
     *
     * @param errors
     */
    void translateErrorMessages(List<ErrorMessage> errors);

    /**
     * Método responsável pela tradução de uma listagem de erros.
     *
     * @param errors
     * @param locale
     */
    void translateErrorMessages(List<ErrorMessage> errors, Locale locale);

    /**
     * Método responsável pela tradução de um erro.
     *
     * @param error
     */
    void translateErrorMessage(ErrorMessage error);

    /**
     * Método responsável pela tradução de um erro.
     *
     * @param error
     * @param locale
     */
    void translateErrorMessage(ErrorMessage error, Locale locale);

}
