/*
Author: Filip Hellgren

The ServerLog class responsible for creating and writing to a log file.
 */

package mainPack;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLog {
    final String fileName = "ServerLog.txt";
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    File logFile;
    public ServerLog() {
        FindOrCreateFile();
    }
    private void FindOrCreateFile() {
        //Finds the old log file or creates a new log file if no file is found.
        try {
            logFile = new File(fileName);
            if (logFile.createNewFile()) {
                //If a file was successfully created with the desired file name.
                System.out.println("Log file created!");
            } else {
                //If a file was already found with this name.
                System.out.println("Log file found!");
            }
        } catch(IOException e){
            System.out.println("Error creating a log file, verify that the path name is valid.");
        }
    }
    public void WriteToFile(String message) {
        //Appends the provided String to the server log file along with the current time at the moment of the log.
        try {
            Date date = new Date(); //Creates a Data object containing info about the current time.
            String dateString = dateFormatter.format(date); //Formats the date object data into a string according to the desired format.

            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter writer = new PrintWriter(bufferedWriter);

            writer.println(dateString + ": " + message + "\n");
            writer.close();

            System.out.println("Successfully wrote to file!");
        } catch(Exception e) {
            System.out.println("Error writing to file: " + e);
        }
    }
}
