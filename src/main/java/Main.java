import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", TipoDeFiltro.Asteriscos, true);
        /*filtro.adicionarBlackList("filho da puta");*/

        //TODO ARTIGO

        filtro.atualizarBlacklist();
        filtro.hasCaminhoLog("log.json");

        System.out.println(filtro.filtrar("", "puta do caralho", Calendar.getInstance().getTime()));
    }
}