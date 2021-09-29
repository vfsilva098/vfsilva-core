package br.com.vfsilvacore.util.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
@Getter
@Setter
@JsonInclude(value = NON_NULL)
@ToString
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1796396187505712462L;

    private String title;
    private String error;
    private String code;
    private Object[] paramsTitle;
    private Object[] paramsError;
    private List<ErrorMessage> details;
}
