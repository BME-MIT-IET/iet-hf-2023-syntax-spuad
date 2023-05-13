package main.com.teamalfa.blindvirologists.consoleController.random;

public class MyRandom{
    private static MyRandom instance = null;
    boolean yesOrNoDeterministic = false;
    boolean choiceDeterministic = false;
    private boolean yesOrNo;
    private int chosenNumber;

    private MyRandom() {
        yesOrNo = true;
        chosenNumber = 1;
    }

    static {
        instance = new MyRandom();
    }

    public boolean isYesOrNoDeterministic() {
        return yesOrNoDeterministic;
    }

    public boolean isChoiceDeterministic() {
        return choiceDeterministic;
    }

    public void setYesOrNoDeterministic(boolean yesOrNoDeterministic) {
        this.yesOrNoDeterministic = yesOrNoDeterministic;
    }

    public void setChoiceDeterministic(boolean choiceDeterministic) {
        this.choiceDeterministic = choiceDeterministic;
    }

    public static MyRandom getInstance() {
        return instance;
    }
    public void setValues(Boolean yesOrNo, int chosenNumber) {
        this.yesOrNo = yesOrNo;
        this.chosenNumber = chosenNumber;
    }

    public void setYesOrNo(boolean yesOrNo) {
        this.yesOrNo = yesOrNo;
    }

    public void setChosenNumber(int chosenNumber) {
        this.chosenNumber = chosenNumber;
    }

    public boolean getYesOrNo() {
        return yesOrNo;
    }

    public int getChoice(){
        return chosenNumber;
    }
}
