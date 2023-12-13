import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h2>Filtro - Utilizada para realizar as ações relacionada a filtragem e gerenciamento de blacklist e whitelist</h2>
 * @author Murilo Mazzini Marian (murilomarian.mm@gmail.com)
 * @version 1.0
 *
 * Classe principal do programa, utilizada para realizar todas a ação principal de filtragem e gerenciamento de blacklist e whitelist
 */
public class Filtro {
    private static Filtro filtro;
    private File fileBlacklist;/*
    private File fileWhitelist = null;*/
    private final File fileBlacklistLeet;
    private File fileLog = null;
    private TipoDeFiltro tipoDeFiltro;
    private boolean autoLeetspeak;
    private List<String> blacklist;
    private List<String> whitelist;

    private Filtro(String caminhoBlacklist, TipoDeFiltro tipoDeFiltro, boolean autoLeetspeak) throws IOException {
        if (!caminhoBlacklist.endsWith(".txt")) {
            throw new FileNotFoundException();
        }
        this.fileBlacklist = new File(caminhoBlacklist);
        int posicaoExtensao = caminhoBlacklist.indexOf(".");
        this.fileBlacklistLeet = new File(caminhoBlacklist.substring(0, posicaoExtensao) + "LeetSpeak" + caminhoBlacklist.substring(posicaoExtensao));
        this.tipoDeFiltro = tipoDeFiltro;
        this.autoLeetspeak = autoLeetspeak;

        if (!fileBlacklist.exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileBlacklist));
            bw.write("");
            bw.close();
        }
        if (!fileBlacklistLeet.exists()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileBlacklistLeet));
            bw.write("");
            bw.close();
        }

        this.atualizarBlacklist();
    }


    /*public void atualizarWhitelist() throws IOException {
        BufferedReader whiteReader = new BufferedReader(new FileReader(fileWhitelist));
        List<String> whitelist = new ArrayList<>();

        String line = whiteReader.readLine();

        while (line != null) {
            whitelist.add(line);
            line = whiteReader.readLine();
        }

        this.whitelist = whitelist;

        whiteReader.close();
    }*/

    /**
     * Método que atualiza o ArrayList contendo as palavras da whitelist, puxando do arquivo de whitelist.txt
     *
     * @throws IOException
     */
    public void atualizarBlacklist() throws IOException {
        BufferedReader blackReader;
        if (autoLeetspeak) {
            blackReader = new BufferedReader(new FileReader(fileBlacklistLeet));
        } else {
            blackReader = new BufferedReader(new FileReader(fileBlacklist));
        }

        List<String> blacklist = new ArrayList<>();

        String line = blackReader.readLine();

        while (line != null) {
            blacklist.add(line);
            line = blackReader.readLine();
        }

        this.blacklist = blacklist;

        blackReader.close();
    }

    /**
     * Método principal do programa, modifica a String passada como parâmetro caso uma palavra que está na blacklist for detectada pelo regex,
     * trocando a palavra detectada pelo caracter especificado no contrutor da classe
     *
     * @param mensagem String - mensagem a ser escaneada e eventualmente filtrada caso for detectada uma palavra proibida
     * @return String - Retorna a mensagem após ela passar pelo processo de filtro
     */
    public String filtrar(String mensagem) {
        String mensagemCensurada = mensagem;
        boolean matchFound = false;
        for (String s : blacklist) {
            Pattern re = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher m = re.matcher(mensagem);
            if (m.find()) {
                Pattern pattern = Pattern.compile("(" + s + ")");
                Matcher groupMatcher = pattern.matcher(mensagem);
                String palavrao = "";
                while (groupMatcher.find()) {
                    palavrao = groupMatcher.group(1);
                }
                /*if (fileWhitelist != null && inWhitelist()) {
                    return mensagem;
                }*/
                mensagemCensurada = m.replaceAll(tipoDeFiltro.getCharacter().repeat(palavrao.length()));
                matchFound = true;
            }
        }
        if (fileLog != null && matchFound) {
            try {
                MensagemFiltrada mensagemFiltrada = new MensagemFiltrada(mensagem, mensagemCensurada);
                mensagemFiltrada.adicionarLog(fileLog);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return mensagemCensurada;
    }

    /**
     * Método principal do programa, modifica a String passada como parâmetro caso uma palavra que está na blacklist for detectada pelo regex,
     * trocando a palavra detectada pelo caracter especificado no contrutor da classe
     * @param usuario String - nome de usuário a ser atrelado a mensagem durante o processo de log de mensagem filtrada
     * @param mensagem String - mensagem a ser escaneada e eventualmente filtrada caso for detectada uma palavra proibida
     * @param dataEnvio Date - Data de envio a ser atrelada a mensagem durante o processo de log de mensagem filtrada
     * @return String - Retorna a mensagem após ela passar pelo processo de filtro
     */
    public String filtrar(String usuario, String mensagem, Date dataEnvio) {
        String mensagemCensurada = mensagem;
        boolean matchFound = false;
        for (String s : blacklist) {
            Pattern re = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher m = re.matcher(mensagem);
            if (m.find()) {
                Pattern pattern = Pattern.compile("(" + s + ")");
                Matcher groupMatcher = pattern.matcher(mensagem);
                String palavrao = "";
                while (groupMatcher.find()) {
                    palavrao = groupMatcher.group(1);
                }
                /*if (fileWhitelist != null && inWhitelist()) {
                    return mensagem;
                }*/
                mensagemCensurada = m.replaceAll(tipoDeFiltro.getCharacter().repeat(palavrao.length()));
                matchFound = true;
            }
        }
        if (fileLog != null && matchFound) {
            try {
                MensagemFiltrada mensagemFiltrada = new MensagemFiltrada(mensagem, mensagemCensurada).hasNomeUsuario(usuario).hasHorarioDeEnvio(dataEnvio);
                mensagemFiltrada.adicionarLog(fileLog);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return mensagemCensurada;
    }

    /**
     * Método principal do programa, modifica a String passada como parâmetro caso uma palavra que está na blacklist for detectada pelo regex,
     * trocando a palavra detectada pelo caracter especificado no contrutor da classe
     * @param mensagem String - mensagem a ser escaneada e eventualmente filtrada caso for detectada uma palavra proibida
     * @param dataEnvio Date - Data de envio a ser atrelada a mensagem durante o processo de log de mensagem filtrada
     * @return String - Retorna a mensagem após ela passar pelo processo de filtro
     */
    public String filtrar(String mensagem, Date dataEnvio) {
        String mensagemCensurada = mensagem;
        boolean matchFound = false;
        for (String s : blacklist) {
            Pattern re = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher m = re.matcher(mensagem);
            if (m.find()) {
                Pattern pattern = Pattern.compile("(" + s + ")");
                Matcher groupMatcher = pattern.matcher(mensagem);
                String palavrao = "";
                while (groupMatcher.find()) {
                    palavrao = groupMatcher.group(1);
                }
                /*if (fileWhitelist != null && inWhitelist()) {
                    return mensagem;
                }*/
                mensagemCensurada = m.replaceAll(tipoDeFiltro.getCharacter().repeat(palavrao.length()));
                matchFound = true;
            }
        }
        if (fileLog != null && matchFound) {
            try {
                MensagemFiltrada mensagemFiltrada = new MensagemFiltrada(mensagem, mensagemCensurada).hasHorarioDeEnvio(dataEnvio);
                mensagemFiltrada.adicionarLog(fileLog);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return mensagemCensurada;
    }


    /**
     * Método principal do programa, modifica a String passada como parâmetro caso uma palavra que está na blacklist for detectada pelo regex,
     * trocando a palavra detectada pelo caracter especificado no contrutor da classe
     * @param usuario String - nome de usuário a ser atrelado a mensagem durante o processo de log de mensagem filtrada
     * @param mensagem String - mensagem a ser escaneada e eventualmente filtrada caso for detectada uma palavra proibida
     * @return String - Retorna a mensagem após ela passar pelo processo de filtro
     */
    public String filtrar(String usuario, String mensagem) {
        String mensagemCensurada = mensagem;
        boolean matchFound = false;
        for (String s : blacklist) {
            Pattern re = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            Matcher m = re.matcher(mensagem);
            if (m.find()) {
                Pattern pattern = Pattern.compile("(" + s + ")");
                Matcher groupMatcher = pattern.matcher(mensagem);
                String palavrao = "";
                while (groupMatcher.find()) {
                    palavrao = groupMatcher.group(1);
                }
                /*if (fileWhitelist != null && inWhitelist()) {
                    return mensagem;
                }*/
                mensagemCensurada = m.replaceAll(tipoDeFiltro.getCharacter().repeat(palavrao.length()));
                matchFound = true;
            }
        }
        if (fileLog != null && matchFound) {
            try {
                MensagemFiltrada mensagemFiltrada = new MensagemFiltrada(mensagem, mensagemCensurada).hasNomeUsuario(usuario);
                mensagemFiltrada.adicionarLog(fileLog);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return mensagemCensurada;
    }


    /*private boolean inWhitelist() {
        for (String s : whitelist) {

        }
        return false;
    }*/

    /**
     * Transforma a palavra numa versão composta por regex dela mesma, sendo o regex atribuido pela lista no enum LeetSpeakRegex
     *
     * @param palavra String - palavra a ser transformada
     * @return String Retorna a palavra modificada
     */
    private String gerarLeetSpeak(String palavra) {

        List<String> letras;
        letras = Arrays.asList(palavra.split(""));

        //iterar nas letras e trocar por regex

        List<String> letrasNovas = new ArrayList<>();
        for (String letra : letras) {
            letrasNovas.add(LeetSpeakRegex.getRegex(letra));
        }

        //remontar palavra

        return String.join("", letrasNovas);
    }

    /**
     * Adiciona a palavra ou frase proibida no arquivo blacklist.txt, criando uma versão composta por regex e adicionando-a na blackListLeetSpeak.txt
     * @param palavra String - palavra a ser adicionada e modificada
     * @throws IOException
     */
    public void adicionarBlackList(String palavra) throws IOException {

        palavra = palavra.replace("\n", "");

        BufferedWriter blackWriter = new BufferedWriter(new FileWriter(fileBlacklist, true));
        BufferedWriter blackWriterLeet = new BufferedWriter(new FileWriter(fileBlacklistLeet, true));

        String palavraNormal = palavra;

        if (fileBlacklist.length() > 0) {
            palavraNormal = System.getProperty("line.separator") + palavra;
        }

        blackWriter.write(palavraNormal);
        blackWriter.close();

        palavra = gerarLeetSpeak(palavra);
        if (fileBlacklist.length() > 0) {
            palavra = System.getProperty("line.separator") + palavra;
        }
        blackWriterLeet.write(palavra);
        blackWriterLeet.close();

        atualizarBlacklist();
    }

    /*public void adicionarWhiteList(String palavra) throws IOException {
        palavra = palavra.replace("\n", "");

        if (fileWhitelist.length() > 0) {
            palavra = System.getProperty("line.separator") + palavra;
        }

        BufferedWriter whiteWriter = new BufferedWriter(new FileWriter(fileWhitelist, true));
        whiteWriter.write(palavra);
        whiteWriter.close();

        atualizarWhitelist();
    }*/

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

    /*public File getFileWhitelist() {
        return fileWhitelist;
    }

    public Filtro hasFileWhitelist(String fileWhitelist) {
        this.fileWhitelist = new File(fileWhitelist);
        if (!this.fileWhitelist.exists()) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileWhitelist));
                bw.write("");
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.atualizarWhitelist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }*/

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
