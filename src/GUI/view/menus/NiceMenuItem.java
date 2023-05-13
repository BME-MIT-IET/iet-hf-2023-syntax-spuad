package GUI.view.menus;

import javax.swing.*;
import java.awt.*;

public class NiceMenuItem extends JMenuItem {
    public NiceMenuItem(Action a) {
        super(a);
        init();
    }

    public NiceMenuItem(String text) {
        super(text);
        init();
    }

    public NiceMenuItem() {
        init();
    }

    private void init() {
        setForeground(Color.RED);
        setBackground(Color.BLACK);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder(0,0 ,0 , 0));
        setFont(new Font("Viner Hand ITC", Font.PLAIN, 12));
    }


}
