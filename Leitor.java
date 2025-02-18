import java.io.*;

public class Leitor {

    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    public Leitor(String diretorio, String codificacao) throws IOException {

        is = new FileInputStream(diretorio);
        isr = new InputStreamReader(is, codificacao);
        br = new BufferedReader(isr);

    }

    public String readLine() throws IOException {
        return br.readLine();
    }

}