import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filtro {
    private static Filtro filtro;
    private File fileBlacklist;
    private File fileWhitelist;
    private String caminhoLog;
    private TipoDeFiltro tipoDeFiltro;
    private boolean autoLeetspeak;
    private List<String> blacklist;
    private List<String> whitelist;

    private Filtro(String caminhoBlacklist, String caminhoWhitelist, TipoDeFiltro tipoDeFiltro, boolean autoLeetspeak) throws IOException {
        this.fileBlacklist = new File(caminhoBlacklist);
        this.fileWhitelist = new File(caminhoWhitelist);
        this.tipoDeFiltro = tipoDeFiltro;
        this.autoLeetspeak = autoLeetspeak;
        this.atualizarBlacklist();
        /*this.atualizarWhitelist();*/
    }

    public void atualizarWhitelist() throws IOException {
        BufferedReader whiteReader = new BufferedReader(new FileReader(fileWhitelist));
        List<String> whitelist = new ArrayList<>();

        String line = whiteReader.readLine();

        while (line != null) {
            whitelist.add(line);
            line = whiteReader.readLine();
        }

        this.whitelist = whitelist;

        whiteReader.close();
    }

    public void atualizarBlacklist() throws IOException {

        BufferedReader blackReader = new BufferedReader(new FileReader(fileBlacklist));

        List<String> blacklist = new ArrayList<>();

        String line = blackReader.readLine();

        while (line != null) {
            blacklist.add(line);
            line = blackReader.readLine();
        }

        this.blacklist = blacklist;

        blackReader.close();
    }

    public String filtrar(String mensagem) {
        for (String s : blacklist) {
            Pattern re = Pattern.compile(s,Pattern.CASE_INSENSITIVE);
            Matcher m = re.matcher(mensagem);
            if (m.find()) {
                System.out.println("match");
                Pattern pattern = Pattern.compile("(" + s + ")");
                Matcher groupMatcher = pattern.matcher(mensagem);
                String palavrao = "";
                while (groupMatcher.find()) {
                    palavrao = groupMatcher.group(1);
                    System.out.println("palavrao = " + palavrao);
                }
                mensagem = m.replaceAll(tipoDeFiltro.getCharacter().repeat(palavrao.length()));
            }
        }

        return mensagem;
    }

    public String gerarLeetSpeak(String palavra) {

        List<String> letras = new ArrayList<>();
        letras = Arrays.asList(palavra.split(""));

        //iterar nas letras e trocar por regex

        for (String letra : letras) {
            /*letra = getRegex(letra);*/
        }

        //remontar palavra

        return palavra;
    }

    public void adicionarBlackList(String palavra) throws IOException {

        palavra = palavra.replace("\n", "");

        if (autoLeetspeak) {
            palavra = gerarLeetSpeak(palavra);
        }

        if (fileBlacklist.length() > 0) {
            palavra = System.getProperty( "line.separator") + palavra;
        }

        FileWriter blackWriter = new FileWriter(fileBlacklist, true);
        blackWriter.write(palavra);
        blackWriter.close();

        atualizarBlacklist();
    }

    public void adicionarWhiteList(String palavra) throws IOException {
        palavra = palavra.replace("\n", "");

        if (fileWhitelist.length() > 0) {
            palavra = System.getProperty( "line.separator") + palavra;
        }

        FileWriter whiteWriter = new FileWriter(fileWhitelist, true);
        whiteWriter.write(palavra);
        whiteWriter.close();

        atualizarWhitelist();
    }

    public String getLog() {
        return null;
    }

    public void saveLog() {

    }

    public static Filtro getInstance(String caminhoBlacklist, String caminhoWhitelist, TipoDeFiltro tipoDeFiltro, boolean autoLeetspeak) throws IOException {
        if (filtro == null) {
            filtro = new Filtro(caminhoBlacklist, caminhoWhitelist, tipoDeFiltro, autoLeetspeak);
        }
        return filtro;
    }
}
