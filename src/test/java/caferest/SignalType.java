package caferest;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="signaltype")
public class SignalType {
	private String key;
	private Integer id;
	private List<SignalValue> signalvalue;
	public SignalType() {
		super();
	}

	
	public SignalType(String key, Integer id, List<SignalValue> values) {
		super();
		this.key = key;
		this.id = id;
		this.signalvalue = values;
	}


	public String getKey() {
		return key;
	}
	
	@XmlElement(name="key")
	public void setKey(String key) {
		this.key = key;
	}
	

	public List<SignalValue> getSignalvalue() {
		return signalvalue;
	}


	public void setSignalvalue(List<SignalValue> signalvalue) {
		this.signalvalue = signalvalue;
	}


	public Integer getId() {
		return id;
	}

	@XmlAttribute(name="id")
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
