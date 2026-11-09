import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

// Selfwork Collection & Generics
// Una Collection e' una struttura che contiene piu' oggetti:
//   List = elenco ordinato con indici, ammette duplicati
//   Set  = insieme senza duplicati
//   Map  = coppie chiave -> valore, con chiavi uniche
class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int scelta;
        do {
            stampaMenu();
            scelta = leggiIntero("Scelta: ");
            System.out.println();
            switch (scelta) {
                case 1: esercizio1Filtro(); break;
                case 2: esercizio2Aggiorna(); break;
                case 3: esercizio3Duplicati(); break;
                case 4: extra1Set(); break;
                case 5: extra2RicercaMappa(); break;
                case 6: extra3MappaInserisciRimuovi(); break;
                case 7: extra4ClasseGenerica(); break;
                case 8: extra5MetodoGenerico(); break;
                case 0: System.out.println("Fine programma"); break;
                default: System.out.println("Scelta non valida");
            }
        } while (scelta != 0);
        input.close();
    }

    private static void stampaMenu() {
        System.out.println("\n=== Selfwork Collection & Generics ===");
        System.out.println("1) Lista filtrata (maggiorenni)");
        System.out.println("2) Aggiorna un elemento della lista per indice");
        System.out.println("3) Rimuovi i duplicati da una lista");
        System.out.println("4) EXTRA - Set senza ripetizioni (equals e hashCode)");
        System.out.println("5) EXTRA - Cerca un oggetto in una mappa");
        System.out.println("6) EXTRA - Mappa: inserisci, rimuovi, cerca");
        System.out.println("7) EXTRA - Classe generica");
        System.out.println("8) EXTRA - Metodo generico");
        System.out.println("0) Esci");
    }

    // ---------------------------------------------------------------
    // Funzioni di supporto per l'input
    // ---------------------------------------------------------------
    private static int leggiIntero(String messaggio) {
        while (true) {
            System.out.print(messaggio);
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero intero valido");
            }
        }
    }

    private static String leggiTesto(String messaggio) {
        System.out.print(messaggio);
        return input.nextLine().trim();
    }

    // Chiede i dati finche' non si ottiene una Persona valida
    private static Persona leggiPersona() {
        while (true) {
            String nome = leggiTesto("Nome: ");
            String cognome = leggiTesto("Cognome: ");
            int eta = leggiIntero("Eta': ");
            try {
                return new Persona(nome, cognome, eta);
            } catch (IllegalArgumentException e) {
                System.out.println("Dati non validi: " + e.getMessage() + ". Riprova.");
            }
        }
    }

    private static List<Persona> creaListaPersone() {
        List<Persona> persone = new ArrayList<>();
        persone.add(new Persona("Giulia", "Rossi", 16));
        persone.add(new Persona("Marco", "Bianchi", 34));
        persone.add(new Persona("Anna", "Verdi", 45));
        persone.add(new Persona("Luca", "Neri", 17));
        persone.add(new Persona("Sara", "Gallo", 22));
        persone.add(new Persona("Paolo", "Conti", 61));
        return persone;
    }

    private static void stampaConIndici(List<Persona> persone) {
        for (int i = 0; i < persone.size(); i++) {
            System.out.println(i + ") " + persone.get(i));
        }
    }

    // ---------------------------------------------------------------
    // Esercizio 1: copia in una seconda lista solo gli elementi che
    // rispettano un criterio (qui: eta' >= 18)
    // ---------------------------------------------------------------
    private static void esercizio1Filtro() {
        List<Persona> tutte = creaListaPersone();
        List<Persona> maggiorenni = new ArrayList<>();

        for (Persona p : tutte) {
            if (p.getEta() >= 18) {
                maggiorenni.add(p);
            }
        }

        System.out.println("Lista completa (" + tutte.size() + " persone):");
        stampaConIndici(tutte);
        System.out.println("\nSolo i maggiorenni (" + maggiorenni.size() + " persone):");
        stampaConIndici(maggiorenni);
    }

    // ---------------------------------------------------------------
    // Esercizio 2: aggiorna l'elemento in una posizione scelta
    // ---------------------------------------------------------------
    private static void esercizio2Aggiorna() {
        List<Persona> persone = creaListaPersone();
        stampaConIndici(persone);

        int indice = leggiIntero("\nQuale persona vuoi modificare? (indice da 0 a "
                + (persone.size() - 1) + "): ");
        if (indice < 0 || indice >= persone.size()) {
            System.out.println("Indice fuori dall'intervallo: nessuna modifica");
            return;
        }

        System.out.println("Inserisci i nuovi dati:");
        Persona nuova = leggiPersona();
        persone.set(indice, nuova); // set sostituisce l'elemento in quella posizione

        System.out.println("\nLista aggiornata:");
        stampaConIndici(persone);
    }

    // ---------------------------------------------------------------
    // Esercizio 3: rimozione dei duplicati
    // ---------------------------------------------------------------
    private static void esercizio3Duplicati() {
        List<String> nomi = new ArrayList<>(List.of(
                "Anna", "Luca", "Anna", "Marta", "Luca", "Giorgio", "Marta"));
        System.out.println("Lista originale:         " + nomi);

        // Metodo 1: ciclo, si aggiunge solo se non e' gia' presente
        List<String> senzaDuplicati = new ArrayList<>();
        for (String nome : nomi) {
            if (!senzaDuplicati.contains(nome)) {
                senzaDuplicati.add(nome);
            }
        }
        System.out.println("Con ciclo e contains:    " + senzaDuplicati);

        // Metodo 2: un Set non ammette duplicati; LinkedHashSet mantiene l'ordine di inserimento
        List<String> conSet = new ArrayList<>(new LinkedHashSet<>(nomi));
        System.out.println("Con LinkedHashSet:       " + conSet);
    }

    // ---------------------------------------------------------------
    // Extra 1: Set di oggetti senza ripetizioni.
    // Funziona perche' Persona sovrascrive equals e hashCode.
    // ---------------------------------------------------------------
    private static void extra1Set() {
        Set<Persona> insieme = new HashSet<>();

        System.out.println("add Giulia Rossi 16:  " + insieme.add(new Persona("Giulia", "Rossi", 16)));
        System.out.println("add Marco Bianchi 34: " + insieme.add(new Persona("Marco", "Bianchi", 34)));
        System.out.println("add Giulia Rossi 16:  " + insieme.add(new Persona("Giulia", "Rossi", 16))
                + " (stessi dati: rifiutata)");
        System.out.println("add Giulia Rossi 17:  " + insieme.add(new Persona("Giulia", "Rossi", 17))
                + " (eta' diversa: accettata)");

        System.out.println("\nElementi nel Set: " + insieme.size());
        for (Persona p : insieme) {
            System.out.println("- " + p);
        }
    }

    // ---------------------------------------------------------------
    // Extra 2: ricerca di un oggetto in una mappa tramite la chiave
    // ---------------------------------------------------------------
    private static void extra2RicercaMappa() {
        Map<String, Persona> rubrica = new HashMap<>();
        rubrica.put("A001", new Persona("Giulia", "Rossi", 16));
        rubrica.put("A002", new Persona("Marco", "Bianchi", 34));
        rubrica.put("A003", new Persona("Anna", "Verdi", 45));

        System.out.println("Chiavi disponibili: " + rubrica.keySet());
        String chiave = leggiTesto("Inserisci la chiave da cercare: ");

        Persona trovata = rubrica.get(chiave); // get restituisce null se la chiave non esiste
        if (trovata != null) {
            System.out.println("Trovata: " + trovata);
        } else {
            System.out.println("Nessun oggetto con chiave \"" + chiave + "\"");
        }
    }

    // ---------------------------------------------------------------
    // Extra 3: inserisci dall'input, poi rimuovi e cerca di nuovo
    // ---------------------------------------------------------------
    private static void extra3MappaInserisciRimuovi() {
        Map<String, Persona> mappa = new HashMap<>();

        String chiave = leggiTesto("Chiave (testo) per il nuovo oggetto: ");
        Persona persona = leggiPersona();
        mappa.put(chiave, persona);

        System.out.println("\nMappa dopo l'inserimento: " + mappa);
        System.out.println("Ricerca di \"" + chiave + "\": " + mappa.get(chiave));

        Persona rimossa = mappa.remove(chiave); // remove restituisce l'oggetto tolto
        System.out.println("\nRimosso: " + rimossa);

        Persona cercata = mappa.get(chiave);
        if (cercata == null) {
            System.out.println("Nuova ricerca di \"" + chiave + "\": oggetto non trovato");
        } else {
            System.out.println("Nuova ricerca: " + cercata);
        }
        System.out.println("Mappa ora: " + mappa);
    }

    // ---------------------------------------------------------------
    // Extra 4: classe generica. T e' un segnaposto per un tipo scelto
    // al momento dell'uso.
    // ---------------------------------------------------------------
    private static void extra4ClasseGenerica() {
        Scatola<String> scatolaTesto = new Scatola<>("ciao");
        Scatola<Integer> scatolaNumero = new Scatola<>(42);
        Scatola<Persona> scatolaPersona = new Scatola<>(new Persona("Anna", "Verdi", 45));

        System.out.println("Scatola<String>:  " + scatolaTesto.getContenuto());
        System.out.println("Scatola<Integer>: " + scatolaNumero.getContenuto());
        System.out.println("Scatola<Persona>: " + scatolaPersona.getContenuto());

        scatolaNumero.setContenuto(100);
        System.out.println("Dopo il setter:   " + scatolaNumero);
        // scatolaNumero.setContenuto("testo"); // ERRORE di compilazione: serve un Integer
    }

    // ---------------------------------------------------------------
    // Extra 5: metodi generici (vedi sotto)
    // ---------------------------------------------------------------
    private static void extra5MetodoGenerico() {
        List<Integer> numeri = List.of(7, 42, 3, 19);
        List<String> parole = List.of("pera", "mela", "zucca", "banana");

        stampaElementi(numeri);
        stampaElementi(parole);

        System.out.println("Massimo dei numeri: " + trovaMassimo(numeri));
        System.out.println("Massimo delle parole (ordine alfabetico): " + trovaMassimo(parole));
    }

    // Funziona con una List di qualsiasi tipo
    private static <T> void stampaElementi(List<T> lista) {
        System.out.print("Elementi: ");
        for (T elemento : lista) {
            System.out.print(elemento + " ");
        }
        System.out.println();
    }

    // T extends Comparable<T>: il tipo deve poter essere confrontato (compareTo)
    private static <T extends Comparable<T>> T trovaMassimo(List<T> lista) {
        if (lista.isEmpty()) {
            return null;
        }
        T massimo = lista.get(0);
        for (T elemento : lista) {
            if (elemento.compareTo(massimo) > 0) {
                massimo = elemento;
            }
        }
        return massimo;
    }
}

