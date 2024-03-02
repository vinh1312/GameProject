/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.util.Comparator;

/**
 * Class comparator sort list user by grade
 *
 * @author Group 3
 */
public class Compare implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        // compare grade of list user
        // sort ascending
        if (o2.getGrade() == o1.getGrade()) {
            return 0;
        } else if (o2.getGrade() > o1.getGrade()) {
            return 1;
        } else {
            return -1;
        }
    }
}
