public enum LeetSpeakRegex {
    A("[a4@]+"), B("[b8]"), C("[ck]"), D("[d]"), E("[e3&]+"), F("[f]"), G("[g6]"), H("[h#]"), I("[i1!]+"), J("[j]"), K("[kc]"), L("[l172]"), M("[m]"), N("[n]"), O("[o0]+"), P("[p9]"), Q("[q9]"), R("[r]"), S("[s52z]"), T("[t7]"), U("[uv]+"), V("[v]"), W("[w]"), X("[x]"), Y("[y]"), Z("[z2s]");
    private String regex;

    LeetSpeakRegex(String regex) {
        this.regex = regex;
    }

    public static String getRegex(String letra) {
        return LeetSpeakRegex.valueOf(letra.toUpperCase()).regex;
    }
}
