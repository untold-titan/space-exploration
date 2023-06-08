import javax.swing.*;
import java.awt.*;

public class GalacticMap {
    private JPanel content;
    private JButton travelLeft;
    private JButton travelRight;
    private JPanel display;
    private JButton goToSystemViewButton;

    private String currentSystem;
    private String leftSystem;
    private String rightSystem;

    GalacticMap(WindowManager manager){
        //If needed, I can convert manager back into a class-wide var, but for now, it seems to be easier
        travelLeft.addActionListener(action -> manager.flyLeft());
        travelRight.addActionListener(action -> manager.flyRight());
        goToSystemViewButton.addActionListener(action -> manager.showSystemMap());
    }

    public void drawMap(){
        JPanel lines = new JPanel(){
            protected void paintComponent(Graphics g){
                Dimension guh = new Dimension();
                guh.setSize(300,300);
                setMinimumSize(guh);
                Dimension d = this.getSize();

                g.clearRect(0,0,(int)d.getWidth(),(int)d.getHeight());

                double midPointX = d.getWidth() / 2;
                double midPointY = (d.getHeight() / 5) * 4;
                double leftLineTipX = d.getWidth() / 4;
                double rightLineTipX = 3 * leftLineTipX;
                double lineTipY = d.getHeight() / 3;

                g.setColor(Color.BLACK);
                //Draw Left Line
                g.drawLine((int) midPointX,(int) midPointY,(int)leftLineTipX,(int) lineTipY);
                //Draw Right Line
                g.drawLine((int) midPointX,(int) midPointY,(int)rightLineTipX,(int) lineTipY);
                //Current System Name
                g.drawChars(currentSystem.toCharArray(),0,currentSystem.length(),(int) midPointX - currentSystem.length() * 3,(int) midPointY + 20);
                //Left System Name
                g.drawChars(leftSystem.toCharArray(),0,leftSystem.length(),(int) leftLineTipX - leftSystem.length() * 3,(int) lineTipY - 15);
                //Right System Name
                g.drawChars(rightSystem.toCharArray(),0,rightSystem.length(),(int) rightLineTipX - rightSystem.length() * 3,(int) lineTipY - 15);
                g.drawChars("Galactic Map".toCharArray(),0,12,(int) (d.getWidth() / 2) - (12 * 3), 30);
            }
        };

        display.add(lines);
        display.revalidate();
        display.repaint();
    }


    // [0] = Current System Name
    // [1] = Left System Name
    // [2] = Right System Name
    public void setCurrentSystem(String[] names){
        if(names.length != 3){
            return;
        }
        currentSystem = names[0];
        leftSystem = names[1];
        rightSystem = names[2];
        this.drawMap();
    }

    public JPanel getContent() {
        return content;
    }
}
