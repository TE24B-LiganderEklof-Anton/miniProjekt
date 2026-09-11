static int getInput(int minValue, int maxValue){
        boolean validAnswer = false;
        String answerString = "";
        int answerInt = 0;
        while (!validAnswer){
            answerString = IO.readln();
            try {
                answerInt = Integer.parseInt(answerString);
                if (answerInt > maxValue || answerInt < minValue){
                    IO.println("Svaret måste vara mellan " + Integer.toString(minValue) + " och " + Integer.toString(maxValue) + ". Försök igen:");
                }
                else
                {
                    validAnswer = true;
                }
            } catch (Exception e) {
                IO.println("Ogiltigt Svar. Svaret måste bestå av endast siffror. Försök igen:");
            }
        }
        return answerInt;
    }
public static void main(){
    
    String[] options = {"Lägg till bokning", "Skriv ut lediga platser","Beräkna vinst", "Hitta bokning", "Se vuxna/barn", "Se fönsterplatser"};
    for (int i=0; i<options.length; i++){
        IO.println(Integer.toString(i+1) + ":" + options[i]);
    }
    IO.println("Val:");
    int val = getInput(1, options.length+1);
}