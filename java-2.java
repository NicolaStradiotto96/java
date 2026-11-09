import java.util.Objects;

// ---------------------------------------------------------------
// Prove
// ---------------------------------------------------------------
public class Main {
    public static void main(String[] args) {
        Auto a1 = new Auto("Fiat", "Panda", 2020, 5);
        Auto a2 = new Auto("Fiat", "Panda", 2020, 5);
        Auto a3 = new Auto("Fiat", "Panda", 2020, 3);
        Moto m1 = new Moto("Honda", "CB500", 2021, 500);
        Moto m2 = new Moto("Honda", "CB500", 2021, 500);
        Veicolo v = new Veicolo("Fiat", "Panda", 2020);

        System.out.println("--- Oggetti creati ---");
        System.out.println(a1);
        System.out.println(m1);
        System.out.println(v);

        System.out.println("\n--- Getter e setter ---");
        System.out.println("Marca di a1: " + a1.getMarca());
        a1.setModello("500");
        System.out.println("Modello di a1 dopo il setter: " + a1.getModello());
        // a1.marca = "Ford";   // ERRORE di compilazione: marca e' private
        try {
            a1.setAnno(1500);
        } catch (IllegalArgumentException e) {
            System.out.println("Setter con valore non valido: " + e.getMessage());
        }
        a1.setModello("Panda");

        System.out.println("\n--- Modificatori ---");
        a1.percorri(120);                       // usa il campo protected dentro Auto
        System.out.println("Km di a1 (protected, via getter): " + a1.getKmPercorsi());
        System.out.println("Package-private: " + a1.descrizioneInterna());

        System.out.println("\n--- == contro equals ---");
        System.out.println("a1 == a2:        " + (a1 == a2));
        System.out.println("a1.equals(a2):   " + a1.equals(a2));
        System.out.println("a1.equals(a3):   " + a1.equals(a3) + " (porte diverse)");
        System.out.println("m1.equals(m2):   " + m1.equals(m2));
        System.out.println("a1.equals(v):    " + a1.equals(v) + " (classi diverse)");
        System.out.println("v.equals(a1):    " + v.equals(a1) + " (simmetrico)");
        System.out.println("a1.equals(null): " + a1.equals(null));
        System.out.println("a1.equals(a1):   " + a1.equals(a1));

        System.out.println("\n--- hashCode ---");
        System.out.println("a1 e a2 stesso hashCode: " + (a1.hashCode() == a2.hashCode()));
    }
}

// ---------------------------------------------------------------
// Classe base
// ---------------------------------------------------------------
class Veicolo {

    // private: visibile solo dentro questa classe -> si accede con getter/setter
    private String marca;
    private String modello;
    private int anno;

    // protected: visibile in questa classe, nelle sottoclassi e nello stesso package
    protected int kmPercorsi;

    // public: visibile ovunque
    public Veicolo(String marca, String modello, int anno) {
        setMarca(marca);
        setModello(modello);
        setAnno(anno);
        this.kmPercorsi = 0;
    }

    // Getter: restituiscono il valore di un campo private
    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public int getAnno() {
        return anno;
    }

    public int getKmPercorsi() {
        return kmPercorsi;
    }

    // Setter: modificano il campo dopo aver validato il valore
    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("La marca non puo' essere vuota");
        }
        this.marca = marca;
    }

    public void setModello(String modello) {
        if (modello == null || modello.isBlank()) {
            throw new IllegalArgumentException("Il modello non puo' essere vuoto");
        }
        this.modello = modello;
    }

    public void setAnno(int anno) {
        if (anno < 1886) {
            throw new IllegalArgumentException("Anno non valido: " + anno);
        }
        this.anno = anno;
    }

    // Nessun modificatore (package-private): visibile solo alle classi dello stesso package
    String descrizioneInterna() {
        return marca + " " + modello + " (" + anno + ")";
    }

    // equals: due veicoli sono uguali se hanno stessa classe, marca, modello e anno.
    // kmPercorsi non conta: e' lo stato d'uso, non l'identita' del veicolo.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Veicolo altro = (Veicolo) o;
        return anno == altro.anno
                && marca.equals(altro.marca)
                && modello.equals(altro.modello);
    }

    // Se due oggetti sono equals, devono avere lo stesso hashCode
    @Override
    public int hashCode() {
        return Objects.hash(marca, modello, anno);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + descrizioneInterna() + ", km=" + kmPercorsi + "]";
    }
}

// ---------------------------------------------------------------
// Prima sottoclasse
// ---------------------------------------------------------------
class Auto extends Veicolo {

    private int numeroPorte;

    public Auto(String marca, String modello, int anno, int numeroPorte) {
        super(marca, modello, anno);
        setNumeroPorte(numeroPorte);
    }

    public int getNumeroPorte() {
        return numeroPorte;
    }

    public void setNumeroPorte(int numeroPorte) {
        if (numeroPorte < 2 || numeroPorte > 5) {
            throw new IllegalArgumentException("Numero di porte non valido: " + numeroPorte);
        }
        this.numeroPorte = numeroPorte;
    }

    // La sottoclasse accede direttamente al campo protected, senza getter/setter
    public void percorri(int km) {
        if (km > 0) {
            kmPercorsi += km;
        }
    }

    @Override
    public boolean equals(Object o) {
        // super.equals controlla gia' null, classe, marca, modello e anno
        if (!super.equals(o)) {
            return false;
        }
        Auto altra = (Auto) o;
        return numeroPorte == altra.numeroPorte;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numeroPorte);
    }

    @Override
    public String toString() {
        return super.toString() + " porte=" + numeroPorte;
    }
}

// ---------------------------------------------------------------
// Seconda sottoclasse
// ---------------------------------------------------------------
class Moto extends Veicolo {

    private int cilindrata;

    public Moto(String marca, String modello, int anno, int cilindrata) {
        super(marca, modello, anno);
        setCilindrata(cilindrata);
    }

    public int getCilindrata() {
        return cilindrata;
    }

    public void setCilindrata(int cilindrata) {
        if (cilindrata <= 0) {
            throw new IllegalArgumentException("Cilindrata non valida: " + cilindrata);
        }
        this.cilindrata = cilindrata;
    }

    public void percorri(int km) {
        if (km > 0) {
            kmPercorsi += km;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        Moto altra = (Moto) o;
        return cilindrata == altra.cilindrata;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cilindrata);
    }

    @Override
    public String toString() {
        return super.toString() + " cc=" + cilindrata;
    }
}
