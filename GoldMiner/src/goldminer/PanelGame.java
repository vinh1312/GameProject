/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class panel contain game
 *
 * @author Group 3
 */
public class PanelGame extends JPanel implements Runnable {

    User user;          // create user
    Thread thread;      // create thread
    MapManagement mapM; // create MapManagemet

    /**
     * Minimum length of wire
     */
    private final int lenghtMinimum = 50;

    /**
     * time turn of wire (time the rope turns again)
     */
    private final int timeTurn = 70;

    /**
     * time drag target lv1
     */
    private final int timeDragTargetlv1 = 5;

    /**
     * time drag target lv2
     */
    private final int timeDragTargetlv2 = 10;

    /**
     * time bomb boom
     */
    private final int timeBoom = 1000;

    private int width;                          // width of screen
    private int height;                         // height of screen
    private int second = 40;                    // 0 - 80 angle of wire
    private int heightOfTarget = 0;             // height of target
    private String type;                        // type of target
    private boolean isTurnWire = true;          // check wire turn
    private boolean isTargetExist = false;      // check match target
    private boolean isDrawLine = true;          // check draw wire 
    private boolean isBoom = false;             // check boom
    private boolean isPicking = true;           // check drag target
    private int level;                          // level of target
    private int length;                         // length of wire
    private double angle;                       // angle of wire
    private int x, y;                           // coordinates x, y

    static int grade;                           // grade of user
    static int round;                           // round of user
    static boolean isStop = false;              // check game stop
    static boolean isEnd = false;               // check game stop by player
    static boolean isRunLine = true;            // check draw more lenght wire
    static boolean isWireTurn = true;           // check wire has turn
    static boolean isPlaying = true;            // check play game

    private ArrayList<Target> target;           // list target
    private ArrayList<TargetOfMap> map;         // list target of map

