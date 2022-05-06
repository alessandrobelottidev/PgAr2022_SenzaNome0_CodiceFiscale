import java.time.LocalDate;

public class CodiceFiscale {
    private final StringBuilder cognome, nome;
    LocalDate Nascita;
    boolean isWoman;
    Comune comune;

    public CodiceFiscale(String cognome, String nome, LocalDate Nascita,
                         boolean isWoman, Comune comune) {
        this.cognome = NominativoNormalizer.normalizza(new StringBuilder(cognome.toUpperCase()));
        this.nome = NominativoNormalizer.normalizza(new StringBuilder(nome.toUpperCase()));
        this.Nascita = Nascita;
        this.isWoman = isWoman;
        this.comune = comune;
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
        int n = Nascita.getYear() % 100;
        return (n < 10 ? "0" : "") + n;
    }

    static private final char[] monthCorrispondente = new char[]{'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};

    private char charMonth() {
        return monthCorrispondente[Nascita.getMonthValue() - 1];
    }

    private String charsDay() {
        int n = Nascita.getDayOfMonth() + (isWoman ? 40 : 0);
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
