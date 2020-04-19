package GameTowerDefense.enemy;

import GameTowerDefense.Value;

public class NormalEnemy extends Enemy {

    public NormalEnemy(){
        this.health = 300;
        firstHealth = 300;
        this.walkSpeed=10;
        this.reward = 5;
    }
    @Override
    public void setHealth() {
        this.health = 52;
        this.firstHealth = 52;
    }


}
