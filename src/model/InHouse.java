package model;


/**
 * The InHouse class. Subclass of Part.
 * */
public class InHouse extends Part {
    private int machineID;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id,name,price,stock,min,max);
        this.machineID = machineID;
    }

    /**
     * @param machineID machine ID to set
     * */
    public void setMachineID(int machineID) { this.machineID = machineID; }

    /**
     * @return machineID
     * */
    public int getMachineID() { return machineID; }
}
