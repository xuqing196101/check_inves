
package extract.autoVoiceExtract;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the extract.autoVoiceExtract package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExpertResult_QNAME = new QName("http://service_005.epoint.com/", "expertResult");
    private final static QName _PutObjectResponse_QNAME = new QName("http://service_005.epoint.com/", "putObjectResponse");
    private final static QName _ExpertResultResponse_QNAME = new QName("http://service_005.epoint.com/", "expertResultResponse");
    private final static QName _PutObject_QNAME = new QName("http://service_005.epoint.com/", "putObject");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: extract.autoVoiceExtract
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExpertResultResponse }
     * 
     */
    public ExpertResultResponse createExpertResultResponse() {
        return new ExpertResultResponse();
    }

    /**
     * Create an instance of {@link PutObject }
     * 
     */
    public PutObject createPutObject() {
        return new PutObject();
    }

    /**
     * Create an instance of {@link ExpertResult }
     * 
     */
    public ExpertResult createExpertResult() {
        return new ExpertResult();
    }

    /**
     * Create an instance of {@link PutObjectResponse }
     * 
     */
    public PutObjectResponse createPutObjectResponse() {
        return new PutObjectResponse();
    }

    /**
     * Create an instance of {@link PeopleYytz }
     * 
     */
    public PeopleYytz createPeopleYytz() {
        return new PeopleYytz();
    }

    /**
     * Create an instance of {@link ProjectYytz }
     * 
     */
    public ProjectYytz createProjectYytz() {
        return new ProjectYytz();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExpertResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service_005.epoint.com/", name = "expertResult")
    public JAXBElement<ExpertResult> createExpertResult(ExpertResult value) {
        return new JAXBElement<ExpertResult>(_ExpertResult_QNAME, ExpertResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service_005.epoint.com/", name = "putObjectResponse")
    public JAXBElement<PutObjectResponse> createPutObjectResponse(PutObjectResponse value) {
        return new JAXBElement<PutObjectResponse>(_PutObjectResponse_QNAME, PutObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExpertResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service_005.epoint.com/", name = "expertResultResponse")
    public JAXBElement<ExpertResultResponse> createExpertResultResponse(ExpertResultResponse value) {
        return new JAXBElement<ExpertResultResponse>(_ExpertResultResponse_QNAME, ExpertResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service_005.epoint.com/", name = "putObject")
    public JAXBElement<PutObject> createPutObject(PutObject value) {
        return new JAXBElement<PutObject>(_PutObject_QNAME, PutObject.class, null, value);
    }

}
