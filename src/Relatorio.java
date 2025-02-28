import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Classe responsável por gerar todos os relatórios requisitados, 
 * cada relatório tem seu respectivo número, assim como na especificação do trabalho.
 * 
 * O cliente pode chamar individualmente cada um dos relatórios, 
 * ou então chamar o método geraRelatorio, cuja função é chamar todos os outros métodos de relatório na sua respectiva ordem
 */

public class Relatorio {
    private HashMap<Integer, Partido> partidos;
    private LinkedList<Candidato> candidatos;
    private LinkedList<Candidato> eleitos_lista = new LinkedList<Candidato>();


    private Comparator<Candidato> comp_candidatos = new ComparaCandidatos();
    private Comparator<Partido> comp_partidos = new ComparaPartidos();
    private Comparator<Partido> comp_partidos_posicao = new ComparaPartidosPosicao();

    /**
     * Inicializa a classe Relatório, com todas as informações 
     * úteis que podem ser necessária para gerar relatório
     * 
     * @param candidatos um HashMap de todos os candidatos a serem utilizados na geração dos relatórios
     * @param partidos um HashMap de todos os partidos a serem utilizados na geração dos relatórios
     */
    public Relatorio(HashMap<Integer, Candidato> candidatos, HashMap<Integer, Partido> partidos) {
        this.partidos = partidos;
        this.candidatos = new LinkedList<Candidato>(candidatos.values());
        Collections.sort(this.candidatos, comp_candidatos);

        for(Candidato c : this.candidatos) if(c.isEleito()) eleitos_lista.add(c);
    }

    /**
     * Gera relatório sobre número de vagas
     */
    public void relatorio1(){

        System.out.println("Número de vagas: " + eleitos_lista.size());

    }

    /**
     * Gera relatório com os vereadores eleitos em ordem decrescente de votos
     */
    public void relatorio2(){

        System.out.println("Vereadores eleitos:");

        int n = 1;
        for(Candidato c : this.candidatos) {
            if(!c.isEleito()) continue;

            c.imprimeCandidato(n++);
        }
    }

    /**
     * Gera relatório com todos os candidatos em ordem decrescente de votos (limitado pelo número de vagas)
     */
    public void relatorio3(){

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int n = 1;
        for(Candidato c : this.candidatos) {
            if(n > eleitos_lista.size()) break;

            c.imprimeCandidato(n++);
        }
    }

