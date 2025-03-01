import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class Refinador {
    private HashMap<Integer, Partido> partidos = new HashMap<Integer, Partido>();
    private HashMap<Integer, Candidato> candidatos = new HashMap<Integer, Candidato>();

    /**
     * Inicializa a classe Refinador, com todas as informações 
     * úteis que podem ser necessária para gerar os dados refinados
     * 
     * @param CIDADE inteiro com código do município
     * @param CODIFICACAO tipo de codificação a ser utilizada na leitura do arquivo
     * @param candidatosFile caminho do arquivo de candidatos
     * @param votacaoFile caminho do arquivo de votação
     * @param dataEleicao data da eleição
     * @return objeto com os dados refinados dos arquivos csv
     */
    public Refinador(int CIDADE, String CODIFICACAO, String candidatosFile, String votacaoFile, String dataEleicao) throws IOException, ParseException {

        Leitor arquivoCandidatos = new Leitor(candidatosFile, CODIFICACAO);
        String linha = arquivoCandidatos.readLine();
        linha = arquivoCandidatos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            for(int i = 0; i < 11; i++) s.next();

            int flagVereador = 1, flagCidade = 1;

            // Seta a flagCidade para 0 caso a cidade lida não seja a do código do município informado
            if(Integer.parseInt(s.next().replace("\"", "")) != CIDADE) flagCidade = 0;

            s.next();

            // Seta a flagVereador para 0 caso o candidato lido não esteja concorrendo ao cargo de vereador
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

            // As flags só são verificadas aqui porque precisamos adicionar um partido mesmo caso nenhum vereador tenha concorrido nele,
            // mas algum prefeito sim
            if(flagVereador == 0 || flagCidade == 0) {
                linha = arquivoCandidatos.readLine();
                s.close();
                continue;
            }

            for(int i = 0; i < 9; i++) s.next();

            nascimento = s.next().replace("\"", "");
            
            s.next();

            genero = s.nextInt();

            for(int i = 0; i < 9; i++) s.next();

            int eleito = s.nextInt();

            String eleicao = s.next().replace("\"", "");
            int resultado = 0;
            if(eleicao.equals("ELEITO POR QP")) resultado = 1;
            else if(eleicao.equals("ELEITO POR MÉDIA")) resultado = 2;

            // Vereadores com candidatura inválida não são adicionados à hash de candidatos
            if(eleito > -1){
                candidato = new Candidato(numero, nome, partidos.get(numeroPartido), nascimento, genero, resultado, dataEleicao);
                candidatos.put(candidato.getNumero(), candidato);
                partidos.get(numeroPartido).adicionaCandidato(candidato);
            }

            linha = arquivoCandidatos.readLine();

            s.close();
            
        }

        Leitor arquivoVotos = new Leitor(votacaoFile, CODIFICACAO);
        linha = arquivoVotos.readLine();
        linha = arquivoVotos.readLine();

        while(linha != null) {

            Scanner s = new Scanner(linha).useDelimiter(";");
            for(int i = 0; i < 13; i++) s.next();

            // Se não for um voto na cidade que queremos, simplesmente ignora e passa para a próxima linha
            if(Integer.parseInt(s.next().replace("\"", "")) != CIDADE) {
                linha = arquivoVotos.readLine();
                s.close();
                continue;
            }

            s.next();

            for(int i = 0; i < 2; i++) s.next();

            // Verifica se é voto de vereador
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

            // Filtra votos de legenda e ignora votos brancos ou nulos
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
    }


    public HashMap<Integer, Partido> getPartidos() {
        return new HashMap<Integer, Partido>(this.partidos);
    }

    public HashMap<Integer, Candidato> getCandidatos() {
        return new HashMap<Integer, Candidato>(this.candidatos);
    }
}
