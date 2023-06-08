import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

public class CoordinateWindow {

    private final JFrame frame;
    private JPanel content;
    private JPanel mapLocations;
    private final ArrayList<Integer> coordinates;
    private final ArrayList<String> names;
    private final WindowManager manager;

    CoordinateWindow(WindowManager manager){
        mapLocations.setLayout(new BoxLayout(mapLocations,BoxLayout.Y_AXIS));
        mapLocations.setBorder(new EmptyBorder(20,20,0,20));
        frame = new JFrame();
        coordinates = new ArrayList<>();
        names = new ArrayList<>();
        this.manager = manager;
    }

    public void startWindow(){
        frame.setTitle("Fast Travel Service");
        frame.setContentPane(content);
        frame.pack();
        frame.setVisible(true);
    }

    public void addCoordinate(int systemId, String coordName){
        if(systemId < 0 || coordName.isBlank()){
            return;
        }
        for(int ids : coordinates){
            if(ids == systemId){
                return;
            }
        }
        coordinates.add(systemId);
        names.add(coordName);
        mapLocations.removeAll();
        for(String name : names){
            JButton button = new JButton(name);
            button.addActionListener(action -> fastTravel(coordinates.get(names.indexOf(name))));
            mapLocations.add(button);
        }
        mapLocations.revalidate();
        mapLocations.repaint();
    }

    private void fastTravel(int coordinate){
        manager.showGalacticMap();
        manager.fastTravel(coordinate);
    }

    public void close(){
        frame.setVisible(false);
    }
}
