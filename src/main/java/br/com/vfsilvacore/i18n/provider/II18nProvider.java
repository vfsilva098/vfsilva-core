package br.com.vfsilvacore.i18n.provider;

import br.com.vfsilvacore.i18n.I18nMessage;

import java.util.Locale;
import java.util.Map;

public interface II18nProvider {

    /**
     * Método responsável pelo carregamento dos mapas de termos.
     */
    void loadTerms();

    /**
     * Método responsável pelo carregamento do mapa de termos do idioma informado.
     *
     * @param locale
     * @return
     */
    public Map<String, I18nMessage> loadMap(Locale locale);

    /**
     * Método responsável pela validação dos idiomas homologados. Se o idioma não
     * for homologado retorna idioma padrão (pt_BR).
     *
     * @param locale
     * @return
     */
    public Locale validateLocale(Locale locale);

}
