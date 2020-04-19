package GameTowerDefense;

import GameTowerDefense.background.Screen;
import GameTowerDefense.enemy.BossEnemy;
import GameTowerDefense.enemy.Enemy;
import GameTowerDefense.enemy.NormalEnemy;
import GameTowerDefense.enemy.TankerEnemy;

public class Spawer  {


    // spawnTime: thời gian giữa lần xhien enemy 1 và enemy 2
    public int spawnTime = 2400, spawnFrame = 0;

    // để mấy giây sau mới xuất hiện enemy
    public Enemy[] againSpawner(Enemy[] enemies) {
        if (spawnFrame >= spawnTime) {
            for (int i = 0; i < enemies.length; i++) {
                if (!enemies[i].inGame) {
                    if(enemies[i] instanceof NormalEnemy) enemies[i]= this.spawnEnemy(enemies[i],Value.enemyNormal);
                    else if(enemies[i] instanceof TankerEnemy) enemies[i]= this.spawnEnemy(enemies[i],Value.enemyTanker);
                    else if(enemies[i] instanceof BossEnemy) enemies[i]= this.spawnEnemy(enemies[i],Value.enemyBoss);
                    else enemies[i]= this.spawnEnemy(enemies[i],Value.enemyGreeny);
                    break;
                }
            }
            spawnFrame = 0;//  xh liên tiếp enemy
        } else {
            spawnFrame += 1;
        }
        return enemies;
    }



    // điểm xuất phát
    public Enemy spawnEnemy(Enemy a, int enemyID){
        for(int y = 0; y < Screen.room.block.length; y++){
            if(Screen.room.block[y][0].groundID == Value.groundRoad){
                a.setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, a.enemySize, a.enemySize);
                a.xC = 0;
                a.yC = y;
            }
        }

        //setBounds(10, 10, 100, 100);

        a.enemyID = enemyID;
        /*a.health = a.enemySize; // thanh máu của enemy = kích thước của enemy
        if(a instanceof BossEnemy) a.health = 100; else if(a instanceof TankerEnemy) a.health = 70;*/
        a.setHealth();
        a.inGame = true;
        return a;
    }
}
