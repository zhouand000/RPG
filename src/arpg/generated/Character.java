//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2015.05.18 at 05:26:16 PM CDT
//


package arpg.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for character complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="character">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="hostile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "character", propOrder = {
		"id",
		"name",
		"hostile"
})
public class Character {
	
	/**
	 * 
	 */
	@XmlElement(required = true)
	public String id;
	/**
	 * 
	 */
	@XmlElement(required = true)
	public String name;
	/**
	 * 
	 */
	public boolean hostile;
	
	/**
	 * Gets the value of the id property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 * 
	 */
	public void setId(String value) {
		id = value;
	}
	
	/**
	 * Gets the value of the name property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 * 
	 */
	public void setName(String value) {
		name = value;
	}
	
	/**
	 * Gets the value of the hostile property.
	 * @return
	 * 
	 */
	public boolean isHostile() {
		return hostile;
	}
	
	/**
	 * Sets the value of the hostile property.
	 * @param value
	 * 
	 */
	public void setHostile(boolean value) {
		hostile = value;
	}
	
}
