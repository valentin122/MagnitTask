package ru.zhigalov.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "entries")
public class Entries {

    @XmlElement(name = "entry")
    public List<Entry> entry;

}