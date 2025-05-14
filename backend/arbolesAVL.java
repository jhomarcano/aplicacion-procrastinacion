class Nodo {
    int valor;
    Nodo izquierda, derecha;
    int altura;

    Nodo(int d) {
        valor = d;
        altura = 1; // nuevo nodo es inicialmente de altura 1
    }
}

class arbolesAVL {
    private Nodo raiz;

    // Método para obtener la altura de un nodo
    private int altura(Nodo nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    // Método para obtener el factor de balance de un nodo
    private int factorBalance(Nodo nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierda) - altura(nodo.derecha);
    }

    // Rotaciones para mantener el equilibrio
    private Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.izquierda;
        Nodo T2 = x.derecha;

        // Realizar rotación
        x.derecha = y;
        y.izquierda = T2;

        // Actualizar alturas
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;

        // Retornar nuevo nodo raíz
        return x;
    }

    private Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.derecha;
        Nodo T2 = y.izquierda;

        // Realizar rotación
        y.izquierda = x;
        x.derecha = T2;

        // Actualizar alturas
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;

        // Retornar nuevo nodo raíz
        return y;
    }

    // Método para insertar un nuevo valor
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        // 1. Realiza la inserción normal
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertarRecursivo(nodo.derecha, valor);
        } else {
            return nodo; // Valores duplicados no se permiten
        }

        // 2. Actualiza la altura del ancestro nodo
        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));//se le quito un ")" que tenia de mas 

        // 3. Obtiene el factor de balance de este nodo
        int balance = factorBalance(nodo);

        // Si el nodo se vuelve desbalanceado, hay 4 casos

        // Caso Izquierda Izquierda
        if (balance > 1 && valor < nodo.izquierda.valor) {
            return rotacionDerecha(nodo);
        }

        // Caso Derecha Derecha
        if (balance < -1 && valor > nodo.derecha.valor) {
            return rotacionIzquierda(nodo);
        }

        // Caso Izquierda Derecha
        if (balance > 1 && valor > nodo.izquierda.valor) {
            nodo.izquierda = rotacionIzquierda(nodo.izquierda);
            return rotacionDerecha(nodo);
        }

        // Caso Derecha Izquierda
        if (balance < -1 && valor < nodo.derecha.valor) {
            nodo.derecha = rotacionDerecha(nodo.derecha);
            return rotacionIzquierda(nodo);
        }

        // Retornar el nodo (sin cambios)
        return nodo;
    }

    // Método para mostrar el árbol en orden
    public void inOrden() {
        inOrdenRecursivo(raiz);
    }

    private void inOrdenRecursivo(Nodo nodo) {
        if (nodo != null) {
            inOrdenRecursivo(nodo.izquierda);
            System.out.print(nodo.valor + " ");
            inOrdenRecursivo(nodo.derecha);
        }
    }

    public static void main(String[] args) {
        arbolesAVL arbol = new arbolesAVL();

        int[] valores = {10, 20, 30, 40, 50, 25};
        for (int valor : valores) {
            arbol.insertar(valor);
        }

        System.out.println("Recorrido en orden del árbol AVL:");
        arbol.inOrden(); // Salida: 10 20 25 30 40 50
    }
}
