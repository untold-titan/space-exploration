import java.util.ArrayList;

public class Galaxy {
    ArrayList<SolarSystem> systems;

    Galaxy(){
        this.systems = new ArrayList<>();
        System.out.println("Creating Systems");
        for(int i = 0; i != 50; i++){
            this.systems.add(new SolarSystem(i,50));
        }
    }

    Galaxy(String data){
        this.systems = new ArrayList<>();
        String[] systemData = data.split(":");
        for(String str : systemData){
            systems.add(new SolarSystem(str));
        }
        System.out.println("Successfully Loaded Galaxy!");
    }

    // each system is seperated by a :
    public String toString(){
        StringBuilder galaxy = new StringBuilder();
        for(SolarSystem sol : systems){
            galaxy.append(sol.toString());
            galaxy.append(":");
        }
        galaxy.deleteCharAt(galaxy.length() - 1);
        System.out.println(galaxy);
        return galaxy.toString();
    }

    public SolarSystem getSystem(int id){
        return systems.get(id);
    }
}
