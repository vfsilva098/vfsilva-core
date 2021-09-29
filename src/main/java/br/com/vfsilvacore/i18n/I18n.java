package br.com.vfsilvacore.i18n;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Classe responsável em manter dos dados de I18n (Termo).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class I18n {

    @EmbeddedId
    private I18nPK id;
    private String term;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

}
