import  java.io.IOException;
import org.firmata4j.firmata.FirmataDevice;

import java.util.Objects;
import java.util.Scanner;

public class Major {

    public static void main(String[] args) throws IOException, InterruptedException{

        // Initializing User Input
        Scanner input = new Scanner(System.in);

        // Initializing Board
        var myUSBPort = "COM3";
        var myArduinoBoard = new FirmataDevice(myUSBPort);

        // Starting Board
        myArduinoBoard.start();
        myArduinoBoard.ensureInitializationIsDone();

        while(true) {
            System.out.println("Would you like to lock the system? (Y/N)");
            String answer = input.nextLine();
            if (Objects.equals(answer, "Y") || Objects.equals(answer, "y")) {
                //Important
                new GUI(myArduinoBoard);
                break;
            } else if (Objects.equals(answer, "N") || Objects.equals(answer, "n")) {

                break;
            }
        }

        // Important
        var Task = new Lock(myArduinoBoard);
        Lock.procedure();
    }


}



