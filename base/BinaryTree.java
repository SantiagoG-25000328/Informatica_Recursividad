public class BinaryTree {
    // Crearemos aqui los nodos
    private Node root;

    public BinaryTree(String filePath) {
        root = null;
        // Construir el arbol mas tarde
    }

    public boolean contains_string(String string) {
        // Evalua si un arbol contiene otro sub arbol que pueda generar la cadena
        // proporcionada
        if (root == null || string == null || string.length() == 0) {
            return false;
        }
        return buscar(root, string, 0);
    }

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
            - Desde el nodo actual intentamos hacer el resto de la cadena, primero probamos por la izquierda
            - Si ese camino logra completar la cadena regresamos al true con nuestro operador ternario
            - Si no funciona se va por la derecha (tambien el operador ternario)

            Ejemplo: Ver si jala "abe"
                a   - a coincide con "a"
               / \      - Intenta irse a la izquierda a ver si coincide con "b"
              b   c         - Intenta irse a la izquierda y como d no coincide con "e" retorna false
             / \ 
            d   e     - Intenta derecha a ver si coincide con "e"
                    - Como es el ultimo caracter ahi muere
         */

        boolean izquierda = buscar(nodo.left, str, i + 1);
        // Primero intenta por la izquierda si funciona regresa a true sino a la derecha
        return izquierda ? true : buscar(nodo.right, str, i + 1);
        
    }

}
