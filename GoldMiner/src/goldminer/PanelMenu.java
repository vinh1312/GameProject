/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class panel contain menu
 *
 * @author Group 3
 */
public class PanelMenu extends JPanel {

    private int width;  // width of screen
    private int height; // height of screen

    /**
     * constructor create 3 button start, about and exit
     *
     * @param width width of screen
     * @param height height of screen
     */
    public PanelMenu(int width, int height) {
        this.width = width;
        this.height = height;
        Controller c = new Controller(this);
        DataImage.loadImage();
        this.setLayout(null);
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(c);
        btnStart.setBounds((int) (width * 0.15) + 37, (int) (height * 0.36) + 80, (int) (width * 0.1), (int) (height * 0.07));
        JButton btnAbout = new JButton("About");
        btnAbout.addActionListener(c);
        btnAbout.setBounds((int) (width * 0.15) + 173, (int) (height * 0.36) + 130, (int) (width * 0.1), (int) (height * 0.07));
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(c);
        btnExit.setBounds((int) (width * 0.15) + 37, (int) (height * 0.36) + 180, (int) (width * 0.1), (int) (height * 0.07));
        this.add(btnStart);
        this.add(btnAbout);
        this.add(btnExit);
    }

    @Override
    public void paint(Graphics g) {
        // paint image for panel menu
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(DataImage.backgound_menu, 0, 0, width, height, null);
        g.drawImage(DataImage.menu, (int) (width * 0.15), (int) (height * 0.46), (int) (width * 0.2196), (int) (height * 0.2956), null);
        g.drawImage(DataImage.rank, (int) (width * 0.75), (int) (height * 0.35), (int) (width * 0.2196), (int) (height * 0.4245), null);
        g.drawImage(DataImage.rank_1, (int) (width * 0.75) + 35, (int) (height * 0.35) + 50, (int) (width * 0.0205), (int) (height * 0.048), null);
        g.drawImage(DataImage.rank_2, (int) (width * 0.75) + 35, (int) (height * 0.35) + 110, (int) (width * 0.0205), (int) (height * 0.048), null);
        g.drawImage(DataImage.rank_3, (int) (width * 0.75) + 35, (int) (height * 0.35) + 170, (int) (width * 0.0205), (int) (height * 0.048), null);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20.0f));
        // hiển thị thông tin điểm của 3 người cao nhất trong danh sách
        g.drawString(GoldMinerFrame.topRank().get(0).getName(), (int) (width * 0.75) + 70, (int) (height * 0.35) + 75);
        g.drawString(GoldMinerFrame.topRank().get(0).getGrade() + "", (int) (width * 0.75) + 210, (int) (height * 0.35) + 75);
        g.drawString(GoldMinerFrame.topRank().get(1).getName(), (int) (width * 0.75) + 70, (int) (height * 0.35) + 135);
        g.drawString(GoldMinerFrame.topRank().get(1).getGrade() + "", (int) (width * 0.75) + 210, (int) (height * 0.35) + 135);
        g.drawString(GoldMinerFrame.topRank().get(2).getName(), (int) (width * 0.75) + 70, (int) (height * 0.35) + 195);
        g.drawString(GoldMinerFrame.topRank().get(2).getGrade() + "", (int) (width * 0.75) + 210, (int) (height * 0.35) + 195);
    }
}
