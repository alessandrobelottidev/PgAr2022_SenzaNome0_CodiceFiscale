package SenzaNome0.com;

import java.time.LocalDate;

public class Persona {
    private int id;
    private StringBuilder nome;
    private StringBuilder cognome;
    private Sesso sesso;
    private String comune;
    private LocalDate nascita;
    private String codiceFiscale;


    public Persona(int id, String nome, String cognome, Sesso sesso, String comune, LocalDate nascita) throws ComuneNotFoundException {
        this.id = id;
        this.nome = NominativoNormalizer.normalizza(new StringBuilder(nome.toUpperCase()));
        this.cognome = NominativoNormalizer.normalizza(new StringBuilder(cognome.toUpperCase()));
        this.sesso = sesso;
        this.comune = comune;
        this.nascita = nascita;
        this.codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNome(StringBuilder nome) throws ComuneNotFoundException {
        this.nome = nome;
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public void setCognome(StringBuilder cognome) throws ComuneNotFoundException {
        this.cognome = cognome;
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public void setSesso(Sesso sesso) throws ComuneNotFoundException {
        this.sesso = sesso;
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public void setComune(String comune) throws ComuneNotFoundException {
        this.comune = comune;
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public void setNascita(LocalDate nascita) throws ComuneNotFoundException {
        this.nascita = nascita;
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public int getId() {
        return id;
    }

    public StringBuilder getNome() {
        return nome;
    }

    public StringBuilder getCognome() {
        return cognome;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public String getComune() {
        return comune;
    }

    public LocalDate getNascita() {
        return nascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
}