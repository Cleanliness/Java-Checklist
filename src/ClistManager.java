import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Contains checklists
 *
 */
public class ClistManager {

    public ArrayList<clist> checklists = new ArrayList<>();
    ClistManager(){
    }

    /** Adds a checklist to the current instance of ClistManager, rejects checklists with the same name in the manager or empty names
     * Precondition: the name of {@code checklist} is trimmed.
     *
     * @param checklist the clist instance to be added
     * @return if the operation was successful
     */
    public boolean addClist(clist checklist){
        if (checklist.name.length() == 0){
            return false;
        }

        for (clist c : this.checklists){
            if (c.name == checklist.name){
                return false;
            }
        }
        this.checklists.add(checklist);
        return true;
    }

    public clist findChecklist(String name){
        return new clist("");
    }

    public item findItem(String name){
        return new item("");
    }

    public void loadFromFile(File f){
        try{
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                int last = 0;

                for (int i = 0; i < line.length(); i++){
                    char curr = line.charAt(i);

                    if (curr == ';'){
                        clist chList = new clist(line.substring(last, i));
                        this.addClist(chList);
                        last = i + 1;
                    }

                    else if(curr == '|'){
                        clist lastCheck = this.checklists.get(this.checklists.size() - 1);

                        //if item is in checked state, check the item, and trim the extra char
                        if (line.charAt(i - 1) == '`'){
                            lastCheck.addItem(line.substring(last, i - 1));
                            lastCheck.getLast().check();
                        }
                        else {
                            lastCheck.addItem(line.substring(last, i));
                        }
                        last = i + 1;
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("file not found");
            e.printStackTrace();
        }
    }

    public void writeToFile(File f){
        try{
            FileWriter writer = new FileWriter(f.getAbsolutePath(), true);

            for (clist c : this.checklists){
                String line = c.name + ";";

                for (item i : c.items){
                    line = line + i.name;

                    //add checked char if item is checked
                    if (i.checked){
                        line = line + "`";
                    }
                    line = line + "|";
                }
                line = line + "\n";
                writer.write(line);
            }

            writer.close();
        } catch (IOException e){
            System.out.println("something wrong happened in file writing");
            e.printStackTrace();
        }
    }

    public void removeAll(){
        this.checklists.clear();
    }
}
