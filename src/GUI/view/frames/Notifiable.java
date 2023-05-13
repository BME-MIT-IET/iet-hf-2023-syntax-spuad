package GUI.view.frames;

public interface Notifiable {
    /**
     * Updates all the graphic views and logs all the changes
     * @param massage The message to be logged on the whatHappenedPanel
     */
    void creativeNotify(String massage);
}
