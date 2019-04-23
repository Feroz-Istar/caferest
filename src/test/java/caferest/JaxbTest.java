package caferest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class JaxbTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SignalValue signalValue = new SignalValue(1,"aa");
		SignalValue signalValue1 = new SignalValue(2,"dd");
		SignalValue signalValue2 = new SignalValue(3,"cc");
		SignalValue signalValue3= new SignalValue(4,"bb");
		SignalValue signalValue4 = new SignalValue(5,"33");
		
		List<SignalValue> values = new ArrayList<SignalValue>();
		values.add(signalValue);
		values.add(signalValue1);
		values.add(signalValue2);
		values.add(signalValue3);
		values.add(signalValue4);
		SignalType signalType = new SignalType("Brand", 1, values);
		 try {

				File file = new File("file.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(SignalType.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(signalType, file);
				jaxbMarshaller.marshal(signalType, System.out);

			      } catch (JAXBException e) {
				e.printStackTrace();
			      }
	}

}
