import junit.framework.TestCase;

import java.io.IOException;

public class FiltroTest extends TestCase {

    public void testFiltrar() {
        try {
            Filtro filtro = Filtro.getInstance("blacklist.txt", TipoDeFiltro.Asteriscos, true);

            assertEquals("teste", filtro.filtrar("teste"));
            assertEquals("**", filtro.filtrar("cu"));
            assertEquals("**sto", filtro.filtrar("custo"));
            assertEquals("**********", filtro.filtrar("caralhoooo"));
            assertEquals("*******", filtro.filtrar("k4r41#0"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}