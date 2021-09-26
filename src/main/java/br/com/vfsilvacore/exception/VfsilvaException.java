package br.com.vfsilvacore.exception;

import br.com.vfsilvacore.util.message.ErrorMessage;

public class VfsilvaException extends BasicException {

    public VfsilvaException(ErrorMessage error) {
        super(error);
    }
}
