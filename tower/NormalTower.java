package GameTowerDefense.tower;

public class NormalTower extends Tower {
    public NormalTower(int x, int y) {
        super(x, y);
        this.towerSquareSize = 90;
        damage = 1;
        speed = 1;

    }
}
