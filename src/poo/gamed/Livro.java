package poo.gamed;

public class Livro extends Obra{
    private static final long serialVersionUID = 2L;
    private String autores;
    private String isbn;

    //metodo construtor
    public Livro(String titulo, float preco, int totalExemplares, int exemplaresdisponiveis, Categoria categoria, String autores, String isbn) {
        super(titulo, preco, totalExemplares, exemplaresdisponiveis, categoria);
        this.autores = autores.toUpperCase();
        this.isbn = isbn;
    }

    //Getter e Setters
    public String getAutores() {
        return autores;
    }
    public void setAutores(String autores) {
        this.autores = autores;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    //metodo que calcula o prazo de devolucao com base a quantidade de exemplares disponiveis
    public int getPrazoMaximoDevolucao() {
        int dias = 0;
        if(this.exemplaresdisponiveis < 6) dias = 7;
        if(this.exemplaresdisponiveis >= 6) dias = 14;
        if(this.exemplaresdisponiveis >=15) dias = 20;
        return dias;
    }

    @Override
    //apresentar dados do livros
    public String toString() {
        return id + " - Livro - "+ titulo +" - " + categoria.getNome() + " - " + totalExemplares +
                " - " + exemplaresdisponiveis+" - " + preco + " - " + autores +" / "+ isbn+ ".";
    }
}
