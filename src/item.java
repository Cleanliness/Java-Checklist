/** Represents an item in a checklist
 * @author Roy Li
 */
public class item {

    public String name;
    public boolean checked;

    /**
     * @param name Name of the item
     */
    public item(String name){
        this.name = name;
        this.checked = false;
    }

    // ==============PUBLIC METHODS=================

    /** edits the name of the item
     * @param name New name of item
     */
    public void edit(String name){
        this.name = name;
    }

    /** check or uncheck the current item
     */
    public void check(){
        this.checked = !this.checked;
    }

}
