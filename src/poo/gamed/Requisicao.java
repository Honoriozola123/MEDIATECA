package poo.gamed;

import java.io.Serializable;
import java.time.LocalDate;

public class Requisicao  implements Serializable{
    private static final long serialVersionUID = 6L;

    private Utente utente;
    private Obra obra;
    private LocalDate dataDaRequisicao;
    private LocalDate dataDeDevolucao;
    private boolean activo;

    public Requisicao(Utente utente, Obra obra){
        this.utente = utente;
        this.obra = obra;
        this.dataDaRequisicao = LocalDate.now();
        this.dataDeDevolucao = dataDaRequisicao.plusDays(obra.getPrazoMaximoDevolucao());
        this.activo = false;
    }

    public Utente getUtente() {
        return utente;
    }
    public Obra getObra() {
        return obra;
    }
    public LocalDate getDataDaRequisicao() {
        return dataDaRequisicao;
    }
    public LocalDate getDataDeDevolucao() {
        return dataDeDevolucao;
    }
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Requisicao: " +
                "\nUtente: " + utente.getId() +"/"+utente.getNome()+
                "\nObra:" + obra.getId() +"/"+obra.getTitulo()+
                "\nData da Requisição:" + dataDaRequisicao +
                "\nData de Devolução:" + dataDeDevolucao;
    }
}
