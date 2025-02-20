import java.util.*;
import java.lang.reflect.*;

public class Relatorio {
    private HashMap<Integer, Candidato> candidatos;
    private HashMap<Integer, Partido> partidos;
    private Comparator<Candidato> comp_candidatos = new ComparaCandidatos();
    private Comparator<Partido> comp_partidos = new ComparaPartidos();
    private Comparator<Partido> comp_partidos_posicao = new ComparaPartidosPosicao();
    private int vagas = 0;
    private LinkedList<Candidato> candidatos_lista;
    private LinkedList<Candidato> eleitos_lista = new LinkedList<Candidato>();

    public Relatorio(HashMap<Integer, Candidato> candidatos, HashMap<Integer, Partido> partidos) {
        this.candidatos = candidatos;
        this.partidos = partidos;

        candidatos_lista = new LinkedList<Candidato>(candidatos.values());
        Collections.sort(candidatos_lista, comp_candidatos);

        for(Candidato c : candidatos.values()) if(c.isEleito()) eleitos_lista.add(c);
    }

    public void relatorio1(){
        int qtd = 0;
        for(Candidato c : candidatos.values()) if(c.isEleito()) qtd++;

        System.out.println("Número de vagas: " + qtd);
        this.vagas = qtd;
    }

    public void relatorio2(){

        System.out.println("Vereadores eleitos:");

        int n = 1;
        for(Candidato c : this.candidatos_lista) {
            if(!c.isEleito()) continue;
            System.out.printf("%d - ", n++);
            if(c.getPartido().isFederacao()) System.out.printf("*");
            System.out.printf("%s (%s, %s votos)\n", c.getNome(), c.getPartido().getNome(), String.format("%,d", c.getVotos()));
        }
    }

    public void relatorio3(){

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int n = 1;
        for(Candidato c : this.candidatos_lista) {
            if(n > this.vagas) break;
            System.out.printf("%d - ", n++);
            if(c.getPartido().isFederacao()) System.out.printf("*");
            System.out.printf("%s (%s, %s votos)\n", c.getNome(), c.getPartido().getNome(), String.format("%,d", c.getVotos()));
        }
    }

    public void relatorio4(){

        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos_lista) {
            n++;
            if(n > this.vagas) break;

            if(c.isEleito()) continue;

            System.out.printf("%d - ", n);
            if(c.getPartido().isFederacao()) System.out.printf("*");
            System.out.printf("%s (%s, %s votos)\n", c.getNome(), c.getPartido().getNome(), String.format("%,d", c.getVotos()));
        }
    }

    public void relatorio5(){

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos_lista) {
            n++;
            if(n <= this.vagas) continue;

            if(!c.isEleito()) continue;

            System.out.printf("%d - ", n);
            if(c.getPartido().isFederacao()) System.out.printf("*");
            System.out.printf("%s (%s, %s votos)\n", c.getNome(), c.getPartido().getNome(), String.format("%,d", c.getVotos()));
        }
    }

    public void relatorio6(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        List<Partido> list_partidos = new LinkedList<Partido>(this.partidos.values());
        Collections.sort(list_partidos, comp_partidos);

        int n = 1;
        for(Partido p : list_partidos){
            System.out.printf("%d - %s - %d, ", n++, p.getNome(), p.getNumero());

            if(p.getVotos() > 1) System.out.printf("%s votos (", String.format("%,d", p.getVotos()));
            else System.out.printf("%s voto (", String.format("%,d", p.getVotos()));

            if(p.getVotosNominais() > 1) System.out.printf("%s nominais e ", String.format("%,d", p.getVotosNominais()));
            else System.out.printf("%s nominal e ", String.format("%,d", p.getVotosNominais()));

            System.out.printf("%s de legenda) ", String.format("%,d", p.getVotosLegenda()));;

            if(p.getQuantidadeEleitos() > 1) System.out.printf("%s candidatos eleitos\n", String.format("%,d", p.getQuantidadeEleitos()));
            else System.out.printf("%s candidato eleito\n", String.format("%,d", p.getQuantidadeEleitos()));

        }
    }

    public void relatorio7(){

        System.out.println("Primeiro e último colocados de cada partido:");


        List<Partido> list_partidos = new LinkedList<Partido>();
        for(Partido p : this.partidos.values()) if(p.getCandidatos().size() != 0) list_partidos.add(p);
        Collections.sort(list_partidos, comp_partidos_posicao);

        int n = 0;
        for(Partido p : list_partidos){
            n++;
            if(p.getCandidatos().size() == 0) continue;

            System.out.printf("%d - %s - %d, ", n, p.getNome(), p.getNumero());
            Candidato primeiro = p.getCandidadoPosicao(0);

            int size = p.getCandidatos().size() - 1;
            Candidato ultimo = p.getCandidadoPosicao(size--);
            while(ultimo.getVotos() == 0) ultimo = p.getCandidadoPosicao(size--);

            System.out.printf("%s (%d, %s votos) / ", primeiro.getNome(), primeiro.getNumero(), String.format("%,d", primeiro.getVotos()));
            System.out.printf("%s (%d, %s votos)\n", ultimo.getNome(), ultimo.getNumero(), String.format("%,d", ultimo.getVotos()));
        }
    }

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

        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        System.out.printf("      Idade < 30: %s (%.2f%%)\n", String.format("%,d", b30), (b30 * 100) / total);
        System.out.printf("30 <= Idade < 40: %s (%.2f%%)\n", String.format("%,d", b30_40), (b30_40 * 100) / total);
        System.out.printf("40 <= Idade < 50: %s (%.2f%%)\n", String.format("%,d", b40_50), (b40_50 * 100) / total);
        System.out.printf("50 <= Idade < 60: %s (%.2f%%)\n", String.format("%,d", b50_60), (b50_60 * 100) / total);
        System.out.printf("60 <= Idade     : %s (%.2f%%)\n", String.format("%,d", b60), (b60 * 100) / total);
    }

    public void relatorio9(){
        int m = 0, f = 0;

        for(Candidato c : eleitos_lista){
            if(c.getGenero() == 2) m++;
            else f++;
        }

        float total = eleitos_lista.size();

        System.out.println("Eleitos, por gênero:");

        System.out.printf("Feminino: %s (%.2f%%)\n", String.format("%,d", f), (f * 100) / total);
        System.out.printf("Masculino: %s (%.2f%%)\n", String.format("%,d", m), (m * 100) / total);
    }

    public void relatorio10(){
        int total = 0, legenda = 0, nominal = 0;

        for(Partido p : this.partidos.values()){
            total += p.getVotos();
            legenda += p.getVotosLegenda();
            nominal += p.getVotosNominais();
        }

        System.out.printf("Total de votos válidos: %s\n", String.format("%,d", total));
        System.out.printf("Total de votos nominais: %s (%.2f%%)\n", String.format("%,d", nominal), (float) (nominal * 100) / total);
        System.out.printf("Total de votos de legenda: %s (%.2f%%)\n", String.format("%,d", legenda), (float) (legenda * 100) / total);
    }

    public void geraRelatorio() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        for(int i = 1; i <= 10; i++){
            Method method = this.getClass().getMethod("relatorio" + i);
            method.invoke(this);
            System.out.println("");
        }
    }
}
