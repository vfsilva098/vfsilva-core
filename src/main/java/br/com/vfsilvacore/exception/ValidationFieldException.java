package br.com.vfsilvacore.exception;


import br.com.vfsilvacore.util.message.ErrorMessage;

public class ValidationFieldException extends BasicException {

    public ValidationFieldException(final ErrorMessage error) {
        super(error);
    }

    public static ValidationFieldException of(final ErrorMessage errorMessage) {
        throw new ValidationFieldException(errorMessage);
    }
}