    /**
     * constructor
     *
     * @param width width of screen
     * @param height height of screen
     * @param user user
     */
    public PanelGame(int width, int height, User user) {
        ContronllerStop cs = new ContronllerStop(this);
        DataImage.loadImage();
        this.user = user;
        this.width = width;
        this.height = height;
        grade = 0;
        round = 1;
        length = lenghtMinimum;
        target = new ArrayList<>();
        mapM = new MapManagement();
        thread = new Thread(this);
        thread.start();
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(cs);
        btnStop.setBounds((width - 50) / 2, 0, 50, 50);
        this.add(btnStop);

        loadMap(); //dòng 331
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        // vẽ nền bầu trời
        g.drawImage(DataImage.background_Sky, 0, 0, width, height / 5, null);

        // vẽ nền mặt đất
        g.drawImage(DataImage.background_Land, 0, height / 5, width, height * 4 / 5, null);

        // vẽ miner
        g.drawImage(DataImage.miner, width / 2 - 33, height / 5 - 65, (int) (width * 0.051245), (int) (height * 0.0846354), null);

        //vẽ nút stop game
        g.drawImage(DataImage.stop_game, (width - 50) / 2, 0, 50, 50, null);

        g.setColor(Color.YELLOW); // vẽ thông tin như tên, điểm, vòng của người chơi
        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString("Name  : " + user.getName(), 10, 30);
        g.drawString("Grades: " + user.getGrade(), 10, 60);
        g.drawString("Round : " + user.getRound(), 10, 90);

        // Second hand's angle in Radian (Góc quay của dây theo Radian, tối đa quay 80 độ) all of them rely on "second"
        angle = Math.toRadians(second * 2 + 100);

        // Position of the second hand
        // with length 210 unit
        y = (int) (Math.cos(angle) * length);
        x = (int) (Math.sin(angle) * length);

        g.setColor(Color.BLACK);
        g.drawLine(width / 2 - 5, height / 5 - 20, width / 2 + x, height / 5 - y);

        if (isTargetExist) { // hiển thị thông tin ảnh trước khi gắp
            // draw target not drag
            for (Target t : target) {
                if (!t.getSrc().isEmpty()) {
                    if (t.getType().equalsIgnoreCase("Diamond")) {
                        g.drawImage(DataImage.diamond, t.getX(), t.getY(), (int) (width * 0.013), (int) (height * 0.0195), null);
                    } else if (t.getType().equalsIgnoreCase("Stone")) {
                        g.drawImage(DataImage.stone, t.getX(), t.getY(), (int) (width * 0.035), (int) (height * 0.046875), null);
                    } else if (t.getType().equalsIgnoreCase("Bomb")) {
                        g.drawImage(DataImage.bomb, t.getX(), t.getY(), (int) (width * 0.01977), (int) (height * 0.0677), null);
                        g.drawImage(DataImage.boom_boom_boom, t.getX() + 10, t.getY() - 17, (int) (width * 0.019), (int) (height * 0.03125), null);
                    } else if (t.getType().equalsIgnoreCase("Bone")) {
                        g.drawImage(DataImage.bone, t.getX(), t.getY(), (int) (width * 0.02), (int) (height * 0.03125), null);
                    } else {
                        if (t.getLevel() == 1) {
                            g.drawImage(DataImage.gold_Lv1, t.getX(), t.getY(), (int) (width * 0.05), (int) (height * 0.084), null);
                        } else if (t.getLevel() == 2) {
                            g.drawImage(DataImage.gold_Lv2, t.getX(), t.getY(), (int) (width * 0.1464), (int) (height * 0.2695), null);
                        }
                    }
                }
            }

            // draw target drag
            if (type.equalsIgnoreCase("Diamond")) {
                g.drawImage(DataImage.diamond_drag, width / 2 + x - heightOfTarget / 2, height / 5 - y, (int) (width * 0.01537), (int) (height * 0.016), null);
            } else if (type.equalsIgnoreCase("Stone")) {
                g.drawImage(DataImage.stone_drag, width / 2 + x - 15, height / 5 - y, (int) (width * 0.02269), (int) (height * 0.04557), null);
            } else if (type.equalsIgnoreCase("Bomb")) {
                g.drawImage(DataImage.bomb, width / 2 + x - 15, height / 5 - y, (int) (width * 0.01977), (int) (height * 0.0677), null);
                g.drawImage(DataImage.gold_Lv2_drag, width / 2 + x - 14, height / 5 - y, (int) (width * 0.01464), (int) (height * 0.0208), null);
                g.drawImage(DataImage.boom_boom_boom, width / 2 + x - 4, height / 5 - y - 17, (int) (width * 0.019), (int) (height * 0.03125), null);
            } else if (type.equalsIgnoreCase("Bone")) {
                g.drawImage(DataImage.bone_drag, width / 2 + x - 20, height / 5 - y, (int) (width * 0.0205), (int) (height * 0.035), null);
            } else {
                if (level == 1) {
                    g.drawImage(DataImage.gold_Lv1_drag, width / 2 + x - heightOfTarget / 2, height / 5 - y, (int) (width * 0.05637), (int) (height * 0.0885), null);
                } else if (level == 2) {
                    g.drawImage(DataImage.gold_Lv2, width / 2 + x - heightOfTarget / 2, height / 5 - y, (int) (width * 0.1464), (int) (height * 0.2695), null);
                    g.drawImage(DataImage.gold_Lv2_drag, width / 2 + x - 14, height / 5 - y, (int) (width * 0.01464), (int) (height * 0.0208), null);
                }
            }
        } else {
            // draw tongs
            g.drawImage(DataImage.tongs, width / 2 + x - 14, height / 5 - y, (int) (width * 0.0205), (int) (height * 0.0208), null);

            // draw target when not target drag
            for (Target t : target) {
                if (!t.getSrc().isEmpty()) {
                    if (t.getType().equalsIgnoreCase("Diamond")) {
                        g.drawImage(DataImage.diamond, t.getX(), t.getY(), (int) (width * 0.013), (int) (height * 0.0195), null);
                    } else if (t.getType().equalsIgnoreCase("Stone")) {
                        g.drawImage(DataImage.stone, t.getX(), t.getY(), (int) (width * 0.035), (int) (height * 0.046875), null);
                    } else if (t.getType().equalsIgnoreCase("Bomb")) {
                        g.drawImage(DataImage.bomb, t.getX(), t.getY(), (int) (width * 0.01977), (int) (height * 0.0677), null);
                        g.drawImage(DataImage.boom_boom_boom, t.getX() + 10, t.getY() - 17, (int) (width * 0.019), (int) (height * 0.03125), null);
                    } else if (t.getType().equalsIgnoreCase("Bone")) {
                        g.drawImage(DataImage.bone, t.getX(), t.getY(), (int) (width * 0.02), (int) (height * 0.03125), null);
                    } else {
                        if (t.getLevel() == 1) {
                            g.drawImage(DataImage.gold_Lv1, t.getX(), t.getY(), (int) (width * 0.05), (int) (height * 0.084), null);
                        } else if (t.getLevel() == 2) {
                            g.drawImage(DataImage.gold_Lv2, t.getX(), t.getY(), (int) (width * 0.1464), (int) (height * 0.2695), null);
                        }
                    }
                }
            }
        }

        // draw time
        int minute = GoldMinerFrame.secondGame / 60;
        int second = GoldMinerFrame.secondGame - (minute * 60);
        String src = String.format("%02d : %02d", minute, second);

        g.setColor(Color.YELLOW);
        g.setFont(g.getFont().deriveFont(30.0f));
        g.drawString(src, width - 120, 30);

        // draw boom when boom
        if (isBoom) {
            g.drawImage(DataImage.boom_boom, width / 2 - 30, height / 5 - 60, (int) (width * 0.0403), (int) (height * 0.0403), null);
            isBoom = false;
        }

        // draw string "Game Over" when over game
        if (isStop) {
            g.drawImage(DataImage.over, width / 2 - (int) (width * 0.25), 0, (int) (width * 0.5), (int) (width * 0.5), null);
        }

        //draw string "Stop Game" when player choose stop the game
        if (isEnd) {
            g.drawImage(DataImage.stopGame, width / 2 - (int) (width * 0.25), 0, (int) (width * 0.5), (int) (width * 0.5), null);
        }

    }

