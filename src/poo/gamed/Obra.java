package poo.gamed;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Obra implements Serializable {
    private static final long serialVersionUID = 1L; //ver

    protected static int  contador = 1;
    protected static int anoActual = LocalDate.now().getYear();//retorna o ano actual do sistema
    protected final String id;
    protected String titulo;
    protected float preco;
    protected int totalExemplares;
    protected int exemplaresdisponiveis;
    protected Categoria categoria;

    public Obra(String titulo, float preco, int totalExemplares, int exemplaresdisponiveis, Categoria categoria) {
        this.id = String.format("%04d/%d",contador++, anoActual); //incrementa uma unidade a cada instancia
        this.titulo = titulo.toUpperCase();
        this.preco = preco;
        this.totalExemplares = totalExemplares;
        this.exemplaresdisponiveis = exemplaresdisponiveis;
        this.categoria = categoria;
    }//getters e setters
    public String getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public float getPreco() {
        return preco;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }
    public int getTotalExemplares() {
        return totalExemplares;
    }
    public void setTotalExemplares(int totalExemplares) {
        this.totalExemplares = totalExemplares;
    }
    public int getExemplaresdisponiveis() {
        return exemplaresdisponiveis;
    }
    public void setExemplaresdisponiveis(int exemplaresdisponiveis) {
        this.exemplaresdisponiveis = exemplaresdisponiveis;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public static void actualizarContador(int maiorContador){
        contador = maiorContador +1;
    }
    public boolean requisitarObra(){
        if (this.isDisponivel()){
            exemplaresdisponiveis--;
            return true;
        }
        return false;
    }
    public  void devolverExemplar(){
        if(exemplaresdisponiveis < totalExemplares){
            exemplaresdisponiveis++;
        }
    }

    //metodo que verica se ainda há exemplares disponiveis da obra para requisição
    public boolean isDisponivel(){
        String referencia = "REFERÊNCIA";
        if (this.exemplaresdisponiveis > 0 && !this.categoria.equals(referencia) ) return true;
        else return false;
    }
    public abstract int getPrazoMaximoDevolucao();
    @Override
    public String toString() {
        return "Obra{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", preco=" + preco +
                ", totalExemplares=" + totalExemplares +
                ", exemplaresdisponiveis=" + exemplaresdisponiveis +
                ", categoria=" + categoria.getNome() +
                '}';
    }
}