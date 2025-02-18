
import java.util.Comparator;

public class ComparaVotos implements Comparator<Candidato>{

    public int compare(Candidato c1, Candidato c2) {
        if (c2.getVotos() - c1.getVotos() != 0) return c2.getVotos() - c1.getVotos(); 
        return c2.getIdade() - c1.getIdade();
    }
}

