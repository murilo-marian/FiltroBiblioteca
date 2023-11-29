import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filtro {
    private static Filtro filtro;
    private File fileBlacklist = null;
    private File fileWhitelist = null;
    private File fileLog = null;
    private TipoDeFiltro tipoDeFiltro;
    private boolean autoLeetspeak;
    private List<String> blacklist;
    private List<String> whitelist;
    private List<MensagemFiltrada> mensagemFiltradas;

    private Filtro(String caminhoBlacklist, TipoDeFiltro tipoDeFiltro, boolean autoLeetspeak) throws IOException {
        this.fileBlacklist = new File(caminhoBlacklist);
        this.tipoDeFiltro = tipoDeFiltro;
        this.autoLeetspeak = autoLeetspeak;

        //T0D0 fazer não explodir o código se o arquivo não existe aqui
        /*this.atualizarBlacklist();*/
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
        if (fileLog != null) {
            try {
                adicionarLog(mensagem);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return mensagem;
    }

    public String gerarLeetSpeak(String palavra) {

        List<String> letras = new ArrayList<>();
        letras = Arrays.asList(palavra.split(""));

        //iterar nas letras e trocar por regex

        List<String> letrasNovas = new ArrayList<>();
        for (String letra : letras) {
            letrasNovas.add(LeetSpeakRegex.getRegex(letra));
        }

        //remontar palavra

        String palavraLeet = String.join( "", letrasNovas);

        return palavraLeet;
    }

    public void adicionarBlackList(String palavra) throws IOException {

        palavra = palavra.replace("\n", "");

        if (autoLeetspeak) {
            palavra = gerarLeetSpeak(palavra);
        }

        if (fileBlacklist.length() > 0) {
            palavra = System.getProperty( "line.separator") + palavra;
        }

        BufferedWriter blackWriter = new BufferedWriter(new FileWriter(fileBlacklist, true));
        blackWriter.write(palavra);
        blackWriter.close();

        atualizarBlacklist();
    }

    public void adicionarWhiteList(String palavra) throws IOException {
        palavra = palavra.replace("\n", "");

        if (fileWhitelist.length() > 0) {
            palavra = System.getProperty( "line.separator") + palavra;
        }

        BufferedWriter whiteWriter = new BufferedWriter(new FileWriter(fileWhitelist, true));
        whiteWriter.write(palavra);
        whiteWriter.close();

        atualizarWhitelist();
    }

    //TODO mudar isso pra json ou algo assim
    //TODO salvar como um objeto MensagemFiltrada
    public void adicionarLog(String mensagem) throws IOException {
        FileWriter logWriter = new FileWriter(fileWhitelist, true);
        logWriter.write(mensagem);
        logWriter.close();
    }

    //TODO puxar o log
    public String getLog() {
        return null;
    }


    public static Filtro getInstance(String caminhoBlacklist, TipoDeFiltro tipoDeFiltro, boolean autoLeetspeak) throws IOException {
        if (filtro == null) {
            filtro = new Filtro(caminhoBlacklist, tipoDeFiltro, autoLeetspeak);
        }
        return filtro;
    }

    public static Filtro getFiltro() {
        return filtro;
    }

    public static void setFiltro(Filtro filtro) {
        Filtro.filtro = filtro;
    }

    public File getFileBlacklist() {
        return fileBlacklist;
    }

    public void setFileBlacklist(File fileBlacklist) {
        this.fileBlacklist = fileBlacklist;
    }

    public File getFileWhitelist() {
        return fileWhitelist;
    }

    public Filtro hasFileWhitelist(String fileWhitelist) {
        this.fileWhitelist = new File(fileWhitelist);
        return this;
    }

    public File getFileLog() {
        return fileLog;
    }

    public Filtro hasCaminhoLog(String caminhoLog) {
        this.fileLog = new File(caminhoLog);
        return this;
    }

    public TipoDeFiltro getTipoDeFiltro() {
        return tipoDeFiltro;
    }

    public void setTipoDeFiltro(TipoDeFiltro tipoDeFiltro) {
        this.tipoDeFiltro = tipoDeFiltro;
    }

    public boolean isAutoLeetspeak() {
        return autoLeetspeak;
    }

    public void setAutoLeetspeak(boolean autoLeetspeak) {
        this.autoLeetspeak = autoLeetspeak;
    }

    public List<String> getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(List<String> blacklist) {
        this.blacklist = blacklist;
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }
}
