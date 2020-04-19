package GameTowerDefense.background;

import GameTowerDefense.*;
import GameTowerDefense.Frame;
import GameTowerDefense.enemy.*;
import GameTowerDefense.player.Player;
import GameTowerDefense.player.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

public class Screen extends JPanel implements Runnable {
    public Thread thread = new Thread(this); // vòng đời thread

    boolean scene = false;
    JButton startButton = new JButton("START");
    public static Image[] ground = new Image[5];
    public static Image[] air = new Image[5];
    public static Image[] res = new Image[3];
    public static Image[] enemy = new Image[4];

    public static int myWidth, myHeight;
  //  public static int coinage = 10, health = 100; // coinage: điểm gốc+ điểm thưởng(nếu có), health: mạng sống
    // public static int killed = 0, killsToWin = 0, level = 1, maxLevel = 3;
    public static int winTime = 4000, winFrame = 0;

    public static boolean isFirst = true;
    public static boolean isDebug = false; // debug = true thì sẽ hiện hcn bao quanh tháp, = false thì ko hiện
    public static boolean isWin = false;

    public static Point mse = new Point(0, 0);

    public static Room room;
    public static Save save;
    public static Store store;
    public static Spawer spawer;
    public static Player player;

    public static Enemy[] enemies = new Enemy[100];

    //  Phương thức start() sẽ cấp phát tài nguyên cho luồng mới rồi chạy phương thức run() ở luồng này
    public Screen(Frame frame) {
        frame.addMouseListener(new KeyHandel());
        frame.addMouseMotionListener(new KeyHandel());
        thread.start();
    }

//    public static void hasWon() {
//        if (killed == killsToWin) {
//            isWin = true;
//            killed = 0;
//            coinage = 0;
//        }
//    }

    public void define() {
        room = new Room();
        save = new Save();
        store = new Store();
        //spawer= new Spawer();
        player= new Player();
//        for(int i = 0; i < enemies.length; i++){
//            enemies[i] = new Enemy();
//            enemies[i].spawnEnemy(0);
//        }
        for (int i = 0; i < 3; i++) {
            ground[i] = new ImageIcon("res/ground.png").getImage();
            ground[i] = createImage(new FilteredImageSource(ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
        ground[3] = new ImageIcon("res/Tower2.png").getImage();
        ground[4] = new ImageIcon("res/Tower3.png").getImage();

        for (int i = 0; i < 3; i++) {
            air[i] = new ImageIcon("res/air.png").getImage();
            air[i] = createImage(new FilteredImageSource(air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
        air[3] = new ImageIcon("res/Tower2.png").getImage();
        air[4] = new ImageIcon("res/Tower3.png").getImage();

        res[0] = new ImageIcon("res/cell.png").getImage(); // taọ ô màu xám button
        res[1] = new ImageIcon("res/heart.png").getImage(); // tạo trái tim mạng sống
        res[2] = new ImageIcon("res/coin.png").getImage(); // ô nhận tiền thưởng

        enemy[0] = new ImageIcon("res/enemy.png").getImage();
        enemy[1] = new ImageIcon("res/heart.png").getImage();
        enemy[2] = new ImageIcon("res/coin.png").getImage();
        enemy[3] =new ImageIcon("res/pig.png").getImage();


        save.loadSave(new File("save/mission1")); // Tạo đường, road

        // Điều chỉnh enemy vừa với đường đi
        for (int i = 0; i < enemies.length; i++) {
            if(i%4==0) enemies[i] = new NormalEnemy();
            else if(i%4==1) enemies[i]= new SmallerEnemy();
            else if(i%4==2)  enemies[i]= new TankerEnemy();
            else enemies[i] = new BossEnemy();
            //enemies[i].spawnEnemy(0);// giúp enemy xuất hiện trên đường
        }
        spawer= new Spawer();
    }

    public void paintComponent(Graphics g) {
        if (isFirst) {
            myWidth = getWidth();
            myHeight = getHeight();
            define();

            isFirst = false;
        }

        g.setColor(new Color(50, 50, 50)); // thiết lập màu hiện tại của đồ họa thành màu đã cho
        // g.clearRect: nền Screen màu trắng (xóa hết mọi điểm trên screen)
        g.fillRect(0, 0, getWidth(), getHeight()); // Điền màu mặc định(màu đen) và độ rộng, chiều cao đã cho vào hcn
        // vẽ line giữa hai điểm có tọa độ lần lượt là (x1, y1) và (x2, y2)(vẽ đậm màu của ô button lên so với nền )
        g.setColor(new Color(0, 0, 0));
        g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1, room.block[room.worldHeight - 1][0].y - 1);

        room.draw(g); // Drawing the room

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].inGame) {
                enemies[i].draw(g);
            }
        }

        store.draw(g); // Drawing the store

        // Khi hết mạng health
        if (player.isLose()) {
            g.setColor(new Color(255, 20, 20));
            g.fillRect(0, 0, myWidth, myHeight);
           /* g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Courier New", Font.BOLD, 14));
            g.drawString("Game Over, Unlucky...", 10, 20);*/
           g.drawImage(new ImageIcon("res/gameOver.png").getImage(),(myWidth-300)/2,(myHeight-100)/2,300,100,null);
        }

            if(isWin){
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, myWidth, myHeight);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
                g.drawString("YOU WON!", 100, 150);

       }

    }
//
//    // spawnTime: thời gian giữa lần xhien enemy 1 và enemy 2
//    public int spawnTime = 2400, spawnFrame = 0;
//
//    // để mấy giây sau mới xuất hiện enemy
//    public void enemySpawner() {
//        if (spawnFrame >= spawnTime) {
//            for (int i = 0; i < enemies.length; i++) {
//                if (!enemies[i].inGame) {
//                    enemies[i]= spawer.spawnEnemy(enemies[i],Value.enemyGreeny);
//                    break;
//                }
//            }
//            spawnFrame = 0;//  xh liên tiếp enemy
//        } else {
//            spawnFrame += 1;
//        }
//    }

    //phương thức run() để thực thi luồng.
    public void run() {

        while (true) {
            if(!scene ) {

                startButton.setLocation(600,450);
                // okButton.isVisible();
                this.add(startButton);

                //JLabel statusLabel = new JLabel("",JLabel.SOUTH_EAST);
                //statusLabel.setSize(350,100);
                startButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //statusLabel.setText("Ok Button clicked.");
                        scene = true;
                        // okButton.removeNotify();
                    }
                });


            } else {
                if(!isWin) {
                    startButton.setVisible(false);
                    if (!isFirst && player.health > 0 /*&& !isWin*/) {
                        room.physic();
                        spawer.againSpawner(enemies);
                        for (int i = 0; i < enemies.length; i++) {
                            if (enemies[i].inGame) {
                                enemies[i].physic();
                            }
                        }
                    }

                }
                if(player.coinage > 100)  {
                    isWin = true;
                }
            }

//            }else{
//                if(isWin){
//                    if(winFrame >= winTime){
//                        if(level > maxLevel){
//                            System.exit(0);
//                        }else{
//                            level +=1;
//                            save.loadSave(new File("save/mission1")); // Tạo đường, road
//                            isWin = false;
//
//                        }
//
//                        winFrame = 0;
//                    }else{
//                        winFrame += 1;
//                    }
//                }
//            }
            repaint();

            try {
                Thread.sleep(1); // tạm ngưng thread trong 1 khỏang thời gian nhất định
            } catch (Exception e) { }
        }
    }

}
