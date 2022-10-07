package carworld;

public class ElectricEngine extends Engine{
    public String getBrakeEnergyRegenerator() {
        return brakeEnergyRegenerator;
    }

    private String brakeEnergyRegenerator;


    public void DieselEngine(){

        System.out.println("Electric Engine has been produced");
    }
}
