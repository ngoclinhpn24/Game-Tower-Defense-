package GameTowerDefense;

import GameTowerDefense.background.Screen;

import javax.swing.*;
import java.awt.*;
// Tạo khung
public class Frame extends JFrame {
    public static String title = " Simple Tower Defense";
    public static Dimension size = new Dimension(700, 550);

    public Frame(){
        setTitle(title);
        setSize(size); // kích thước của khung
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
    }
//    public void add(Component c)	thêm một thành phần vào thành phần khác.
//    public void setSize(int width, int height)	thiết lập kích thước của thành phần.
//    public void setLayout(LayoutManager m)	thiết lập trình quản lý bố cục (layout) cho thành phần.
//    public void setVisible(boolean b)	thiết lập khả năng hiển thị của thành phần. Nó theo mặc định là false (ẩn)
//    b.setBounds(130, 50, 100, 40);// trục x , y , width, height

//    public void setSize(Dimension dim){
//        System.out.print("Hello");
//
//    }
    public void init(){
        setLayout(new GridLayout(1, 1, 0, 0));

        Screen screen = new Screen(this);
        add(screen);

        setVisible(true); //hiển thị cửa sổ
    }
    public static void main(String args[]){
        Frame frame = new Frame();
    }
}

