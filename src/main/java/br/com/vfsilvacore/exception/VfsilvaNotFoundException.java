package br.com.vfsilvacore.exception;

import br.com.vfsilvacore.util.message.ErrorMessage;

public class VfsilvaNotFoundException extends BasicException {

    public VfsilvaNotFoundException(ErrorMessage error) {
        super(error);
    }

}
