public class NominativoNormalizer {
    private static final String[] illegaliDaEliminare = new String[]{" '", "ÀÁÂÃÄÅ", "ÈÉÊËÆŒ", "ÌÍÎÏ", "ÒÓÔÖÕ", "ÙÚÛÜ", "ÝŸ", "Ñ", "Ç"};
    private static final String[] illegaliDaSostituire = new String[]{"", "A", "E", "I", "O", "U", "Y", "N", "C"};

    public static StringBuilder normalizza(StringBuilder nominativo) {
        boolean trovato;
        for (int i = 0; i < nominativo.length(); i++) {
            trovato = false;
            for (int j = 0; j < illegaliDaEliminare.length && !trovato; j++)
                for (int k = 0; k < illegaliDaEliminare[j].length(); k++)
                    if (nominativo.charAt(i) == illegaliDaEliminare[j].charAt(k)) {
                        nominativo.replace(i, i + 1, illegaliDaSostituire[j]);
                        if (j == 0) i--;
                        trovato = true;
                        break;
                    }
        }
        return nominativo;
    }
}
