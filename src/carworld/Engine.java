package carworld;

class Engine {
    private EngineState state;
    private short rpm;
    private float throttle;


    //Function to start the engine
    public void start() {
        state = EngineState.ON;
        rpm = 900;
        throttle = 0.1f;
        System.out.println("Engine Started");
    }

    //Function to stop the engine
    public void stop() {
        state = EngineState.OFF;
        rpm = 0;
        throttle = 0f;
        System.out.println("Engine Stopped");
    }

    public void setThrottle(float newThrottle) {
        if (state == EngineState.ON) {
            float ratio = newThrottle / throttle;
            rpm = (short) (rpm * ratio);
            throttle = newThrottle;
            System.out.println("Throttle is set to: " + (100 * newThrottle) + " percent");
        } else {
            System.out.println("Engine is off, cannot set throttle");
        }
    }

    public void brake() {
        setThrottle(0.1f);
    }
}
