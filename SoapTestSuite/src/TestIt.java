import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.soap.MessageFactoryImpl;

public class TestIt {

	public static void main(String[] args) throws SOAPException, ServiceException, MalformedURLException, AxisFault{

		String payload = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://www.webserviceX.NET/\"><soapenv:Header/><soapenv:Body>   <web:ConversionRate>      <web:FromCurrency>AFA</web:FromCurrency>      <web:ToCurrency>DZD</web:ToCurrency>   </web:ConversionRate></soapenv:Body></soapenv:Envelope>";
		byte[] data = payload.getBytes();
		ByteArrayInputStream dataStream = new ByteArrayInputStream(data);
		StreamSource streamSource = new StreamSource(dataStream);

		MessageFactoryImpl messageFactory = new MessageFactoryImpl();
		SOAPMessage message = messageFactory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();

		soapPart.setContent(streamSource);

		Service service = new Service();
		Call call = (Call) service.createCall();

		call.setTargetEndpointAddress(new java.net.URL("http://www.webservicex.net/CurrencyConvertor.asmx"));
		call.setProperty(Call.SOAPACTION_USE_PROPERTY, new Boolean(true));
		call.setProperty(Call.SOAPACTION_URI_PROPERTY, "http://www.webserviceX.NET/ConversionRate");
		call.setEncodingStyle("utf-8");

		System.out.println("Request : " + payload);
		SOAPEnvelope resp = call.invoke(((org.apache.axis.SOAPPart) soapPart).getAsSOAPEnvelope());
		System.out.println("Response : " + resp.toString());
	}

}
