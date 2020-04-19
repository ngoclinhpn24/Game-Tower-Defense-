package GameTowerDefense.enemy;

import GameTowerDefense.Value;
import GameTowerDefense.background.Screen;

import java.awt.*;

public abstract class Enemy extends Rectangle {
    // Quân địch
    public int reward;
    public int xC, yC;
    public int health, firstHealth; // thanh máu của enemy
    // healthSpace: khoảng cách từ enemy đến thanh máu,healthHeight: chiều cao thanh máu
    public int healthSpace = 3, healthHeight = 8;
    public int enemySize = 52;
    public int enemyWalk = 0;
    public int upward = 0, downward = 1, right = 2, left = 3;
    public int direction = right;
    public int enemyID = Value.enemyAir;

    public boolean inGame = false;
    public boolean hasUpward = false;
    public boolean hasDownward = false;
    public boolean hasRight = false;
    public boolean hasLeft = false;

    public int speed=1;

    // walkSpeed: tốc độ di chuyển của enemy
    // walkSpeed càng nhỏ tốc độ di chuyển càng nhanh
    public int walkFrame = 0, walkSpeed = 30;//40


    public Enemy(){
        //this.speed=1;
    }
    public abstract void setHealth();
    // health comment là health bên enemy
    // điểm xuất phát
//    public void spawnEnemy(int enemyID){
//        for(int y = 0; y < Screen.room.block.length; y++){
//            if(Screen.room.block[y][0].groundID == Value.groundRoad){
//                setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, enemySize, enemySize);
//                xC = 0;
//                yC = y;
//            }
//        }
//
//        //setBounds(10, 10, 100, 100);
//
//        this.enemyID = enemyID;
//        this.health = enemySize; // thanh máu của enemy = kích thước của enemy
//
//        inGame = true;
//
//    }

    // quân địch biến mất
    public void deleteEnemy(){
        inGame = false;
        // khi enemy trước bị xóa thì những enemy sau vẫn đi đúng đường
        // tránh bị lệch đường
        direction = right;
        enemyWalk = 0;
    }

    // Mạng sống sẽ bị trừ điểm khi quân địch về đích
    public void looseHealth(){
        Screen.player.downHealth();
    }



    // chuyển động của enemy
    public void physic(){
        if(walkFrame >= walkSpeed){
            if(direction == right){
                x += speed;
            }else if(direction ==  upward){
                y -= speed;
            }else if(direction == downward){
                y += speed;
            }else if(direction == left){
                x -= speed;
            }

            enemyWalk += speed;

            if(enemyWalk == Screen.room.blockSize){
                if(direction == right){
                    xC += speed;
                    hasRight = true;
                }else if(direction ==  upward){
                    yC -= speed;
                    hasUpward = true;
                }else if(direction == downward){
                    yC += speed;
                    hasDownward = true;
                }else if(direction == left){
                    xC -= speed;
                    hasLeft = true;
                }

                if(!hasUpward){
                    try{
                        if(Screen.room.block[yC+speed][xC].groundID == Value.groundRoad){
                            direction = downward;
                        }
                    } catch(Exception e){ }
                }

                if(!hasDownward){
                    try{
                        if(Screen.room.block[yC-speed][xC].groundID == Value.groundRoad){
                            direction = upward;
                        }

                    } catch(Exception e){ }
                }
                // từ 83-90: if(!hasRight): rẽ phải
                if(!hasLeft){
                    try{
                        if(Screen.room.block[yC][xC+speed].groundID == Value.groundRoad){
                            direction = right;
                        }

                    } catch(Exception e){ }
                }

                if(!hasRight){
                    try{
                        if(Screen.room.block[yC][xC-speed].groundID == Value.groundRoad){
                            direction = left;
                        }

                    } catch(Exception e){ }
                }
// đã đi đến đích
                if(Screen.room.block[yC][xC].airID == Value.airCave){
                    deleteEnemy();
                    looseHealth();
                }

                // hasUpward = false; hasDownward = false;: lên đường dọc lần 2
                hasUpward = false;
                hasDownward = false;
                hasLeft = false;
                hasRight = false;
                enemyWalk = 0;
            }
            walkFrame = 0; // rẽ xuống đường dọc lần 1
        }else{
            walkFrame += 1;
        }
    }

    // mất 1 lượng máu ở thanh máu
    public void loseHealth(int amo){
        health -= amo;

        checkDeath();
    }

    public void checkDeath(){
        if(health <= 0){
            deleteEnemy();
        }
    }

    public boolean isDead(){
        if(inGame){
            return false;
        }else{
            return true;
        }
    }

    public void draw(Graphics g){
        g.drawImage(Screen.enemy[enemyID], x, y, width, height,null);

        // màu của thanh máu khi bị bắn
        g.setColor(new Color(255, 96, 57)); // màu thanh máu
        g.fillRect(x, y -(healthSpace + healthHeight), width, healthHeight);

        // vẽ ra thanh máu ban đầu của enemy
        g.setColor(new Color(50, 100, 50)); // màu thanh máu
        g.fillRect(x, y -(healthSpace + healthHeight), (int)((double)width*((double)health/(double)firstHealth)), healthHeight); // điền màu vào thanh máu

        // vẽ viền đen của thanh máu
        g.setColor(new Color(0, 0, 0));
        g.drawRect(x, y -(healthSpace + healthHeight), width*(health/firstHealth), healthHeight -1);

    }
}
