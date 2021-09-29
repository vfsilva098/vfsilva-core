package br.com.vfsilvacore.i18n.provider;

import br.com.vfsilvacore.i18n.I18nMessage;
import br.com.vfsilvacore.i18n.pattern.ApprovedLanguages;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static br.com.vfsilvacore.i18n.pattern.ApprovedLanguages.PORTUGUES;
import static br.com.vfsilvacore.i18n.pattern.ApprovedLanguages.values;
import static java.util.Arrays.asList;

@Component(value = "i18nFilterProvider")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class I18nFilterProvider implements II18nProvider {

    @Autowired
    private II18nDataProviderRepository dataProvider;

    private Map<Locale, Map<String, I18nMessage>> mapaTermos;

    /**
     * Método responsável pelo carregamento dos mapas de termos.
     */
    @Override
    public void loadTerms() {

        log.debug(" ");
        log.debug(" I18nProvider.loadTerms() ");
        log.debug(" ");

        try {

            mapaTermos = dataProvider.recuperarPorIdioma();

        } catch (Exception e) {

            log.error(" Falha no carregamento do mapa de termos. Erro: [ " + e.getMessage() + " ]");

            mapaTermos = null;

        }
    }

    /**
     * Método responsável pelo carregamento do mapa de termos do idioma informado.
     *
     * @param locale
     * @return
     */
    @Override
    public Map<String, I18nMessage> loadMap(Locale locale) {

        if (mapaTermos == null) {
            loadTerms();
        }

        if (mapaTermos == null) {
            return null;
        }

        return mapaTermos.get(locale);
    }

    /**
     * Método responsável pela validação dos idiomas homologados. Se o idioma não
     * for homologado retorna idioma padrão (pt_BR).
     *
     * @param locale
     * @return
     */
    @Override
    public Locale validateLocale(Locale locale) {

        List<ApprovedLanguages> approveds = asList(values());

        for (ApprovedLanguages approved : approveds) {

            if (approved.getValue().equals(locale.toString())) {
                return locale;
            }
        }

        String _locale[] = PORTUGUES.getValue().split("_");

        return new Locale(_locale[0], _locale[1]);
    }

}
