
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, java.time.LocalDate> {
    public java.time.LocalDate unmarshal(String v) throws Exception {
        return java.time.LocalDate.parse(v);
    }

    public String marshal(java.time.LocalDate v) throws Exception {
        return v.toString();
    }
}
