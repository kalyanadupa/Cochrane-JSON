/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonparser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aka324
 */
public class studies {
    String method;
    String interventions;
    String notes;
    String outcomes;
    String participants;  
    String label;
    
    public studies(){
        this.method = "";
        this.interventions = "";
        this.notes = "";
        this.outcomes = "";
        this.participants = "";
        this.label = "";
        
    }
    
    public void print(){
        System.out.println("Methods : " + this.method);
        System.out.println("Interventions : " + this.interventions);
        System.out.println("Notes : " + this.notes);
        System.out.println("Outcomes : " + this.outcomes);
        System.out.println("Participants : " + this.participants);        
        System.out.println("Label : " + this.label);        
    }
    
    public void parseParticipants(){
        String str = this.participants;
        String[] tokens = str.split("(?<!\\d)\\.(?!\\d)|(?<=[A-Za-z0-9]\\d)\\.(?=\\s)"); 
        List<String> keys = new ArrayList<String>();
        for (String p : tokens) {
            if ((!p.matches("\\s+")) && (!p.equalsIgnoreCase(""))) {
                if (p.contains(":")) {
                    String[] tokens1 = p.split(":");
                    keys.add(tokens1[0]);
                }
            }
        }
        System.out.println(keys.toString());
    }
    
}
