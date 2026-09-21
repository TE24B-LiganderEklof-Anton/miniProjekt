import java.lang.reflect.Array;
import java.time.LocalDate;

final static double PRICE_ADULT = 299.9;
final static double PRICE_CHILD = 149.9;
final static int BUSS_LENGTH = 20;

static int getInputInt(int minValue, int maxValue) {
    boolean validAnswer = false;
    String answerString = "";
    int answerInt = 0;
    while (!validAnswer) {
        answerString = IO.readln();
        try {
            answerInt = Integer.parseInt(answerString);
            if (answerInt > maxValue || answerInt < minValue) {
                IO.println("Svaret måste vara mellan " + minValue + " och "
                        + maxValue + ". Försök igen:");
            } else {
                validAnswer = true;
            }
        } catch (Exception e) {
            IO.println("Ogiltigt Svar. Svaret måste bestå av endast siffror. Försök igen:");
        }
    }
    return answerInt;
}

static void printStringArray(String[] array) {
    for (int i = 0; i < array.length; i++) {
        IO.println(i + 1 + ":" + array[i]);
    }
}

static int ask(String[] options) {
    printStringArray(options);
    IO.println("Val:");
    int choice = getInputInt(1, options.length);
    return choice;
}

static Boolean isWindowSeat(int spot_i) {
    double spot_d = spot_i;
    if (spot_d / 4 == Math.floor(spot_d / 4))
        return true;
    if ((spot_d - 1) / 4 == Math.floor((spot_d - 1) / 4))
        return true;
    return false;
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

static int findBooking(int[] bookingsBirthdays, String[] bookingsNames) {
    String[] searchOptions = { "Sök efter födelsedatum", "Sök efter namn" };
    int searchChoice = ask(searchOptions);

    int index = -1;
    if (searchChoice == 1) {

        IO.println("Ange födelsedatum:");
        int birthday = getInputInt(10000000, 99999999);
        index = findIndex(birthday, bookingsBirthdays);

    } else if (searchChoice == 2) {
        String name = IO.readln("Ange namn:").toLowerCase();
        index = Arrays.asList(bookingsNames).indexOf(name);
    }
    return index;
}

public static void main() {

    int[] bookingsBirthdays = new int[BUSS_LENGTH];
    String[] bookingsNames = new String[BUSS_LENGTH];

    while (true) {

        String[] options = { "Lägg till bokning", "Skriv ut lediga platser", "Beräkna vinst", "Hitta bokning",
                "Ta bort bokning",
                "Se vuxna/barn", "Se fönsterplatser" };
        // for (int i = 0; i < options.length; i++) {
        // IO.println(Integer.toString(i + 1) + ":" + options[i]);
        // }
        // IO.println("Val:");
        // int choice = getInputInt(1, options.length + 1);
        int choice = ask(options);
        switch (choice) {
            case 1 -> {// add booking
                IO.println("Ange plats att boka:");
                int spot = getInputInt(1, bookingsBirthdays.length + 1);
                IO.println("Ange födelsedag (ååååmmdd):");
                int birthday = getInputInt(10000000, 99999999);
                String name = IO.readln("Ange namn:").toLowerCase();

                bookingsBirthdays[spot - 1] = birthday;
                bookingsNames[spot - 1] = name;
            }
            case 2 -> {// write empty spots
                for (int i = 0; i < bookingsBirthdays.length; i++) {
                    if (bookingsBirthdays[i] == 0) {
                        IO.println(i + 1);
                    }
                }
            }
            case 3 -> {// calculate profit
                double totalPrice = 0;
                for (int birthday : bookingsBirthdays) {
                    if (birthday != 0) {
                        if (isAdult(birthday))
                            totalPrice += PRICE_ADULT;
                        else
                            totalPrice += PRICE_CHILD;
                    }
                }
                IO.println("Total vinst:" + totalPrice);
            }
            case 4 -> {// find booking
                int spot = findBooking(bookingsBirthdays, bookingsNames);
                if (spot == -1) {
                    IO.println("Person har ej en bokad plats.");
                } else {
                    IO.println("Personen har bokats plats: " + spot + 1);
                }
            }
            case 5 -> {// remove booking
                IO.println("Ange födelsedatum:");
                int spot = findBooking(bookingsBirthdays, bookingsNames);
                if (spot == -1) {
                    IO.println("Person har ej en bokad plats.");
                } else {
                    bookingsBirthdays[spot] = 0;
                    IO.println("Plats " + spot + 1 + " för person " + bookingsNames[spot] + " har avbokats.");
                }
            }
            case 6 -> {// see adults/children
                for (int i = 0; i < bookingsBirthdays.length; i++) {
                    if (bookingsBirthdays[i] == 0)
                        continue;
                    if (isAdult(bookingsBirthdays[i]))
                        IO.println(i + 1 + ":" + "Vuxen");
                    else
                        IO.println(i + 1 + ":" + "Barn");
                }
            }
            case 7 -> {// view window seats
                for (int i = 0; i < bookingsBirthdays.length; i++) {
                    if (isWindowSeat(i + 1)) {
                        IO.println(i + 1);
                    }
                }
            }
        }
        IO.readln();
    }
}