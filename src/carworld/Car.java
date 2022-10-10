package carworld;

class Car {
    private Engine engine;
    private Transmission transmission;
    private Wheel[] wheels;

    public Car(Engine pEngine, Transmission pTransmission, Wheel[] pWheels) {
        this.engine = pEngine;
        this.transmission = pTransmission;
        this.wheels = pWheels;
        //arda was a
        //deneme123
    }

    public void start() {
        engine.start();
    }

    public void stop() {
        engine.stop();
    }

    public void setThrottle(float newThrottle) {
        engine.setThrottle(newThrottle);
    }

    public void changeGear(short newGear) {
        transmission.changeGear(newGear);
    }

    public void brake() {
        engine.brake();

        for (int i = 0; i < wheels.length; i++) {
            wheels[i].brake();
        }
    }
}
