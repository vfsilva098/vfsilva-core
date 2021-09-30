package br.com.vfsilvacore.util.abstracao;


import br.com.vfsilvacore.dto.Dto;
import br.com.vfsilvacore.exception.ValidationFieldException;
import br.com.vfsilvacore.util.message.ErrorMessage;
import br.com.vfsilvacore.util.message.stack.ErrorStack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Valid;

@Slf4j
@Component
public abstract class ValidatorFieldDefault<DTO> implements Dto {

    @Autowired
    private ErrorStack errorStack;

    protected void validateField(@Valid final DTO model, final String message) {

        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        final BindingResult result = new BeanPropertyBindingResult(model, model.getClass().getName());
        validator.validate(model, result);

        log.debug("Validação padrão dos campos");

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> errorStack.addMessage(error.getDefaultMessage()));

            final ErrorMessage error = ErrorMessage
                    .builder()
                    .title("atencao")
                    .error(message)
                    .details(errorStack.getErrors())
                    .build();

            throw ValidationFieldException.of(error);
        }
    }
}
