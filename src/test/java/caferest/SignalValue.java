package caferest;

import javax.xml.bind.annotation.XmlAttribute;

public class SignalValue {
	private Integer id;
	private String value ;

	public SignalValue() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public SignalValue(Integer id, String value) {
		super();
		this.id = id;
		this.value = value;
	}



	public Integer getId() {
		return id;
	}

	@XmlAttribute(name="id")
	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute(name="value")
	public void setValue(String value) {
		this.value = value;
	}

}
