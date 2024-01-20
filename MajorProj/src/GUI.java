import org.firmata4j.I2CDevice;
import org.firmata4j.firmata.FirmataDevice;
import org.firmata4j.ssd1306.SSD1306;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class GUI extends Major implements ActionListener {

    private static FirmataDevice myArduinoBoard = null;
    private static JLabel userlabel;
    private static JTextField userText;
    private static JLabel passlabel;
    private static JPasswordField passText;
    private static JButton button;
    private static JLabel success;
    private static SSD1306 theOledObject;

    public GUI(FirmataDevice myArduinoBoard) throws IOException {
        this.myArduinoBoard = myArduinoBoard;

        // OLED Initialization
        I2CDevice i2cObject = myArduinoBoard.getI2CDevice((byte) 0x3C);
        theOledObject = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        theOledObject.init();

        theOledObject.clear();
        theOledObject.getCanvas().drawString(20, 0, "Lockdown Active");
        theOledObject.getCanvas().drawString(30, 20, "System is Secure");
        theOledObject.display();

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Authenticator");
        frame.add(panel);

        panel.setLayout(null);

        userlabel = new JLabel("User");
        userlabel.setBounds(10, 20, 80, 25);
        panel.add(userlabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passlabel = new JLabel("Password");
        passlabel.setBounds(10, 50, 80, 25);
        panel.add(passlabel);

        passText = new JPasswordField();
        passText.setBounds(100, 50, 165, 25);
        panel.add(passText);

        button = new JButton("Authenticate");
        button.setBounds(110, 90, 140, 25);
        button.addActionListener(this);
        panel.add(button);

        success = new JLabel("");
        success.setBounds(154, 115, 300, 25);
        panel.add(success);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passText.getText();

        if (user.equals("TJ") && password.equals("bob")){
            success.setText("Successful");
            theOledObject.clear();
            theOledObject.getCanvas().drawString(20, 0, "Lockdown");
            theOledObject.getCanvas().drawString(30, 20, "is Inactive");
            theOledObject.display();
        }else{
            success.setText("Incorrect");
            theOledObject.clear();
            theOledObject.getCanvas().drawString(35, 0, "Incorrect");
            theOledObject.getCanvas().drawString(30, 20, "Lockdown Active");
            theOledObject.display();
        }
    }

}