import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int scelta;
        do {
            stampaMenu();
            scelta = leggiIntero("Scelta: ");
            System.out.println();
            switch (scelta) {
                case 1 -> esercizio1PariDispari();
                case 2 -> esercizio2Medie();
                case 3 -> esercizio3PosizioniPari();
                case 4 -> esercizio4Massimo();
                case 5 -> esercizio5Sommatoria();
                case 6 -> extra1Palindroma();
                case 7 -> extra2VocaliRipetute();
                case 0 -> System.out.println("Fine programma");
                default -> System.out.println("Scelta non valida");
            }
        } while (scelta != 0);
        input.close();
    }

    private static void stampaMenu() {
        System.out.println("\n=== Selfwork Introduzione Java ===");
        System.out.println("1) Pari o dispari");
        System.out.println("2) Medie (tutti, pari, dispari)");
        System.out.println("3) Elementi in posizione pari di un array");
        System.out.println("4) Elemento maggiore di un array");
        System.out.println("5) Sommatoria di numeri inseriti dall'utente");
        System.out.println("6) EXTRA - Stringa palindroma");
        System.out.println("7) EXTRA - Vocali ripetute consecutive");
        System.out.println("0) Esci");
    }

    // Legge un intero ripetendo la richiesta finche' l'input non e' valido
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

    // Esercizio 1: pari o dispari
    private static void esercizio1PariDispari() {
        int numero = leggiIntero("Inserisci un numero intero: ");
        if (numero % 2 == 0) {
            System.out.println(numero + " e' pari");
        } else {
            System.out.println(numero + " e' dispari");
        }
    }

    // Esercizio 2: media di tutti i numeri, dei pari e dei dispari
    private static void esercizio2Medie() {
        int[] numeri = {4, 7, 10, 3, 8, 15, 22, 9};

        int sommaTutti = 0;
        int sommaPari = 0;
        int sommaDispari = 0;
        int contaPari = 0;
        int contaDispari = 0;

        for (int n : numeri) {
            sommaTutti += n;
            if (n % 2 == 0) {
                sommaPari += n;
                contaPari++;
            } else {
                sommaDispari += n;
                contaDispari++;
            }
        }

        System.out.println("Media di tutti i numeri: " + (double) sommaTutti / numeri.length);

        // Si controlla il contatore per evitare la divisione per zero
        if (contaPari > 0) {
            System.out.println("Media dei pari: " + (double) sommaPari / contaPari);
        } else {
            System.out.println("Nessun numero pari nell'array");
        }
        if (contaDispari > 0) {
            System.out.println("Media dei dispari: " + (double) sommaDispari / contaDispari);
        } else {
            System.out.println("Nessun numero dispari nell'array");
        }
    }

    // Esercizio 3: elementi in posizione pari (indici 0, 2, 4, ...)
    private static void esercizio3PosizioniPari() {
        int[] numeri = {10, 20, 30, 40, 50, 60, 70};

        System.out.println("Elementi in posizione pari (indici 0, 2, 4, ...):");
        for (int i = 0; i < numeri.length; i += 2) {
            System.out.println("posizione " + i + ": " + numeri[i]);
        }
    }

    // Esercizio 4: elemento maggiore
    private static void esercizio4Massimo() {
        int[] numeri = {12, 45, 7, 89, 23, 56};

        int massimo = numeri[0];
        for (int i = 1; i < numeri.length; i++) {
            if (numeri[i] > massimo) {
                massimo = numeri[i];
            }
        }
        System.out.println("L'elemento maggiore e': " + massimo);
    }

    // Esercizio 5: array di lunghezza scelta dall'utente e sommatoria
    private static void esercizio5Sommatoria() {
        int lunghezza = leggiIntero("Quanti numeri vuoi inserire? ");
        if (lunghezza <= 0) {
            System.out.println("La lunghezza deve essere maggiore di zero");
            return;
        }

        int[] numeri = new int[lunghezza];
        int somma = 0;
        for (int i = 0; i < numeri.length; i++) {
            numeri[i] = leggiIntero("Inserisci il numero " + (i + 1) + ": ");
            somma += numeri[i];
        }
        System.out.println("La sommatoria e': " + somma);
    }

    // Extra 1: stringa palindroma (ignora maiuscole, spazi e punteggiatura)
    private static void extra1Palindroma() {
        System.out.print("Inserisci una stringa: ");
        String testo = input.nextLine();

        String pulita = testo.toLowerCase().replaceAll("[^\\p{L}\\p{N}]", "");

        boolean palindroma = true;
        int inizio = 0;
        int fine = pulita.length() - 1;
        while (inizio < fine) {
            if (pulita.charAt(inizio) != pulita.charAt(fine)) {
                palindroma = false;
                break;
            }
            inizio++;
            fine--;
        }

        if (palindroma) {
            System.out.println("\"" + testo + "\" e' palindroma");
        } else {
            System.out.println("\"" + testo + "\" non e' palindroma");
        }
    }

    // Extra 2: due vocali uguali consecutive
    private static void extra2VocaliRipetute() {
        System.out.print("Inserisci una stringa: ");
        String testo = input.nextLine().toLowerCase();

        String vocali = "aeiou";
        boolean trovate = false;
        for (int i = 0; i < testo.length() - 1; i++) {
            char corrente = testo.charAt(i);
            if (vocali.indexOf(corrente) != -1 && corrente == testo.charAt(i + 1)) {
                trovate = true;
                break;
            }
        }

        if (trovate) {
            System.out.println("Ci sono due vocali uguali consecutive");
        } else {
            System.out.println("Non ci sono due vocali uguali consecutive");
        }
    }
}
