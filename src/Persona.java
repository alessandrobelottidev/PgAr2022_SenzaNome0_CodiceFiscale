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
}
