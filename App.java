import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        HashMap<Integer, Partido> partidos = new HashMap<Integer, Partido>();
        HashMap<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>();

        Leitor arquivoCandidatos = new Leitor(args[0], args[2]);
        String linha = arquivoCandidatos.readLine();
        linha = arquivoCandidatos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            for(int i = 0; i < 12; i++) s.next();

            if(!s.next().replace("\"", "").equals("VITÓRIA")) {
                linha = arquivoCandidatos.readLine();
                continue;
            }

            if(s.nextInt() != 13) {
                linha = arquivoCandidatos.readLine();
                continue;
            }

            int numero;
            String nome;
            int idade;
            int genero;
            Candidato candidato;

            int numeroPartido;
            String nomePartido;
            boolean federacao = false;

            for(int i = 0; i < 2; i++) s.next();

            numero = s.nextInt();
            nome = s.next().replace("\"", "");

            for(int i = 0; i < 6; i++) s.next();

            if(s.next().replace("\"", "").equals("FEDERAÇÃO")) federacao = true;

            numeroPartido = s.nextInt();
            nomePartido = s.next().replace("\"", "");

            if(partidos.get(numeroPartido) == null) {
                partidos.put(numeroPartido, new Partido(numeroPartido, nomePartido, federacao));
            }

            for(int i = 0; i < 9; i++) s.next();

            String[] nascimento = s.next().split("/");
            idade = 2024 - Integer.valueOf(nascimento[2].replace("\"", ""));
            
            s.next();

            genero = s.nextInt();

            for(int i = 0; i < 10; i++) s.next();

            candidato = new Candidato(numero, nome, partidos.get(numeroPartido), idade, genero, (s.next().replace("\"", "").equals("ELEITO POR QP")));
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

            if(!s.next().replace("\"", "").equals("VITÓRIA")) {
                linha = arquivoVotos.readLine();
                continue;
            }

            for(int i = 0; i < 2; i++) s.next();

            if(s.nextInt() != 13) {
                linha = arquivoVotos.readLine();
                continue;
            }

            int numeroCandidato;
            int votos = 0;

            s.next();

            numeroCandidato = s.nextInt();

            s.next();

            votos = s.nextInt();

            if(candidatos.get(numeroCandidato) != null) candidatos.get(numeroCandidato).aumentaVotos(votos);

            linha = arquivoVotos.readLine();

            s.close();
            
        }

        Relatorio relatorio = new Relatorio(candidatos, partidos);
        relatorio.geraRelatorio();

    }

}
