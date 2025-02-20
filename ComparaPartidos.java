
import java.util.Comparator;

public class ComparaPartidos implements Comparator<Partido>{

    public int compare(Partido p1, Partido p2) {
        if (p2.getVotos() - p1.getVotos() != 0) return p2.getVotos() - p1.getVotos(); 
        return p2.getNumero() - p1.getNumero();
    }
}