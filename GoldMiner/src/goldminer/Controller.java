/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Class controller 3 button PanelMenu
 *
 * @author Group 3
 */
public class Controller implements ActionListener {

    private PanelMenu menu;

    /**
     * constructor link to menu in class PanelMenu to control button
     *
     * @param menu class PanelMenu
     */
    public Controller(PanelMenu menu) {
        this.menu = menu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // get string name button
        String button = e.getActionCommand();

        // handle the event when the button with the corresponding string is clicked
        if (button.equals("Start")) {
            String name = JOptionPane.showInputDialog("Your name: ");
            if (!name.isEmpty()) {
                GoldMinerFrame.user.setName(name);
                GoldMinerFrame.user.setGrade(0);
                GoldMinerFrame.user.setRound(1);
                GoldMinerFrame.isRunGame = true;
            }
        } else if (button.equals("About")) {
            JOptionPane.showMessageDialog(menu, GoldMinerFrame.getStringAbout(), "About", 1);
        } else if (button.equals("Exit")) {
            GoldMinerFrame.isExitGame = true;
        }
    }
}
