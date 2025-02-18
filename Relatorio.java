import java.util.*;

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

    private void relatorio1(){
        int qtd = 0;
        for(Candidato c : candidatos.values()) if(c.isEleito()) qtd++;

        System.out.println("Número de vagas: " + qtd);
        this.vagas = qtd;

        System.out.println("");
    }

    private void relatorio2(){

        System.out.println("Vereadores eleitos:");

        int n = 1;
        for(Candidato c : this.candidatos_lista) {
            if(!c.isEleito()) continue;
            System.out.printf("%d - ", n++);
            //if(c.getFederacao() != -1) System.out.printf("*");
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }

        System.out.println("");
    }

    private void relatorio3(){

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int n = 1;
        for(Candidato c : this.candidatos_lista) {
            if(n > this.vagas) break;
            System.out.printf("%d - ", n++);
            //if(c.getFederacao() != -1) System.out.printf("*");
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }

        System.out.println("");
    }

    private void relatorio4(){

        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos_lista) {
            n++;
            if(n > this.vagas) break;

            if(c.isEleito()) continue;

            System.out.printf("%d - ", n);
            //if(c.getFederacao() != -1) System.out.printf("*");
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }

        System.out.println("");
    }

    private void relatorio5(){

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n" + "(com sua posição no ranking de mais votados)");

        int n = 0;
        for(Candidato c : this.candidatos_lista) {
            n++;
            if(n <= this.vagas) continue;

            if(!c.isEleito()) continue;

            System.out.printf("%d - ", n);
            //if(c.getFederacao() != -1) System.out.printf("*");
            System.out.printf("%s (%s, %d votos)\n", c.getNome(), c.getPartido().getNome(),c.getVotos());
        }

        System.out.println("");
    }

    private void relatorio8(){
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

        System.out.println("");
    }

    private void relatorio9(){
        int m = 0, f = 0;

        for(Candidato c : candidatos_lista){
            if(c.getGenero() == 2) m++;
            else f++;
        }

        float total = candidatos_lista.size();

        System.out.println("Eleitos, por gênero:");

        System.out.printf("Feminino: %d (%.2f%%)\n", f, (f * 100) / total);
        System.out.printf("Masculino: %d (%.2f%%)\n", m, (m * 100) / total);

        System.out.println("");
    }

    public void geraRelatorio(){
        relatorio1();
        relatorio2();
        relatorio3();
        relatorio4();
        relatorio5();
        relatorio8();
        relatorio9();
    }
}
