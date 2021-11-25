package testeApi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proposta {

    private String cpf;
    private String nome;
    private String email;
    private long valor;
    private long parcelas;
    private boolean seguro;

}
