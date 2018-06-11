package com.jmeter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.AuthManager;
import org.apache.jmeter.protocol.http.control.Authorization;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;

public class JMeterTestFromCode {

        public static void main(String[] argv) throws Exception {
        	
        	LoadPropertyFile loadProp = new LoadPropertyFile();
        	Properties prop = loadProp.getProps("JmeterTest");
        	String domain = prop.getProperty("Domain");
        	String port1 = prop.getProperty("Port");
        	int port = Integer.parseInt(port1);
        	String password = prop.getProperty("Password");
        	String path = prop.getProperty("Path");
        	String userName = prop.getProperty("UserName");
        	String method = prop.getProperty("Method");
        	System.out.println(domain+port+password+userName+path+method);
        	
            String jmeterHome1 = "C:\\Users\\dija\\Downloads\\apache-jmeter-4.0";
            File jmeterHome=new File(jmeterHome1);
           System.out.println(jmeterHome);
            String slash = System.getProperty("file.separator");

            if (jmeterHome.exists()) {
                File jmeterProperties = new File(jmeterHome.getPath() + slash + "bin" + slash + "jmeter.properties");
                System.out.println(jmeterProperties);
                if (jmeterProperties.exists()) {
                   
                    StandardJMeterEngine jmeter = new StandardJMeterEngine();

                   
                    JMeterUtils.setJMeterHome(jmeterHome.getPath());
                    JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
                    JMeterUtils.initLocale();

                  
                    HashTree testPlanTree = new HashTree();
                    
                    
                    AuthManager manager = new AuthManager();
                    Authorization authorization = new Authorization();
                    authorization.setURL("http://MCNRAD01:5555/gateway/Demo/1.0");
                    authorization.setUser(userName);
                    authorization.setPass(password);
                    manager.addAuth(authorization);
                    manager.setName(JMeterUtils.getResString("auth_manager_title")); // $NON-NLS-1$
                    
                    HTTPSamplerProxy httpSampler = new HTTPSamplerProxy();
                    httpSampler.setDomain(domain);
                    httpSampler.setPort(port);
                    httpSampler.setPath(path);
                    httpSampler.setMethod(method);
                    httpSampler.setName("Open API Gateway Service Management Service");
                
                    LoopController loopController = new LoopController();
                    loopController.setLoops(2);
                    loopController.setFirst(true);
                    loopController.initialize();

                    ThreadGroup threadGroup = new ThreadGroup();
                    threadGroup.setName("Example Thread Group");
                    threadGroup.setNumThreads(3);
                    threadGroup.setRampUp(10);
                    threadGroup.setSamplerController(loopController);
                    
                    TestPlan testPlan = new TestPlan("Create JMeter Script From Java Code");

                   testPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

                    // Construct Test Plan from previously initialized elements
                   	testPlanTree.add(testPlan);
                    HashTree threadGroupHashTree = testPlanTree.add(testPlan, threadGroup);
                    threadGroupHashTree.add(httpSampler);

                    // save generated test plan to JMeter's .jmx file format
                    SaveService.saveTree(testPlanTree, new FileOutputStream("C:\\Users\\dija\\Desktop\\Jmeter\\JmeterTest.jmx"));

                    //add Summarizer output to get test progress in stdout like:
                    // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
                    Summariser summer = null;
                    String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
                    if (summariserName.length() > 0) {
                        summer = new Summariser(summariserName);
                    }


                    // Store execution results into a .jtl file
                    //String logFile = "C:\\Users\\dija\\Desktop\\Jmeter\\JmeterTest.jtl";
                    String csvFile = "C:\\Users\\dija\\Desktop\\Jmeter\\JmeterTest.csv";
                    //ResultCollector logger = new ResultCollector(summer);
                    //logger.setFilename(logFile);
                    ResultCollector csvlogger = new ResultCollector(summer);
                    csvlogger.setFilename(csvFile);
                    //testPlanTree.add(testPlanTree.getArray()[0], logger);
                    testPlanTree.add(testPlanTree.getArray()[0], csvlogger);
                    // Run Test Plan
                    jmeter.configure(testPlanTree);
                    jmeter.run();

                    System.out.println("Test completed " + "C:\\Users\\dija\\Desktop\\Jmeter\\" + "JmeterTest.csv file for results");
                    System.out.println("JMeterTest.jmx script is available at " + "C:\\Users\\dija\\Desktop\\Jmeter\\" + "JmeterTest.jmx");
                    System.exit(0);

                }
            }

            System.err.println("jmeter.home property is not set or pointing to incorrect location");
            System.exit(1);


        }
    
}