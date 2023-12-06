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
