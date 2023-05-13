package GUI.view.menus;

import GUI.view.frames.GameFrame;
import main.com.teamalfa.blindvirologists.agents.Agent;
import main.com.teamalfa.blindvirologists.equipments.Equipment;
import main.com.teamalfa.blindvirologists.turn_handler.Game;
import main.com.teamalfa.blindvirologists.turn_handler.TurnHandler;
import main.com.teamalfa.blindvirologists.virologist.Virologist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class RobPopupMenu extends JPopupMenu {
    public RobPopupMenu(Virologist target) {
        ArrayList<Equipment> eqs = target.getBackpack().getEquipmentPocket().getEquipmentHolder();
        ArrayList<Agent> agents = target.getBackpack().getAgents();

        JMenuItem newMenuItem = new NiceMenuItem();
        newMenuItem.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Virologist actor = TurnHandler.getActiveVirologist();
                actor.robElements(target);
            }
        });
        newMenuItem.setText("Rob elements");
        add(newMenuItem);

        for(var eq: eqs) {
            newMenuItem = new NiceMenuItem();
            newMenuItem.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Virologist actor = TurnHandler.getActiveVirologist();
                    actor.robEquipment(eq);
                }
            });
            newMenuItem.setText(eq.getName());
            add(newMenuItem);
        }

        for(var a: agents) {
            newMenuItem = new NiceMenuItem();
            newMenuItem.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Virologist actor = TurnHandler.getActiveVirologist();
                    actor.robAgent(a, target);
                }
            });
            newMenuItem.setText(a.getName());
            add(newMenuItem);
        }
    }

}
