package br.com.vfsilvacore.exception;


import br.com.vfsilvacore.util.message.ErrorMessage;
import lombok.ToString;

import java.text.MessageFormat;


@ToString
public class BasicException extends RuntimeException {

    private static final long serialVersionUID = -7198616026085428301L;

    private ErrorMessage error;


    public BasicException(final ErrorMessage error) {
        super(MessageFormat.format("{0}: {1} ({2})", error.getTitle(), error.getError(), error.getDetails()));
        this.error = error;

    }


    public ErrorMessage getErrorMessage() {
        return error;
    }

}
