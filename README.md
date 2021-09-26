# VFSilva Core

### Dependência utilitária para projetos.

# Para que serve?

### Biblioteca responsável por organizar os retorno de erros com a classe abaixo:

- **VfsilvaExceptionHandler:** Classe responsável por genrenciar o retorno com erro das APIs com a estrutura:
    - **Exemplo:**
  
```
{
  "failure": {
    "title": "Atenção",
    "error": "Falha ao salvar usuário.",
    "details": [
      {
        "error": "Nome do usuário obrigatório."
      }
    ]
  }
}
```

- ### Caso queira criar essa estrutura no seu projeto, segue um exemplo abaixo:
```
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class VfsilvaExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorStack errorStack;

    @ExceptionHandler(VfsilvaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(final VfsilvaNotFoundException ex, final HttpServletRequest request, final HttpServletResponse response) {

        log.error(ex.getErrorMessage().toString());
        return new ResponseEntity<>(new DTOError(ex.getErrorMessage()), HttpStatus.NOT_FOUND);
    }
}
```

### Contém também classes para paginação, segue abaixo um exemplo do response e mais abaixo exemplos de implementação:
- ***Response:***
```
{
  "data": [
    {
      "email": "string",
      "id": 0,
      "name": "string",
      "lastname": "ADM"
    }
  ],
  "pagination": {
    "page": 0,
    "pageSize": 0,
    "pageTotal": 0,
    "totalElements": 0
  }
}
```
- ***Implementação:***
### Ex.: UserQueryDTO
```
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.domain.Example.of;
import static org.springframework.data.domain.ExampleMatcher.StringMatcher.CONTAINING;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserQueryDTO extends CollectionRequestDTO<UserModel> {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private TypeUser typeUser;
    private String city;
    private String contact;

    @Override
    public Example<UserModel> generateWhere() {

        final var builder = UserModel.builder();

        builder.active(TRUE);

        if (isBlank(lastName) && isBlank(email) && isNull(id) && isBlank(name) && isBlank(city) && isBlank(contact) && isNull(typeUser)) {
            final var matcher = ExampleMatcher.matching()
                    .withIgnoreCase()
                    .withStringMatcher(CONTAINING);

            return of(builder.build(), matcher);
        }


        if (isNotBlank(name)) {
            builder.name(name);
        }

        if (isNotBlank(lastName)) {
            builder.lastName(lastName);
        }

        if (isNotBlank(contact)) {
            builder.contact(contact);
        }

        if (isNotBlank(city)) {
            builder.address(AddressModel.builder().city(city).build());
        }

        if (nonNull(id)) {
            builder.id(id);
        }

        if (nonNull(typeUser)) {
            builder.typeUser(typeUser);
        }

        final var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(CONTAINING);

        return of(builder.build(), matcher);
    }
}

```
### Ex.: Controller
```
import br.com.peacehealth.util.pagination.CollectionResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Api(value = "[/user] - Api User", tags = {"User"})
public class UserController {

    private final UserApp app;

    @LogInfo
    @GetMapping(params = "apiVersion=1")
    @ApiOperation(value = "Buscar todos", response = CollectionResponseDTO.class, responseContainer = "CollectionResponseDTO")
    public ResponseEntity<CollectionResponseDTO<UserDTO>> selectAll(final UserQueryDTO filter) {
        return ResponseEntity.ok(app.findAll(filter));
    }
}
```
### Ex.: APP
```
    @LogInfo(logParameters = true)
    @Transactional(readOnly = true)
    public CollectionResponseDTO<UserDTO> findAll(final UserQueryDTO request) {

        final var where = new PaginationType<UserModel>();
        where.setPage(request.generatePage(UserDTO.class));
        where.setWhere(request.generateWhere());

        final var converter = new UserConverter(messageStack, request);

        return converter.convertManyCollectionResponse(service.findAll(where));
    }
```
### Ex.: Service
```
    @Override
    public PageModel<UserModel> findAll(final PaginationType<UserModel> paginationType) {
        return repository.findAll(paginationType);
    }
```
### Ex.: Repository
```
    @Override
    public PageModel<UserModel> findAll(PaginationType<UserModel> handler) {
        final Page<UserModel> page;

        if (nonNull(handler) && nonNull(handler.getWhere())) {
            page = repository.findAll(handler.getWhere(), handler.getPage());

        } else {
            page = repository.findAll(handler.getPage());
        }

        return new PageModel<>(page.getContent(), page.getTotalElements(), page.getNumber() + 1, page.getSize(), page.getTotalPages());
    }
```
<a href="https://www.linkedin.com/in/victor-felix-513462110/" target="_blank">
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/linkedin/linkedin-original.svg" height="50"/>
</a>