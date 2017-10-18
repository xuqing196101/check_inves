
package extract.autoVoiceExtract;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for peopleYytz complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="peopleYytz">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contactmobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="iscandidate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mobile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "peopleYytz", propOrder = {
    "contactmobile",
    "contactname",
    "iscandidate",
    "mobile",
    "username"
})
public class PeopleYytz {

    protected String contactmobile;
    protected String contactname;
    protected String iscandidate;
    protected String mobile;
    protected String username;

    /**
     * Gets the value of the contactmobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactmobile() {
        return contactmobile;
    }

    /**
     * Sets the value of the contactmobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactmobile(String value) {
        this.contactmobile = value;
    }

    /**
     * Gets the value of the contactname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactname() {
        return contactname;
    }

    /**
     * Sets the value of the contactname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactname(String value) {
        this.contactname = value;
    }

    /**
     * Gets the value of the iscandidate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIscandidate() {
        return iscandidate;
    }

    /**
     * Sets the value of the iscandidate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIscandidate(String value) {
        this.iscandidate = value;
    }

    /**
     * Gets the value of the mobile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Sets the value of the mobile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
