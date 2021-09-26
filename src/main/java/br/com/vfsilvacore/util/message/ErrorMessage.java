package br.com.vfsilvacore.util.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@ToString
public class ErrorMessage implements Serializable {

    private String title;
    private String error;
    private List<ErrorMessage> details;

}
