package ru.zhigalov.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "entries")
public class Entries {


    private ArrayList<Entry> entries = new ArrayList<>();

    public Entries() {
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }
}