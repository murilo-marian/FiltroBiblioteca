import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", TipoDeFiltro.Asteriscos, true);
        /*filtro.adicionarBlackList("caralho");*/

        //TODO sistema de log
        //TODO sistema de whitelist
        //TODO JAVADOC
        //TODO ARTIGO
        //TODO ARRUMAR O QUE O CURVELLO FALOU, DE SEPARAR O NORMAL E O LEETSPEAK

        //talvez vou mudar o sistema do leetSpeakRegex, um enum com cada letra do alfabeto separada não me parece muito eficiente
        //Não sei se salvo as mensagens logadas como JSON ou outra coisa, mas JSON parece bom

        filtro.atualizarBlacklist();
        filtro.hasFileWhitelist("whitelist.txt").hasCaminhoLog("log.json");

        System.out.println(filtro.filtrar("", "caralho", Calendar.getInstance().getTime()));
    }
}