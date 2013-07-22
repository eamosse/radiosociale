/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.unice.i3s.wimmics.radio.test;

import com.hp.hpl.jena.ontology.Ontology;
import fr.unice.i3s.wimmics.radio.model.Frequency;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author eamosse
 */
public class Reminder {

    Timer timer;
    Frequency frequency;

    public Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 0, seconds * 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {

            System.out.format("Time's up!%n");
            //timer.cancel(); //Terminate the timer thread
        }
    }

    public static void main(String args[]) {

        //  new Reminder(5);
        System.out.format("Task scheduled.%n");
        SimpleDateFormat formater = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
        formater.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println(formater.format(new Date()));
        
        
         //ResourceBundle bundle = ResourceBundle.getBundle("application.properties");
         //System.out.println("bundle " + bundle);
    }
}
