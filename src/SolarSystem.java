import java.util.ArrayList;
import java.util.Random;

//I would name this System, but that is a really important class that is already used by Java
public class SolarSystem {

    private enum Race{
        KORVAX,
        VYKEEN,
        GEK,
    }

    // The ID makes it easier to tell the server what system the player wants to navigate with,
    // instead of matching the name. The ID will be unique to just the game instance.
    private final int id;
    private final String name;
    private final ArrayList<Planet> planets;
    private Race race;
    private final int economy; //1-5x the units found in this system

    private final int leftLink;
    private final int rightLink;

    private int currentPlanet;
    private int actions;

    SolarSystem(int id, int sizeOfGalaxy){
        this.id = id;
        this.name = generateSystemName();
        planets = new ArrayList<>();
        Random rand = new Random();

        //Actions is equal to the number of planets in the system.
        //Each player has a finite number of things they can do in a system.
        actions = rand.nextInt(7) + 1;
        for(int i = 0; i != actions;  i++){
            this.planets.add(new Planet(i));
        }
        switch (rand.nextInt(3) + 1) {
            case 1 -> race = Race.VYKEEN;
            case 2 -> race = Race.KORVAX;
            case 3 -> race = Race.GEK;
        }
        economy = rand.nextInt(5) + 1;
        leftLink = rand.nextInt(sizeOfGalaxy);
        rightLink = rand.nextInt(sizeOfGalaxy);
    }

    SolarSystem(String data){
        //0,System Name,VYKEEN,,leftlink,rightlink,2~PLANETDATA-PLANETDATA2-PLANETDATA3
        this.planets = new ArrayList<>();
        String[] systemData = data.split("~")[0].split(",");
        String[] planetData = data.split("~")[1].split("-");
        this.id = Integer.parseInt(systemData[0]);
        this.name = systemData[1];
        switch(systemData[2]){
            case "VYKEEN" -> race = Race.VYKEEN;
            case "KORVAX" -> race = Race.KORVAX;
            case "GEK" -> race = Race.GEK;
        }
        this.economy = Integer.parseInt(systemData[3]);
        this.leftLink = Integer.parseInt(systemData[4]);
        this.rightLink = Integer.parseInt(systemData[5]);
        for(String planet : planetData){
            planets.add(new Planet(planet));
        }
        actions = planets.size();
    }

    public String generateSystemName(){
        String letters = "BCDFGHJKLMNPQRSTVXYZ";
        String vowels = "AEIOU";
        Random rand = new Random();
        StringBuilder name = new StringBuilder();
        for(int i = 0; i != 3; i++){
            name.append(letters.charAt(rand.nextInt(letters.length()-1)));
            name.append(vowels.charAt(rand.nextInt(vowels.length()-1)));
        }
        name.append(" ");
        name.append(rand.nextInt(5) + 1);
        return name.toString();
    }
    @Override
    public String toString() {
        StringBuilder system = new StringBuilder();
        system.append(this.id);
        system.append(",");
        system.append(this.name);
        system.append(",");
        system.append(this.race);
        system.append(",");
        system.append(this.economy);
        system.append(",");
        system.append(this.leftLink);
        system.append(",");
        system.append(this.rightLink);
        system.append("~");
        for(Planet planet : planets){
            system.append(planet.toString());
            system.append("-");
        }
        system.deleteCharAt(system.length() - 1);
        return system.toString();
    }

    public String getName() {
        return name;
    }

    public int getLeftLink() {
        return leftLink;
    }

    public int getRightLink() {
        return rightLink;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setCurrentPlanet(Planet planet) {
        this.currentPlanet = planets.indexOf(planet);
    }

    public Planet getPlanet(int id){
        return planets.get(id);
    }

    public int getEconomy() {
        return economy;
    }

    public void useAction(){
        this.actions -= 1;
    }

    public int getActionsLeft(){
        return this.actions;
    }

    public Planet getCurrentPlanet() {
        return planets.get(currentPlanet);
    }
}
