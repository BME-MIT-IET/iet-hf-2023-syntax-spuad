package GUI.view.frames.menuFrames;

import GUI.view.frames.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener {
    /**
     * You can adjust the number of players of the game
     */
    JSlider playerSlider;
    /**
     * Play button to start the game
     */
    JButton playButton;
    /**
     * Exit button to close the program
     */
    JButton exitButton;
    /**
     * For opening the help frame where you can see the rules
     * and how to play the game
     */
    JButton kittyButton;

    public MenuFrame() {
        this.setTitle("BV");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(430,550);
        ImageIcon imageIcon = new ImageIcon("resources/logo.png");
        this.setIconImage(imageIcon.getImage());
        this.getContentPane().setBackground(new Color(0,0,0));

        //The title of the menu
        JLabel titleLabel = new JLabel("Blind Virologist by Alfa Team");
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Bauhaus 93",Font.PLAIN,32));
        titleLabel.setVerticalAlignment(JLabel.NORTH);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(2,0,410,35);

        //Slider for the player count
        playerSlider = new JSlider(1,6,3);
        playerSlider.setOrientation(SwingConstants.VERTICAL);
        playerSlider.setFont(new Font("Bauhaus 93",Font.PLAIN,32));
        playerSlider.setForeground(Color.RED);
        playerSlider.setBackground(Color.BLACK);
        playerSlider.setMinorTickSpacing(1);
        playerSlider.setMajorTickSpacing(1);
        playerSlider.setPaintTicks(true);
        playerSlider.setPaintLabels(true);
        playerSlider.setBounds(0,300,100,200);

        //The kitty button for the user manual
        ImageIcon cicaIcon = new ImageIcon("resources/cicmic.gif");
        kittyButton = new JButton();
        kittyButton.addActionListener(this);
        kittyButton.setIcon(cicaIcon);
        kittyButton.setBounds(200,330,200,180);
        kittyButton.setBackground(Color.BLACK);
        kittyButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //Play button
        playButton = new JButton();
        playButton.addActionListener(this);
        playButton.setText("Play");
        playButton.setFont(new Font("Bauhaus 93",Font.PLAIN,30));
        playButton.setForeground(Color.RED);
        playButton.setBounds(150,100,100,50);
        playButton.setBackground(Color.BLACK);
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        playButton.setFocusPainted(false);  //removes the line around the button

        //Exit button
        exitButton = new JButton();
        exitButton.addActionListener(this);
        exitButton.setText("Exit");
        exitButton.setFont(new Font("Bauhaus 93",Font.PLAIN,30));
        exitButton.setForeground(Color.RED);
        exitButton.setBounds(150,150,100,50);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        exitButton.setFocusPainted(false);

        this.setLayout(null);
        this.setVisible(true);
        this.add(kittyButton);
        this.add(titleLabel);
        this.add(playButton);
        this.add(exitButton);
        this.add(playerSlider);
        this.repaint();
    }

    /**
     * Overriding action performed to bind actions to the buttons
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exitButton){
            System.exit(0);
        }
        else if(e.getSource() == playButton){
            int players = playerSlider.getValue();
            new GameFrame(players);
            this.dispose();
        }
        else if(e.getSource() == kittyButton){
            new HelpFrame();
        }

    }
}