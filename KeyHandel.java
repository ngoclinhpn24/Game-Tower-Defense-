package GameTowerDefense;

import GameTowerDefense.background.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class KeyHandel implements MouseMotionListener, MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Screen.store.click(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    // Được triệu hồi khi một nút chuột được nhấn trên một thành phần và sau đó được kéo (drag)
    public void mouseDragged(MouseEvent e) {
        Screen.mse = new Point((e.getX()) + ((Frame.size.width - Screen.myWidth)/2),
                (e.getY())+ ((Frame.size.height - Screen.myHeight)/2));
    }

    @Override
    // Được triệu hồi khi con trỏ chuột đã được di chuyển trên một thành phần nhưng không có nút nào được nhấn
    public void mouseMoved(MouseEvent e) {
        Screen.mse = new Point((e.getX()) + ((Frame.size.width - Screen.myWidth)/2),
                (e.getY())+ ((Frame.size.height - Screen.myHeight)/2));
    }
}
