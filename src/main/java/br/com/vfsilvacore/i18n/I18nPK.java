package br.com.vfsilvacore.i18n;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável em criar PK (chave-primária do model I18n).
 * 
 * @author Patrick Francis Gomes Rocha.
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "key", "idiom" })
@Embeddable
public class I18nPK implements Serializable {

	private static final long serialVersionUID = 8840434725380985312L;

	private String key;
	private String idiom;

}
