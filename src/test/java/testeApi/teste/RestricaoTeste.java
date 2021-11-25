package testeApi.teste;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.CoreMatchers.containsString;

public class RestricaoTeste extends BaseTeste {

    private static final String RESTRICAO_ENDPOINT = "/restricoes/{cpf}";

    @BeforeClass
    public static void setupRestricaoTest(){
        responseSpecification = new ResponseSpecBuilder()
                .build();
    }

    @Test
    public void testCpfSemRestricao() {
        given().
                    pathParam("cpf", "46103203805").
                when().
                    get(RESTRICAO_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testCpfComRestricao(){
        given().
                    pathParam("cpf", "97093236014").
                when().
                    get(RESTRICAO_ENDPOINT).
                then().
                    statusCode(HttpStatus.SC_OK).
                    body("mensagem", containsString("tem problema"));
    }
}
