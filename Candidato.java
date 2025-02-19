public class Candidato {

    private int numero;
    private String nome;
    private Partido partido;
    private int idade;
    private boolean eleito;
    private int genero;
    private int votos;

    public Candidato(int numero, String nome, Partido partido, int idade, int genero, boolean eleito) {
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.idade = idade;
        this.genero = genero;
        this.eleito = eleito;
        if(eleito) partido.aumentaQuantidadeEleitos();
    }

    public int getNumero() {
        return numero;
    }


    public String getNome() {
        return nome;
    }


    public Partido getPartido() {
        return partido;
    }


    public int getIdade() {
        return idade;
    }


    public boolean isEleito() {
        return eleito;
    }


    public int getGenero() {
        return genero;
    }


    public int getVotos() {
        return votos;
    }

    public void aumentaVotos(int novosVotos) {
        partido.adicionaVotos(novosVotos);
        votos += novosVotos;
    }

    public void eleito() {
        this.eleito = true;
    }

    @Override
    public String toString() {
        return "Candidato: " + getNome() + "\n";
    }

}
