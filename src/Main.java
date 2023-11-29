import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", TipoDeFiltro.Asteriscos, true);
        /*filtro.adicionarBlackList("leet");*/

        //TODO sistema de log
        //TODO sistema de whitelist

        filtro.atualizarBlacklist();
        filtro.hasFileWhitelist("whitelist.txt").hasCaminhoLog("log.txt");

        System.out.println(filtro.filtrar("1e33ee7"));
    }
}