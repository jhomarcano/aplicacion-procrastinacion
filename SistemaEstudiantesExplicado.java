import java.util.*; 
import java.util.regex.Pattern; // Importa la clase Pattern para trabajar con expresiones regulares

// Nodo de la lista enlazada
class Nodo<T> {
    // Atributos de la lista
    T dato; // Almacena el dato del nodo
    Nodo<T> siguiente; // Puntero al siguiente nodo en la lista

    // Constructor 
    public Nodo(T dato) {
        this.dato = dato; // Inicializa el dato del nodo
        this.siguiente = null; // Inicializa el puntero siguiente como null
    }
}

// Clase de la lista enlazada
class ListaEnlazada<T> {
    // Atributos de la clase
    private Nodo<T> cabeza; // Puntero al primer nodo de la lista
    private int tamaño; 

    // Constructor
    public ListaEnlazada() {
        cabeza = null; // Inicializa la cabeza como null
        tamaño = 0; // Inicializa el tamaño en 0
    }

    // Método para agregar un nuevo dato
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato); // Crea un nuevo nodo con el dato proporcionado
        if (cabeza == null) { // Si la lista está vacía
            cabeza = nuevoNodo; // Establece el nuevo nodo como la cabeza
        } else {
            Nodo<T> actual = cabeza; // Comienza desde la cabeza
            // Recorre la lista hasta el último nodo
            while (actual.siguiente != null) {
                actual = actual.siguiente; // Avanza al siguiente nodo
            }
            actual.siguiente = nuevoNodo; // Establece el nuevo nodo como el siguiente del último nodo
        }
        tamaño++; // Incrementa el tamaño de la lista
    }

    // Método para convertir la lista enlazada a un arraylist
    public List<T> toList() {
        List<T> lista = new ArrayList<>(); // Crea una nueva lista
        Nodo<T> actual = cabeza; // Comienza desde la cabeza
        // Recorre la lista y agrega cada dato a la lista
        while (actual != null) {
            lista.add(actual.dato); // Agrega el dato del nodo actual a la lista
            actual = actual.siguiente; // Avanza al siguiente nodo
        }
        return lista; // Devuelve la lista
    }

    // Método para obtener el tamaño de la lista
    public int tamaño() {
        return tamaño; // Devuelve el tamaño actual de la lista
    }
}

// Clase para la pila
class Pila<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>(); // Inicializa la lista enlazada para almacenar elementos

    // Método para agregar un elemento a la pila
    public void push(T elemento) {
        elementos.agregar(elemento); // Agrega el elemento a la lista
    }

    // Método para eliminar y devolver el elemento superior de la pila
    public T pop() {
        if (elementos.tamaño() == 0) { // Si la pila está vacía
            throw new EmptyStackException(); // Lanza una excepción
        }
        
        List<T> lista = elementos.toList(); // Convierte la lista enlazada a una lista
        T ultimo = lista.get(lista.size() - 1); // Obtiene el último elemento (superior de la pila)
        elementos = new ListaEnlazada<>(); // Reinicia la lista enlazada
        // Vuelve a agregar todos los elementos excepto el último
        for (int i = 0; i < lista.size() - 1; i++) {
            elementos.agregar(lista.get(i)); // Agrega los elementos restantes
        }
        return ultimo; // Devuelve el último elemento
    }

    // Método para verificar si la pila está vacía
    public boolean estaVacia() {
        return elementos.tamaño() == 0; // Devuelve true si el tamaño es 0
    }
}

// Clase para la cola
class Cola<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>(); // Inicializa la lista enlazada para almacenar elementos

    // Método para agregar un elemento a la cola
    public void encolar(T elemento) {
        elementos.agregar(elemento); // Agrega el elemento a la lista
    }

    // Método para eliminar y devolver el primer elemento de la cola
    public T desencolar() {
        if (elementos.tamaño() == 0) { // Si la cola está vacía
            throw new NoSuchElementException(); // Lanza una excepción
        }
        
        List<T> lista = elementos.toList(); // Convierte la lista enlazada a una lista
        T primero = lista.get(0); // Obtiene el primer elemento
        elementos = new ListaEnlazada<>(); // Reinicia la lista enlazada
        // Vuelve a agregar todos los elementos excepto el primero
        for (int i = 1; i < lista.size(); i++) {
            elementos.agregar(lista.get(i)); // Agrega los elementos restantes
        }
        return primero; // Devuelve el primer elemento
    }

    // Método para verificar si la cola está vacía
    public boolean estaVacia() {
        return elementos.tamaño() == 0; // Devuelve true si el tamaño es 0
    }
}

