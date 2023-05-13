package main.com.teamalfa.blindvirologists.consoleController;

public enum Prefixes {
    Field("F"),
    Laboratory("L"),
    StoreHouse("ST"),
    SafeHouse("SA"),
    Virologist("V"),
    Backpack("B"),
    ElementBank("EB"),
    GeneticCode("GC"),
    Agent("A"),
    Equipment("E")
    ;

    private final String text;

    Prefixes(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
