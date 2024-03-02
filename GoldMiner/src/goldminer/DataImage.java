/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Class load image from file
 *
 * @author Group 3
 */
public class DataImage {

    /**
     * Buffered Image of miner, gold, diamond,...
     */
    public static BufferedImage mainImage;

    /**
     * Buffered Image of medal 1 2 3
     */
    public static BufferedImage rankImage;

    /**
     * Background main panel menu
     */
    public static Image backgound_menu;

    /**
     * Background contain menu Start, About, Exit
     */
    public static Image menu;

    /**
     * Background contain top 3 rank
     */
    public static Image rank;

    /**
     * Medal top 1
     */
    public static Image rank_1;

    /**
     * Medal top 2
     */
    public static Image rank_2;

    /**
     * Medal top 3
     */
    public static Image rank_3;

    /**
     * String name game "Gold Miner"
     */
    public static Image goldMiner;

    /**
     * Image string "Game Over" when over game
     */
    public static Image over;

    /**
     * Background land in panel game
     */
    public static Image background_Land;

    /**
     * Background Sky in panel game
     */
    public static Image background_Sky;

    /**
     * Image Miner
     */
    public static Image miner;

    /**
     * Image tongs
     */
    public static Image tongs;

    /**
     * Image normal gold lv 1
     */
    public static Image gold_Lv1;

    /**
     * Image normal gold lv 2
     */
    public static Image gold_Lv2;

    /**
     * Image gold lv 1 when drag
     */
    public static Image gold_Lv1_drag;

    /**
     * Image gold lv 2 when drag
     */
    public static Image gold_Lv2_drag;

    /**
     * Image normal diamond
     */
    public static Image diamond;

    /**
     * Image diamond when drag
     */
    public static Image diamond_drag;

    /**
     * Image normal stone
     */
    public static Image stone;

    /**
     * Image normal stone when drag
     */
    public static Image stone_drag;

    /**
     * Image normal bomb
     */
    public static Image bomb;

    /**
     * Image normal bomb when boom
     */
    public static Image boom_boom;

    /**
     * Image fire on top of the bomb
     */
    public static Image boom_boom_boom;

    /**
     * Image normal bone
     */
    public static Image bone;

    /**
     * Image bone when drag
     */
    public static Image bone_drag;

    /**
     * Image stop button
     */
    public static Image stop_game;

    /**
     * Image stop message
     */
    public static Image stopGame;
    
    /**
     * load image. Must be call function when want to draw image
     */
    public static void loadImage() {
        try {
            background_Land = ImageIO.read(new File("src/img/309.jpg"));
            background_Sky = ImageIO.read(new File("src/img/241.jpg"));

            over = ImageIO.read(new File("src/img/101.png"));
            backgound_menu = ImageIO.read(new File("src/img/345.jpg"));
            menu = ImageIO.read(new File("src/img/111.png"));
            rank = ImageIO.read(new File("src/img/222.jpg"));

            stop_game = ImageIO.read(new File("src/img/stop.png"));
            stopGame = ImageIO.read(new File("src/img/gameStop.png"));

            mainImage = ImageIO.read(new File("src/img/atlas.png"));
            rankImage = ImageIO.read(new File("src/img/123.png"));

            rank_1 = rankImage.getSubimage(25, 9, 28, 37);
            rank_2 = rankImage.getSubimage(0, 32, 28, 37);
            rank_3 = rankImage.getSubimage(53, 32, 27, 37);

            goldMiner = mainImage.getSubimage(0, 139, 445, 69);

            miner = mainImage.getSubimage(197, 1104, 70, 65);
            tongs = mainImage.getSubimage(987, 759, 28, 16);

            gold_Lv1 = mainImage.getSubimage(655, 1015, 70, 65);
            gold_Lv1_drag = mainImage.getSubimage(725, 1043, 77, 68);
            gold_Lv2 = mainImage.getSubimage(0, 731, 200, 207);
            gold_Lv2_drag = mainImage.getSubimage(987, 759, 20, 16);

            diamond = mainImage.getSubimage(1005, 888, 18, 15);
            diamond_drag = mainImage.getSubimage(985, 850, 21, 22);

            stone = mainImage.getSubimage(396, 302, 48, 36);
            stone_drag = mainImage.getSubimage(902, 229, 31, 35);

            bomb = mainImage.getSubimage(995, 556, 27, 52);
            boom_boom = mainImage.getSubimage(55, 1147, 55, 47);
            boom_boom_boom = mainImage.getSubimage(994, 532, 26, 24);

            bone = mainImage.getSubimage(987, 710, 28, 24);
            bone_drag = mainImage.getSubimage(987, 608, 28, 27);
        } catch (IOException ex) {
            Logger.getLogger(DataImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
