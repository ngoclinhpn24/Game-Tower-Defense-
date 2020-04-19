package GameTowerDefense.enemy;

import GameTowerDefense.Value;

public class BossEnemy extends Enemy {
    public BossEnemy(){
        this.health = 1000;
        this.firstHealth = 1000;
        this.walkSpeed=40;
        this.reward = 20;
    }

    @Override
    public void setHealth() {
        this.health = 100;
        this.firstHealth = 100;
    }

}