    /**
     * Gera relatório com os candidatos que não foram eleitos, mas seriam caso a votação fosse majoritária,
     * em ordem decrescente de votos
     */
    public void relatorio4(){

        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos) {
            n++;
            if(n > eleitos_lista.size()) break;

            if(c.isEleito()) continue;

            c.imprimeCandidato(n);
        }
    }

    /**
     * Gera relatório com os candidatos que foram eleitos, mas não seriam caso a votação fosse majoritária,
     * em ordem decrescente de votos
     */
    public void relatorio5(){

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos) {
            n++;
            if(n <= eleitos_lista.size()) continue;

            if(!c.isEleito()) continue;

            c.imprimeCandidato(n);
        }
    }

    /**
     * Gera relatório com os informações sobre os partidos,
     * como votos totalizados e candidatos eleitos por partido,
     * organizado em ordem decrescente de votos totais do partido
     */
    public void relatorio6(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        List<Partido> list_partidos = new LinkedList<Partido>(this.partidos.values());
        Collections.sort(list_partidos, comp_partidos);

        int n = 1;
        for(Partido p : list_partidos){
            System.out.printf("%d - %s - %d, ", n++, p.getNome(), p.getNumero());

            if(p.getVotos() > 1) System.out.printf("%s votos ", String.format("%,d", p.getVotos()));
            else System.out.printf("%s voto ", String.format("%,d", p.getVotos()));

            if(p.getVotosNominais() > 1) System.out.printf("(%s nominais e ", String.format("%,d", p.getVotosNominais()));
            else System.out.printf("(%s nominal e ", String.format("%,d", p.getVotosNominais()));

            System.out.printf("%s de legenda), ", String.format("%,d", p.getVotosLegenda()));;

            if(p.getQuantidadeEleitos() > 1) System.out.printf("%s candidatos eleitos\n", String.format("%,d", p.getQuantidadeEleitos()));
            else System.out.printf("%s candidato eleito\n", String.format("%,d", p.getQuantidadeEleitos()));

        }
    }

    /**
     * Gera relatório com os candidatos mais e menos votados de cada partido,
     * em ordem decrescente de votos em relação ao mais votado de cada partido
     */
    public void relatorio7(){

        System.out.println("Primeiro e último colocados de cada partido:");


        List<Partido> list_partidos = new LinkedList<Partido>();
        for(Partido p : this.partidos.values()) if(p.getCandidatos().size() != 0) list_partidos.add(p);
        Collections.sort(list_partidos, comp_partidos_posicao);

        int n = 0;
        for(Partido p : list_partidos){
            n++;
            if(p.getVotosNominais() == 0) continue;

            System.out.printf("%d - %s - %d, ", n, p.getNome(), p.getNumero());
            Candidato primeiro = p.getCandidadoPosicao(0);

            int size = p.getCandidatos().size() - 1;
            Candidato ultimo = p.getCandidadoPosicao(size--);

            if(primeiro.getVotos() > 1) System.out.printf("%s (%d, %s votos) / ", primeiro.getNome(), primeiro.getNumero(), String.format("%,d", primeiro.getVotos()));
            else System.out.printf("%s (%d, %s voto) / ", primeiro.getNome(), primeiro.getNumero(), String.format("%,d", primeiro.getVotos()));


            if(ultimo.getVotos() > 1) System.out.printf("%s (%d, %s votos)\n", ultimo.getNome(), ultimo.getNumero(), String.format("%,d", ultimo.getVotos()));
            else System.out.printf("%s (%d, %s voto)\n", ultimo.getNome(), ultimo.getNumero(), String.format("%,d", ultimo.getVotos()));
        }
    }

    /**
     * Gera relatório sobre a distribuição dos vereadores eleitos por faixa etária,
     * considerando a idade no dia da votação
     */
    public void relatorio8(){
        int b30 = 0, b30_40 = 0, b40_50 = 0, b50_60 = 0, b60 = 0;

        int idade = 0;
        for(Candidato c : eleitos_lista){
            idade = c.getIdade();

            if(idade < 30) b30++;
            else if(idade < 40) b30_40++;
            else if(idade < 50) b40_50++;
            else if(idade < 60) b50_60++;
            else b60++;
        }

        float total = eleitos_lista.size();

        if(total == 0) {
            System.out.println("0 eleitos registrados");
            return;
        }

        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        System.out.printf("      Idade < 30: %s (%.2f%%)\n", String.format("%,d", b30), (b30 * 100) / total);
        System.out.printf("30 <= Idade < 40: %s (%.2f%%)\n", String.format("%,d", b30_40), (b30_40 * 100) / total);
        System.out.printf("40 <= Idade < 50: %s (%.2f%%)\n", String.format("%,d", b40_50), (b40_50 * 100) / total);
        System.out.printf("50 <= Idade < 60: %s (%.2f%%)\n", String.format("%,d", b50_60), (b50_60 * 100) / total);
        System.out.printf("60 <= Idade     : %s (%.2f%%)\n", String.format("%,d", b60), (b60 * 100) / total);
    }

    /**
     * Gera relatório csobre a distribuição dos vereadores eleitos por sexo
     */
    public void relatorio9(){
        int m = 0, f = 0;

        for(Candidato c : eleitos_lista){
            if(c.getGenero() == 2) m++;
            else f++;
        }

        float total = eleitos_lista.size();

        if(total == 0) {
            System.out.println("0 eleitos registrados");
            return;
        }

        System.out.println("Eleitos, por gênero:");

        System.out.printf("Feminino: %s (%.2f%%)\n", String.format("%,d", f), (f * 100) / total);
        System.out.printf("Masculino: %s (%.2f%%)\n", String.format("%,d", m), (m * 100) / total);
    }

    /**
     * Gera relatório com o total de votos, votos nominais e votos de legenda
     */
    public void relatorio10(){
        int total = 0, legenda = 0, nominal = 0;

        for(Partido p : this.partidos.values()){
            total += p.getVotos();
            legenda += p.getVotosLegenda();
            nominal += p.getVotosNominais();
        }

        if(total == 0) {
            System.out.println("0 votos registrados");
            return;
        }

        System.out.printf("Total de votos válidos: %s\n", String.format("%,d", total));
        System.out.printf("Total de votos nominais: %s (%.2f%%)\n", String.format("%,d", nominal), (float) (nominal * 100) / total);
        System.out.printf("Total de votos de legenda: %s (%.2f%%)\n", String.format("%,d", legenda), (float) (legenda * 100) / total);
    }

    /**
     * Método responsável por chamar todos os outros relatórios dessa mesma classe
     * em suas respectivas ordens
     */
    public void geraRelatorio() {
        String methodName = "";

        /**
         * Como eu chamo meus métodos em um loop onde o único valor que muda eh o ultimo dígito,
         * que representa qual dos relatórios está sendo chamado, utilizei dos métodos "getMethod"
         * e "invoke", responsáveis for facilitar a chamada dos relatórios.
         * Contudo foi necessário tratar algumas exceções que esses métodos emetiam, como por exemplo:
         * NoSuchMethodException, quando a variável passada por parâmetro do método "getMethod" não representava nenhuma função dessa classe;
         * IllegalAccessException e InvocationTargetException, quando havia um erro ao invocar determinado método.
         */
        for(int i = 1; i <= 10; i++){
            try {
                methodName = "relatorio" + i;
                this.getClass().getMethod(methodName).invoke(this);
            } catch (NoSuchMethodException exc){
                System.out.println("Método não encontrado em geraRelatório: " + methodName);
            } catch (IllegalAccessException | InvocationTargetException exc){
                System.out.println("Erro ao invocar método em geraRelatório:  " + methodName);
                exc.printStackTrace();
            }

            System.out.println("");
        }
    }
}
