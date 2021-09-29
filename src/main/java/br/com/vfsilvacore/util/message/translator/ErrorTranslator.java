package br.com.vfsilvacore.util.message.translator;

import br.com.vfsilvacore.i18n.I18nMessage;
import br.com.vfsilvacore.i18n.provider.II18nProvider;
import br.com.vfsilvacore.util.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Classe responsável pelo mecanismo de tradução dos erros.
 *
 */
@Service
@Primary
@RequestScope
public class ErrorTranslator implements IErrorTranslator {

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
    public void translateErrorMessages(List<ErrorMessage> errors) {

        for (ErrorMessage error : errors) {
            translateErrorMessage(error);
        }

    }

    @Override
    public void translateErrorMessages(List<ErrorMessage> errors, Locale locale) {

        for (ErrorMessage error : errors) {
            translateErrorMessage(error, locale);
        }

    }

    @Override
    public void translateErrorMessage(ErrorMessage errorMessage) {

        I18nMessage messageTile = internationalizeTerm(errorMessage.getTitle());

        String title = messageTile != null ? messageTile.getMessage() : null;
        I18nMessage i18nMessage = internationalizeTerm((errorMessage.getError()));

        String _error = null;
        String code = messageTile != null ? messageTile.getCode() : null;
        if (i18nMessage != null) {
            _error = i18nMessage.getMessage();
            code = i18nMessage.getCode();
        }

        if (errorMessage.getParamsTitle() != null && errorMessage.getParamsTitle().length > 0 && !title.equals(errorMessage.getParamsTitle())) {
            title = MessageFormat.format(title, errorMessage.getParamsTitle());
            errorMessage.setParamsTitle(null);
        }

        if (errorMessage.getParamsError() != null && errorMessage.getParamsError().length > 0 && !_error.equals(errorMessage.getParamsError())) {
            _error = MessageFormat.format(_error, errorMessage.getParamsError());
            errorMessage.setParamsError(null);
        }

        errorMessage.setTitle(title);
        errorMessage.setError(_error);
        errorMessage.setCode(code);

        // TRADUÇÃO DOS DETALHES DA MENSAGEM.
        if (!CollectionUtils.isEmpty(errorMessage.getDetails())) {
            translateErrorMessages(errorMessage.getDetails());
        }

    }

    @Override
    public void translateErrorMessage(ErrorMessage errorMessage, Locale locale) {

        I18nMessage messageTile = internationalizeTerm(errorMessage.getTitle());

        String title = messageTile != null ? messageTile.getMessage() : null;
        I18nMessage i18nMessage = internationalizeTerm((errorMessage.getError()));

        String _error = null;
        String code = messageTile != null ? messageTile.getCode() : null;
        if (i18nMessage != null) {
            _error = i18nMessage.getMessage();
            code = i18nMessage.getCode();
        }

        if (errorMessage.getParamsTitle() != null && errorMessage.getParamsTitle().length > 0 && !title.equals(errorMessage.getParamsTitle())) {
            title = MessageFormat.format(title, errorMessage.getParamsTitle());
            errorMessage.setParamsTitle(null);
        }

        if (errorMessage.getParamsError() != null && errorMessage.getParamsError().length > 0 && !_error.equals(errorMessage.getParamsError())) {
            _error = MessageFormat.format(_error, errorMessage.getParamsError());
            errorMessage.setParamsError(null);
        }

        errorMessage.setTitle(title);
        errorMessage.setError(_error);
        errorMessage.setCode(code);

        // TRADUÇÃO DOS DETALHES DA MENSAGEM.
        if (!CollectionUtils.isEmpty(errorMessage.getDetails())) {
            translateErrorMessages(errorMessage.getDetails(), locale);
        }

    }

    /**
     * Método interno responsável pela internacionalização dos termos.
     */
    private I18nMessage internationalizeTerm(String key) {

        if (key == null) {
            return null;
        }

        // CARREGAMENTO DOS TERMOS DE ACORDO COM IDIOMA DA REQUIÇÃO.
        if (termos == null) {
            termos = provider.loadMap(getLocale());
        }

        return termos.get(key) != null ? termos.get(key) : new I18nMessage(null, key);

    }

    /**
     * Método interno responsável pela internacionalização dos termos.
     */
    private I18nMessage internationalizeTerm(String key, Locale locale) {

        if (key == null) {
            return null;
        }

        // CARREGAMENTO DOS TERMOS DE ACORDO COM IDIOMA DA REQUIÇÃO.
        if (termos == null) {
            termos = provider.loadMap(locale);
        }

        return termos.get(key) != null ? termos.get(key) : new I18nMessage(null, key);

    }

}
