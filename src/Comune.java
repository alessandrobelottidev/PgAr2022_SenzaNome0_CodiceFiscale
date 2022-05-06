
public class Comune {
    private String nome, codice;

    public Comune(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object c) {
        if (this == c) return true;
        if (!(c instanceof Comune)) return false;

        return codice.equals(((Comune)c).codice);
    }

    @Override
    public int hashCode() {
        return codice != null ? codice.hashCode() : 0;
    }
}
