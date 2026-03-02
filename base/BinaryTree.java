import java.util.ArrayList;
import java.io.*;

public class BinaryTree {
    // Crearemos aqui los nodos
    private Node root;
    // Usaremos este encapsulado para usar el archivo Node.java

    public BinaryTree(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            reader.readLine(); // alfabeto

            String linea = reader.readLine();
            if (linea == null) {
                throw new Exception("No hay una raiz en el archivo");
            }

            String[] partesRaiz = linea.trim().split(",");

            char caracterRaiz = partesRaiz[0].charAt(0);
            boolean tieneIzquierda = partesRaiz[1].equals("1");
            boolean tieneDerecha = partesRaiz[2].equals("1");

            root = new Node(caracterRaiz);

            ArrayList<Node> nodosActuales = new ArrayList<>();
            ArrayList<boolean[]> banderasActuales = new ArrayList<>();

            nodosActuales.add(root);
            banderasActuales.add(new boolean[]{tieneIzquierda, tieneDerecha});


            ArrayList<String> bloques = new ArrayList<>();

            
            while ((linea = reader.readLine()) != null) {
            linea = linea.trim();
            if (!linea.isEmpty()) {
                String[] partes = linea.split("\\|");
                for (String b : partes) {
                    bloques.add(b.trim());
                }
            }
        }

        reader.close();

        int indice = 0;

        // 4) Construcción por niveles respetando los flags
        while (!nodosActuales.isEmpty()) {

            ArrayList<Node> nodosSiguientes = new ArrayList<>();
            ArrayList<boolean[]> banderasSiguientes = new ArrayList<>();

            for (int i = 0; i < nodosActuales.size(); i++) {

                Node padre = nodosActuales.get(i);
                boolean tieneIzq = banderasActuales.get(i)[0];
                boolean tieneDer = banderasActuales.get(i)[1];

                // Hijo izquierdo
                if (tieneIzq) {
                    if (indice >= bloques.size()) {
                        throw new Exception("Faltan nodos (hijo izquierdo).");
                    }

                    String[] datos = bloques.get(indice++).split(",");
                    Node hijoIzq = new Node(datos[0].charAt(0));
                    padre.left = hijoIzq;

                    nodosSiguientes.add(hijoIzq);
                    banderasSiguientes.add(new boolean[]{
                        datos[1].equals("1"),
                        datos[2].equals("1")
                    });
                }

                // Hijo derecho
                if (tieneDer) {
                    if (indice >= bloques.size()) {
                        throw new Exception("Faltan nodos (hijo derecho).");
                    }

                    String[] datos = bloques.get(indice++).split(",");
                    Node hijoDer = new Node(datos[0].charAt(0));
                    padre.right = hijoDer;

                    nodosSiguientes.add(hijoDer);
                    banderasSiguientes.add(new boolean[]{
                        datos[1].equals("1"),
                        datos[2].equals("1")
                    });
                }
            }

            nodosActuales = nodosSiguientes;
            banderasActuales = banderasSiguientes;
        }

        if (indice != bloques.size()) {
            throw new Exception("Sobran nodos en el archivo.");
        }

    } catch (Exception e) {
        System.out.println("Error al construir el árbol: " + e.getMessage());
        root = null;
    }
}

    public boolean contains_string(String string) {
        // Evalua si un arbol contiene otro sub arbol que pueda generar la cadena
        // proporcionada
        if (root == null || string == null || string.length() == 0) {
            return false;
        }
        // Parte recursiva
        return buscar(root, string, 0);
    }
    // Funcion recursiva
    private boolean buscar(Node nodo, String str, int i) {
        // Evaluaremos cada caso posible
        // Si el nodo no existe ahi muere
        if (nodo == null) {
            return false;
        }
        // Si el carácter no coincide, ahi muere
        if (nodo.value != str.charAt(i)) {
            return false;
        }
        // Si es ultimo caracter y no coincide, terminamos con exito
        if (i == str.length() - 1) {
            return true;
        }

        /*
         * Explicación rapida de todo esto:
         * - Desde el nodo actual intentamos hacer el resto de la cadena, primero
         * probamos por la izquierda
         * - Si ese camino logra completar la cadena regresamos al true con nuestro
         * operador ternario
         * - Si no funciona se va por la derecha (tambien el operador ternario)
         * 
         * Ejemplo: Ver si jala "abe"
         * a - a coincide con "a"
         * / \ - Intenta irse a la izquierda a ver si coincide con "b"
         * b c - Intenta irse a la izquierda y como d no coincide con "e" retorna false
         * / \
         * d e - Intenta derecha a ver si coincide con "e"
         * - Como es el ultimo caracter ahi muere
         */

        boolean izquierda = buscar(nodo.left, str, i + 1);
        // Primero intenta por la izquierda si funciona regresa a true sino a la derecha
        return izquierda ? true : buscar(nodo.right, str, i + 1);

    }

    // Temporal
    public void imprimirCaminos(int maxLen) {
    imprimirRec(root, "", maxLen);
}

private void imprimirRec(Node n, String pref, int maxLen) {
    if (n == null) return;

    String nuevo = pref + n.value;
    System.out.println(nuevo);

    if (nuevo.length() == maxLen) return;

    imprimirRec(n.left, nuevo, maxLen);
    imprimirRec(n.right, nuevo, maxLen);
}

}
