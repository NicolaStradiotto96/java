import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Main {

    static class Person {
        public String name;
        public Gender gender;

        Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }
    }

    enum Gender {
        MALE, FEMALE
    }

    public static void main(String[] args) {
        persone();
        numeri();
        lambda();
        mele();
    }

    static void persone() {
        System.out.println("=== Persone ===");

        List<Person> people = List.of(
            new Person("Andrea", Gender.MALE),
            new Person("Maria", Gender.FEMALE),
            new Person("Paola", Gender.FEMALE),
            new Person("Roberto", Gender.MALE),
            new Person("Marco", Gender.MALE)
        );

        // imperativo
        List<Person> females = new ArrayList<>();
        for (Person person : people) {
            if (Gender.FEMALE.equals(person.gender)) {
                females.add(person);
            }
        }
        for (Person female : females) {
            System.out.println(female.name);
        }

        // funzionale
        List<Person> femalesStream = people.stream()
            .filter(person -> person.gender.equals(Gender.FEMALE))
            .toList();
        System.out.println("Con stream: " + femalesStream.size() + " persone");
    }

    static void numeri() {
        System.out.println("\n=== Numeri ===");

        List<Integer> numbers = List.of(2, 5, 6, 7, 8, 9, 12);
        Integer sum = numbers.stream()
            .filter(number -> number % 2 == 0)
            .map(number -> number * number)
            .reduce(0, Integer::sum);
        System.out.println(sum);
    }

    static void lambda() {
        System.out.println("\n=== Lambda ===");

        Function<Integer, Integer> doppio = num -> num * 2;
        Function<Integer, Integer> doppioTipizzato = (Integer num) -> num * 2;
        BiFunction<Integer, Integer, Integer> somma = (a, b) -> {
            int risultato = a + b;
            return risultato;
        };
        System.out.println(doppio.apply(7) + " " + doppioTipizzato.apply(21) + " " + somma.apply(3, 4));

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Predicate<Integer> check = (Integer num) -> num % 2 == 0;
        List<Integer> evens = numbers.stream().filter(check).collect(Collectors.toList());
        System.out.println(evens.size() + " pari: " + evens);
    }

    static void mele() {
        System.out.println("\n=== Mele ===");

        List<Apple> apples = List.of(
            new Apple(Color.GREEN, 120),
            new Apple(Color.RED, 170),
            new Apple(Color.GREEN, 150),
            new Apple(Color.RED, 110),
            new Apple(Color.GREEN, 89),
            new Apple(Color.RED, 75),
            new Apple(Color.GREEN, 180),
            new Apple(Color.RED, 115)
        );

        // step 1 e 2
        stampa("Verdi", filterGreenApples(apples));
        stampa("Rosse", filterRedApples(apples));
        stampa("Rosse (colore come parametro)", filterApplesByColor(apples, Color.RED));
        stampa("Oltre 150g", filterApplesByWeight(apples, 150));

        // step 3: interfaccia e classi predicato
        stampa("Verdi (classe)", filterApplesCustom(apples, new AppleGreenColorPredicate()));
        stampa("Pesanti (classe)", filterApplesCustom(apples, new AppleWeightPredicate()));
        stampa("Leggere (classe)", filterApplesCustom(apples, new AppleLightPredicate()));
        stampa("Rosse pesanti", filterApplesCustom(apples, new AppleRedHeavyPredicate()));
        stampa("Rosse leggere", filterApplesCustom(apples, new AppleRedLightPredicate()));

        // step 4: lambda
        ApplePredicateInterface redApple = (Apple a) -> a.getColor().equals(Color.RED);
        ApplePredicateInterface heavyApple = (Apple a) -> a.getWeight() > 150;
        stampa("Rosse (lambda in variabile)", filterApplesCustom(apples, redApple));
        stampa("Pesanti (lambda in variabile)", filterApplesCustom(apples, heavyApple));

        // lambda passata direttamente, con Predicate di Java
        stampa("Verdi (Predicate)", filterApples(apples, (Apple a) -> a.getColor().equals(Color.GREEN)));
        stampa("Leggere (Predicate)", filterApples(apples, (Apple a) -> a.getWeight() < 150));
        stampa("Rosse e leggere (Predicate)",
            filterApples(apples, a -> a.getColor().equals(Color.RED) && a.getWeight() < 150));
    }

    static void stampa(String titolo, List<Apple> apples) {
        System.out.println(titolo + ": " + apples);
    }

    public static List<Apple> filterGreenApples(List<Apple> apples) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getColor().equals(Color.GREEN)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterRedApples(List<Apple> apples) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getColor().equals(Color.RED)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> apples, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> apples, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesCustom(List<Apple> apples, ApplePredicateInterface predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (predicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}

enum Color {
    RED, GREEN
}

class Apple {
    public Color color;
    public int weight;

    public Apple(Color color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return color + " " + weight + "g";
    }
}

interface ApplePredicateInterface {
    boolean test(Apple apple);
}

class AppleGreenColorPredicate implements ApplePredicateInterface {
    public boolean test(Apple apple) {
        return Color.GREEN.equals(apple.getColor());
    }
}

class AppleRedColorPredicate implements ApplePredicateInterface {
    public boolean test(Apple apple) {
        return Color.RED.equals(apple.getColor());
    }
}

class AppleWeightPredicate implements ApplePredicateInterface {
    public boolean test(Apple apple) {
        return apple.getWeight() > 150;
    }
}

class AppleLightPredicate implements ApplePredicateInterface {
    public boolean test(Apple apple) {
        return apple.getWeight() <= 150;
    }
}

class AppleRedHeavyPredicate implements ApplePredicateInterface {
    @Override
    public boolean test(Apple apple) {
        return Color.RED.equals(apple.getColor()) && apple.getWeight() > 150;
    }
}

class AppleRedLightPredicate implements ApplePredicateInterface {
    @Override
    public boolean test(Apple apple) {
        return Color.RED.equals(apple.getColor()) && apple.getWeight() < 150;
    }
}
