package GameTowerDefense.tower;

public class SniperTower extends Tower {
    public SniperTower(int x, int y) {
        super(x, y);
        this.towerSquareSize = 130;
        damage = 1;
        speed = 0;
    }
}
