package br.com.vfsilvacore.i18n.provider.file;

import br.com.vfsilvacore.i18n.I18nMessage;
import br.com.vfsilvacore.i18n.pattern.ApprovedLanguages;
import br.com.vfsilvacore.i18n.provider.II18nDataProviderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.*;

import static br.com.vfsilvacore.i18n.pattern.ApprovedLanguages.values;
import static java.util.Arrays.asList;

@Log4j2
@Repository
public class I18nProviderFileRepository implements II18nDataProviderRepository {

    @Override
    public Map<Locale, Map<String, I18nMessage>> recuperarPorIdioma() {

        log.debug(" ");
        log.debug(" I18nProviderFileRepository.recuperarPorIdioma() ");
        log.debug(" ");

        Map<Locale, Map<String, I18nMessage>> mapa = new HashMap<>();

        // RECUPERAÇÃO DE IDIOMAS.
        List<Locale> idiomas = recuperarIdiomas();

        log.debug(" Montagem de termos por idioma ");

        idiomas.forEach(idioma -> {

            log.debug(" Carregamento de termos com idioma: " + idioma.toString());

            mapa.put(idioma, getMapLocale(idioma));

        });

        log.debug(" Mapa de idiomas carregado com sucesso... ");
        log.debug(mapa.toString());

        return mapa;

    }

    @Override
    public List<Locale> recuperarIdiomas() {

        log.debug(" ");
        log.debug(" I18nProviderFileRepository.recuperarIdiomas() ");
        log.debug(" ");

        List<Locale> locales = new ArrayList<>();

        List<ApprovedLanguages> approvedsLocales = asList(values());

        for (ApprovedLanguages approved : approvedsLocales) {

            String _locale[] = approved.getValue().split("_");
            locales.add(new Locale(_locale[0], _locale[1]));

        }

        log.debug(" Idiomas recuperados: " + locales.toString());

        return locales;
    }

    /**
     * Método interno responsável pela recuperação do arquivo de termos de acordo
     * com idioma informado.
     *
     * @param locale
     * @return
     */
    private Map<String, I18nMessage> getMapLocale(Locale locale) {

        log.debug(" ");
        log.debug(" Execução I18nProviderFileRepository.getMapLocale");
        log.debug(" Locassle: " + locale);
        log.debug(" ");

        Map<String, I18nMessage> mapTerm = new HashMap<>();

        try {

            String bundleFile = "bundles.i18n.terms-" + locale.toString();
            ResourceBundle bundle = ResourceBundle.getBundle(bundleFile);

            Enumeration<String> keys = bundle.getKeys();

            while (keys.hasMoreElements()) {

                String key = keys.nextElement();
                String values[] = key.split("\\.");
                if (values.length == 2) {
                    String keyValue = values[0];
                    String code = values[1];
                    String message = bundle.getString(key);

                    I18nMessage i18nMessage = new I18nMessage(code, message);
                    mapTerm.put(keyValue, i18nMessage);
                }
            }


        } catch (Exception e) {

            log.error(" ");
            log.error(" ERRO: Execução I18nProviderFileRepository.getBundle ");
            log.error(" Java ERRO: " + e.getMessage());
            log.error(" ");

        }

        return mapTerm;

    }
}
