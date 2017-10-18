package extract.util;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import extract.autoVoiceExtract.Epoint005WebService;

public class WebServiceUtil {

	public static final String VOICE_URL = "http://218.4.136.118:81/05557WebService/services/Epoint005WebService";
	
	public static Epoint005WebService getService() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Epoint005WebService.class);
		factory.setAddress(VOICE_URL);
		return (Epoint005WebService)factory.create();
		 
	}
}
