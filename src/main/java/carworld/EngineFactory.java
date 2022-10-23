package carworld;

public class EngineFactory {
    private EngineRequestHTTPServer webService;

    public EngineRequestHTTPServer getWebService() {
        return webService;
    }

    public void setWebService(EngineRequestHTTPServer webService) {
        this.webService = webService;
    }

    public Engine produceEngine(EngineType type){
        System.out.println(type + " Engine produced");
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