/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

/**
 * Class create user
 *
 * @author Group 3
 */
public class User {

    private String name;    // name of user
    private int grade;      // grade of user
    private int round;      // round of user

    /**
     * constructor default
     */
    public User() {
    }

    /**
     * Constructor create with name
     *
     * @param name name of user
     */
    public User(String name) {
        this.name = name;
        this.grade = 0;
        this.round = 0;
    }

    /**
     * Constructor add new user
     *
     * @param name name of user
     * @param grade grade of user
     * @param round round of user
     */
    public User(String name, int grade, int round) {
        this.name = name;
        this.grade = grade;
        this.round = round;
    }

    /**
     * get round of user
     *
     * @return round of user
     */
    public int getRound() {
        return round;
    }

    /**
     * set round of user
     *
     * @param round round of user
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * get name of user
     *
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * set name of user
     *
     * @param name name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get grade of user
     *
     * @return grade of user
     */
    public int getGrade() {
        return grade;
    }

    /**
     * set grade of user
     *
     * @param grade grade of user
     */
    public void setGrade(int grade) {
        this.grade = grade;
    }
}
