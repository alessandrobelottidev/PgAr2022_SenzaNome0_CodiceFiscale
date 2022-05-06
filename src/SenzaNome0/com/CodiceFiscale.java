package SenzaNome0.com;

import java.time.LocalDate;

public class CodiceFiscale {
    public static String genera(StringBuilder nome, StringBuilder cognome, Sesso sesso, String comune, LocalDate nascita) throws ComuneNotFoundException {
        StringBuilder codiceFiscale = new StringBuilder();
        codiceFiscale.append(charsNominativo(cognome, false)).append(charsNominativo(nome, true));
        codiceFiscale.append(charsYear(nascita)).append(charMonth(nascita)).append(charsDay(nascita, sesso)).append(Comuni.getCodiceComune(comune));
        codiceFiscale.append(lastChar(codiceFiscale));
        return codiceFiscale.toString();
    }
    private static StringBuilder charsNominativo(StringBuilder nominativo, boolean isNome) { //passare true se si vuole calcolare il nome, false se il cognome
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

    private static boolean isVocale(char c) {
        return c == 'A' || c == 'E' || c == 'I' ||
                c == 'O' || c == 'U';
    }

    private static String charsYear(LocalDate nascita) {
        int n = nascita.getYear() % 100;
        return (n < 10 ? "0" : "") + n;

    }

    static private final char[] monthCorrispondente = new char[]{'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T'};
    static private final int[] daysCorrispondente = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

    private static char charMonth(LocalDate nascita) {
        return monthCorrispondente[nascita.getMonthValue() - 1];
    }

    private static String charsDay(LocalDate nascita, Sesso sesso) {
        int n = nascita.getDayOfMonth() + (sesso.equals(Sesso.F) ? 40 : 0);
        return (n < 10 ? "0" : "") + n;
    }

    static private final int[] conversionePosDispari = new int[]{1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};

    private static char lastChar(StringBuilder codiceFiscale) {
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

    public static boolean isValido(String codiceFiscale){
        if (codiceFiscale.length()!=16)
            return false;
        //controlla che il codice fiscale non abbia all'interno caratteri diversi da lettere o numeri
        for (int i = 0; i < codiceFiscale.length(); i++) {
            boolean letterOrDigit = Character.isLetterOrDigit(codiceFiscale.charAt(i));
            if(!(letterOrDigit))
                return false;
        }

        //divide il codice fiscale per analizzare ogni parte singola
        String codCognome = codiceFiscale.substring(0, 3);
        String codNome = codiceFiscale.substring(3,6);
        String codAnno = codiceFiscale.substring(6,8);
        char codMese = codiceFiscale.charAt(8);
        String codGiorno = codiceFiscale.substring(9, 11);
        char codLetteraComune = codiceFiscale.charAt(11);
        String codNumeriComune = codiceFiscale.substring(12,15);
        char codControllo = codiceFiscale.charAt(15);
        boolean trovato=false;
        //controllo che la lettera del mese corrisponda a
        for (int i = 0; i < 12; i++) {
            if (codMese==monthCorrispondente[i]){
                trovato=true;
                break;
            }


        }
        if(!(trovato))
            return false;
        //controlla che il giorno sia scritto in numeri
        for (int i = 0; i < 2; i++) {
            if (!('0'<= codGiorno.charAt(i) && codGiorno.charAt(i)<= '9'))
                return false;
        }
        //controlla le lettere del codice del cognome
        for (int i = 0; i < 3; i++) {
            if (!('A'<= codNome.charAt(i) && codNome.charAt(i)<= 'Z'))
                return false;
        }
        //controlla le lettere del codice del cognome
        for (int i = 0; i < 3; i++) {
            if (!('A'<= codCognome.charAt(i) && codCognome.charAt(i)<= 'Z'))
                return false;
        }

        int giorno= Integer.parseInt(codGiorno);
        //controlla i numeri del codice del giorno
        if (!(giorno >=1 && giorno<=31||giorno>=41 && giorno<=71))
            return false;
        //controlla i numeri del codice dell'anno
        for (int i = 0; i < 2; i++) {
            if (!('0'<= codAnno.charAt(i) && codAnno.charAt(i)<= '9'))
                return false;
        }
        //controlla la prima lettera del codice del comune
        if (!('A'<= codLetteraComune && codLetteraComune<= 'Z'))
            return false;
        //controlla i numeri del codice del comune
        for (int i = 0; i < 3; i++) {
            if (!('0'<= codNumeriComune.charAt(i) && codNumeriComune.charAt(i)<= '9'))
                return false;
        }
        //controlla la lettera di controllo
        if (!(codControllo==lastChar(new StringBuilder(codiceFiscale.substring(0, 15)))))
            return false;

        //controlla che il numero del giorno sia presente nei giorni del mese di nascita
        // (es: se il giorno è 31 ed il mese è febbraio ritorna false)
        for (int i = 0; i < 12; i++) {
            if (codMese==monthCorrispondente[i]){
                if(!(giorno%40<=daysCorrispondente[i]))
                    return false;
            }
        }

        return true;

    }
}