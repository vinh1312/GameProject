/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class management map
 *
 * @author Group 3
 */
public class MapManagement {

    private static String FILE = "";    // path file load map
    private static int numberOfTarget;  // number of target of map
    private static ArrayList<TargetOfMap> map;  // list target of map

    /**
     * get list target of map
     *
     * @return list target of map
     */
    public ArrayList<TargetOfMap> getMap() {
        getMapOrder();
        try {
            loadMap();
        } catch (IOException ex) {
            Logger.getLogger(MapManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    /**
     * constructor create MapManagement
     */
    public MapManagement() {
        map = new ArrayList<>(); // create list map
        getMapOrder();           // load map from file
        try {
            loadMap();  // load file map
        } catch (IOException ex) {
            Logger.getLogger(MapManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * random map, function must run before function load map
     */
    public static void getMapOrder() {
        int order = randomNumber(0, 9);
        FILE = "";
        switch (order) {
            case 1:
                FILE = "src/map/map01.txt";
                break;
            case 2:
                FILE = "src/map/map02.txt";
                break;
            case 3:
                FILE = "src/map/map03.txt";
                break;
            case 4:
                FILE = "src/map/map04.txt";
                break;
            case 5:
                FILE = "src/map/map05.txt";
                break;
            case 6:
                FILE = "src/map/map00.txt";
                break;
            case 7:
                FILE = "src/map/map07.txt";
                break;
            case 8:
                FILE = "src/map/map08.txt";
                break;
            case 9:
                FILE = "src/map/map09.txt";
                break;
            default:
                FILE = "src/map/map00.txt";
                break;
        }
    }

    /**
     * random number from m to n
     *
     * @param m from m
     * @param n to n
     * @return random number from m to n
     */
    public static int randomNumber(int m, int n) {
        Random random = new Random();
        return random.nextInt(n - m + 1) + m;
    }

    /**
     * Load word into map.txt
     *
     * @throws IOException Thrown to indicate that the application has attempted
     * to convert a string to one of the numeric types, but that the string does
     * not have the appropriate format. Signals that an I/O exception of some
     * sort has occurred. This class is the general class of exceptions produced
     * by failed or interrupted I/O operations. Reading a network file and
     * disconnected. Reading a local file is no more. Use some thread to read
     * data and some other process closed thread. Trying to read/write file but
     * not allowed. Trying to write to a file, but disk space is no longer
     * available.
     */
    public static void loadMap() throws IOException {
        File qFile = new File(FILE);
        if (!qFile.exists()) { //Checks is file created
            qFile.createNewFile(); //If not, creates new file
            numberOfTarget = 0; //New data file with the number of question is 0
        } else {
            //If file is existed, so loading this data file
            BufferedReader br = new BufferedReader(new FileReader(FILE)); //Loads text file into buffer
            try {
                double x;
                double y;
                int level;
                String type;

                numberOfTarget = Integer.parseInt(br.readLine()); //Reads number of 
                for (int i = 0; i < numberOfTarget; i++) {
                    type = br.readLine();
                    level = Integer.parseInt(br.readLine());
                    x = Double.parseDouble(br.readLine());
                    y = Double.parseDouble(br.readLine());

                    map.add(new TargetOfMap(x, y, type, level));
                }
            } catch (IOException | NumberFormatException e) {
                br.close();
            }
        }
    }
}
