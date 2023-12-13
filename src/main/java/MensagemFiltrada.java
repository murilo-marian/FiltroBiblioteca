import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Date;

/**
 * <h2>MensagemFiltrada - Classe que contém uma mensagem original e sua versão filtrada, opcionalmente, uma data de envio e um nome de usuário</h2>
 * @author Murilo Mazzini Marian (murilomarian.mm@gmail.com)
 * @version 1.0
 *
 * <p>Classe utilizada apenas para a função de log do sistema, salvando a mensagem original e a mensagem filtrada, e, de forma opcional, um nome de usuario e a data de envio</p>
 */
public class MensagemFiltrada {
    private String nomeUsuario = null;
    private String mensagemOriginal;
    private String mensagemFiltrada;
    private Date horarioDeEnvio = null;

    public MensagemFiltrada(String mensagemOriginal, String mensagemFiltrada) {
        this.mensagemOriginal = mensagemOriginal;
        this.mensagemFiltrada = mensagemFiltrada;
    }

    /**
     * Adiciona um objeto MensagemFiltrada ao log.json, salvando a mensagem filtrada, a mensagem original, e, de forma opcional, um nome de usuário e uma data
     * @param fileLog File - contém o arquivo onde a mensagem em questão será salva
     * @throws IOException
     */
    public void adicionarLog(File fileLog) throws IOException {

        JSONArray logJSON = new JSONArray();
        if (fileLog.exists() && fileLog.length() != 0) {
            JSONParser parser = new JSONParser();
            try {
                logJSON = (JSONArray) parser.parse(new FileReader(fileLog));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileLog));

        if (!fileLog.exists()) {
            bw.write("");
        }
        JSONObject mensagemJSON = new JSONObject();
        if (this.getNomeUsuario() != null) {
            mensagemJSON.put("usuario", this.getNomeUsuario());
        }
        mensagemJSON.put("mensagem", this.getMensagemOriginal());
        mensagemJSON.put("mensagem filtrada", this.getMensagemFiltrada());
        if (this.getHorarioDeEnvio() != null) {
            mensagemJSON.put("Data de envio", this.getHorarioDeEnvio().toString());
        }

        logJSON.add(mensagemJSON);

        bw.write(logJSON.toJSONString());
        bw.close();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public MensagemFiltrada hasNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        return this;
    }

    public String getMensagemOriginal() {
        return mensagemOriginal;
    }

    public void setMensagemOriginal(String mensagemOriginal) {
        this.mensagemOriginal = mensagemOriginal;
    }

    public String getMensagemFiltrada() {
        return mensagemFiltrada;
    }

    public void setMensagemFiltrada(String mensagemFiltrada) {
        this.mensagemFiltrada = mensagemFiltrada;
    }

    public Date getHorarioDeEnvio() {
        return horarioDeEnvio;
    }

    public MensagemFiltrada hasHorarioDeEnvio(Date horarioDeEnvio) {
        this.horarioDeEnvio = horarioDeEnvio;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MensagemFiltrada{");
        sb.append("nomeUsuario='").append(nomeUsuario).append('\'');
        sb.append(", mensagemOriginal='").append(mensagemOriginal).append('\'');
        sb.append(", mensagemFiltrada='").append(mensagemFiltrada).append('\'');
        sb.append(", horarioDeEnvio=").append(horarioDeEnvio);
        sb.append('}');
        return sb.toString();
    }
}
