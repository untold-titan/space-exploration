import java.util.ArrayList;
import java.util.Objects;
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
    private int id;
    private String name;
    private ArrayList<Planet> planets;
    private Race race;
    private int economy; //1-5x the units found in this system

    private int leftLink;
    private int rightLink;

    private ArrayList<Player> players = new ArrayList();

    SolarSystem(int id, int sizeOfGalaxy){
        this.id = id;
        this.name = generateSystemName();
        planets = new ArrayList<>();
        Random rand = new Random();
        int planetsToMake = rand.nextInt(7) + 1;
        for(int i = 0; i != planetsToMake;  i++){
            this.planets.add(new Planet());
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
        //0,VYKEEN,2~PLANETDATA-PLANETDATA2-PLANETDATA3
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
        name.append(rand.nextInt(6));
        return name.toString();
    }

    public void addPlayerToSystem(Player player){
        players.add(player);
    }

    public void removePlayerFromSystem(Player playerToRemove){
        Player removeMe = null;
        for(Player player : players){
            if(Objects.equals(player.getIpAddress(), playerToRemove.getIpAddress())){
                removeMe = player;
            }
        }
        if(removeMe != null){
            players.remove(removeMe);
        }
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
}
