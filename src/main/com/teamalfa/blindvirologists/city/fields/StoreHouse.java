package main.com.teamalfa.blindvirologists.city.fields;
import main.com.teamalfa.blindvirologists.virologist.Virologist;
import main.com.teamalfa.blindvirologists.virologist.backpack.ElementBank;

public class StoreHouse extends Field {

    private ElementBank elements;
    public StoreHouse(){ elements = new ElementBank(0,0); }
    public StoreHouse(int amino, int nucleotide) {
        elements = new ElementBank(amino, nucleotide);
    }

    /**
     * This method is called when the StoreHouse is searched by a Virologist.
     * It puts elements into it's backpack.
     * @param virologist The Virologist who stands on the Field and searches it.
     */
    @Override
    public void searchedBy(Virologist virologist) {
        virologist.getBackpack().add(elements);
    }

    //setter
    public void setElements(ElementBank elements) {
        this.elements = elements;
    }
    public ElementBank getElements() {
        return elements;
    }

    /**
     * Removes all elements from the safehouse.
     */
    public void destroy() {
       elements.removeAll();
    }
}
