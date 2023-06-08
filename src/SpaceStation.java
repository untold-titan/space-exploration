import javax.swing.*;

public class SpaceStation {
    private JPanel content;
    private JButton addOne;
    private JButton removeOne;
    private JButton sellButton;
    private JButton returnToSystemMapButton;
    private JLabel artifactsText;
    private JLabel sellingText;
    private JLabel units;
    private final WindowManager manager;
    private int ownedArtifacts;
    private int numberToSell = 0;
    private int artifactRate = 1500;


    SpaceStation(WindowManager manager){
        this.manager = manager;
        returnToSystemMapButton.addActionListener(action -> returnToSystemMap());
        addOne.addActionListener(action -> updateSellingText(+1));
        removeOne.addActionListener(action -> updateSellingText(-1));
        sellButton.addActionListener(action -> sellOut());
    }

    private void updateSellingText(int val){
        if(numberToSell >= ownedArtifacts && val > 0){
            //Don't increment
            return;
        }
        if(numberToSell == 0 && val < 0){
            //Don't decrement
            return;
        }
        numberToSell += val;
        sellingText.setText("Selling " + numberToSell + " artifacts for $" + artifactRate + " each");
        sellingText.revalidate();
        sellingText.repaint();
    }

    public void setEconomy(int econ){
        artifactRate *= econ;
    }

    public void setInformation(Player player){
        ownedArtifacts = player.getArtifacts();
        units.setText("Units: $" + player.getUnits());
        artifactsText.setText("You have " + ownedArtifacts + ", how many do you want to sell?");
        sellingText.setText("Selling 0 artifacts for $" + artifactRate + " each");
    }

    private void returnToSystemMap(){
        manager.showSystemMap();
    }

    private void sellOut(){
        manager.sellArtifacts(numberToSell,artifactRate);
        this.ownedArtifacts -= numberToSell;
        artifactsText.setText("You have " + ownedArtifacts + ", how many do you want to sell?");
    }

    public JPanel getContent() {
        return content;
    }
}
