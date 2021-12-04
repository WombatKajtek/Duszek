package com.example.appsar.objects;

//klasa ekwipunku, pozwala na zbieranie obiektów klasy Collectible
public class Equipment {
    private int[] slot = new int[3];
    private boolean gum=false, flashlight=false, paper=false;


    //konstruktor
    public Equipment() {
        clearEq();
    }

    //metoda pozwalająca zbierać przedmioty, przedmiot jest umiejscowiony w pierwszym możliwym
    //miejscu w ekwipunku dodatkowo rejestrowany jest typ zebranego obiektu
    public void setSlot(int collectible){
        if (collectible == 0)
            gum=true;
        if (collectible == 1)
            flashlight=true;
        if (collectible == 2)
            paper=true;

            if (slot[0] < 0)
                slot[0] = collectible;
            else if (slot[1] < 0)
                slot[1] = collectible;
            else if (slot[2] < 0)
                slot[2] = collectible;

    }

    //metoda pozwalająca usunąć obiekt z ekripunku
    public void unsetSlot(int index, int collectible){
        slot[index] = 0;
        if (collectible == 0)
            gum=false;
        if (collectible == 1)
            flashlight=false;
        if (collectible == 2)
            paper=false;
    }

    //metoda czyszcząca cały ekwpinek
    public void clearEq(){
        for (int i=0;i<3;i++)
            slot[i]=-1;
    }

    //gettery i settery
    public int getSlot(int index) {
        return slot[index];
    }

    public boolean isGum() {
        return gum;
    }

    public boolean isFlashlight() {
        return flashlight;
    }

    public boolean isPaper() {
        return paper;
    }
}
