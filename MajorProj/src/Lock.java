import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.I2CDevice;
import org.firmata4j.Pin;
import  org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.ssd1306.MonochromeCanvas;
import  java.io.IOException;
import org.firmata4j.Pin.Mode;


public class Lock extends Major{

    private static FirmataDevice myArduinoBoard = null;

    static final int D9 = 9; // Servo
    //static final int D11 = 11; //IR Receiver INPUT
    static final int A2 = 16; //IR Receiver ANALOG
    static final int D6 = 6; // Button
    static final int D4 = 4; // Button

    static final int THRESHOLD = 500;


    public Lock(FirmataDevice myArduinoBoard){
        this.myArduinoBoard = myArduinoBoard;
    }
    public static void procedure() throws IOException, InterruptedException{

        try{

            // Servo Initialization
            Pin servo = myArduinoBoard.getPin(D9);
            servo.setMode(Pin.Mode.SERVO);

            // IR Receiver Initialization
            Pin IR = myArduinoBoard.getPin(A2);
            IR.setMode(Pin.Mode.ANALOG);

            // Button Initialization
            Pin buttonPin = myArduinoBoard.getPin(D6);
            buttonPin.setMode(Pin.Mode.INPUT);

            // LED Initialization
            Pin ledPin = myArduinoBoard.getPin(D4);
            ledPin.setMode(Pin.Mode.OUTPUT);

            servo.setValue(100);
            ledPin.setValue(1);

            boolean servoState = false;
            boolean ledState = false;
            boolean lastIRState = false;
            int servoAngle = 0;

            while (true) {
                // Read the button state
                boolean IRState = IR.getValue() < 35;

                // Check if the button state has changed
                if (IRState != lastIRState) {
                    lastIRState = IRState;

                    // Toggle the LED state if the button is pressed
                    if (IRState) {
                        servoState = !servoState;
                        ledState = !ledState;
                        servoAngle = servoState ? 100:0;
                        ledPin.setValue(ledState ? 1:0);
                        servo.setValue(servoAngle);
                    }
                }
            }

        }catch(Exception ex){
            System.out.println("Couldn't connect to Arduino board.");
            throw ex;
        }finally {
            myArduinoBoard.stop();
        }

    }
}
