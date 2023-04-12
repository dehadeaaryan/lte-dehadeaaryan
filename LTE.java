import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class LTE {

    // Data Members
    static CommandLine cmdl;
    static Buffer buffer;
    static Buffer clipboard;
    static BufferedReader reader;
    static boolean run;
    static boolean lineNumVisible;
    static int commandNum;

    // Main Method
    public static void main(String[] args) {

        // Initialize reader for entire program
        reader = new BufferedReader(new InputStreamReader(System.in));

        // create Buffers
        cmdl = new CommandLine();
        buffer = new Buffer();
        clipboard = new Buffer();

        // Initialize variables
        run = true;
        lineNumVisible = true;

        // Check if single extra argument was passed and set file.
        if (args.length == 1) {
            buffer.setFile(args[0]);
            buffer.read(buffer.getFile());
        } else if (args.length > 1) {
            System.out.println("Usage: LTE [filename]");
            run = false;
        }

        // Main loop
        while (run) {
            process_command();
        }
    }

    // Helper command to read commands form command line class
    public static void readCommand() {
        cmdl.read(reader);
    }

    // Helper command to create prompt
    public static void prompt() {
        String output = "LTE:";

        output = output + buffer.getFile();
        output += ":";
        output = output + commandNum;
        output += ":";
        if (lineNumVisible) output += (buffer.list.getIndex() + 1);
        if (buffer.isDirty()) output += "*";
        output += "> ";
        System.out.print(output);
    }

    // Process command
    public static void process_command() {

        // Increment the number of commands
        commandNum++;

        // Print prompt, read command, and split into command and arguments
        prompt();
        readCommand();
        String command = cmdl.getCommand();
        String[] arguments = cmdl.getArguments();

        // Check if command is valid
        if (cmdl.getCommand() == "error") {
            System.out.println("Invalid command");
            return;
        } 

        // Switch statement to handle commands
        switch (command) {
            case "h":
                if (arguments.length > 0) {
                    System.out.println("Invalid command");
                    return;
                }
                help();
                break;
            case "r":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                }
                readFile(arguments[0]);
                break;
            case "w":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                    return;
                }
                writeFile(arguments[0]);
                break;
            case "f":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                    return;
                }
                changeFile(arguments[0]);
                break;
            case "q":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                quit();
                break;
            case "q!":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                forceQuit();
                break;
            case "t":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                top();
                break;
            case "b":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                bottom();
                break;
            case "g":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                    return;
                }
                goTo(Integer.parseInt(arguments[0]));
                break;
            case "-":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                goToPrev();
                break;
            case "+":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                goToNext();
                break;
            case "n":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                toggleLineNum();
                break;
            case "p":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                printCurrentLine();
                break;
            case "pr":
                if (arguments.length != 2) {
                    System.out.println("Invalid arguments");
                    return;
                }
                printRange(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                break;
            case "?":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                    return;
                }
                searchBackwards(arguments[0]);
                break;
            case "/":
                if (arguments.length > 1) {
                    System.out.println("Too many arguments");
                    return;
                } else if (arguments.length == 0) {
                    System.out.println("Too few arguments");
                    return;
                }
                searchForwards(arguments[0]);
                break;
            case "s":
                if (arguments.length != 2) {
                    System.out.println("Invalid arguments");
                    return;
                }
                substitute(arguments[0], arguments[1]);
                break;
            case "sr":
                if (arguments.length != 4) {
                    System.out.println("Invalid arguments");
                    return;
                }
                substituteRange(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), arguments[2], arguments[3]);
                break;
            case "d":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                deleteCurrentLine();
                break;
            case "dr":
                if (arguments.length != 2) {
                    System.out.println("Invalid arguments");
                    return;
                }
                deleteRange(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                break;
            case "c":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                copy();
                break;
            case "cr":
                if (arguments.length != 2) {
                    System.out.println("Invalid arguments");
                    return;
                }
                copyRange(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                break;
            case "pa":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                pasteAbove();
                break;
            case "pb":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                pasteBelow();
                break;
            case "ia":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                insertAbove();
                break;
            case "ic":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                insertCurrent();
                break;
            case "ib":
                if (arguments.length > 0) {
                    System.out.println("Too many arguments");
                    return;
                }
                insertBelow();
                break;
            default:
                return;
        }
    }







    // Display help menu
    static void help() {
        System.out.println("h: Display help\n" +
                "r: Read a file into the current buffer\n" +
                "w: Write the current buffer to a file on disk\n" +
                "f: Change the name of the current buffer\n" +
                "q: Quit the line editor\n" +
                "q!: Quit the line editor without saving\n" +
                "t: Go to the first line in the buffer\n" +
                "b: Go to the last line in the buffer\n" +
                "g: Go to line num in the buffer\n" +
                "-: Go to the previous line\n" +
                "+: Go to the next line\n" +
                "n: Toggle line number displayed\n" +
                "p: Print the current line\n" +
                "pr: Print several lines\n" +
                "?: Search backwards for a pattern\n" +
                "/: Search forwards for a pattern\n" +
                "s: Substitute all occurrences of text1 with text2 on current line\n" +
                "sr: Substitute all occurrences of text1 with text2 between start and stop\n" +
                "d: Delete the current line from buffer and copy into the clipboard (CUT)\n" +
                "dr: Delete several lines from buffer and copy into the clipboard (CUT)\n" +
                "c: Copy current line into clipboard (COPY)\n" +
                "cr: Copy lines between start and stop into the clipboard (COPY)\n" +
                "pa: Paste the contents of the clipboard above the current line (PASTE)\n" +
                "pb: Paste the contents of the clipboard below the current line (PASTE)\n" +
                "ia: Insert new lines of text above the current line until ”.” appears on its own line\n" +
                "ic: Insert new lines of text at the current line until ”.” appears on its own line (REPLACE current line)\n" +
                "ib: Insert new lines of text after the current line until ”.” appears on its own line");
    }

    // Read a file into the current buffer
    static void readFile(String filespec) {
        if (!buffer.isDirty()) {
            buffer.setFile(filespec);
            buffer.read(filespec);
        } else {
            System.out.print("Write Buffer to file? (y/n)");
            try {
                String input = reader.readLine();
                if (input.equals("y")) {
                    buffer.write();
                } else if (input.equals("n")) {
                    buffer.setFile(filespec);
                    buffer.read(filespec);
                } else {
                    System.out.println("==>> ERROR <<==");
                }
            } catch (IOException e) {
                System.out.println("==>> ERROR <<==");
            }
        }
    }

    // Write the current buffer to a file on disk
    static void writeFile(String filespec) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            buffer.setFile(filespec);
            buffer.write();
        }
    }

    // Change the name of the current buffer
    static void changeFile(String filespec) {
        buffer.setFile(filespec);
    }

    // Quit the line editor
    static void quit() {
        if (buffer.isEmpty()) {
            run = false;
        } else {
            if (buffer.isDirty()) {
                System.out.print("Write Buffer to file? (y/n)");
                try {
                    String input = reader.readLine();
                    if (input.equals("y")) {
                        buffer.write();
                        run = false;
                    } else if (input.equals("n")) {
                        run = false;
                    } else {
                        System.out.println("==>> INVALID INPUT <<==");
                    }
            
                } catch (IOException e) {
                    System.out.println("==>> ERROR <<==");
                }
            } else {
                run = false;
            }
        }
    }

    // Quit the line editor without saving
    static void forceQuit() {
        run = false;
    }

    // Go to the first line in the buffer
    static void top() {
        if (!buffer.list.seekFirst()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        }
    }

    // Go to the last line in the buffer
    static void bottom() {
        if (!buffer.list.seekLast()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        }
    }

    // Go to line num in the buffer
    static void goTo(int line) {
        if (!buffer.list.seek(line - 1)) {
            System.out.println("==>> RANGE ERROR - num MUST BE [1..n] <<==");
        }
    }

    // Go to the previous line
    static void goToPrev() {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            if (!buffer.list.seekPrevious()) {
                System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
            }
        }
    }

    // Go to the next line
    static void goToNext() {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            if (!buffer.list.seekNext()) {
                System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
            }
        }
    }

    // Toggle line number displayed
    static void toggleLineNum() {
        lineNumVisible = !lineNumVisible;
    }

    // Print the current line
    static void printCurrentLine() {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            int index = buffer.list.getIndex();
            String line = buffer.list.getData();
            if (lineNumVisible) {
                System.out.println((index + 1) + ": " + line);
            } else {
                System.out.println(line);
            }
        }
    }

    // Print several lines
    static void printRange(int start, int stop) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            int oldIndex = buffer.list.getIndex();
            if (buffer.list.seek(start - 1) && stop >= start && stop <= buffer.list.getSize()) {
                for (int i = start - 1; i <= stop - 1; i++) {
                    int index = buffer.list.getIndex();
                    String line = buffer.list.getData();
                    if (lineNumVisible) {
                        System.out.println((index + 1) + ": " + line);
                    } else {
                        System.out.println(line);
                    }
                    if (!buffer.list.seekNext()) {
                        break;
                    }
                }
                buffer.list.seek(oldIndex);
            } else {
                System.out.println("==>> RANGE ERROR - num MUST BE [1..n] <<==");
            }
        }
    }

    // Search backwards for a pattern
    static void searchBackwards(String pattern) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else if (buffer.list.getIndex() == 0) {
            System.out.println("==>> ALREADY AT TOP OF BUFFER <<==");
        } else {
            if (buffer.list.seekPrevious()) {
                do {
                    if (buffer.list.getData().contains(pattern)) {
                        return;
                    }
                } while (buffer.list.seekPrevious());
            } else {
                System.out.println("==>> STRING pattern NOT FOUND <<==");
            }
        }
    }

    // Search forwards for a pattern
    static void searchForwards(String pattern) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else if (buffer.list.atLast()) {
            System.out.println("==>> ALREADY AT BOTTOM OF BUFFER <<==");
        } else {
            if (buffer.list.seekNext()) {
                do {
                    if (buffer.list.getData().contains(pattern)) {
                        return;
                    }
                } while (buffer.list.seekNext());
            } else {
                System.out.println("==>> STRING pattern NOT FOUND <<==");
            }
        }
    }

    // Substitute a pattern with a replacement
    static void substitute(String text1, String text2) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            buffer.setDirty(true);
            int index = buffer.list.getIndex();
            boolean found = false;
            buffer.list.seekFirst();
            while (!buffer.list.atLast()) {
                String line = buffer.list.getData();
                if (line.contains(text1)) {
                    line = line.replace(text1, text2);
                    buffer.list.setData(line);
                    found = true;
                }
                buffer.list.seekNext();
            }
            if (buffer.list.atLast()) {
                String line = buffer.list.getData();
                if (line.contains(text1)) {
                    line = line.replace(text1, text2);
                    buffer.list.setData(line);
                    found = true;
                }
            }
            if (!found) System.out.println("==>> STRING text1 NOT FOUND <<==");

            buffer.list.seek(index);
        }
    }

    // substitute a pattern with a replacement in a range of lines
    static void substituteRange(int start, int stop, String text1, String text2) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            buffer.setDirty(true);
            int index = buffer.list.getIndex();
            boolean found = false;
            if (buffer.list.seek(start) && stop >= start && stop <= buffer.list.getSize()) {
                buffer.list.seek(start - 1);
                while (buffer.list.index != stop - 1) {
                    String line = buffer.list.getData();
                    if (line.contains(text1)) {
                        line = line.replace(text1, text2);
                        buffer.list.setData(line);
                        found = true;
                    }
                    buffer.list.seekNext();
                }
                if (buffer.list.atLast()) {
                    String line = buffer.list.getData();
                    if (line.contains(text1)) {
                        line = line.replace(text1, text2);
                        buffer.list.setData(line);
                        found = true;
                    }
                }
                if (!found) System.out.println("==>> STRING text1 NOT FOUND <<==");
    
                buffer.list.seek(index);
            } else {
                System.out.println("==>> RANGE ERROR - num MUST BE [1..n] <<==");
            }
        }
    }

    // Delte the current line
    static void deleteCurrentLine() {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            buffer.setDirty(true);
            clipboard.list.clear();
            clipboard.list.insert(buffer.list.getData());
            buffer.list.delete();
        }
    }

    // Delete a range of lines
    static void deleteRange(int start, int stop) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else if (start == 1 && stop == buffer.list.size - 1) {
            buffer.setDirty(true);
            buffer.list.clear();
        } else {
            buffer.setDirty(true);
            int index = buffer.list.getIndex();
            if (buffer.list.seek(start - 1) && stop >= start && stop <= buffer.list.getSize()) {
                clipboard.list.clear();
                for (int i = start - 1; i <= stop - 1; i++) {
                    clipboard.list.insertLast(buffer.list.getData());
                    buffer.list.delete();
                }
            } else {
                System.out.println("==>> RANGE ERROR - num MUST BE [1..n] <<==");
            }
            buffer.list.seek(index);
        }
    }

    // Copy a line
    static void copy() {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            clipboard.list.clear();
            clipboard.list.insertLast(buffer.list.getData());
        }
    }

    // Copy a range of lines
    static void copyRange(int start, int stop) {
        if (buffer.isEmpty()) {
            System.out.println("==>> BUFFER IS EMPTY <<==");
        } else {
            if (buffer.list.seek(start - 1) && stop >= start && stop <= buffer.list.getSize()) {
                clipboard.list.clear();
                for (int i = start - 1; i <= stop - 1; i++) {
                    clipboard.list.insertLast(buffer.list.getData());
                    buffer.list.seekNext();
                }
            } else {
                System.out.println("==>> RANGE ERROR - num MUST BE [1..n] <<==");
            }
        }
    }

    // Paste a line above the current line
    static void pasteAbove() {
        if (clipboard.isEmpty()) {
            System.out.println("==>> CLIPBOARD EMPTY <<==");
        } else {
            buffer.setDirty(true);
            clipboard.list.seekFirst();
            boolean wasAtLast = false;
            if (buffer.list.atLast()) {
                int oldIndex = buffer.list.getIndex();
                buffer.list.insertLast("dummy");
                buffer.list.seek(oldIndex);
                wasAtLast = true;
            }
            boolean ok = !clipboard.isEmpty();
            while (ok) {
                buffer.list.insert(clipboard.list.getData());
                buffer.list.seekNext();
                ok = clipboard.list.seekNext();
            }
            if (wasAtLast) {
                int oldIndex = buffer.list.getIndex();
                buffer.list.deleteLast();
                buffer.list.seek(oldIndex);
            }
        }
    }

    // Paste a line below the current line
    static void pasteBelow() {
        if (clipboard.isEmpty()) {
            System.out.println("==>> CLIPBOARD EMPTY <<==");
        } else {
            buffer.setDirty(true);
            clipboard.list.seekFirst();
            if (buffer.list.atLast()) {
                boolean ok = !clipboard.isEmpty();
                while (ok) {
                    buffer.list.insertLast(clipboard.list.getData());
                    ok = clipboard.list.seekNext();
                }
            } else {
                boolean ok = !clipboard.isEmpty();
                while (ok) {
                    buffer.list.seekNext();
                    buffer.list.insert(clipboard.list.getData());
                    ok = clipboard.list.seekNext();
                }
            }
        }
    }

    // Insert line using loop above
    static void insertAbove() {
        buffer.setDirty(true);
        boolean wasAtLast = false;
        if (buffer.list.atLast()) {
            int oldIndex = buffer.list.getIndex();
            buffer.list.insertLast("dummy");
            buffer.list.seek(oldIndex);
            wasAtLast = true;
        }
        while (true) {
            String line = readForInsert();
            buffer.list.insert(line);
            buffer.list.seekNext();
            if (line.contains(".")) break;
        }
        if (wasAtLast) {
            int oldIndex = buffer.list.getIndex();
            buffer.list.deleteLast();
            buffer.list.seek(oldIndex);
        }
    }

    // Insert line using loop at the current index
    static void insertCurrent() {
        buffer.setDirty(true);
        boolean wasAtLast = false;
        if (buffer.list.atLast()) {
            int oldIndex = buffer.list.getIndex();
            buffer.list.insertLast("dummy");
            buffer.list.seek(oldIndex);
            wasAtLast = true;
        }
        while (true) {
            String line = readForInsert();
            buffer.list.insert(line);
            buffer.list.seekNext();
            if (line.contains(".")) break;
        }
        buffer.list.delete();
        if (wasAtLast) {
            int oldIndex = buffer.list.getIndex();
            buffer.list.deleteLast();
            buffer.list.seek(oldIndex);
        }
    }

    // Insert line using loop below
    static void insertBelow() {
        buffer.setDirty(true);
        while (true) {
            String line = readForInsert();
            if (buffer.list.atLast()) {
                buffer.list.insertLast(line);
            } else {
                buffer.list.seekNext();
                buffer.list.insert(line);
            }
            if (line.contains(".")) break;
        }
    }

    // Helper for insert methods
    static String readForInsert() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("==>> ERROR <<==");
        }
        return line;
    }


}