import java.util.*;
import java.lang.reflect.*;

public class Relatorio {
    private HashMap<Integer, Candidato> candidatos;
    private HashMap<Integer, Partido> partidos;
    private Comparator<Candidato> comp = new ComparaVotos();
    private int vagas = 0;
    private LinkedList<Candidato> candidatos_lista;

    public Relatorio(HashMap<Integer, Candidato> candidatos, HashMap<Integer, Partido> partidos) {
        this.candidatos = candidatos;
        this.partidos = partidos;

        candidatos_lista = new LinkedList<Candidato>(candidatos.values());
        Collections.sort(candidatos_lista, comp);
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
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }
    }

    public void relatorio3(){

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int n = 1;
        for(Candidato c : this.candidatos_lista) {
            if(n > this.vagas) break;
            System.out.printf("%d - ", n++);
            if(c.getPartido().isFederacao()) System.out.printf("*");
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
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
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
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
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }
    }

    public void relatorio6(){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        int n = 1;
        for(Partido p : this.partidos.values()){
            System.out.printf("%d - %s, ", n++, p.getNome());

            if(p.getVotos() > 1) System.out.print(p.getVotos() + " votos (");
            else System.out.print(p.getVotos() + " voto (");

            /*if(p.getVotosNominais() > 1) System.out.print(p.getVotosNominais() + " nominais e ");
            else System.out.print(p.getVotosNominais() + " nominal e ");

            System.out.print(p.getVotosLegenda() + " de legenda),  ");*/

            System.out.println(p.getQuantidadeEleitos() + "candidatos eleitos");

        }
    }

    public void relatorio7(){

        System.out.println("Primeiro e último colocados de cada partido:");

        int n = 0;
        for(Partido p : this.partidos.values()){
            n++;
            //if(p.getVotos() <= 0) continue;

            System.out.printf("%d - %s, ", n, p.getNome());
            List<Candidato> p_candidatos = new LinkedList<Candidato>(p.getCandidatos().values());

            Collections.sort(p_candidatos, this.comp);
            Candidato primeiro = p_candidatos.get(0);
            Candidato ultimo = p_candidatos.get(p_candidatos.size() - 1);

            System.out.printf("%s (%d, %d votos) / ", primeiro.getNome(), primeiro.getNumero(), primeiro.getVotos());
            System.out.printf("%s (%d, %d votos)\n", ultimo.getNome(), ultimo.getNumero(), ultimo.getVotos());
        }
    }

    public void relatorio8(){
        int b30 = 0, b30_40 = 0, b40_50 = 0, b50_60 = 0, b60 = 0;

        int idade = 0;
        for(Candidato c : candidatos_lista){
            idade = c.getIdade();

            if(idade < 30) b30++;
            else if(idade < 40) b30_40++;
            else if(idade < 50) b40_50++;
            else if(idade < 60) b50_60++;
            else b60++;
        }

        float total = candidatos_lista.size();

        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        System.out.printf("      Idade < 30: %d (%.2f%%)\n", b30, (b30 * 100) / total);
        System.out.printf("30 <= Idade < 40: %d (%.2f%%)\n", b30_40, (b30_40 * 100) / total);
        System.out.printf("40 <= Idade < 50: %d (%.2f%%)\n", b40_50, (b40_50 * 100) / total);
        System.out.printf("50 <= Idade < 60: %d (%.2f%%)\n", b50_60, (b50_60 * 100) / total);
        System.out.printf("60 <= Idade     : %d (%.2f%%)\n", b60, (b60 * 100) / total);
    }

    public void relatorio9(){
        int m = 0, f = 0;

        for(Candidato c : candidatos_lista){
            if(c.getGenero() == 2) m++;
            else f++;
        }

        float total = candidatos_lista.size();

        System.out.println("Eleitos, por gênero:");

        System.out.printf("Feminino: %d (%.2f%%)\n", f, (f * 100) / total);
        System.out.printf("Masculino: %d (%.2f%%)\n", m, (m * 100) / total);
    }

    public void relatorio10(){
        int total = 0, legenda = 0, nominal = 0;

        for(Partido p : this.partidos.values()){
            total += p.getVotos();
            /*legenda += p.getVotosLegenda();
            nominal += p.getVotosNominais();*/
        }

        System.out.printf("Total de votos válidos: %d\n", total);
        System.out.printf("Total de votos nominais: %d (%.2f)\n", total, (float) (nominal * 100) / total);
        System.out.printf("Total de votos de legenda: %d (%.2f)\n", total, (float) (legenda * 100) / total);
    }

    public void geraRelatorio() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        for(int i = 1; i <= 10; i++){
            Method method = this.getClass().getMethod("relatorio" + i);
            method.invoke(this);
            System.out.println("");
        }
    }
}