// Clase para representar a un estudiante
class Estudiante {
    private String usuario; // Almacena el nombre de usuario
    private String email; // Almacena el email
    private String contraseña; // Almacena la contraseña
    private ListaEnlazada<HorarioClase> horario; // Almacena el horario de clases
    private Cola<Tarea> tareasPendientes; // Almacena las tareas pendientes
    private Pila<Tarea> tareasCompletadas; // Almacena las tareas completadas

    // Constructor de la clase Estudiante
    public Estudiante(String usuario, String email, String contraseña) {
        // Verifica que el email tenga el formato correcto
        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@poligran\\.edu\\.co$", email)) {
            throw new IllegalArgumentException("Email debe ser @poligran.edu.co"); // Lanza excepción si el email es inválido
        }
        
        this.usuario = usuario; // Inicializa el usuario
        this.email = email; // Inicializa el email
        this.contraseña = contraseña; // Inicializa la contraseña
        this.horario = new ListaEnlazada<>(); // Inicializa el horario
        this.tareasPendientes = new Cola<>(); // Inicializa las tareas pendientes
        this.tareasCompletadas = new Pila<>(); // Inicializa las tareas completadas
    }

    // Método para agregar una clase al horario
    public void agregarClase(HorarioClase clase) {
        horario.agregar(clase); // Agrega la clase al horario
    }

    // Método para agregar una tarea a las tareas pendientes
    public void agregarTarea(Tarea tarea) {
        tareasPendientes.encolar(tarea); // Encola la tarea
    }

    // Método para completar una tarea
    public void completarTarea() {
        if (!tareasPendientes.estaVacia()) { // Si hay tareas pendientes
            Tarea tarea = tareasPendientes.desencolar(); // Desencola la tarea
            tareasCompletadas.push(tarea); // Agrega la tarea a las completadas
        }
    }

    // Métodos para obtener información del estudiante
    public String getUsuario() { return usuario; }
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    public List<HorarioClase> getHorario() { return horario.toList(); }
    
    // Método para obtener las tareas pendientes
    public List<Tarea> getTareasPendientes() { 
        List<Tarea> lista = new ArrayList<>(); // Crea una lista para almacenar las tareas
        Cola<Tarea> temp = new Cola<>(); // Cola temporal para restaurar las tareas
        
        // Desencola todas las tareas y las agrega a la lista y a la cola temporal
        while (!tareasPendientes.estaVacia()) {
            Tarea t = tareasPendientes.desencolar(); // Desencola la tarea
            lista.add(t); // Agrega la tarea a la lista
            temp.encolar(t); // Encola la tarea en la cola temporal
        }
        
        this.tareasPendientes = temp; // Restaura las tareas pendientes
        return lista; // Devuelve la lista de tareas
    }
}

// Clase para representar el horario de una clase
class HorarioClase {
    private String dia; // Almacena el día de la clase
    private String horaInicio; // Almacena la hora de inicio
    private String horaFin; // Almacena la hora de fin
    private String materia; // Almacena la materia

    // Constructor de la clase HorarioClase
    public HorarioClase(String dia, String horaInicio, String horaFin, String materia) {
        this.dia = dia; // Inicializa el día
        this.horaInicio = horaInicio; // Inicializa la hora de inicio
        this.horaFin = horaFin; // Inicializa la hora de fin
        this.materia = materia; // Inicializa la materia
    }

