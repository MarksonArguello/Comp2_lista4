package model.book;

public class Estilos implements Comparable<Estilos>{
    private String estilo;
    private int quantidade;

    public Estilos(String estilo, int quantidade) {
        this.estilo = estilo;
        this.quantidade = quantidade;
    }

    @Override
    public int compareTo(Estilos other) {
        return other.getQuantidade() - this.getQuantidade();
    }

    public String getEstilo() {
        return estilo;
    }

    public int getQuantidade() {
        return quantidade;
    }
}