    @Override
    public void run() {
        // run program
        while (true) {

            // repaint
            repaint();

            // check for hitting the target when dropping the rope and not checking 
            // when pulling the rope and when hitting the target
            if (isPicking) {
                checkTarget();
            }

            // when drop the wire (thả dây ra)
            if (isPlaying) {
                length = lenghtMinimum;
                // check turn wire R to L, L to R
                if (isTurnWire) {
                    second++;
                } else {
                    second--;
                }

                if (second == 80) {// back lại khi max phạm vi
                    isTurnWire = false;
                } else if (second == 0) {// next tiếp khi min phạm vi
                    isTurnWire = true;
                }

                // increase speed turn when pass turn
                try {
                    thread.sleep(timeTurn - (round * 4));
                } catch (InterruptedException e) {
                }
            } else {
                // when drag the wire (thu dây lại)
                if (isTargetExist) {
                    isPicking = false;
                    isRunLine = false;
                    length -= 1;

                    // when drag wire equal 0
                    // add grade
                    if (length <= 0) {
                        isTargetExist = false;
                        isDrawLine = true;
                        isPlaying = true;
                        isWireTurn = true;
                        isRunLine = true;
                        isPicking = true;
                        level = 1;
                        user.setGrade(user.getGrade() + grade);

                        // draw image boom if drag bomd
                        if (type.equalsIgnoreCase("Bomb")) {
                            isBoom = true;
                            try {
                                thread.sleep(timeBoom);
                            } catch (InterruptedException e) {
                            }
                        }
                    }

                    // target lv2 drag slow lv1
                    if (level == 2) {
                        try {
                            thread.sleep(timeDragTargetlv2);
                        } catch (InterruptedException e) {
                        }
                    } else {
                        try {
                            thread.sleep(timeDragTargetlv1);
                        } catch (InterruptedException e) {
                        }
                    }
                    // when not drag target
                } else {
                    // if wire less than maximun length increase lenght of wire
                    if (isDrawLine) {
                        length += 1;
                        isPicking = true;
                        if (length >= height) {
                            isDrawLine = false;
                            isPlaying = false;
                        }
                        try {
                            thread.sleep(timeDragTargetlv1);
                        } catch (InterruptedException e) {
                        }
                        // if wire greater than maximun length and not drag decrease lenght of wire
                    } else {
                        length -= 1;
                        isPicking = false;
                        if (length <= lenghtMinimum) {
                            isDrawLine = true;
                            isPlaying = true;
                            isWireTurn = true;
                        }
                        try {
                            thread.sleep(timeDragTargetlv1);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }
    }

    /**
     * load new random map
     */
    public void loadMap() {
        map = mapM.getMap();
        target.clear();
        for (TargetOfMap m : map) {
            target.add(new Target(new Point((int) (width * m.getX()), (int) (height * m.getY())), m.getLevel(), m.getType()));
        }
        repaint();
    }

    /**
     * check target match
     */
    public void checkTarget() {
        boolean checkTargetMatch = false;
        for (Target t : target) {
            for (Point point : t.getSrc()) {
                for (int i = -4; i < 10; i++) {
                    if (width / 2 + x + i == point.x && height / 5 - y + 10 == point.y) {
                        heightOfTarget = t.getHeightOfTarget();
                        type = t.getType();
                        level = t.getLevel();
                        grade = t.getGrade();
                        checkTargetMatch = true;
                        isWireTurn = true;
                    }
                }
            }

            // if target match clear target
            if (checkTargetMatch) {
                t.getSrc().clear();
                checkTargetMatch = false;
                isTargetExist = true;
            }
        }
    }
}