    // Métodos para obtener información del horario
    public String getDia() { return dia; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public String getMateria() { return materia; }
}

// Clase para representar una tarea
class Tarea {
    private String descripcion; // Almacena la descripción de la tarea
    private int prioridad; // Almacena la prioridad de la tarea

    // Constructor de la clase Tarea
    public Tarea(String descripcion, int prioridad) {
        this.descripcion = descripcion; // Inicializa la descripción
        this.prioridad = prioridad; // Inicializa la prioridad
    }

    // Métodos para obtener información de la tarea
    public String getDescripcion() { return descripcion; }
    public int getPrioridad() { return prioridad; }
}

// Clase principal del sistema de estudiantes
public class SistemaEstudiantesExplicado {
    private Map<String, Estudiante> estudiantes = new HashMap<>(); // Mapa para almacenar estudiantes por usuario
    private Scanner scanner = new Scanner(System.in); // Scanner para leer la entrada del usuario

    // Método principal
    public static void main(String[] args) {
        SistemaEstudiantesExplicado sistema = new SistemaEstudiantesExplicado(); // Crea una instancia del sistema
        sistema.iniciar(); // Inicia el sistema
    }

    // Método para iniciar el sistema
    public void iniciar() {
        while (true) { // Bucle infinito para mostrar el menú
            System.out.println("\nSistema de Planificación Estudiantil");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine()); // Lee la opción del usuario

                switch (opcion) {
                    case 1:
                        registrarEstudiante(); // Llama al método para registrar estudiante
                        break;
                    case 2:
                        iniciarSesion(); // Llama al método para iniciar sesión
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema..."); // Mensaje de salida
                        return; // Sale del bucle
                    default:
                        System.out.println("Opción no válida"); // Mensaje de error
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido"); // Mensaje de error si la entrada no es un número
            }
        }
    }

    // Método para registrar un nuevo estudiante
    private void registrarEstudiante() {
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine(); // Lee el nombre de usuario
        
        // Verifica si el usuario ya existe
        if (estudiantes.containsKey(usuario)) {
            System.out.println("Error: El usuario ya está registrado"); // Mensaje de error
            return; // Sale del método
        }
        
        System.out.print("Ingrese email (@poligran.edu.co): ");
        String email = scanner.nextLine(); // Lee el email
        
        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine(); // Lee la contraseña

        try {
            Estudiante estudiante = new Estudiante(usuario, email, contraseña); // Crea un nuevo estudiante
            estudiantes.put(usuario, estudiante); // Agrega el estudiante al mapa
            System.out.println("Estudiante registrado exitosamente!"); // Mensaje de éxito
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage()); // Mensaje de error si el email es inválido
        }
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine(); // Lee el nombre de usuario
        
        // Verifica si el usuario existe
        if (!estudiantes.containsKey(usuario)) {
            System.out.println("Error: El usuario no está registrado"); // Mensaje de error
            return; // Sale del método
        }
        
        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine(); // Lee la contraseña

