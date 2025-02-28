import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class App {
    
    public static void main(String[] args) {

        try {
            int CIDADE = Integer.parseInt(args[0]);
            String CODIFICACAO = "Windows-1252";
    
            Refinador refinador = new Refinador(CIDADE, CODIFICACAO, args[1], args[2], args[3]);
    
            Relatorio relatorio = new Relatorio(refinador.getCandidatos(), refinador.getPartidos());
            relatorio.geraRelatorio();
        } catch(FileNotFoundException exc){
            System.out.println("Arquivo não encontrado: " + exc.getMessage().split(" ")[0]);
        } catch (IOException exc) {
            System.out.println("Erro de I/O");
            exc.printStackTrace();
        } catch (NumberFormatException | ParseException exc) {
            System.out.println("Código do Município deve ser um inteiro, evite usar aspas e caracteres");
        }

    }

}
