import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", "whiteList.txt", TipoDeFiltro.Asteriscos, false);
        /*filtro.adicionarBlackList("caralho");*/

        System.out.println(filtro.filtrar("chupa o meu caralho alado gigante"));
    }
}