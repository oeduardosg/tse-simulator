
import java.util.Comparator;

/**
 * Responsável por comparar Partidos em relação ao número total de votos
 */
public class ComparaPartidos implements Comparator<Partido>{

    public int compare(Partido p1, Partido p2) {
        if (p2.getVotos() - p1.getVotos() != 0) return p2.getVotos() - p1.getVotos(); 
        return p1.getNumero() - p2.getNumero();
    }
}