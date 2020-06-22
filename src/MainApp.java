import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/** Represents the main window this app is in
 * @author Roy Li
 * @version 0.1
 *
 */
public class MainApp {
    final static int WINDOW_WIDTH = 600;
    final static int WINDOW_HEIGHT = 800;
    public static List cl_list = new List(12, false);
    public static Frame f = new Frame();
    public static String lastItem = "";
    public static int lastItemIndex = -1;
    public static ArrayList<clist> checklists = new ArrayList<clist>();

    public static void main(String[] args){
        //setting up gui (frame, button)
        //main window
        f.setBounds(12,12, WINDOW_WIDTH, WINDOW_HEIGHT);
        f.setTitle("Checklist");
        f.setLayout(null);

        //add checklist button
        Button addButton = new Button("Add Checklist");
        addButton.setBounds(WINDOW_WIDTH/4 - 50,WINDOW_HEIGHT/12,100,30);
        f.add(addButton);

        //delete checklist button
        Button delButton = new Button("Delete checklist");
        delButton.setBounds(WINDOW_WIDTH/2 - 50, WINDOW_HEIGHT/12, 100, 30);
        f.add(delButton);

        //checklist list
        cl_list.setBounds(WINDOW_WIDTH/2 - WINDOW_WIDTH*7/16, 100, WINDOW_WIDTH*7/8, WINDOW_HEIGHT*6/7);
        cl_list.isMultipleMode();
        f.add(cl_list);

        //defining button behaviour
        //adding a checklist
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewClistWindow clistWindow = new NewClistWindow();
            }
        });

        //deleting a checklist
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cl_list.getSelectedItem() != null){
                    cl_list.remove(cl_list.getSelectedItem());
                    checklists.remove(lastItemIndex);
                }
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
                new clistWindow(lastItem, checklists.get(lastItemIndex));
            }
        });

        //selecting a checklist, get name of last selected item
        cl_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (cl_list.getSelectedItem() != null){
                    lastItem = cl_list.getSelectedItem();
                    lastItemIndex = cl_list.getSelectedIndex();

                    System.out.println(Integer.toString(lastItemIndex) + ", " + lastItem);
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
                //skipping duplicate names
                if (MainApp.cl_list.getItemCount() == 0 && tf.getText().trim().isEmpty()){
                    return;
                }
                for(String i: MainApp.cl_list.getItems()){
                    if (i.equals(tf.getText().trim()) || tf.getText().isEmpty()){
                        return;
                    }
                }

                // add a new clist instance and update the list in the main window
                MainApp.cl_list.add(tf.getText().trim());
                MainApp.checklists.add(new clist(MainApp.cl_list.getItem(MainApp.cl_list.getItemCount() - 1)));
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
     * @param selClist the checklist that has been selected
     */

    clistWindow(String name, clist selClist){
        //setting up gui
        //checklist window
        setSize(400,700);
        this.setTitle(name);
        this.setLayout(null);

        Panel chPanel = new Panel(new GridLayout(0,1));

        //instantiating buttons
        Button newItemButton = new Button("add");
        Button delChecked = new Button("remove checked");
        Button searchButton = new Button("search item");

        //text field
        TextField itemField = new TextField();
        itemField.setBounds(400/2 - 75, 35, 150, 20);
        this.add(itemField);

        //button panel
        Panel bPanel = new Panel(new GridLayout(1,3, 30, 30));
        bPanel.setBounds(10,70, 380, 40);
        bPanel.add(newItemButton);
        bPanel.add(delChecked);
        bPanel.add(searchButton);
        this.add(bPanel);

        //checkbox scrollpane
        ScrollPane t = new ScrollPane();
        t.setBounds(10, 130, 380, 530);
        t.add(chPanel);
        this.add(t);

        //loading in items from the checklist
        this.loadItems();

        //defining button behaviours
        //closing window
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        //adding an item to the checklist
        newItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selClist.addItem(itemField.getText().trim())){
                    Checkbox cbox = new Checkbox(selClist.getLast().name);
                    chPanel.add(cbox);
                    cbox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            selClist.getLast().check();
                            System.out.println(selClist.getLast().name +  " has been checked");
                        }
                    });

                    //refresh checkbox panel
                    chPanel.validate();
                }
            }
        });

        //remove checked items from checklist, refresh layout
        delChecked.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selClist.removeChecked();
                chPanel.removeAll();

                for(item i : selClist.items){
                    Checkbox cbox = new Checkbox(i.name);
                    cbox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            i.check();
                            System.out.println(i.name +  "has been checked");
                        }
                    });
                    chPanel.add(cbox);
                    chPanel.revalidate();
                }
            }
        });

        setVisible(true);
    }


    /** Loads in items from selected checklist
     *
     */
    private void loadItems(){

    }
}
