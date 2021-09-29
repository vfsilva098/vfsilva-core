package br.com.vfsilvacore.util.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Message implements Serializable {

    private static final long serialVersionUID = 3425074067837552028L;

    private TypeMessage type;
    private String title;
    private String code;
    private Object[] paramsTitle;
    private Object[] paramsDescription;
    private String description;


}
