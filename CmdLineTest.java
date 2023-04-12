import javax.sound.sampled.SourceDataLine;

public class CmdLineTest {
    public static void main(String[] args) {
        CommandLine c = new CommandLine();
        String[] str = "x 1 2".split(" ");
        System.out.println(c.isValid(str));
    }
}
