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

    public void setNome(String nome) throws ComuneNotFoundException {
        this.nome = new StringBuilder(nome);
        codiceFiscale=CodiceFiscale.genera(this.nome, this.cognome,this.sesso,this.comune,this.nascita);
    }

    public void setCognome(String cognome) throws ComuneNotFoundException {
        this.cognome = new StringBuilder(cognome);
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

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome.toString();
    }

    public String getCognome() {
        return cognome.toString();
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