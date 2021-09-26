package br.com.vfsilvacore.util.message.stack;


import br.com.vfsilvacore.util.message.Message;
import br.com.vfsilvacore.util.message.TypeMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RequestScope
@Component
public class MessageStack {

    private List<Message> messages;

    public MessageStack() {
        messages = new ArrayList<>();
    }


    public void addMessage(final TypeMessage type, final String title, final String description) {

        final Message message = Message
                .builder()
                .type(type)
                .title(title)
                .description(description)
                .build();

        messages.add(message);
    }

    public void addMessage(final TypeMessage type, final String description) {

        final Message message = Message.builder()
                .description(description)
                .type(type)
                .build();

        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }


    public void clearMessages() {
        messages = null;
        messages = new ArrayList<>();
    }


    public Boolean isWarnningMessage() {
        return isLevelMessage(TypeMessage.warning);
    }


    public Boolean isMessage() {

        return messages.size() > 0;
    }


    private Boolean isLevelMessage(final TypeMessage type) {

        for (Message message : messages) {
            if (type.equals(message.getType())) {
                return true;
            }
        }

        return false;
    }
}
