# VFSilva Core

### Dependência utilitária para projetos.

# O que tem ?

### Contém sistema de tradução de mensagens de response para ***Inglês (en_US)***, ***Portugês (pt_BR)***, ***Espanhol (es_ES)***

- Para traduzir as mensagens de response, basta passar no header: ***Accept-Language: en_US | pt_BR | es_ES***
- O parâmetro que estiver no ***Accept-Language*** será o utilizado, caso não estiver nenhum será utilizado o 
***pt_BR***

## Annotation
***@LogInfo:*** pode ser colocada em cima dos métodos que é gerado um log no console com o nome da classe e o nome do método que foi chamado, bom para conseguir trackear por onde passou a requisição. *Implementação do logParameters em desenvolvimento.

## Como configurar a tradução no meu projeto?`

- Basta criar uma classe de filter como exemplo abaixo com o método ***getLocale*** responsável por identificar a linguagem desejada:

```
public class SecurityFilter extends GenericFilterBean {

    @Autowired
    @Qualifier(value = "translatorFilter")
    private IErrorTranslator translator;

    @Autowired
    private DomainFilter domainFilter;

    @Autowired
    private ErrorStack errorStack;

    @Autowired
    @Qualifier(value = "i18nFilterProvider")
    private II18nProvider provider;


    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain)
            throws IOException, ServletException {

        final var request = (HttpServletRequest) req;
        final var response = (HttpServletResponse) resp;

        final var status = domainFilter.verifyToken(errorStack, request);

        if (OK.equals(status)) {
            chain.doFilter(request, response);
        } else if (TIMEOUT.equals(status)) {

            response.setStatus(419);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            final var error = getErrorMessage(errorStack);
            translator.translateErrorMessage(error, getLocale(request));

            response.getWriter().append(new Gson().toJson(error));
            response.getWriter().flush();
            response.getWriter().close();

        } else {

            response.setStatus(401);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            final var error = getErrorMessage(errorStack);
            translator.translateErrorMessage(error, getLocale(request));

            response.getWriter().append(new Gson().toJson(error));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }


    /**
     * Método interno responsável em criar a entidade de Erro das mensagens de validação.
     *
     * @param error Erros.
     * @return
     */
    private ErrorMessage getErrorMessage(final ErrorStack error) {
        return ErrorMessage
                .builder()
                .title("atencao")
                .error("falha_autenticacao")
                .details(error.getErrors())
                .build();
    }

    private Locale getLocale(final HttpServletRequest request) {

        final var language = request.getHeader("Accept-Language");

        if (nonNull(language) && !"".equals(language)) {

            final var languageLocal = language.split("_");
            if (languageLocal.length == 2) {
                return provider.validateLocale(new Locale(languageLocal[0], languageLocal[1]));
            }
        }

        if (nonNull(request.getLocale()) && !"".equals(request.getLocale().toString())) {
            return provider.validateLocale(request.getLocale());
        }

        return provider.validateLocale(new Locale("pt", "BR"));
    }
}

```

### Biblioteca também responsável por organizar os retorno de erros com a classe abaixo:

- **VfsilvaExceptionHandler:** Classe responsável por genrenciar o retorno com erro das APIs com a estrutura:
    - **Exemplo:**
      ![Alt text](src/main/resources/img/exemplo_erro.png?raw=true "Title")

- ### Caso queira criar essa estrutura no seu projeto com a tradução, segue um exemplo abaixo:

```
/**
 * Classe default Handler Exception das aplicações web.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class VfsilvaExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorStack errorStack;
    private final IErrorTranslator translator;

    @ExceptionHandler(VfsilvaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleNotFoundException(final VfsilvaNotFoundException ex, final HttpServletRequest request, final HttpServletResponse response) {

        log.error(ex.getErrorMessage().toString());
        translator.translateErrorMessage(ex.getErrorMessage());
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

## ***Implementação:***

### Ex.: UserQueryDTO

- Colocando o extends ***CollectionRequestDTO*** passando o model / entidade pelo generics.
- Implementando o método ***generateWhere***, que é o método responsável por montar a query de consulta.

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

- Colocando no retorno do controller / resource o ***CollectionResponseDTO*** passando no generics o ***DTO*** de
  response.
- Esperando no parametro do metodo de controller / resource o ***DTO*** criado acima

```
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

- Criando a variável ***where*** que será responsável pela criação da query.
- Utilizando o ***setPage*** passando o ***generatePage (que está dentro do CollectionRequestDTO)*** o método pede como
  parâmetro a classe do ***DTO*** de retorno.

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

- Verifica se tem algum filtro dentro do where montado no ***DTO***, caso tenha busca pelos filtros passados, caso não
  tenha busca por todos os registros.

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
