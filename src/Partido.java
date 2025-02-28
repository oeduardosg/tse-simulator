import java.util.*;

public class Partido {
    
    private int numero;
    private String nome;
    private HashMap<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>();
    private int quantidadeEleitos;
    private int votos;
    private int votosLegenda;
    private boolean federacao;
    private Comparator<Candidato> comp_candidatos = new ComparaCandidatos();

    public Partido(int numero, String nome, boolean federacao) {
        this.numero = numero;
        this.nome = nome;
        this.federacao = federacao;
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

    public int getVotosNominais() {
        return votos;
    }

    public int getVotosLegenda() {
        return votosLegenda;
    }

    public int getVotos() {
        return votos + votosLegenda;
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

    public void adicionaVotos(int novosVotos) {
        votos += novosVotos;
    }

    public void adicionaVotosLegenda(int novosVotos) {
        votosLegenda += novosVotos;
    }

    public void aumentaQuantidadeEleitos() {
        quantidadeEleitos++;
    }

    /**
     * Organiza a lista de candidatos do partido de acordo com o número de votos
     * e retorna um Candidadto de uma determinada posição
     * 
     * @param pos posição do candidato a ser retornado
     * @return Candidato da Posicão "pos" da lista ordenada de candidatos
     */
    public Candidato getCandidadoPosicao(int pos){
        if(this.candidatos.size() == 0) return null;
        List<Candidato> p_candidatos = new LinkedList<Candidato>(this.candidatos.values());

        Collections.sort(p_candidatos, this.comp_candidatos);
        return p_candidatos.get(pos);
    }

}
