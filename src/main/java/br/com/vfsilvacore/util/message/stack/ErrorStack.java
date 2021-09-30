package br.com.vfsilvacore.util.message.stack;

import br.com.vfsilvacore.util.message.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Component
@RequestScope
public class ErrorStack {

    private List<ErrorMessage> errors;

    public ErrorStack() {
        errors = new ArrayList<>();
    }

    public void addMessage(final String title, final String error) {

        final ErrorMessage handler = ErrorMessage.builder().title(title).error(error).build();

        errors.add(handler);
    }

    public void addMessage(final String error) {

        final ErrorMessage handler = ErrorMessage.builder().error(error).build();

        errors.add(handler);
    }

    public void addMessage(final String error, final Object[] params) {

        final ErrorMessage handler = ErrorMessage.builder().error(error).paramsError(params).build();

        errors.add(handler);
    }

    public List<ErrorMessage> getErrors() {
        if (Objects.isNull(errors)) {
            return null;
        }
        return Collections.unmodifiableList(errors);
    }


    public Boolean isError() {

        return errors.size() > 0;
    }
}
