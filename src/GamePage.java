import javax.swing.*;
import java.awt.*;

public class GamePage {
    private JPanel content;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JPanel display;
    private JButton confirm;
    private JButton deny;

    private WindowManager manager;

    GamePage(WindowManager manager){
        this.manager = manager;
        this.display = new JPanel(){
            protected void paintComponent(Graphics g){
                Dimension d = this.getSize();
                g.setColor(Color.BLACK);
                g.drawLine(0,0,d.width,d.height);
            }
        };
        display.revalidate();
        display.repaint();
    }

    public JPanel getContent() {
        return content;
    }
}
