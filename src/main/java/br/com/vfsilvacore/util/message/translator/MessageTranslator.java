package br.com.vfsilvacore.util.message.translator;

import br.com.vfsilvacore.i18n.I18nMessage;
import br.com.vfsilvacore.i18n.provider.II18nProvider;
import br.com.vfsilvacore.util.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Classe responsável pelo mecanismo de tradução das mensagens.
 */
@Service
@RequestScope
public class MessageTranslator implements IMessageTranslator {

    @Autowired
    private II18nProvider provider;

    @Autowired
    private HttpServletRequest request;

    private Map<String, I18nMessage> termos;

    /**
     * Método responsável em capturar o Locale da requisição
     */
    private Locale getLocale() {

        String language = request.getHeader("Accept-Language");

        if (language != null && !"".equals(language)) {

            String _language[] = language.split("_");
            if (_language.length == 2) {
                return provider.validateLocale(new Locale(_language[0], _language[1]));
            }
        }

        if (request.getLocale() != null && !"".equals(request.getLocale().toString())) {
            return provider.validateLocale(request.getLocale());
        }

        return provider.validateLocale(new Locale("pt", "BR"));
    }

    @Override
    public void translateMessages(List<Message> messages) {

        for (Message message : messages) {
            translateMessage(message);
        }

    }

    @Override
    public void translateMessage(Message message) {
        I18nMessage messageTile = internationalizeTerm(message.getTitle());

        String title = messageTile != null ? messageTile.getMessage() : null;
        I18nMessage i18nMessage = internationalizeTerm(message.getDescription());

        String description = null;
        String code = messageTile != null ? messageTile.getCode() : null;
        if (i18nMessage != null) {
            description = i18nMessage.getMessage();
            code = i18nMessage.getCode();
        }

        if (message.getParamsTitle() != null && message.getParamsTitle().length > 0) {
            title = MessageFormat.format(title, message.getParamsTitle());
            message.setParamsTitle(null);
        }

        if (message.getParamsDescription() != null && message.getParamsDescription().length > 0) {
            description = MessageFormat.format(description, message.getParamsDescription());
            message.setParamsDescription(null);
        }

        message.setTitle(title);
        message.setDescription(description);
        message.setCode(code);
    }

    @Override
    public I18nMessage internationalizeTerm(String key) {

        if (key == null) {
            return null;
        }

        // CARREGAMENTO DOS TERMOS DE ACORDO COM IDIOMA DA REQUIÇÃO.
        if (termos == null) {
            termos = provider.loadMap(getLocale());
        }

        return termos.get(key) != null ? termos.get(key) : new I18nMessage(null, key);

    }

    @Override
    public List<I18nMessage> internationalizeTerm(List<String> keys) {

        if (keys == null) {
            return null;
        }

        List<I18nMessage> termos = new ArrayList<>();

        for (String key : keys) {
            termos.add(internationalizeTerm(key));
        }

        return termos;
    }
}
