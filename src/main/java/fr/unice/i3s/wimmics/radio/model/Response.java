/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.model;

import fr.unice.i3s.wimmics.radio.utils.Constant;
import java.io.Serializable;
import java.util.Random;
import thewebsemantic.Id;
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.Transient;

/**
 *
 * @author eamosse
 */
@Namespace(Constant.NS)
@RdfType("Response")
public class Response implements Serializable{
    private String text; 
    private String image; 
    private Long id; 
    private Frequency frequency;

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }
    
    public Response(){
      //  this.id = System.currentTimeMillis(); 
    }
    
    public Response(String text, String url){
        this.text = text; 
        this.image = url; 
        Random r = new Random(); 
        this.id = System.currentTimeMillis() + r.nextInt(); 
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Transient
    public String getUri(){
        return Constant.NS + "Response/"+ this.id; 
    }
    
}
