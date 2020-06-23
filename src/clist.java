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

    /** Adds a new item with the name {@code name} to the checklist
     * @param name the name of the item
     * Precondition: name has been trimmed already
     */
    public boolean addItem(String name){
        for (item i : this.items){
            if (i.name.equals(name)){
                //skip duplicate names
                return false;
            }
        }

        if (name.length() != 0){
            this.items.add(new item(name.trim()));
            return true;
        }
        return false;
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


    /** Finds the first occurrence of the item by name, and returns its index
     * @return the item (if found), empty item (no name) if not found
     */
    public item findItem(String name){
        for (item i : this.items){
            if (i.name.equals(name)){
                return i;
            }
        }
        return new item("");
    }

    /** Determines if there exists an item in the checklist containing the string {@code name}
     * @param name the string that is being searched for
     */
    public boolean containsString(String name){
        for (item i: this.items){
            if (i.name.contains(name)){
                return true;
            }
        }
        return false;
    }
}
