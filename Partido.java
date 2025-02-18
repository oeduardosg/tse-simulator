import java.util.*;

public class Partido {
    
    private int numero;
    private String nome;
    private HashMap<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>();
    private int quantidadeEleitos = 0;
    private int votos;
    private boolean federacao = false;

    public Partido(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

    public HashMap<Integer, Candidato> getCandidatos() {
        return new HashMap<Integer, Candidato>(candidatos);
    }

    public int getQuantidadeEleitos() {
        return quantidadeEleitos;
    }

    public int getVotos() {
        return votos;
    }

    public void setFederacao(boolean v) {
        federacao = v;
    }

    public void adicionaCandidato(Candidato candidato) {
        this.candidatos.put(candidato.getNumero(), candidato);
    }

    public boolean isFederacao() {
        return federacao;
    }

}
