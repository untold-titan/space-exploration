import java.util.Random;

public class Planet {
    private String planetName;
    private int settlements;
    private int artifacts;
    private int units;

    Planet(){
        this.planetName = generatePlanetName();
        Random rand = new Random();
        settlements = rand.nextInt(6);
        artifacts = rand.nextInt(6);
        units = rand.nextInt(1000);
    }

    Planet(String data){
        String[] planetData = data.split(",");
        this.planetName = planetData[0];
        this.settlements = Integer.parseInt(planetData[1]);
        this.artifacts = Integer.parseInt(planetData[2]);
        this.units = Integer.parseInt(planetData[3]);
    }

    private String generatePlanetName(){
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
        StringBuilder name = new StringBuilder();
        Random rand = new Random();
        while(name.length() < 8){
            int index = rand.nextInt(possibleChars.length());
            name.append(possibleChars.charAt(index));
        }
        return name.toString();
    }

    @Override
    public String toString() {
        return this.planetName +
                "," +
                this.settlements +
                "," +
                this.artifacts +
                "," +
                this.units;
    }
}