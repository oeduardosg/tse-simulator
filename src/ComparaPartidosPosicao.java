/*
 * Primeiro Trabalho de Programação Orientada a Objetos (2024/02)
 * Feito por Eduardo Silva e Gabriel Sena
 * Fevereiro de 2025
 */

import java.util.Comparator;

/**
 * Responsável por comparar Partidos em relação ao candidato mais votado de cada um dos partidos
 */
public class ComparaPartidosPosicao implements Comparator<Partido>{

    private Comparator<Candidato> comp_candidatos = new ComparaCandidatos();

    public int compare(Partido p1, Partido p2) {
        Candidato c1 = p1.getCandidadoPosicao(0);
        Candidato c2 = p2.getCandidadoPosicao(0);

        return comp_candidatos.compare(c1, c2);
    }
}