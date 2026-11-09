// Selfwork Polimorfismo
// Esercizio 1: gerarchia Persona nel dominio "scuola"
// Esercizio 2: ristorante con Restaurant, Dish e Drink

class Main {

    public static void main(String[] args) {
        esercizio1Scuola();
        System.out.println();
        esercizio2Ristorante();
    }

    // ---------------------------------------------------------------
    // Esercizio 1
    // ---------------------------------------------------------------
    private static void esercizio1Scuola() {
        System.out.println("===== ESERCIZIO 1: SCUOLA =====");

        // Upcasting: oggetti di sottoclassi diverse in un array di tipo Persona
        Persona[] scuola = {
            new Studente("Giulia", "Rossi", 16, "3B"),
            new Studente("Marco", "Bianchi", 17, "4A"),
            new Docente("Anna", "Verdi", 45, "Matematica"),
            new Segretario("Paolo", "Neri", 52, "Segreteria didattica")
        };

        // Polimorfismo: la stessa chiamata presentati() esegue il metodo
        // della classe reale di ogni oggetto
        for (Persona p : scuola) {
            System.out.println(p.presentati());
        }

        int oreTotali = 0;
        for (Persona p : scuola) {
            oreTotali += p.oreSettimanali();
        }
        System.out.println("Ore settimanali totali: " + oreTotali);

        // Downcasting: per usare un metodo che esiste solo nella sottoclasse
        // si controlla il tipo reale con instanceof e poi si fa il cast
        for (Persona p : scuola) {
            if (p instanceof Docente) {
                Docente d = (Docente) p;
                System.out.println("Materia insegnata da " + d.getNome() + ": " + d.getMateria());
            }
        }
    }

    // ---------------------------------------------------------------
    // Esercizio 2
    // ---------------------------------------------------------------
    private static void esercizio2Ristorante() {
        System.out.println("===== ESERCIZIO 2: RISTORANTE =====");

        Dish[] piatti = {
            new Dish("Spaghetti alla carbonara", 12.50,
                    new String[] {"spaghetti", "guanciale", "uova", "pecorino"}),
            new Dish("Tagliata di manzo", 18.00),
            new Dish("Tiramisu", 6.50)
        };

        Drink[] bevande = {
            new Drink("Acqua naturale", 2.00),
            new Drink("Vino rosso della casa", 4.50),
            new Drink("Caffe'", 1.50)
        };

        Restaurant ristorante = new Restaurant("Trattoria Il Grappolo", piatti, bevande);
        ristorante.stampaMenu();

        System.out.println("\nIngredienti di " + piatti[0].getNome() + ":");
        for (String ingrediente : piatti[0].getIngredienti()) {
            System.out.println("- " + ingrediente);
        }

        // Il setter modifica il prezzo; il menu si aggiorna perche' l'array contiene gli stessi oggetti
        piatti[1].setPrezzo(19.50);
        System.out.println("\nDopo l'aumento del prezzo:");
        ristorante.stampaMenu();

        try {
            piatti[2].setPrezzo(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("\nSetter con valore non valido: " + e.getMessage());
        }
    }
}

// ===================================================================
// Esercizio 1: classi
// ===================================================================

// Classe astratta: non si puo' istanziare, serve da base comune
abstract class Persona {

    private String nome;
    private String cognome;
    private int eta;

    public Persona(String nome, String cognome, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEta() {
        return eta;
    }

    // Metodi astratti: ogni sottoclasse e' obbligata a darne la propria versione
    public abstract String ruolo();

    public abstract int oreSettimanali();

    public String presentati() {
        return "Sono " + nome + " " + cognome + ", " + eta + " anni, " + ruolo() + ".";
    }
}

class Studente extends Persona {

    private String classe;

