import java.time.LocalDate;

public record Persona(int id, String nome, String cognome, Sesso sesso, Comune comune, LocalDate nascita) {

}
