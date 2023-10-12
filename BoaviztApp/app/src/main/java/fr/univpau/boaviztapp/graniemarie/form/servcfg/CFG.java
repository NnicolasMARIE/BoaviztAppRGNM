package fr.univpau.boaviztapp.graniemarie.form.servcfg;

import java.io.Serializable;
import java.util.ArrayList;

public class CFG implements Serializable {
    public Cpu cpu;
    public ArrayList<Ram> ram;
    public ArrayList<Disk> disk;

    public CFG(Cpu cpu, ArrayList<Ram> ram, ArrayList<Disk> disk) {
        this.cpu = cpu;
        this.ram = ram;
        this.disk = disk;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "cpu=" + cpu +
                ", ram=" + ram +
                ", disk=" + disk +
                '}';
    }
}
