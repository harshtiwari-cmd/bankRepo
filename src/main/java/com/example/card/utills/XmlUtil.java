package com.example.card.utills;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtil {

    public static String toXml(Object object)
    {
        try {
            JAXBContext context=  JAXBContext.newInstance(object.getClass());
            Marshaller marshaller= context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);

        StringWriter stringwriter=new StringWriter();
        marshaller.marshal(object,stringwriter);
        return stringwriter.toString();


        }
        catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromXml(String xml,Class<T> clazz)
    {
        try{
            JAXBContext context= JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller=context.createUnmarshaller();
            return clazz.cast(unmarshaller.unmarshal(new StringReader(xml)));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
