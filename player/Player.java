package GameTowerDefense.player;

public class Player {
    public int coinage = 15, health = 10; // coinage: điểm gốc+ điểm thưởng(nếu có), health: mạng sống

    public boolean isLose(){
        return this.health<1;
    }

    public void downHealth(){
        this.health-=1;
    }

    public void upCoinage(int money){
        this.coinage+=money;
    }

    public void downCoinage(int money){
        this.coinage-=money;
    }
}
