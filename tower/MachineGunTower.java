package GameTowerDefense.tower;

public class MachineGunTower extends Tower {
    public MachineGunTower(int x, int y) {
        super(x, y);
        this.towerSquareSize = 90;
        damage = 1;
        speed = 2;
    }
}
