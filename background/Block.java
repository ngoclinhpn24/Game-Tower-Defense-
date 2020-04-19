package GameTowerDefense.background;

import GameTowerDefense.Value;
import GameTowerDefense.enemy.Enemy;
import GameTowerDefense.tower.MachineGunTower;
import GameTowerDefense.tower.NormalTower;
import GameTowerDefense.tower.SniperTower;
import GameTowerDefense.tower.Tower;

import java.awt.*;

public class Block extends Rectangle{
    public Rectangle towerSquare; // vùng bao hcn của tháp cần xét khi va chạm
    //public int towerSquareSize= 130; // kích thước size của vùng bao hcn quanh tháp
    public int groundID;
    public int airID;
    public int loseTime = 100, loseFrame = 0;

//    public int shotEnemy = -1;
//    public boolean shoting = false; // bắn đạn
    Tower tower;

    public Block(int x, int y, int width, int height, int groundID, int airID){

        setBounds(x, y, width, height); // trục x , y , width, height(Thiết lập hcn bao quanh đối tượng)
        this.groundID = groundID;
        this.airID = airID;
        // tower = new Tower(x,y);
        if(airID%3 == 0) {
            tower = new NormalTower(x,y);
        } else if(airID%3 == 1) {
            tower = new SniperTower(x,y);
        } else tower = new MachineGunTower(x,y);

        towerSquare = new Rectangle(x- (tower.towerSquareSize/2), y - (tower.towerSquareSize/2), width + tower.towerSquareSize
                , height + tower.towerSquareSize);

    }

    public void draw(Graphics g){
        g.drawImage(Screen.ground[groundID], x, y, width, height, null);
        if(airID != Value.airAir){
            g.drawImage(Screen.air[airID], x, y, width, height, null);


        }
    }


    // intersects() Kiểm tra va chạm giữa 2 hcn
    // kiểm tra va chạm và bắn đạn
    public void physic(){
        if(tower.shotEnemy != -1 && towerSquare.intersects(Screen.enemies[tower.shotEnemy])){
            tower.shoting = true;
        }else{
            tower.shoting = false;
        }

        if(!tower.shoting){
            if( airID ==Value.airTowerLaser[0] || airID ==Value.airTowerLaser[1] || airID ==Value.airTowerLaser[2]){
                for(int i =0; i < Screen.enemies.length; i++){
                    if(Screen.enemies[i].inGame){

                        if(towerSquare.intersects(Screen.enemies[i])){
                            tower.shoting = true;
                            tower.shotEnemy = i;
                        }
                    }
                }
            }
        }

        // khi enemy bị bắn, lượng máu sẽ bị trừ dần đi
        if(tower.shoting){
            if(loseFrame >= loseTime){
                Screen.enemies[tower.shotEnemy].loseHealth(tower.damage);

                loseFrame = 0;
            }else{
                loseFrame += tower.speed;
            }

            if(Screen.enemies[tower.shotEnemy].isDead()){
                getMoney(Screen.enemies[tower.shotEnemy]);

                tower.shoting = false;
                tower.shotEnemy = -1;

                //Screen.hasWon();
            }
        }
    }

    // Cộng tiền vào coinage khi quân địch chết
    public void getMoney(Enemy e){
        Screen.player.upCoinage(e.reward);
    }

    public void fight(Graphics g){
        // để xác định khoảng vuông mà tháp có thể bắn đến được
        if(Screen.isDebug) {
            if (airID ==Value.airTowerLaser[0] || airID ==Value.airTowerLaser[1] || airID ==Value.airTowerLaser[2]) {
                g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
            }
        }

        // Bắn đạn(tia lazer)
        if(tower.shoting){
            g.setColor(new Color(255, 24, 128));
            g.drawLine(x + (width/2), y +(height/2), Screen.enemies[tower.shotEnemy].x
                            +(Screen.enemies[tower.shotEnemy].width/2),
                    Screen.enemies[tower.shotEnemy].y +(Screen.enemies[tower.shotEnemy].height/2));
        }
    }

}
