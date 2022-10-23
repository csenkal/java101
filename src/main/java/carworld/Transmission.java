package carworld;

class Transmission {
    private short currentGear;
    private GearType gearType;

    public void changeGear(short newGear) {
        currentGear = newGear;
    }
}
