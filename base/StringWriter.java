import java.io.*;

public class StringWriter {
    private FileWriter writer;
    public StringWriter(String filePath) {
        try {
            writer = new FileWriter(filePath);
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void write(boolean result) {
        try {// dependiendo cuakl sea el resultado imprime el true o el false
            
            if (result) {
                writer.write("true\n");// el \n para que cada resultado quede en lineas diferentes
            } else {
                writer.write("false\n");
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void close() {//mandar a llamar el archivo para que se guarde 
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}