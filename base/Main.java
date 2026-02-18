public class Main {
    public static void main(String[] args) throws Exception {
        BinaryTree binary_tree = new BinaryTree(args[0]);
        StringWriter string_writer = new StringWriter(args[2]);
        StringReader string_reader = new StringReader(args[1]);

        for (int index = 0; index < string_reader.length(); index++) {
            if (binary_tree.contains_string(string_reader.get(index))) {
                string_writer.write(true);
            } else {
                string_writer.write(false);
            }
        }

        string_writer.close();
    }
}
