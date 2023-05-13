package main.com.teamalfa.blindvirologists.virologist.backpack;

public class ElementBank {
    private int aminoAcid;
    private int nucleotide;
    private int aminoAcidMaxSize;
    private int nucleotideMaxSize;

    public ElementBank(int amino, int nucleotide){
        this.aminoAcid = amino;
        this.nucleotide = nucleotide;
        aminoAcidMaxSize = 40;
        nucleotideMaxSize = 40;

    }

    public ElementBank(int nucleoQuantity, int aminoQuantity, int nucleoSize, int aminoSize) {
        nucleotide = nucleoQuantity;
        aminoAcid = aminoQuantity;
        nucleotideMaxSize = nucleoSize;
        aminoAcidMaxSize = aminoSize;
    }

    /**
     * Increases the maxsize with the parameter.
     * @param extraSize The number the maxsize is increased with.
     */
    public void increaseMaxSize(int extraSize) {
        aminoAcidMaxSize += extraSize;
        nucleotideMaxSize += extraSize;
    }

    /**
     * Decreases the maxsize with the parameter.
     * @param extraSize The number the maxsize is decreased by.
     */
    public void decreaseMaxSize(int extraSize) {
        aminoAcidMaxSize -= extraSize;
        nucleotideMaxSize -= extraSize;
    }

    /**
     * Adds the element if they fit.
     * @param elements The elements that is needed to be added-
     */
    public void add(ElementBank elements) {
        if(elements.getAminoAcid() > aminoAcidMaxSize-aminoAcid){
            int tmp = aminoAcidMaxSize - aminoAcid;
            elements.aminoAcid -= tmp;
            aminoAcid += tmp;
        }
        else{
            aminoAcid += elements.aminoAcid;
            elements.aminoAcid -= elements.aminoAcid;
        }
        if(elements.getNucleotide() > nucleotideMaxSize-nucleotide){
            int tmp = nucleotideMaxSize - nucleotide;
            elements.nucleotide -= tmp;
            nucleotide += tmp;
        }
        else{
            nucleotide += elements.nucleotide;
            elements.nucleotide -= elements.nucleotide;
        }
    }

    /**
     * Removes the elements if there were enough in the element bank.
     * @param added The amount that needs to be removed.
     * @return True if it was successful, false if it wasn't.
     */
    public boolean remove(ElementBank added) {

        boolean ret = false;
        if(added.getAminoAcid() < aminoAcid && added.getNucleotide() < nucleotide) {
            aminoAcid -= added.getAminoAcid();
            nucleotide -= added.getNucleotide();
            ret = true;
        }
        return ret;
    }

    /**
     * Sets the number of the nucleotides and the amino acids to zero.
     */
    public void removeAll() {
        nucleotide = aminoAcid = 0;
    }

    public boolean isFull() {
        return nucleotide == nucleotideMaxSize && aminoAcid == aminoAcidMaxSize;
    }

    //getters, setters
    public int getAminoAcid() {
        return aminoAcid;
    }
    public int getNucleotide() {
        return  nucleotide;
    }
    public int getAminoAcidMaxSize() {
        return nucleotideMaxSize;
    }
    public int getNucleotideMaxSize() {
        return aminoAcidMaxSize;
    }

    public void setAminoAcid(int num) {
        aminoAcid = num;
    }

    public void setNucleotide(int num) {
        nucleotide = num;
    }


}
