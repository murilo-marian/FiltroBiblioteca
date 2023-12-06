/**
 * <h2>Tipo de Filtro</h2>
 * @author Murilo Mazzini Marian (murilomarian.mm@gmail.com)
 * @version 1.0
 *
 * Enum utilizado para indicar ao Filtro com que caractére a palavra a ser censurada será trocada
 */
public enum TipoDeFiltro {
    Asteriscos("*"), Underline("_"), Tracos("-"), Deletar("");

    private String character;

    TipoDeFiltro(String character) {
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
