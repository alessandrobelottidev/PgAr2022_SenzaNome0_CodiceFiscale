import java.time.LocalDate;

public class Persona {
    private final int id;
    private final StringBuilder nome;
    private final StringBuilder cognome;
    private final Sesso sesso;
    private final Comune comune;
    private final LocalDate nascita;

    public Persona(int id, String nome, String cognome, Sesso sesso, Comune comune, LocalDate nascita) {
        this.id = id;
        this.nome = NominativoNormalizer.normalizza(new StringBuilder(nome.toUpperCase()));
        this.cognome = NominativoNormalizer.normalizza(new StringBuilder(cognome.toUpperCase()));
        this.sesso = sesso;
        this.comune = comune;
        this.nascita = nascita;
    }

    public String getCodiceFiscale() {
        StringBuilder codiceFiscale = new StringBuilder();
        codiceFiscale.append(charsNominativo(cognome, false)).append(charsNominativo(nome, true));
        codiceFiscale.append(charsYear()).append(charMonth()).append(charsDay()).append(comune.getCodice());
        codiceFiscale.append(lastChar(codiceFiscale));
        return codiceFiscale.toString();
    }

    private StringBuilder charsNominativo(StringBuilder nominativo, boolean isNome) {
        StringBuilder letterine = new StringBuilder();
        for (int i = 0; i < nominativo.length(); i++)
            if (!isVocale(nominativo.charAt(i))) {
                letterine.append(nominativo.charAt(i));

                if ((letterine.length() == 3 && !isNome) || letterine.length() > 3) {
                    if (isNome) letterine.deleteCharAt(1);
                    return letterine;
                }
            }
        if (letterine.length() >= 3) // puo' capitare solo se isNome = true
            return letterine;

        for (int i = 0; i < nominativo.length(); i++)
            if (isVocale(nominativo.charAt(i))) {
                letterine.append(nominativo.charAt(i));
                if (letterine.length() >= 3)
                    return letterine;
            }

        letterine.append("X".repeat(Math.max(0, 3 - letterine.length())));
        return letterine;
    }

    private boolean isVocale(char c) {
        return c == 'A' || c == 'E' || c == 'I' ||
                c == 'O' || c == 'U';
    }

    private String charsYear() {
        int n = nascita.getYear() % 100;
        return (n < 10 ? "0" : "") + n;
    }

    static private final char[] monthCorrispondente = new char[]{'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

    private char charMonth() {
        return monthCorrispondente[nascita.getMonthValue() - 1];
    }

    private String charsDay() {
        int n = nascita.getDayOfMonth() + (sesso.equals(Sesso.F) ? 40 : 0);
        return (n < 10 ? "0" : "") + n;
    }

    static private final int[] conversionePosDispari = new int[]{1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};

    private char lastChar(StringBuilder codiceFiscale) {
        int sum = 0;
        for (int i = 1; i < codiceFiscale.length(); i += 2) {
            if (codiceFiscale.charAt(i) >= '0' && codiceFiscale.charAt(i) <= '9')
                sum += codiceFiscale.charAt(i) - '0';
            else
                sum += codiceFiscale.charAt(i) - 'A';
        }

        for (int i = 0; i < codiceFiscale.length(); i += 2) {
            if (codiceFiscale.charAt(i) >= '0' && codiceFiscale.charAt(i) <= '9')
                sum += conversionePosDispari[codiceFiscale.charAt(i) - '0'];
            else
                sum += conversionePosDispari[codiceFiscale.charAt(i) - 'A'];
        }

        return (char) ('A' + sum % 26);
    }
}
