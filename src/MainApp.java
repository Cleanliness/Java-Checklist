import java.awt.*;
import java.awt.event.*;


/** Represents the main window this app is in
 * @author Roy Li
 * @version 0.1
 *
 */
public class MainApp {
    final static int WINDOW_WIDTH = 600;
    final static int WINDOW_HEIGHT = 800;
    public static List cl_list = new List(12, true);
    public static Frame f = new Frame();
    public static String lastItem = "";

    public static void main(String[] args){
        //setting up gui (frame, button)

        f.setBounds(12,12, WINDOW_WIDTH, WINDOW_HEIGHT);
        f.setTitle("Checklist");
        f.setLayout(null);

        Button addButton = new Button("Add Checklist");
        addButton.setBounds(WINDOW_WIDTH/2 - 50,WINDOW_HEIGHT/12,100,30);
        f.add(addButton);

        cl_list.setBounds(WINDOW_WIDTH/2 - WINDOW_WIDTH*7/16, 100, WINDOW_WIDTH*7/8, WINDOW_HEIGHT*6/7);
        cl_list.isMultipleMode();
        f.add(cl_list);

        //defining button behaviour
        //behaviour for add checklist button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewClistWindow clistWindow = new NewClistWindow();
            }
        });

        // close main window
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //opening a checklist (double clicking)
        cl_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("opened a new checklist window");
                new clistWindow(lastItem);
            }
        });
        //selecting a checklist, get name of last selected item
        cl_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cl_list.getSelectedItem() != null){
                    lastItem = cl_list.getSelectedItem();
                }
            }
        });

        f.setVisible(true);
    }
}


/** Represents popup window when prompted to make a new checklist
 */
class NewClistWindow extends Frame{
    NewClistWindow(){
        //setting up gui
        setSize(300,150);
        this.setTitle("Add New Checklist");

        Button confirmButton = new Button("Confirm");
        confirmButton.setBounds(300/2 - 35, 80, 70, 35);
        add(confirmButton);

        TextField tf = new TextField(1);
        tf.setBounds(300/2 -75,40, 150, 20);
        add(tf);

        setLayout(null);
        setVisible(true);

        // defining behaviour for buttons
        // close window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // adding new checklist
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                //skipping duplicates
                if (MainApp.cl_list.getItemCount() == 0 && tf.getText().trim().isEmpty()){
                    return;
                }
                for(String i: MainApp.cl_list.getItems()){
                    if (i.equals(tf.getText().trim()) || tf.getText().isEmpty()){
                        return;
                    }
                }
                MainApp.cl_list.add(tf.getText().trim());
                dispose();
            }
        });
    }
}

/** Represents a popup window representing a singular checklist populated with its items
 *
 */
class clistWindow extends Frame{

    /** Creates a new window containing checklist items
     * @param name name of the checklist that has been selected
     */
    clistWindow(String name){
        //setting up gui
        setSize(400,700);
        this.setTitle(name);
        setVisible(true);

        //defining button behaviours
        //closing window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        
    }

}
