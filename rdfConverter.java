import java.io.*;
import java.util.*;

public class rdfConverter{

public static void main(String args[]) {
    try{
        
        int count = 1;
        String testid = null;
        
        FileInputStream fstream = new FileInputStream(args[0]);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        
        FileWriter fstream3 = new FileWriter("RDF-Output.txt", false);
        BufferedWriter out = new BufferedWriter(fstream3);
        
        out.write("@Prefix dbpedia: <http://dbpedia.org/resource/> .");
        out.newLine();
        out.write("@Prefix vso: <http://www.heppnetz.de/ontologies/vso/ns#> .");
        out.newLine();
        out.write("@Prefix gr: <http://purl.org/goodrelations/v1#> .");
        out.newLine();
        out.newLine();
        

        while (((strLine = br.readLine()) != null)) {
            String [] objects = strLine.split("\\|");
            
            for (int i = 0; i < objects.length; i++){
                
                if (i == 0) {
                    testid = objects[i];

                }
                if (i == 1) {
                    System.out.println("_:" + testid + " VehicleID " + objects[i] + ";");
                    out.write("_:" + testid + " VehicleID " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 2) {
                    System.out.println(" TestDate " + objects[i] + ";");
                    out.write(" TestDate " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 3) {
                    System.out.println(" TestClassID " + objects[i] + ";");
                    out.write(" TestClassID " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 4) {
                    System.out.println(" TestType " + objects[i] + ";");
                    out.write(" TestType " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 5) {
                    System.out.println(" TestResult " + objects[i] + ";");
                    out.write(" TestResult " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 6) {
                    System.out.println(" vso:mileageFromOdometer " + objects[i] + ";");
                    out.write(" vso:mileageFromOdometer " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 7) {
                    System.out.println(" dbpedia:Postal_Code " + objects[i] + ";");
                    out.write(" dbpedia:Postal_Code " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 8) {
                    System.out.println(" gr:hasManufacturer " + objects[i] + ";");
                    out.write(" gr:hasManufacturer " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 9) {
                    System.out.println(" Model " + objects[i] + ";");
                    out.write(" Model " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 10) {
                    System.out.println(" vso:color " + objects[i] + ";");
                    out.write(" vso:color " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 11) {
                    if("P".equals(objects[i])){
                    System.out.println(" vso:fuelType " + "dbpedia:Gasoline" + ";");
                    out.write(" vso:fuelType " + objects[i] + ";");
                    out.newLine();
                    }
                    if("D".equals(objects[i])){
                    System.out.println(" vso:fuelType " + "dbpedia:Diesel_fuel" + ";");
                    out.write(" vso:fuelType " + objects[i] + ";");
                    out.newLine();
                    }
                    if("E".equals(objects[i])){
                    System.out.println(" vso:fuelType " + "dbpedia:Electricity" + ";");
                    out.write(" vso:fuelType " + objects[i] + ";");
                    out.newLine();
                    }
                    if("S".equals(objects[i])){
                    System.out.println(" vso:fuelType " + "dbpedia:Steam" + ";");
                    out.write(" vso:fuelType " + objects[i] + ";");
                    out.newLine();
                    }
                    else {
                        System.out.println(" vso:fuelType " + objects[i] + ";");
                        out.write(" vso:fuelType " + objects[i] + ";");
                        out.newLine();
                    }
                }
                if (i == 12) {
                    System.out.println(" vso:engineDisplacement " + objects[i] + ";");
                    out.write(" vso:engineDisplacement " + objects[i] + ";");
                    out.newLine();
                }
                if (i == 13) {
                    System.out.println(" vso:firstRegistration " + objects[i] + ".");
                    out.write(" vso:firstRegistration " + objects[i] + ".");
                    out.newLine();
                }
                
                
           
            }
            count = count + 1;
            out.newLine();
            
        }
        out.close();
        fstream.close();
        
        }
    
    catch (Exception e) {// Catch exception if any
    System.err.println("Error: " + e.getMessage());
    }

    }
}
