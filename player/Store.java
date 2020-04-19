package GameTowerDefense.player;

import GameTowerDefense.Value;
import GameTowerDefense.background.Screen;

import java.awt.*;

public class Store {
    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 2; // để tạo khoảng cách giữa các button
    public static int awayFromRoom = 27; // 29 tạo khoảng cách với màn xanh Room
    public static int iconSize = 20; // kích thước của Health

    public static int iconSpace = 6; // khoảng cách từ số 100, 10 đến biểu tượng
    public static int iconTextY = 15;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID = -1;

    public static int[] buttonID = {Value.airTowerLaser[0], Value.airTowerLaser[1], Value.airTowerLaser[2], Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airTrashCan}; // chứa các hình trong nút bên dưới
    public static int[] buttonPrice = {10, 20, 30, 0, 0, 0, 0, 0}; // giá tiền của tháp

    // vẽ nút ở dưới sân chơi
    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth; // Mạng sống
    public Rectangle buttonCoins; // tiền được thưởng

    public boolean holdsItem = false;
    public Store(){
        define();
    }

    // Sử dụng con trỏ chuột để lấy tower và xóa khi cho vào thùng rác
    public void click(int mouseButton){
        if(mouseButton == 1){
            for(int i=0; i < button.length; i++){
                if(button[i].contains(Screen.mse)){
                    if(buttonID[i] != Value.airAir){
                        if(buttonID[i] == Value.airTrashCan){ // lấy tower va xóa nếu cần
                            holdsItem = false;
                        }else{
                            heldID = buttonID[i];
                            realID = i;
                            holdsItem = true;
                        }
                    }
                }
            }

            // đặt tháp lên trên nền xanh
            if(holdsItem){
                if(Screen.player.coinage >= buttonPrice[realID]){
                    for(int y=0; y < Screen.room.block.length; y++){
                        for(int x =0; x < Screen.room.block[0].length; x++){ // phải có block[0] để có thể đặt tháp ở cả gần chỗ về đích nữa
                            if(Screen.room.block[y][x].contains(Screen.mse)){ // sử dụng con trỏ chuột để di chuyển tháp
                                if(Screen.room.block[y][x].groundID != Value.groundRoad && Screen.room.block[y][x].airID == Value.airAir){
                                    Screen.room.block[y][x].airID = heldID;
                                    Screen.player.downCoinage(buttonPrice[realID]);// -= buttonPrice[realID];// mỗi lần đặt tháp sẽ bị trừ coinage = giá tiền của tháp
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private void define() {
        for(int i = 0 ; i < button.length; i++){
            // ve nut hinh chu nhat
            button[i] = new Rectangle((Screen.myWidth/2) -((shopWidth*(buttonSize + cellSpace))/2) + ((buttonSize + cellSpace)*i),
                    Screen.room.block[Screen.room.worldHeight -1][0].y + Screen.room.blockSize + awayFromRoom, buttonSize, buttonSize);
            // y :...+Screen.room.blockSize + awayFromRoom: để nút button xuống dưới màn xanh
        }
        buttonHealth = new Rectangle(Screen.room.block[0][0].x -1, button[0].y, iconSize, iconSize);
        buttonCoins = new Rectangle(Screen.room.block[0][0].x -1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
    }

    // public abstract void fillRect(int x, int y, int width, int height): được sử dụng để điền màu mặc định và độ rộng và chiều cao đã cho vào hình chữ nhật
    // g.drawImage(): để vẽ ra màn hình
    // mse: Point(x, y)
    public void draw(Graphics g){
        for(int i = 0; i < button.length; i++){
            if(button[i].contains(Screen.mse)){
                g.setColor(new Color(255, 255, 255, 100));
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }

            g.drawImage(Screen.res[0], button[i].x, button[i].y, button[i].width, button[i].height, null); // chuyển ô button thành màu trong cell.png
            if(buttonID[i] != Value.airAir){
                g.drawImage(Screen.air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width- (itemIn*2), button[i].height -(itemIn*2), null);// chuyển hình vào những ô dưới cùng
            }
            // hiện giá trị của tháp
            if (buttonPrice[i] > 0){
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("$" + buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn );
            }

        }
        g.drawImage( Screen.res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null); // vẽ 1 hcn tọa đọ x,y, cdai, ccao tương ứng
        g.drawImage(Screen.res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height,null);

        // điền số mạng 100 cho Heart
        g.setFont(new Font("Courier New", Font.BOLD, 14));// font của số 100, 10 hiển thị text trong các font khác nhau, BOLD =1
        g.setColor(new Color(98, 255, 50)); // màu của 100
        g.drawString(""+ Screen.player.health, buttonHealth.x + buttonHealth.width + iconSpace , buttonHealth.y + iconTextY);

        // điền 10 cho coins
        g.drawString(""+ Screen.player.coinage, buttonCoins.x + buttonCoins.width + iconSpace , buttonCoins.y + iconTextY);

        // di chuyển tháp(ở trong button ở dưới) theo chuột
        if(holdsItem){
            g.drawImage(Screen.air[heldID], Screen.mse.x -((button[0].width- (itemIn*2))/2)+ itemIn,
                    Screen.mse.y-((button[0].width- (itemIn*2))/2)+ itemIn,
                    button[0].width- (itemIn*2), button[0].height -(itemIn*2), null);
        }
    }

}
