/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.controller;

import fr.unice.i3s.wimmics.radio.model.*;
import fr.unice.i3s.wimmics.radio.utils.Constant;
import fr.unice.i3s.wimmics.radio.utils.QueryHelper;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author eamosse
 */
@Stateless
public class FrequencyFacade extends AbstractFacade<Frequency> {
    public FrequencyFacade() {
        super(Frequency.class);
    }
    
    
     public List<Frequency> findByCategory(Long id) {
        String query = String.format(QueryHelper.findByCategory, id);
        query = String.format(query, Constant.NS, Constant.NS + rdfType, "category", String.valueOf(id));
        List<Frequency> results = executeDescribe(createQuery(query));
        return results;
    }
}
