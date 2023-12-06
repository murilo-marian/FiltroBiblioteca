/**
 * <h2>Enum LeetSpeakRegex - Contém o regéx equivalente a cada letra do alfabeto</h2>
 * @author Murilo Mazzini Marian (murilomarian.mm@gmail.com)
 * @version 1.0
 * <p>Enum que contém o regéx equivalente a cada letra do alfabeto e o espaço, novas letras podem ser adicionadas e o regex pode ser modificado a qualquer momento</p>
 */
public enum LeetSpeakRegex {
    A("[a4@]+"), B("[b8]"), C("[ck]"), D("[d]"), E("[e3&]+"), F("[f]"), G("[g6]"), H("[h#]"), I("[i1!]+"), J("[j]"), K("[kc]"), L("[l172]"), M("[m]"), N("[n]"), O("[o0]+"), P("[p9]"), Q("[q9]"), R("[r]"), S("[s52z]"), T("[t7]"), U("[uv]+"), V("[v]"), W("[w]"), X("[x]"), Y("[y]"), Z("[z2s]"), ESPACO("[-_ \\/]+");
    private String regex;

    LeetSpeakRegex(String regex) {
        this.regex = regex;
    }

    /**
     * <p>Método que retorna o regex equivalente a letra passada como parâmetro</p>
     * @param letra a letra a ser procurada no enum
     * @return Retorna o regex correspondente a letra passada como parâmetro
     */
    public static String getRegex(String letra) {
        if (letra.trim().length() == 0) {
            return LeetSpeakRegex.ESPACO.regex;
        }
        return LeetSpeakRegex.valueOf(letra.toUpperCase()).regex;
    }
}
