/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nguyen Xuan Quang Vinh - CE170144
 */
public class ContronllerStop implements ActionListener {

    private PanelGame game;

    public ContronllerStop(PanelGame game) {
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        if (button.equals("Stop")) {
            if (JOptionPane.showConfirmDialog(game, "Do you want to stop the game?", "Stop the game", 0) == JOptionPane.YES_OPTION) {
                GoldMinerFrame.isEndgame = true;
            } else {
                // Tắt panel và tự động nhấn nút "Tab"
                SwingUtilities.invokeLater(() -> {
                    try {
                        Robot robot = new Robot();
                        robot.keyPress(KeyEvent.VK_TAB);
                        robot.keyRelease(KeyEvent.VK_TAB);
                    } catch (AWTException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }
}
