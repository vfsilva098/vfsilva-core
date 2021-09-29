package br.com.vfsilvacore.i18n.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApprovedLanguages {

	PORTUGUES("pt_BR", "Português"), 
	INGLES("en_US", "Inglês"), 
	ESPANHOL("es_ES", "Espanhol");

	private String value;
	private String description;

}
