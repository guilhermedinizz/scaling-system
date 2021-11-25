package testeApi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testeApi.teste.RestricaoTeste;
import testeApi.teste.SimulacaoTeste;

@RunWith(Suite.class)
@Suite.SuiteClasses({RestricaoTeste.class, SimulacaoTeste.class})
public class Runner {
}
