import java.util.LinkedList;

/** Represents a checklist which contains items
 * @author Roy Li
 */
public class clist {

    public String name;
    private LinkedList<item> items;

    public clist(String name){
        this.name = name;
        this.items = new LinkedList<item>();
    }

    // ==============PUBLIC METHODS==================

    /** Adds a new item to the checklist
     * @param name the name of the item
     */
    public void addItem(String name){
        for (item i : this.items){
            if (i.name.equals(name)){
                //skip duplicate names
                return;
            }
        }

        this.items.add(new item(name));
    }

    /** Removes all checked items
     */
    public void removeChecked(){
        if (this.items.isEmpty()){
            return;
        }
        this.items.removeIf(i -> i.checked);
    }

    /** Removes all items
     */
    public void removall(){
        this.items = new LinkedList<item>();
    }
}
