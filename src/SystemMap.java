import javax.swing.*;

public class SystemMap {
    private JPanel content;
    private JButton returnToGalacticMapButton;
    private JButton explore;
    private JButton ransack;
    private JButton settle;
    private JPanel buttonHolder;
    private JLabel currentPlanet;
    private JLabel otherPlayers;
    private JLabel status;
    private final WindowManager manager;
    private SolarSystem system;


    SystemMap(WindowManager manager){
        this.manager = manager;
        returnToGalacticMapButton.addActionListener(action -> manager.showGalacticMap());

        ransack.addActionListener(action -> ransack());
        explore.addActionListener(action -> explore());
        settle.addActionListener(action -> settle());
    }

    public void setSystem(SolarSystem system) {
        this.system = system;
        createButtons();
        manager.pack();
    }

    private void createButtons(){
        buttonHolder.removeAll();
        for(Planet planet : system.getPlanets()){
            JButton button = new JButton(planet.getPlanetName());
            button.addActionListener(action -> setCurrentPlanet(planet));
            buttonHolder.add(button);
        }

        JButton station = new JButton("Space Station");
        station.addActionListener(action -> goToSpaceStation());
        buttonHolder.add(station);

        buttonHolder.revalidate();
        buttonHolder.repaint();
    }

    private void goToSpaceStation(){
        manager.showStation();
    }

    private void setCurrentPlanet(Planet planet){
        system.setCurrentPlanet(planet);
        currentPlanet.setText("<html>Current Planet: " + planet.getPlanetName() + "<br/>"+system.getActionsLeft() + " Actions Left in this System.</html>");
        currentPlanet.revalidate();
        currentPlanet.repaint();
    }

    private void explore(){
        Planet planet = system.getCurrentPlanet();
        int result = manager.awardUnits(planet);
        if(result != 0){
            useAction();
            setStatus("Looted " + result + " units!!");
        }
        else{
            setStatus("No units to be looted!");
        }
    }

    private void ransack(){
        Planet planet = system.getCurrentPlanet();
        boolean result = manager.awardArtifacts(1,planet);

        if(result){
            useAction();
            setStatus("Ransacked one artifact from settlement.");
        }
        else{
            setStatus("Error: No Artifacts left to ransack!");
        }
    }

    private void settle(){
        //Creates a waypoint, and a small settlement that rewards you with units after returning a set amount of time.
        manager.createWaypoint(this.system.getId(),this.system.getName());
    }

    private void setStatus(String statusStr){
        status.setText(statusStr);
        status.revalidate();
        status.repaint();
    }

    private void useAction(){
        system.useAction();
        currentPlanet.setText("<html>Current Planet: " + system.getCurrentPlanet().getPlanetName() + "<br/>"+system.getActionsLeft() + " Actions Left in this System.</html>");
        currentPlanet.revalidate();
        currentPlanet.repaint();
    }

    public JPanel getContent() {
        return content;
    }
}


