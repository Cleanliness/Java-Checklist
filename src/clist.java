import java.util.LinkedList;

/** Represents a checklist which contains items
 * @author Roy Li
 */
public class clist {

    public String name;
    public LinkedList<item> items;

    public clist(String name){
        this.name = name;
        this.items = new LinkedList<item>();
    }

    // ==============PUBLIC METHODS==================

    /** Adds a new item to the checklist
     * @param name the name of the item
     */
    public boolean addItem(String name){
        for (item i : this.items){
            if (i.name.equals(name)){
                //skip duplicate names
                return false;
            }
        }

        this.items.add(new item(name.trim()));
        return true;
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

    /** Returns the last item contained in this checklist
     * @return returns the last item contained in this checklist
     */
    public item getLast(){
        return this.items.getLast();
    }
}
