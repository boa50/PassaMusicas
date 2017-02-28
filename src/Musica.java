public class Musica {
    private Integer codigo;
    private String nome;
    private String letra;
    private String cantor;
    private boolean favorito;

    public Musica(Integer codigo, String nome, String letra, String cantor, boolean favorito) {
        this.codigo = codigo;
        this.nome = nome;
        this.letra = letra;
        this.cantor = cantor;
        this.favorito = favorito;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getCantor() {
        return cantor;
    }

    public void setCantor(String cantor) {
        this.cantor = cantor;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}
