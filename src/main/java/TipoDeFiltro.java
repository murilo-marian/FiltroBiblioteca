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