    public Studente(String nome, String cognome, int eta, String classe) {
        super(nome, cognome, eta);
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    @Override
    public String ruolo() {
        return "studente";
    }

    @Override
    public int oreSettimanali() {
        return 30;
    }

    @Override
    public String presentati() {
        return super.presentati() + " Frequento la classe " + classe + ".";
    }
}

class Docente extends Persona {

    private String materia;

    public Docente(String nome, String cognome, int eta, String materia) {
        super(nome, cognome, eta);
        this.materia = materia;
    }

    public String getMateria() {
        return materia;
    }

    @Override
    public String ruolo() {
        return "docente";
    }

    @Override
    public int oreSettimanali() {
        return 18;
    }

    @Override
    public String presentati() {
        return super.presentati() + " Insegno " + materia + ".";
    }
}

class Segretario extends Persona {

    private String ufficio;

    public Segretario(String nome, String cognome, int eta, String ufficio) {
        super(nome, cognome, eta);
        this.ufficio = ufficio;
    }

    public String getUfficio() {
        return ufficio;
    }

    @Override
    public String ruolo() {
        return "personale amministrativo";
    }

    @Override
    public int oreSettimanali() {
        return 36;
    }

    @Override
    public String presentati() {
        return super.presentati() + " Lavoro in " + ufficio + ".";
    }
}

// ===================================================================
// Esercizio 2: classi
// ===================================================================

class Dish {

    private String nome;
    private double prezzo;
    private String[] ingredienti; // opzionale

    public Dish(String nome, double prezzo) {
        this(nome, prezzo, new String[0]);
    }

    public Dish(String nome, double prezzo, String[] ingredienti) {
        setNome(nome);
        setPrezzo(prezzo);
        setIngredienti(ingredienti);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome del piatto non puo' essere vuoto");
        }
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        if (prezzo < 0) {
            throw new IllegalArgumentException("Il prezzo non puo' essere negativo: " + prezzo);
        }
        this.prezzo = prezzo;
    }

    public String[] getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String[] ingredienti) {
        this.ingredienti = (ingredienti == null) ? new String[0] : ingredienti;
    }
}

class Drink {

    private String nome;
    private double prezzo;

    public Drink(String nome, double prezzo) {
        setNome(nome);
        setPrezzo(prezzo);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della bevanda non puo' essere vuoto");
        }
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        if (prezzo < 0) {
            throw new IllegalArgumentException("Il prezzo non puo' essere negativo: " + prezzo);
        }
        this.prezzo = prezzo;
    }
}

class Restaurant {

    private String name;
    private Dish[] dishes;
    private Drink[] drinks;

    public Restaurant(String name, Dish[] dishes, Drink[] drinks) {
        setName(name);
        setDishes(dishes);
        setDrinks(drinks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Il nome del ristorante non puo' essere vuoto");
        }
        this.name = name;
    }

    public Dish[] getDishes() {
        return dishes;
    }

    public void setDishes(Dish[] dishes) {
        if (dishes == null) {
            throw new IllegalArgumentException("L'elenco dei piatti non puo' essere null");
        }
        this.dishes = dishes;
    }

    public Drink[] getDrinks() {
        return drinks;
    }

    public void setDrinks(Drink[] drinks) {
        if (drinks == null) {
            throw new IllegalArgumentException("L'elenco delle bevande non puo' essere null");
        }
        this.drinks = drinks;
    }

    // Prima i piatti (nome e prezzo), poi le bevande
    public void stampaMenu() {
        System.out.println("=== Menu - " + name + " ===");

        System.out.println("-- Piatti --");
        for (Dish piatto : dishes) {
            System.out.println(piatto.getNome() + " - " + formattaPrezzo(piatto.getPrezzo()));
        }

        System.out.println("-- Bevande --");
        for (Drink bevanda : drinks) {
            System.out.println(bevanda.getNome() + " - " + formattaPrezzo(bevanda.getPrezzo()));
        }
    }

    private static String formattaPrezzo(double prezzo) {
        return String.format("%.2f EUR", prezzo);
    }
}
