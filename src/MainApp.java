import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApp {
    final static int WINDOW_WIDTH = 600;
    final static int WINDOW_HEIGHT = 800;


    public static void main(String[] args){
        //setting up gui (frame, button)
        Frame f = new Frame();
        f.setBounds(12,12, WINDOW_WIDTH, WINDOW_HEIGHT);
        Button addButton = new Button("Add Checklist");
        addButton.setBounds(WINDOW_WIDTH/2 - 35,WINDOW_HEIGHT/12,70,30);

        //defining button behaviour
        //behaviour for add checklist button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewItemWindow itemWindow = new NewItemWindow();
            }
        });

        // close main window
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        //drawing main gui
        f.setLayout(null);
        f.add(addButton);
        f.setVisible(true);

    }
}


/** Represents popup window when prompted to make a new item
 */
class NewItemWindow extends Frame{
    NewItemWindow(){
        //setting up gui
        setSize(300,100);
        Button confirmButton = new Button("Confirm");
        confirmButton.setBounds(300/2, 80, 70, 35);
        add(confirmButton);

        setLayout(null);
        setVisible(true);

        // defining behaviour for buttons
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
