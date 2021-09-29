package br.com.vfsilvacore.util.message.translator;

import br.com.vfsilvacore.i18n.I18nMessage;
import br.com.vfsilvacore.util.message.Message;

import java.util.List;

/**
 * Interface responsável pela prototipação do tradutor de mensagens.
 */
public interface IMessageTranslator {

    /**
     * Método responsável pela tradução de uma listagem de mensagens.
     *
     * @param message
     */
    void translateMessages(List<Message> message);

    /**
     * Método responsável pela tradução de uma mensagem.
     *
     * @param message
     */
    void translateMessage(Message message);

    /**
     * Método responsável pela tradução de um termo.
     *
     * @param key
     * @return
     */
    I18nMessage internationalizeTerm(String key);

    /**
     * Método responsável pela tradução de uma lista de termos.
     *
     * @param keys
     * @return
     */
    List<I18nMessage> internationalizeTerm(List<String> keys);

}
