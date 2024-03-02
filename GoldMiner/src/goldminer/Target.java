/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Class create target
 *
 * @author Group 3
 */
public class Target {

    private ArrayList<Point> src = new ArrayList<>();   // List point check match
    private int level;                                  // level of target
    private int heightOfTarget;                         // height of target
    private int grade;                                  // grade of target
    private int x;                                      // coordinates x of target to draw image
    private int y;                                      // coordinates y of target to draw image
    private String type;                                // type of target

    /**
     * constructor create target
     *
     * @param c Point coordinates x, y of target to draw image
     * @param level Integer level of target only gold has lv 2, remaining lv 1
     * @param type String type of target
     */
    public Target(Point c, int level, String type) {
        this.type = type;
        x = c.x;
        y = c.y;

        // type equal gold lv 1, heightOfTarget equal 70
        // type equal gold lv 2, heightOfTarget equal 200
        // type equal diamond lv 1, heightOfTarget equal 20
        // type equal stone lv 1, heightOfTarget equal 50
        // type equal bomd lv 1, heightOfTarget equal 30
        // type equal bone lv 1, heightOfTarget equal 30
        // remaining heightOfTarget equal 0
        if (type.equalsIgnoreCase("Gold")) {
            if (level == 1) {
                heightOfTarget = 70;
            } else if (level == 2) {
                heightOfTarget = 200;
            }
        } else if (type.equalsIgnoreCase("Diamond")) {
            heightOfTarget = 20;
        } else if (type.equalsIgnoreCase("Stone")) {
            heightOfTarget = 50;
        } else if (type.equalsIgnoreCase("Bomb")) {
            heightOfTarget = 30;
        } else if (type.equalsIgnoreCase("Bone")) {
            heightOfTarget = 30;
        } else {
            heightOfTarget = 0;
        }
        this.level = level;

        //create 3 left, right and top coordinate edges around the target to check if it hit the target
        // top
        for (int i = 0; i < heightOfTarget; i++) {
            src.add(new Point(c.x + i, c.y));
        }
        // left
        for (int i = 0; i < heightOfTarget; i++) {
            src.add(new Point(c.x, c.y + i));
        }
        // right
        for (int i = 0; i < heightOfTarget; i++) {
            src.add(new Point(c.x + heightOfTarget, c.y + i));
        }
    }

    /**
     * get grade of target. Diamond is 200, Stone 10, Bomb -50, Bone 0, Gold lv1
     * 50, Gold lv2 100
     *
     * @return grade of target
     */
    public int getGrade() {
        if (type.equalsIgnoreCase("Diamond")) {
            grade = 200;
        } else if (type.equalsIgnoreCase("Stone")) {
            grade = 10;
        } else if (type.equalsIgnoreCase("Bomb")) {
            grade = -50;
        } else if (type.equalsIgnoreCase("Bone")) {
            grade = 0;
        } else {
            grade = level * 50;
        }
        return grade;
    }

    /**
     * get level of target
     *
     * @return level of target
     */
    public int getLevel() {
        return level;
    }

    /**
     * get height of target
     *
     * @return height of target
     */
    public int getHeightOfTarget() {
        return heightOfTarget;
    }

    /**
     * get coordinates x
     *
     * @return coordinates x
     */
    public int getX() {
        return x;
    }

    /**
     * get coordinates y
     *
     * @return coordinates y
     */
    public int getY() {
        return y;
    }

    /**
     * get list point of target
     *
     * @return list point of target
     */
    public ArrayList<Point> getSrc() {
        return src;
    }

    /**
     * get type of target
     *
     * @return type of target
     */
    public String getType() {
        return type;
    }
}
