import java.lang.reflect.Array;
import java.time.LocalDate;

final static double PRICE_ADULT = 299.9;
final static double PRICE_CHILD = 149.9;

static int getInput(int minValue, int maxValue) {
    boolean validAnswer = false;
    String answerString = "";
    int answerInt = 0;
    while (!validAnswer) {
        answerString = IO.readln();
        try {
            answerInt = Integer.parseInt(answerString);
            if (answerInt > maxValue || answerInt < minValue) {
                IO.println("Svaret måste vara mellan " + Integer.toString(minValue) + " och "
                        + Integer.toString(maxValue) + ". Försök igen:");
            } else {
                validAnswer = true;
            }
        } catch (Exception e) {
            IO.println("Ogiltigt Svar. Svaret måste bestå av endast siffror. Försök igen:");
        }
    }
    return answerInt;
}

// a returned index of -1 means the value was not found
static int findIndex(int value, int[] values) {
    for (int i = 0; i < values.length; i++) {
        if (values[i] == value)
            return i;
    }
    return -1;
};

static boolean isAdult(int birthday) {
    LocalDate today_ld = LocalDate.now();
    String today_s = today_ld.toString();
    today_s = today_s.replace("-", "");
    int today_i = Integer.parseInt(today_s);
    if (birthday + 180000 <= today_i)
        return true;
    return false;
}

public static void main() {
    int[] bookings = new int[20];
    while (true) {

        String[] options = { "Lägg till bokning", "Skriv ut lediga platser", "Beräkna vinst", "Hitta bokning",
                "Ta bort bokning",
                "Se vuxna/barn", "Se fönsterplatser" };
        for (int i = 0; i < options.length; i++) {
            IO.println(Integer.toString(i + 1) + ":" + options[i]);
        }
        IO.println("Val:");
        int choice = getInput(1, options.length + 1);
        switch (choice) {
            case 1 -> {
                IO.println("Ange plats att boka:");
                int spot = getInput(1, bookings.length + 1);
                IO.println("Ange födelsedag (ååååmmdd):");
                int birthday = getInput(10000000, 99999999);
                bookings[spot - 1] = birthday;
            }
            case 2 -> {
                for (int i = 0; i < bookings.length; i++) {
                    if (bookings[i] == 0) {
                        IO.println(Integer.toString(i + 1));
                    }

                }
            }
            case 3 -> {
                double totalPrice = 0;
                for (int birthday : bookings) {
                    if (birthday != 0) {
                        if (isAdult(birthday))
                            totalPrice += PRICE_ADULT;
                        else
                            totalPrice += PRICE_CHILD;
                    }
                }
                IO.println("Total vinst:" + Double.toString(totalPrice));
            }
            case 4 -> {
                IO.println("Ange födelsedatum:");
                int birthday = getInput(10000000, 99999999);
                int spot = findIndex(birthday, bookings);
                if (spot == -1) {
                    IO.println("Person har ej en bokad plats.");
                } else {
                    IO.println("Personen har bokats plats: " + Integer.toString(spot + 1));
                }
            }
            case 5 -> {
                IO.println("Ange födelsedatum:");
                int birthday = getInput(10000000, 99999999);
                int spot = findIndex(birthday, bookings);
                if (spot == -1) {
                    IO.println("Person har ej en bokad plats.");
                } else {
                    bookings[spot] = 0;
                    IO.println("Plats " + Integer.toString(spot + 1) + " för person " + Integer.toString(birthday)
                            + " har avbokats.");
                }
            }
            case 6 -> {
                for (int i = 0; i<bookings.length; i++){
                    if (bookings[i] == 0) continue;
                    if (isAdult(bookings[i])) IO.println(Integer.toString(i+1) + ":" + "Vuxen");
                    else IO.println(Integer.toString(i+1) + ":" + "Barn");
                }
            }
        }
        IO.readln();
    }
}