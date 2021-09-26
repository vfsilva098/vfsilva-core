package br.com.vfsilvacore.dto;


import br.com.vfsilvacore.util.message.Message;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SimpleResponseDTO implements Serializable {

    private List<Message> messages;

    @ApiModelProperty(hidden = true)
    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(final List<Message> messages) {
        this.messages = messages;
    }
}
