//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.18 at 05:26:16 PM CDT 
//


package arpg.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="choice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="choiceText" type="{http://www.example.org/NewXMLSchema}text"/>
 *         &lt;element name="requirement" type="{http://www.w3.org/2001/XMLSchema}IDREFS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="event" type="{http://www.example.org/NewXMLSchema}event"/>
 *           &lt;element name="eventList" type="{http://www.example.org/NewXMLSchema}eventList"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "choice", propOrder = {
    "choiceText",
    "requirement",
    "event",
    "eventList"
})
public class Choice {

    @XmlElement(required = true)
    protected String choiceText;
    @XmlElementRef(name = "requirement", namespace = "http://www.example.org/NewXMLSchema", type = JAXBElement.class, required = false)
    protected List<JAXBElement<List<Object>>> requirement;
    protected Event event;
    protected EventList eventList;

    /**
     * Gets the value of the choiceText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChoiceText() {
        return choiceText;
    }

    /**
     * Sets the value of the choiceText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChoiceText(String value) {
        this.choiceText = value;
    }

    /**
     * Gets the value of the requirement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requirement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequirement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link List }{@code <}{@link Object }{@code >}{@code >}
     * 
     * 
     */
    public List<JAXBElement<List<Object>>> getRequirement() {
        if (requirement == null) {
            requirement = new ArrayList<JAXBElement<List<Object>>>();
        }
        return this.requirement;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link Event }
     *     
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link Event }
     *     
     */
    public void setEvent(Event value) {
        this.event = value;
    }

    /**
     * Gets the value of the eventList property.
     * 
     * @return
     *     possible object is
     *     {@link EventList }
     *     
     */
    public EventList getEventList() {
        return eventList;
    }

    /**
     * Sets the value of the eventList property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventList }
     *     
     */
    public void setEventList(EventList value) {
        this.eventList = value;
    }

}