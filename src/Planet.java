import java.util.Random;

public class Planet {
    private final int id;
    private final String planetName;
    private final int settlements;
    private int artifacts;
    private int units;

    Planet(int id){
        this.id = id;
        this.planetName = generatePlanetName();
        Random rand = new Random();
        settlements = rand.nextInt(6);
        artifacts = rand.nextInt(6);
        units = rand.nextInt(1000);
    }

    Planet(String data){
        String[] planetData = data.split(",");
        this.id = Integer.parseInt(planetData[0]);
        this.planetName = planetData[1];
        this.settlements = Integer.parseInt(planetData[2]);
        this.artifacts = Integer.parseInt(planetData[3]);
        this.units = Integer.parseInt(planetData[4]);
    }


    private String generatePlanetName(){
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVXYZ123456789";
        StringBuilder name = new StringBuilder();
        Random rand = new Random();
        while(name.length() < 8){
            int index = rand.nextInt(possibleChars.length());
            name.append(possibleChars.charAt(index));
        }
        String nameStr = name.toString();
        nameStr = nameStr.replaceAll("--", "-");
        return nameStr;
    }

    @Override
    public String toString() {
        return  this.id +
                "," +
                this.planetName +
                "," +
                this.settlements +
                "," +
                this.artifacts +
                "," +
                this.units;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void grabArtifact() {
        this.artifacts--;
        //TODO: Update Server, removing artifact from it.
    }

    public int getArtifacts() {
        return artifacts;
    }

    public int getUnits() {
        int money = units;
        units = 0;
        return money;
    }

    public int getId() {
        return id;
    }
}