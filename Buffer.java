import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Buffer {



    // Data Members

    String filespec;
    boolean dirty;
    public DLList <String> list;



    // Constructors

    public Buffer() {
        setFile("");
        dirty = false;
        list = new DLList <String> ();
    }

    public Buffer(String fileName) {
        setFile(fileName);
        dirty = false;
        list = new DLList <String> ();
    }



    // Member Functions

    // setFile method to set the file name
    public boolean setFile(String filespec) {
        if (!filespec.isEmpty()) {
            this.filespec = filespec;
            return true;
        }
        this.filespec = "";
        return false;
    }

    // getFile method to get the file name
    public String getFile() {
        return filespec;
    }

    // setDirty method to set the buffer as dirty
    public boolean setDirty(boolean dirty) {
        this.dirty = dirty;
        return true;
    }

    // isDirty method to check if the buffer is dirty
    public boolean isDirty() {
        return dirty;
    }

    // isEmpty method to check if the buffer is empty
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // read method to read in the file and store it in the buffer
    public boolean read(String filespec) {
        list.clear();
        String line = "";
        try {
            setFile(filespec);
            BufferedReader reader = new BufferedReader(new FileReader(this.filespec));
            while ((line = reader.readLine()) != null) {
                list.insertLast(line);
            }
            reader.close();
            setDirty(false);
            return true;
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    // write method to write the buffer to the file
    public boolean write() {
        try {
            if (isDirty()) {
                System.out.println("Writing to file...");
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.filespec));
                for (int i = 0; i < list.getSize(); i++) {
                    list.seek(i);
                    writer.write(list.getData());
                    writer.newLine();
                }
                writer.close();
                setDirty(false);
                return true;
            } else return false;
            
        } catch (IOException e) {
            return false;
        }
    }

    // clear method to clear the buffer
    public void clear() {
        list.clear();
    }

}
