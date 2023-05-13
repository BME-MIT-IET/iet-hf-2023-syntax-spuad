package GUI.view.view;
/**
 * Interface that every viewable object implements.
 */
public interface View {
    /**
     * A method that they for some graphical change.
     */
    void update();
    /**
     * A method that they do when someone clicks them.
     */
    void onClick();
}
