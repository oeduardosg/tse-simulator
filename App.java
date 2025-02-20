import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        String CIDADE = "VITÓRIA";

        HashMap<Integer, Partido> partidos = new HashMap<Integer, Partido>();
        HashMap<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>();

        Leitor arquivoCandidatos = new Leitor(args[0], args[2]);
        String linha = arquivoCandidatos.readLine();
        linha = arquivoCandidatos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            for(int i = 0; i < 12; i++) s.next();

            int flagVereador = 1, flagCidade = 1;
            if(!s.next().replace("\"", "").equals(CIDADE)) {
                flagCidade = 0;
            }

            if(s.nextInt() != 13) flagVereador = 0;

            int numero;
            String nome;
            String nascimento;
            int genero;
            Candidato candidato;

            int numeroPartido;
            String nomePartido;
            boolean federacao = false;

            for(int i = 0; i < 2; i++) s.next();

            numero = s.nextInt();

            s.next();

            nome = s.next().replace("\"", "");

            for(int i = 0; i < 5; i++) s.next();

            if(s.next().replace("\"", "").equals("FEDERAÇÃO")) federacao = true;

            numeroPartido = s.nextInt();
            nomePartido = s.next().replace("\"", "");

            if(partidos.get(numeroPartido) == null) {
                partidos.put(numeroPartido, new Partido(numeroPartido, nomePartido, federacao));
            }

            if(flagVereador == 0 || flagCidade == 0) {
                linha = arquivoCandidatos.readLine();
                s.close();
                continue;
            }

            for(int i = 0; i < 9; i++) s.next();

            nascimento = s.next().replace("\"", "");
            
            s.next();

            genero = s.nextInt();

            for(int i = 0; i < 10; i++) s.next();

            String eleicao = s.next().replace("\"", "");
            int resultado = 0;
            if(eleicao.equals("ELEITO POR QP")) resultado = 1;
            else if(eleicao.equals("ELEITO POR MÉDIA")) resultado = 2;

            candidato = new Candidato(numero, nome, partidos.get(numeroPartido), nascimento, genero, resultado);
            candidatos.put(candidato.getNumero(), candidato);
            partidos.get(numeroPartido).adicionaCandidato(candidato);

            linha = arquivoCandidatos.readLine();

            s.close();
            
        }

        Leitor arquivoVotos = new Leitor(args[1], args[2]);
        linha = arquivoVotos.readLine();
        linha = arquivoVotos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            for(int i = 0; i < 14; i++) s.next();

            if(!s.next().replace("\"", "").equals(CIDADE)) {
                linha = arquivoVotos.readLine();
                s.close();
                continue;
            }

            for(int i = 0; i < 2; i++) s.next();

            if(s.nextInt() != 13) {
                linha = arquivoVotos.readLine();
                s.close();
                continue;
            }

            int numeroCandidato;
            int votos = 0;

            s.next();

            numeroCandidato = s.nextInt();

            s.next();

            votos = s.nextInt();

            if(numeroCandidato <= 94) {
                partidos.get(numeroCandidato).adicionaVotosLegenda(votos);
                linha = arquivoVotos.readLine();
                s.close();
                continue;
            }

            if(candidatos.get(numeroCandidato) != null) candidatos.get(numeroCandidato).aumentaVotos(votos);

            linha = arquivoVotos.readLine();

            s.close();
            
        }

        Relatorio relatorio = new Relatorio(candidatos, partidos);
        relatorio.geraRelatorio();

    }

}
