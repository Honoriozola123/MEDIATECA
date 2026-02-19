package poo.gamed;
public class DVD extends Obra{
    private static final long serialVersionUID = 3L;
    private String realizador;
    private String registoDNDAC;
    public DVD(String titulo, float preco, int totalExemplares, int exemplaresdisponiveis, Categoria categoria, String realizador, String registoDNDAC) {
        super(titulo, preco, totalExemplares, exemplaresdisponiveis, categoria);
        this.realizador = realizador.toUpperCase();
        this.registoDNDAC = registoDNDAC;
    }
    //getters e setters
    public String getRealizador() {
        return realizador;
    }
    public void setRealizador(String realizador) {
        this.realizador = realizador;
    }
    public String getRegistoDNDAC() {
        return registoDNDAC;
    }
    public void setRegistoDNDAC(String registoDNDAC) {
        this.registoDNDAC = registoDNDAC;
    }
    @Override
    public int getPrazoMaximoDevolucao() {
        int dias = 0;
        if (this.exemplaresdisponiveis < 6) dias = 3;
        if(this.exemplaresdisponiveis >= 6) dias = 7;
        if(this.exemplaresdisponiveis >=15) dias = 14;
        return dias;
    }
    @Override
    public String toString() {
        return  id + " - DVD - "+ titulo +" - " + categoria.getNome() + " - " + totalExemplares +
                " - " + exemplaresdisponiveis+" - " + preco + " - " + realizador +" / "+ registoDNDAC+ ".";
    }
}
