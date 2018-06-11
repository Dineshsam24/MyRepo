package com.jmeter;


import java.io.FileInputStream;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

import java.io.File;  
  
public class JMeterFromExistingJMX {  
  
  public static void main(String[] argv) throws Exception {  
  
    //Set jmeter home for the jmeter utils to load  
    String jmeterHomelocation = "C:\\Users\\dija\\Downloads\\apache-jmeter-2.9";  
    String jmeterPropertieslocation = "C:\\Users\\dija\\Downloads\\apache-jmeter-2.9\\bin\\jmeter.properties";  
  
    // JMeter Engine  
    StandardJMeterEngine jmeter = new StandardJMeterEngine();  
  
  
    // Initialize Properties, logging, locale, etc.  
    JMeterUtils.loadJMeterProperties(new File(jmeterPropertieslocation).getPath());  
    JMeterUtils.setJMeterHome(new File(jmeterHomelocation).getPath());  
    // you can comment this line out to see extra log messages of i.e. DEBUG level  
    //JMeterUtils.initLogging();  
    JMeterUtils.initLocale();  
  
    // Initialize JMeter SaveService  
    SaveService.loadProperties();  
  
    // Load existing .jmx Test Plan  
    File in = new File("C:\\Users\\dija\\Desktop\\Thread Group.jmx");  
    HashTree testPlanTree = SaveService.loadTree(in);  
            
    Summariser summer = null;  
    String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");  
  
    if (summariserName.length() > 0) {  
      summer = new Summariser(summariserName);  
    }  
  
    ResultCollector logger = new ResultCollector(summer);  
    testPlanTree.add(testPlanTree.getArray()[0], logger);  
  
    // Run JMeter Test  
    jmeter.configure(testPlanTree);  
    jmeter.run();  
  }  
}  

/*   public class JMeterFromExistingJMX {

    public static void main(String[] args) throws Exception {
        // JMeter Engine
        StandardJMeterEngine jmeter = new StandardJMeterEngine();


        // Initialize Properties, logging, locale, etc.
        JMeterUtils.loadJMeterProperties("C:\\Users\\dija\\Downloads\\apache-jmeter-2.9\\bin\\jmeter.properties");
        JMeterUtils.setJMeterHome("C:\\Users\\dija\\Downloads\\apache-jmeter-2.9");
        JMeterUtils.initLogging();// you can comment this line out to see extra log messages of i.e. DEBUG level
        JMeterUtils.initLocale();

        // Initialize JMeter SaveService
        SaveService.loadProperties();

        // Load existing .jmx Test Plan
        FileInputStream in = new FileInputStream("C:\\Users\\dija\\Desktop\\Thread Group.jmx");
        HashTree testPlanTree = SaveService.loadTree(in);
        in.close();

        // Run JMeter Test
        jmeter.configure(testPlanTree);
        jmeter.run();
    }
}
*/