public class test {
    public static void main(String[] args) {
        Buffer editor = new Buffer("test.txt");
        System.out.println(editor.read());
        if (editor.write("moo2")) {
            System.out.println(editor.read());
        }
        editor.quit();
    }
}
