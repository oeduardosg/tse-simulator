public class Candidato {

    private int numero;
    private String nome;
    private Partido partido;
    private String nascimento;
    private int eleito;
    private int genero;
    private int votos;
    private String dataEleicao;

    public Candidato(int numero, String nome, Partido partido, String nascimento, int genero, int eleito, String dataEleicao) {
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.nascimento = nascimento;
        this.genero = genero;
        this.dataEleicao = dataEleicao;
        this.eleito = eleito;
        if(eleito > 0) partido.aumentaQuantidadeEleitos();
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


    public String getNascimento() {
        return nascimento;
    }


    public boolean isEleito() {
        return eleito > 0;
    }


    public int getGenero() {
        return genero;
    }


    public int getVotos() {
        return votos;
    }

    public int getIdade() {
        String[] datas = getNascimento().split("/");
        String[] datasEleicao = this.dataEleicao.split("/");

        int idade = Integer.valueOf(datasEleicao[2]) - Integer.valueOf(datas[2]);

        if(Integer.valueOf(datas[1]) < Integer.valueOf(datasEleicao[1])) return idade;
        else if(Integer.valueOf(datas[1]) > Integer.valueOf(datasEleicao[1])) return idade - 1;
        else {
            if(Integer.valueOf(datas[0]) <= Integer.valueOf(datasEleicao[0])) return idade;
            else return idade - 1;
        }

    }

    public int getMaisVelho(Candidato x, Candidato y) {
        String[] nascimentoX = x.getNascimento().split("/");
        String[] nascimentoY = x.getNascimento().split("/");

        if(Integer.valueOf(nascimentoX[2]) < Integer.valueOf(nascimentoY[2])) return 1;
        else if(Integer.valueOf(nascimentoX[2]) > Integer.valueOf(nascimentoY[2])) return -1;
        else {
            if(Integer.valueOf(nascimentoX[1]) < Integer.valueOf(nascimentoY[1])) return 1;
            else if(Integer.valueOf(nascimentoX[1]) > Integer.valueOf(nascimentoY[1])) return -1;
            else {
                if(Integer.valueOf(nascimentoX[0]) < Integer.valueOf(nascimentoY[0])) return 1;
                else if(Integer.valueOf(nascimentoX[0]) > Integer.valueOf(nascimentoY[0])) return -1;
                else return 0;
            }           
        }
    }

    public void aumentaVotos(int novosVotos) {
        partido.adicionaVotos(novosVotos);
        votos += novosVotos;
    }

    /**
     * Imprime candidado de acordo com o padrão de saída da especificação do trabalho
     * 
     * @param n posição que deve ser impressa junto com o candidato
     */
    public void imprimeCandidato(int n) {
        System.out.printf("%d - ", n);

        if(this.getPartido().isFederacao()) System.out.printf("*");
        System.out.printf("%s (%s, %s votos)\n", this.getNome(), this.getPartido().getNome(), String.format("%,d", this.getVotos()));
    }

    @Override
    public String toString() {
        return "Candidato: " + getNome() + "\n";
    }

}
