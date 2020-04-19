package GameTowerDefense.enemy;

//import GameTowerDefense.Value;

public class TankerEnemy extends Enemy{

    //public int enemyID = Value.enemyAir;
    public TankerEnemy(){
        this.health = 600;
        firstHealth = 600;
        this.walkSpeed=15;
        this.reward = 10;
    }
    @Override
    public void setHealth() {
        this.health = 70;
        this.firstHealth = 70;
    }


}
