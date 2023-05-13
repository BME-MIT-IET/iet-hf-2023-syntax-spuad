package GUI.view.frames.menuFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * On this frame the player can learn how to play
 * the game and learn the rules
 */
public class HelpFrame extends JFrame implements ActionListener {
    /**
     * Closes this window and goes back
     * to the main menu
     */
    JButton backButton;

    HelpFrame(){
        this.setTitle("Help");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(400,700);
        ImageIcon imageIcon = new ImageIcon("logo.png");
        this.setIconImage(imageIcon.getImage());
        this.getContentPane().setBackground(new Color(0,0,0));

        //back button to go back to the menu
        backButton = new JButton();
        backButton.addActionListener(this);
        backButton.setText("Back");
        backButton.setFont(new Font("Bauhaus 93",Font.PLAIN,30));
        backButton.setForeground(Color.RED);
        backButton.setBackground(Color.BLACK);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setFocusPainted(false);
        backButton.setBounds(0,0,100,50);

        //Textarea for how to play the game
        TextArea textArea = new TextArea();
        textArea.setForeground(Color.RED);
        textArea.setBackground(Color.BLACK);
        textArea.setFont(new Font("Viner Hand ITC",Font.PLAIN,11));
        textArea.setBounds(0,50,385,610);
        String help = "Welcome to the blind virologist game kitty help panel,\n" +
                "where you can learn about the gameplay in general.\n" +
                "\n" +
                "It's a turn based game, every player has 5 action points \n" +
                "in each turn, if you feel like you can't do anything else in this\n" +
                "turn or you don't have any actions points remaining, you can switch\n" +
                "to the next player by pressing the \"End Turn button\". This game can be\n" +
                "played by 6 people or less. To make the game more fair, the order of \n" +
                "the players get shuffeled every round.\n" +
                "\n" +
                "Each player has their own coloured shirt, the first order of the game \n" +
                "is always the same, so the colours go like this:\n" +
                "Player 1 - blue\n" +
                "Player 2 - green\n" +
                "Player 3 - grey\n" +
                "Player 4 - orange\n" +
                "Player 5 - red\n" +
                "Player 6 - yellow\n" +
                "\n" +
                "To move between the fields you need to click on one of the neigbouring\n" +
                "fields. After moving to a new field you can search it by clicking on it\n" +
                "again. There are four field types: field, laboratory, safehouse, and \n" +
                "storehouse. You can collect the things from each field by clicking on them.\n" +
                "After collecting them, they will appear in you inventory that is positioned\n" +
                "on the right side of the frame. After collecting them you can choose from \n" +
                "the actions they provide by clicking on them in the inventory, you can use\n" +
                "them after clicking on the virlogist you targeted.\n" +
                "\n" +
                "The equipments and the element banks are well distinguished, \n" +
                "but we'll give you some help in identifying the genetic codes:\n" +
                "big buff blue one - amnesia code\n" +
                "green one that dances around - dance code\n" +
                "redish one that looks like something from your nightmares - paralyze code\n" +
                "the sparrow that's going through an acid-trip - bear code\n" +
                "Vaccines and Viruses are colour coded:\n" +
                "amnesia - green\tvaccine cost: 10,10\tvirus cost: 20,20\n" +
                "dance - yellow \tvaccine cost: 10,10\tvirus cost: 15,15\n" +
                "paralyze - blue\tvaccine cost: 10,10\tvirus cost: 18,18\n" +
                "bear - red\tvaccine cost: 10,10\tvirus cost: 40,40\n" +
                "\n" +
                "If you want to rob an enemy virologist you need to click on them first\n" +
                "than click the rob panel. If they were paralyzed the you can choose from\n" +
                "the robbing options that are offered to you.\n" +
                "\n" +
                "You can win the game by collecting every genetic code on the field. \n" +
                "But beware the game can also win if every player gets turned into a bear.";
        textArea.setText(help);

        this.setLayout(null);
        this.setVisible(true);
        this.add(backButton);
        this.add(textArea);
        this.repaint();
    }

    /**
     * Upon clicking the "back" button the player
     * goes back to the main menu
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            this.dispose();
        }
    }
}