        Estudiante estudiante = estudiantes.get(usuario); // Obtiene el estudiante del mapa
        if (estudiante.getContraseña().equals(contraseña)) { // Verifica si la contraseña es correcta
            menuEstudiante(estudiante); // Llama al menú del estudiante
        } else {
            System.out.println("Error: Contraseña incorrecta"); // Mensaje de error
        }
    }

    // Método para mostrar el menú del estudiante
    private void menuEstudiante(Estudiante estudiante) {
        while (true) { // Bucle infinito para mostrar el menú
            System.out.println("\nBienvenido, " + estudiante.getUsuario()); // Mensaje de bienvenida
            System.out.println("1. Ver horario");
            System.out.println("2. Agregar clase");
            System.out.println("3. Ver tareas pendientes");
            System.out.println("4. Agregar tarea");
            System.out.println("5. Completar tarea");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine()); // Lee la opción del usuario

                switch (opcion) {
                    case 1:
                        verHorario(estudiante); // Llama al método para ver el horario
                        break;
                    case 2:
                        agregarClase(estudiante); // Llama al método para agregar una clase
                        break;
                    case 3:
                        verTareasPendientes(estudiante); // Llama al método para ver tareas pendientes
                        break;
                    case 4:
                        agregarTarea(estudiante); // Llama al método para agregar una tarea
                        break;
                    case 5:
                        completarTarea(estudiante); // Llama al método para completar una tarea
                        break;
                    case 6:
                        return; // Sale del método y cierra sesión
                    default:
                        System.out.println("Opción no válida"); // Mensaje de error
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido"); // Mensaje de error si la entrada no es un número
            }
        }
    }

    // Método para ver el horario del estudiante
    private void verHorario(Estudiante estudiante) {
        List<HorarioClase> horario = estudiante.getHorario(); // Obtiene el horario del estudiante
        if (horario.isEmpty()) { // Si el horario está vacío
            System.out.println("\nNo hay clases en el horario"); // Mensaje de información
            return; // Sale del método
        }
        
        System.out.println("\nHorario de clases:"); // Mensaje de encabezado
        // Recorre el horario y muestra cada clase
        for (HorarioClase clase : horario) {
            System.out.printf("%s: %s - %s (%s)\n", 
                clase.getDia(), clase.getHoraInicio(), 
                clase.getHoraFin(), clase.getMateria());
        }
    }

    // Método para agregar una clase al horario del estudiante
    private void agregarClase(Estudiante estudiante) {
        System.out.print("Día (Lunes-Viernes): ");
        String dia = scanner.nextLine(); // Lee el día
        
        System.out.print("Hora inicio (HH:MM): ");
        String horaInicio = scanner.nextLine(); // Lee la hora de inicio
        
        System.out.print("Hora fin (HH:MM): ");
        String horaFin = scanner.nextLine(); // Lee la hora de fin
        
        System.out.print("Materia: ");
        String materia = scanner.nextLine(); // Lee la materia

        HorarioClase nuevaClase = new HorarioClase(dia, horaInicio, horaFin, materia); // Crea una nueva clase
        estudiante.agregarClase(nuevaClase); // Agrega la clase al horario del estudiante
        System.out.println("Clase agregada al horario!"); // Mensaje de éxito
    }

    // Método para ver las tareas pendientes del estudiante
    private void verTareasPendientes(Estudiante estudiante) {
        List<Tarea> tareas = estudiante.getTareasPendientes(); // Obtiene las tareas pendientes
        if (tareas.isEmpty()) { // Si no hay tareas pendientes
            System.out.println("\nNo hay tareas pendientes"); // Mensaje de información
            return; // Sale del método
        }
        
        System.out.println("\nTareas pendientes:"); // Mensaje de encabezado
        int i = 1; // Contador para enumerar las tareas
        // Recorre las tareas y muestra cada una
        for (Tarea tarea : tareas) {
            System.out.printf("%d. %s (Prioridad: %d)\n", 
                i++, tarea.getDescripcion(), tarea.getPrioridad());
        }
    }

    // Método para agregar una tarea al estudiante
    private void agregarTarea(Estudiante estudiante) {
        System.out.print("Descripción de la tarea: ");
        String descripcion = scanner.nextLine(); // Lee la descripción de la tarea
        
        System.out.print("Prioridad (1-10, 1=alta): ");
        try {
            int prioridad = Integer.parseInt(scanner.nextLine()); // Lee la prioridad
            if (prioridad < 1 || prioridad > 10) { // Verifica que la prioridad esté en el rango correcto
                System.out.println("Error: La prioridad debe estar entre 1 y 10"); // Mensaje de error
                return; // Sale del método
            }
            
            Tarea nuevaTarea = new Tarea(descripcion, prioridad); // Crea una nueva tarea
            estudiante.agregarTarea(nuevaTarea); // Agrega la tarea a las tareas pendientes
            System.out.println("Tarea agregada!"); // Mensaje de éxito
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        }
    }
    private void completarTarea(Estudiante estudiante) {
        if (estudiante.getTareasPendientes().isEmpty()) {
            System.out.println("No hay tareas pendientes para completar");
            return;
        }
        
        estudiante.completarTarea();
        System.out.println("Tarea completada!");
    }
}
