/*
 * Primeiro Trabalho de Programação Orientada a Objetos (2024/02)
 * Feito por Eduardo Silva e Gabriel Sena
 * Fevereiro de 2025
 */

import java.io.*;

public class Leitor {

    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    /**
     * Cria um leitor
     * 
     * @param diretorio caminho dos arquivos
     * @param codificacao tipo de codificação a ser utilizada na leitura dos arquivos
     */
    public Leitor(String diretorio, String codificacao) throws IOException {

        is = new FileInputStream(diretorio);
        isr = new InputStreamReader(is, codificacao);
        br = new BufferedReader(isr);

    }

    public String readLine() throws IOException {
        return br.readLine();
    }

}