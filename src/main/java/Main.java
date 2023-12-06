import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", TipoDeFiltro.Asteriscos, true);
        /*filtro.adicionarBlackList("filho da puta");*/

        //TODO adicionarBlacklist código meio ruim no LineSeparator
        //TODO sistema de whitelist
        //TODO JAVADOC
        //TODO ARTIGO
        //TODO Deixar todos os overload de filtrar iguais

        filtro.atualizarBlacklist();
        filtro.hasFileWhitelist("whitelist.txt").hasCaminhoLog("log.json");

        System.out.println(filtro.filtrar("", "puta do caralho", Calendar.getInstance().getTime()));
    }
}