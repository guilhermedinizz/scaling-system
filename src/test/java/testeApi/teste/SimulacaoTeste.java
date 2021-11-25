package testeApi.teste;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runners.Suite;
import testeApi.dominio.Proposta;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class SimulacaoTeste extends BaseTeste{

    private static final String SIMULACAO_ENDPOINT = "/simulacoes/";
    private static final String MOSTRAR_SIMULACAO_ENDPOINT = "/simulacoes/{cpf}";


    // Como a API não tem paginação nas consultas estou apenas validando se não é nulo
    @Test
    public void testListarTodasSimulacoes(){
        given().
                when().
                    get(SIMULACAO_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                    body("findAll{ it.email.contains('.com')}.size()", notNullValue());
    }

    @Test
    public void testInsereUmaNovaSimulacao(){
        Proposta proposta = new Proposta("46103203805",
                "Guilherme Diniz",
                "dinizdesousaguilherme@gmail.com",
                1200,
                3,
                true);

        given().
                body(proposta).
                when().
                post(SIMULACAO_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("nome", is("Guilherme Diniz"));

    }

    // Serialização
    @Test
    public void testInsereUmaSimulacaoDuplicada(){
        Proposta proposta = new Proposta("66414919004",
                "Fulano",
                "fulano@gmail.com",
                11000,
                3,
                true);
        given().
                body(proposta).
                when().
                post(SIMULACAO_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("mensagem", containsString("CPF duplicado"));

    }

    // Serialização
    @Test
    public void testInsereUmaSimulacaoSemInformacoes(){
        Proposta proposta = new Proposta("46103203805",
                null,
                "",
                0,
                0,
                true);

        given().
                    body(proposta).
                when().
                    post(SIMULACAO_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_BAD_REQUEST).
                    body("erros.size()", notNullValue());
    }

    // Algumas verificações são apenas para exemplificar a deserialização
    @Test
    public void testRetornaUmaSimulacaoPeloCpf(){
        final Proposta proposta = given().
                    pathParam("cpf", "66414919004").
                when().
                    get(MOSTRAR_SIMULACAO_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                extract().
                    as(Proposta.class);

        assertThat(proposta.getCpf(), is("66414919004"));
        assertThat(proposta.getEmail(), containsString("@gmail.com"));
        assertThat(proposta.getNome(), is("Fulano"));
    }
}
