package ru.zhigalov.entity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.List;


/**
 * Тестовый класс для проверки работы JAXB
 */

public class XmlUsage {

    @XmlRootElement(name = "entries")
    public static class Entry {

        private List<Field> values;

        public Entry() {
        }

        public Entry(List<Field> values) {
            this.values = values;
        }

        public List<Field> getValues() {
            return values;
        }
        @XmlElement(name = "entry")
        public void setValues(List<Field> values) {
            this.values = values;
        }
    }

    public static class Field {
        private int value;

        public Field() {
        }

        public Field(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @XmlElement(name = "field")
        public void setValue(int value) {
            this.value = value;
        }
    }

    public void getEntity() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Entry.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(
                new Entry(Arrays.asList(new Field(1), new Field(2))),
                System.out
        );
    }
}
