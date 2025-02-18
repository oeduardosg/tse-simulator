import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws IOException{

        HashMap<Integer, Partido> partidos = new HashMap<Integer, Partido>(); 

        Leitor arquivoCandidatos = new Leitor(args[0], args[1]);
        String linha = arquivoCandidatos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            // s.useLocale(Locale.ENGLISH);
            for(int i = 0; i < 13; i++) s.next();

            if(!(s.nextInt() == 13)) continue;

            int numero;
            String nome;

            int numeroPartido;
            String nomePartido;
            boolean federacao = false;
            Partido partido;
            
            int idade;
            int genero;
            int votos;

            for(int i = 0; i < 2; i++) s.next();

            numero = s.nextInt();
            nome = s.next();

            for(int i = 0; i < 6; i++) s.next();

            if(s.next() == "FEDERAÇÃO") federacao = true;
            numeroPartido = s.nextInt();
            nomePartido = s.next();

            if(partidos.get(numeroPartido) == null) {
                partidos.put(numeroPartido, new Partido(numeroPartido, nomePartido));
            }

            // Falta colocar idade e gênero

            partidos.get(numeroPartido).adicionaCandidato(new Candidato(numero, nome, partidos.get(numeroPartido), idade, genero));

            linha = arquivoCandidatos.readLine();

            s.close();
            
        }

    }

}
