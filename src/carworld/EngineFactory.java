package carworld;

public class EngineFactory {
    public static Engine produceEngine(EngineType type){
        if(type.equals(EngineType.GAS))
            return new GasEngine();
        else if(type.equals(EngineType.DIESEL))
            return new DieselEngine();
        else if(type.equals(EngineType.ELECTRIC))
            return new ElectricEngine();
        else
            return null;
    }
}
