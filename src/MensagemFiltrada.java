import java.util.Date;

public class MensagemFiltrada {
    public String nomeUsuario;
    public String mensagemOriginal;
    public String mensagemFiltrada;
    public Date horarioDeEnvio;

    public MensagemFiltrada(String nomeUsuario, String mensagemOriginal, String mensagemFiltrada, Date horarioDeEnvio) {
        this.nomeUsuario = nomeUsuario;
        this.mensagemOriginal = mensagemOriginal;
        this.mensagemFiltrada = mensagemFiltrada;
        this.horarioDeEnvio = horarioDeEnvio;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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

    public void setHorarioDeEnvio(Date horarioDeEnvio) {
        this.horarioDeEnvio = horarioDeEnvio;
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
