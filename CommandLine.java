import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CommandLine {
    // Data Members
    String[] commands = {"h", "r", "w", "f", "q", "q!", "t", "b", "g", "-", "+", "=", "n", "#", "p", 
                        "pr", "?", "/", "s", "sr", "d", "dr", "c", "cr", "pa", "pb", "ia", "ic", "ib"};
    List<String> commandsList = Arrays.asList(commands);
    String command;
    String[] arguments;

    // read method to read in the command line and split into command and arguments and store them
    public void read(BufferedReader reader) {
        String input = "";
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] inputArray = input.split(" ");

        if (isValid(inputArray)) {
            command = inputArray[0];
            arguments = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        } else {
            command = "error";
            arguments = new String[0];
        }
    }

    // isValid method to check if the command is valid
    public Boolean isValid(String[] input) {
        return (commandsList.contains(input[0]) && input.length <= 5);
    }

    // getCommand method to return the command
    public String getCommand() {
        return command;
    }

    // getArguments method to return the arguments
    public String[] getArguments() {
        return arguments;
    }
}
