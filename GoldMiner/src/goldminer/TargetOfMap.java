/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

/**
 * Class create target of Map
 *
 * @author Group 3
 */
public class TargetOfMap {

    private double x;
    private double y;
    private String type;
    private int level;

    /**
     * constructor default
     */
    public TargetOfMap() {
    }

    /**
     * constructor create target of map
     *
     * @param x coordinates x of target to draw image
     * @param y coordinates y of target to draw image
     * @param type type of target
     * @param level level of target
     */
    public TargetOfMap(double x, double y, String type, int level) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.level = level;
    }

    /**
     * get coordinates x of target to draw image
     *
     * @return coordinates x of target
     */
    public double getX() {
        return x;
    }

    /**
     * set coordinates x of target to draw image
     *
     * @param x coordinates x of target to draw image
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * get coordinates y of target to draw image
     *
     * @return coordinates y of target
     */
    public double getY() {
        return y;
    }

    /**
     * set coordinates y of target to draw image
     *
     * @param y coordinates y of target
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * get type of target
     *
     * @return type of target
     */
    public String getType() {
        return type;
    }

    /**
     * set type of target
     *
     * @param type type of target
     */
    public void setType(String type) {
        this.type = type;
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
     * set level of target
     *
     * @param level level of target
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
