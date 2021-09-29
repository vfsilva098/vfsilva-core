package br.com.vfsilvacore.i18n.provider;

import br.com.vfsilvacore.i18n.I18nMessage;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Interface responsável pela prototipação de repositório I18n.
 */
public interface II18nDataProviderRepository {

    /**
     * Método responsável pelo carregamento de termos por idioma.
     *
     * @return
     */
    Map<Locale, Map<String, I18nMessage>> recuperarPorIdioma();

    /**
     * Método responsável pela recuperação de idiomas disponíveis da aplicação.
     *
     * @return
     */
    List<Locale> recuperarIdiomas();

}
