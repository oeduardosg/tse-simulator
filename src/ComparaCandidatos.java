/*
 * Primeiro Trabalho de Programação Orientada a Objetos (2024/02)
 * Feito por Eduardo Silva e Gabriel Sena
 * Fevereiro de 2025
 */

import java.util.Comparator;

/**
 * Responsável por comparar Candidatos em relação ao número total de votos
 */
public class ComparaCandidatos implements Comparator<Candidato>{

    public int compare(Candidato c1, Candidato c2) {
        if (c2.getVotos() - c1.getVotos() != 0) return c2.getVotos() - c1.getVotos(); 
        return c2.getIdade() - c1.getIdade();
    }
}