// ===================================================================
// Classi di supporto
// ===================================================================

class Persona {

    private String nome;
    private String cognome;
    private int eta;

    public Persona(String nome, String cognome, int eta) {
        setNome(nome);
        setCognome(cognome);
        setEta(eta);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("il nome non puo' essere vuoto");
        }
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        if (cognome == null || cognome.isBlank()) {
            throw new IllegalArgumentException("il cognome non puo' essere vuoto");
        }
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        if (eta < 0 || eta > 120) {
            throw new IllegalArgumentException("eta' non valida: " + eta);
        }
        this.eta = eta;
    }

    // Due persone sono uguali se hanno stessi nome, cognome ed eta'
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Persona altra = (Persona) o;
        return eta == altra.eta
                && nome.equals(altra.nome)
                && cognome.equals(altra.cognome);
    }

    // Oggetti uguali devono avere lo stesso hashCode: i Set e le Map lo usano per trovare gli oggetti
    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, eta);
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + eta + " anni)";
    }
}

class Scatola<T> {

    private T contenuto;

    public Scatola(T contenuto) {
        this.contenuto = contenuto;
    }

    public T getContenuto() {
        return contenuto;
    }

    public void setContenuto(T contenuto) {
        this.contenuto = contenuto;
    }

    @Override
    public String toString() {
        return "Scatola[" + contenuto + "]";
    }
}
