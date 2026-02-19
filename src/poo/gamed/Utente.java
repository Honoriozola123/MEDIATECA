package poo.gamed;
//gcc -std=c99 -o sgde main.c candidatos.c caps.c eleitores.c filas.c pilhas.c votacao.c arvore_resultados.c apuracao.c persistencia.c entrada.c -lm
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class  Utente implements Serializable {
    private static final long serialVersionUID = 4L;
    private static int proximoId = 1; // analizar
    private final int id; // perque ?
    private String nome;
    private String email;
    private EstadoUtente estado;
    private List<Requisicao> obrasRequisitadas;
    private int numRequisicoes = 0;


    public Utente(String nome, String email) {
        this.id = proximoId++;
        this.nome = nome.toUpperCase();
        this.email = email;
        this.estado = EstadoUtente.ACTIVO;
        this.obrasRequisitadas = new ArrayList<>();
    }
    //getters e setters
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public EstadoUtente getEstado() {
        return estado;
    }
    public List<Requisicao> getObrasRequisitadas() {
        return new ArrayList<>(obrasRequisitadas);
    }
    public void setEstado(EstadoUtente estado) {
        this.estado = estado;
    }

    //outros metodos
    public static void actualizarContador(int maiorId){
        proximoId = maiorId + 1;
    }
    public boolean podeRequisitar(int numeroMaximoRequisicoes) {
     
        if (estado != EstadoUtente.ACTIVO) {
            return false;
        }
     
        if (obrasRequisitadas.size() >= numeroMaximoRequisicoes) {
            return false;
        }
        
        return true;
    }
    public boolean temObraRequisitada(int obraId) {
        return obrasRequisitadas.contains(obraId);
    }
    public void adicionarRequisicao(Requisicao novaRequisicao) {
        if (!obrasRequisitadas.contains(novaRequisicao)) {
            obrasRequisitadas.add(novaRequisicao);
            this.setNumRequisicoes(getNumRequisicoes()+1);
        }
    }
    public void removerRequisicao(int obraId) {
        obrasRequisitadas.remove(Integer.valueOf(obraId));
    }
    public int getNumeroRequisicoesAtivas() {
        return obrasRequisitadas.size();
    }
    public boolean jaRequisitouObra(int obraId) {
        return obrasRequisitadas.contains(obraId);
    }
    public int getNumRequisicoes() {
        return numRequisicoes;
    }
    public void setNumRequisicoes(int numRequisicoes) {
        this.numRequisicoes = numRequisicoes;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + email + " - " + estado;
    }
}