
package extract.autoVoiceExtract;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for projectYytz complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="projectYytz">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactnum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactperson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="peoplelist" type="{http://service_005.epoint.com/}peopleYytz" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="projectid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="province" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recordid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="reviewdays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sellend" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="site" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="starttime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectYytz", propOrder = {
    "address",
    "contactnum",
    "contactperson",
    "peoplelist",
    "projectid",
    "projectname",
    "province",
    "recordid",
    "reviewdays",
    "sellend",
    "site",
    "starttime",
    "specexpnum",
})
public class ProjectYytz {

    protected String address;
    protected String contactnum;
    protected String contactperson;
    @XmlElement(nillable = true)
    protected List<PeopleYytz> peoplelist;
    protected String projectid;
    protected String projectname;
    protected String province;
    protected String recordid;
    protected int reviewdays;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar sellend;
    protected String site;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar starttime;
    protected int specexpnum;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the contactnum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactnum() {
        return contactnum;
    }

    /**
     * Sets the value of the contactnum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactnum(String value) {
        this.contactnum = value;
    }

    /**
     * Gets the value of the contactperson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactperson() {
        return contactperson;
    }

    /**
     * Sets the value of the contactperson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactperson(String value) {
        this.contactperson = value;
    }

    /**
     * Gets the value of the peoplelist property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the peoplelist property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPeoplelist().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PeopleYytz }
     * 
     * 
     */
    public List<PeopleYytz> getPeoplelist() {
        if (peoplelist == null) {
            peoplelist = new ArrayList<PeopleYytz>();
        }
        return this.peoplelist;
    }

    /**
     * Gets the value of the projectid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectid() {
        return projectid;
    }

    /**
     * Sets the value of the projectid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectid(String value) {
        this.projectid = value;
    }

    /**
     * Gets the value of the projectname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectname() {
        return projectname;
    }

    /**
     * Sets the value of the projectname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectname(String value) {
        this.projectname = value;
    }

    /**
     * Gets the value of the province property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the value of the province property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvince(String value) {
        this.province = value;
    }

    /**
     * Gets the value of the recordid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordid() {
        return recordid;
    }

    /**
     * Sets the value of the recordid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordid(String value) {
        this.recordid = value;
    }

    /**
     * Gets the value of the reviewdays property.
     * 
     */
    public int getReviewdays() {
        return reviewdays;
    }

    /**
     * Sets the value of the reviewdays property.
     * 
     */
    public void setReviewdays(int value) {
        this.reviewdays = value;
    }

    /**
     * Gets the value of the sellend property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSellend() {
        return sellend;
    }

    /**
     * Sets the value of the sellend property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSellend(XMLGregorianCalendar value) {
        this.sellend = value;
    }

    /**
     * Gets the value of the site property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSite() {
        return site;
    }

    /**
     * Sets the value of the site property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSite(String value) {
        this.site = value;
    }

    /**
     * Gets the value of the starttime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStarttime() {
        return starttime;
    }

    /**
     * Sets the value of the starttime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStarttime(XMLGregorianCalendar value) {
        this.starttime = value;
    }

	public int getSpecexpnum() {
		return specexpnum;
	}

	public void setSpecexpnum(int specexpnum) {
		this.specexpnum = specexpnum;
	}

}
