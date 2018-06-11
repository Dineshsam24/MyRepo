package com.jmeter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPropertyFile {
	  //public static void main(String[] args) {
	public Properties getProps(String filename)
	{
			Properties prop = new Properties();
			InputStream input = null;

			try {

				input = new FileInputStream("C:\\Users\\dija\\Desktop\\Jmeter\\"+filename+".properties");

				// load a properties file
				prop.load(input);

				// get the property value and print it out
				/* System.out.println(prop.getProperty("Domain"));
				System.out.println(prop.getProperty("Port"));
				System.out.println(prop.getProperty("Password")); */

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return prop;
		 }

}
