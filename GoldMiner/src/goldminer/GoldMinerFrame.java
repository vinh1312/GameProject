/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldminer;

import static goldminer.PanelGame.round;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class frame main program
 *
 * @author Group 3
 */
public class GoldMinerFrame extends javax.swing.JFrame implements Runnable {

    private int width;                      // width of screen
    private int height;                     // height of screen

    private final int timeEnd = 3000;       // time wait when game over
    private final int timeLoadPanel = 10;   // time reload panel
    private final int timeSecond = 1000;    // 1 second
    private final int timeRound = 120;      // time 1 basic round

    PanelGame gamePanel;                    // create PanelGame
    PanelMenu menu;                         // create PanelMenu
    Thread thread;                          // create thread
    static User user = new User();          // create user
    static String name = "";                // name of user
    static ArrayList<User> lUser = new ArrayList<>();
    private static String T_FILE = "src/data/player.txt"; //file player = user
    private static int numberOfUser = 0;

    public static boolean isExitGame = false;
    public static boolean isRunGame = false;
    public static boolean isStopGame = true;
    public static boolean isEndgame = false;
    public static int secondGame = 120;

    public GoldMinerFrame() {
        initComponents();
        try {
            loadUser();// load dữ liệu của người chơi cũ
        } catch (IOException ex) {
            Logger.getLogger(GoldMinerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(getToolkit().getDefaultToolkit().getImage(getClass().getResource("/img/708.jpg")));// cập nhật ảnh của game
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// lấy size màn hình
        width = 1366;
        height = 768;
        this.setTitle("Gold Miner");// cập nhập tựa đề game
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);// cập nhật full màn hình
        addKeyListener(new handler());
        // setFocusable true được sử dụng để keyListener có thể hoạt động khi repaint jPanel
        setFocusable(true);

        menu = new PanelMenu(width, height);

        panelMain.setLayout(new BorderLayout());
        panelMain.add(menu, BorderLayout.CENTER);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {

            // xử lý (dispose) jframe để giải phóng các tài nguyên dư thừa khi kết thúc game
            if (isExitGame) {
                this.dispose();
                System.exit(0);
            }

            // khi bắt đầu trò chơi, set lại toàn bộ dữ liệu
            if (isRunGame) {
                panelMain.remove(0);
                gamePanel = new PanelGame(width, height, user);
                panelMain.setLayout(new BorderLayout());
                panelMain.add(gamePanel, BorderLayout.CENTER);
                panelMain.revalidate();
                panelMain.repaint();
                isRunGame = false;
                isStopGame = false;
                try {
                    thread.sleep(timeLoadPanel);
                } catch (InterruptedException e) {
                }
                // khi trò chơi kết thúc
            } else {
                if (!isStopGame) {
                    secondGame--;// giảm dần thời gian về 0 khi chơi
                    // countdown time out
                    if (secondGame == 0) {// kiểm tra khi hết giờ
                        // when game over
                        if (user.getGrade() <= round * 500) {// nếu hết giờ mà điểm không đạt thì end game
                            secondGame = timeRound; //timeRound = 120
                            isStopGame = true;
                            PanelGame.isStop = true;
                            try {
                                thread.sleep(timeEnd); //timeEnd = 3000
                            } catch (InterruptedException e) {
                            }
                            PanelGame.isStop = false;
                            numberOfUser++;
                            lUser.add(user);
                            try {
                                saveUser(); // đưa dữ liệu người vừa chơi vào file
                            } catch (IOException ex) {
                                Logger.getLogger(GoldMinerFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            user.setName("");
                            user.setGrade(0);
                            user.setRound(0);
                            panelMain.remove(0);
                            menu.revalidate();
                            menu.repaint();
                            panelMain.setLayout(new BorderLayout());
                            panelMain.add(menu, BorderLayout.CENTER);
                            panelMain.revalidate();
                            panelMain.repaint();
                            // when pass round
                        } else {                  // nếu điểm đạt yêu cầu thì thời gian giảm đi 5s và tiếp tục trò chơi
                            secondGame = timeRound - ((round) * 5);
                            round++;
                            user.setRound(round);
                            gamePanel.loadMap();
                            PanelGame.isRunLine = true;
                            PanelGame.isWireTurn = true;
                        }
                    } else if (isEndgame) { // khi người chơi chủ động kết thúc game
                        secondGame = timeRound; //timeRound = 120
                        isStopGame = true;
                        isEndgame = false; //set giá trị lại khi chọn stop game
                        PanelGame.isEnd = true;
                        try {
                            thread.sleep(timeEnd); //timeEnd = 3000
                        } catch (InterruptedException e) {
                        }
                        PanelGame.isEnd = false;
                        numberOfUser++;
                        lUser.add(user);
                        try {
                            saveUser(); // đưa dữ liệu người vừa chơi vào file
                        } catch (IOException ex) {
                            Logger.getLogger(GoldMinerFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        user.setName("");
                        user.setGrade(0);
                        user.setRound(0);
                        panelMain.remove(0);
                        menu.revalidate();
                        menu.repaint();
                        panelMain.setLayout(new BorderLayout());
                        panelMain.add(menu, BorderLayout.CENTER);
                        panelMain.revalidate();
                        panelMain.repaint();
                    }
                }
                try {
                    thread.sleep(timeSecond);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private class handler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {// nghe lệnh khi nhấn space
            if (PanelGame.isRunLine) {
                if (PanelGame.isWireTurn) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        PanelGame.isWireTurn = false;
                        PanelGame.isPlaying = !PanelGame.isPlaying;
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    /**
     * get list top 3 rank
     *
     * @return list top 3 rank
     */
    public static ArrayList<User> topRank() {
        ArrayList<User> rank = new ArrayList<>();
        lUser.clear();
        try {
            loadUser();
        } catch (IOException ex) {
            Logger.getLogger(GoldMinerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!lUser.isEmpty()) {// nếu danh sách không trống thì sẽ in ra 3 đứa cao nhất trong danh sách
            Collections.sort(lUser, new Compare()); // sắp xếp lại rồi mới in
            if (lUser.size() > 0) {// nếu danh sách lớn hơn 0 tức là chỉ có 1 đứa thì lấy đứa đó
                rank.add(lUser.get(0));
            } else {// không có thì in tạm user 1
                rank.add(new User("User 1", 0, 0));
            }
            if (lUser.size() > 1) {// sau khi in xong đứa ở index 0 thì tiếp tục in đứa ở index 1
                rank.add(lUser.get(1));
            } else {// không có thì in tạm user 2
                rank.add(new User("User 2", 0, 0));
            }
            if (lUser.size() > 2) { // sau khi in xong đứa ở index 1 thì tiếp tục in đứa ở index 2
                rank.add(lUser.get(2));
            } else {// không có thì in tạm user 3
                rank.add(new User("User 3", 0, 0));
            }
        } else {// nếu danh sách trống thì add 3 thông tin trống để giữ chỗ
            rank.add(new User("User 1", 0, 0));
            rank.add(new User("User 2", 0, 0));
            rank.add(new User("User 3", 0, 0));
        }
        return rank;
    }

    /**
     * string about by html
     *
     * @return string about
     */
    public static String getStringAbout() {
        String s = "<html><h1 align=\"center\">Gold Miner V0.1</h1>"
                + "<div style=\"margin-left: 20px;\">"
                + "<p>This is a classic gold mining game that is remade according to the concept "
                + "of the game \"Gold Miner\" on the website <a href=\"https://codecanyon.net/search/gold\">codecanyon.net</a>.</p>"
                + "<p>All images in the game are taken and edited from <a href=\"https://codecanyon.net/search/gold\">codecanyon.net</a> "
                + "and some other websites. All code in the game is coded by the development team.</p>"
                + "</div>"
                + "<div style=\"margin-left: 40px;\">"
                + "<h3>Rule:</h3><ul type=\"disc\"><li>The game will automatically change the map "
                + "if you pass the specified number of points in each round. The specified number of points "
                + "for each round will be (round * 500).</li><li>After each round, the anchor's rotation "
                + "speed will increase and the time will be reduced by 10 seconds.</li></ul>"
                + "</div>"
                + "<div style=\"margin-left: 40px;\">"
                + "<h3>How to play:</h3><ul type=\"disc\"><li>Click \"Start\" to start playing.</li><li>Click \"About\" to view information about the game."
                + "</li><li>Click \"Exit\" to exit the game.</li><li>Click \"Space\" to release the gold hook.</li></ul>"
                + "</div>"
                + "<div style=\"margin-left: 40px;\">"
                + "<h3>General information:</h3><ul type=\"disc\"><li>FPT University - IA1701.</li>"
                + "<li>Semester: Summer 2023.</li><li>Subject: Data Structures and Algorithms (CSD201).</li></ul>"
                + "</div>"
                + "<div style=\"margin-left: 40px;\">"
                + "<h3>Development team:</h3><p><b>Mentor: </b>Vo Hoang Tu.</p><p><b>Members of the group:</b></p><ul type=\"circle\">"
                + "<li>CE170144 - Nguyen Xuan Quang Vinh.</li><li>CE170109 - Nguyen Thuong Tin."
                + "</li><li>CE170637 - Mai Hoang Kha.</li><li>CE171875 - Tran Truong Tuan Vy."
                + "</li><li>CE171573 - Phan Huynh Anh Thu.</li><li>CE170360 - Nguyen Van Manh Truong.</li></ul>"
                + "</div>"
                + "<h4>If you have copyright "
                + "issues or game suggestions, please contact email:</h4><a href=\"VinhNXQCE170144@fpt.edu.vn\">"
                + "VinhNXQCE170144@fpt.edu.vn</a></html>";
        return s;
    }

    /**
     * Save word into user.txt
     *
     * @throws IOException Signals that an I/O exception of some sort has
     * occurred. This class is the general class of exceptions produced by
     * failed or interrupted I/O operations. Reading a network file and
     * disconnected. Reading a local file is no more. Use some thread to read
     * data and some other process closed thread. Trying to read/write file but
     * not allowed. Trying to write to a file, but disk space is no longer
     * available.
     */
    public static void saveUser() throws IOException {
        try (FileWriter fw = new FileWriter(new File(T_FILE), false) //Overwrite data files
                ) {
            fw.append(String.valueOf(numberOfUser) + "\n"); //Writes number of question

            String name;
            int grade;
            int round;

            for (User u : lUser) {
                name = u.getName();
                grade = u.getGrade();
                round = u.getRound();

                fw.append(name + "\n");
                fw.append(grade + "\n");
                fw.append(round + "\n");
            }

        } finally {

        }
    }

    /**
     * Load word into user.txt
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
    public static void loadUser() throws IOException {
        File qFile = new File(T_FILE);
        if (!qFile.exists()) { //Checks is file created
            qFile.createNewFile(); //If not, creates new file
            numberOfUser = 0; //New data file with the number of question is 0
        } else {
            //If file is existed, so loading this data file
            BufferedReader br = new BufferedReader(new FileReader(T_FILE)); //Loads text file into buffer
            try {
                String name;
                int grade;
                int round;

                numberOfUser = Integer.parseInt(br.readLine()); //Reads number of word
                for (int i = 0; i < numberOfUser; i++) {
                    //Reads word's information
                    name = br.readLine();
                    grade = Integer.parseInt(br.readLine());
                    round = Integer.parseInt(br.readLine());

                    //Create new instance
                    lUser.add(new User(name, grade, round));
                }
            } catch (IOException | NumberFormatException e) {
                br.close();
            }

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMain = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelMainLayout = new javax.swing.GroupLayout(panelMain);
        panelMain.setLayout(panelMainLayout);
        panelMainLayout.setHorizontalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        panelMainLayout.setVerticalGroup(
            panelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 485, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GoldMinerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GoldMinerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GoldMinerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GoldMinerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoldMinerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMain;
    // End of variables declaration//GEN-END:variables
}
