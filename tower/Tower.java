package GameTowerDefense.tower;

import GameTowerDefense.Value;
import GameTowerDefense.background.Block;
import GameTowerDefense.background.Screen;

import java.awt.*;

public abstract class Tower  {
    public int shotEnemy = -1;
    public boolean shoting = false; // bắn đạn
    public int x;
    public  int y;
    public int damage;
    public int towerSquareSize;
    public int speed;

    public Tower(int x, int y) {
        this.x=x;
        this.y=y;
    }

}
