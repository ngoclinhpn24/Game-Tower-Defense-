package GameTowerDefense.enemy;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy() {
        this.health = 600;
        this.firstHealth = 600;
        this.walkSpeed=5;
        this.reward = 8;
    }
    @Override
    public void setHealth() {
        this.health = 52;
        this.firstHealth = 52;
    }

}
