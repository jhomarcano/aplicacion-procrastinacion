import java.util.*;
import java.util.regex.Pattern;

class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamaño++;
    }

    public List<T> toList() {
        List<T> lista = new ArrayList<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            lista.add(actual.dato);
            actual = actual.siguiente;
        }
        return lista;
    }

    public int tamaño() {
        return tamaño;
    }
}

class Pila<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public void push(T elemento) {
        elementos.agregar(elemento);
    }

    public T pop() {
        if (elementos.tamaño() == 0) {
            throw new EmptyStackException();
        }

        List<T> lista = elementos.toList();
        T ultimo = lista.get(lista.size() - 1);
        elementos = new ListaEnlazada<>();
        for (int i = 0; i < lista.size() - 1; i++) {
            elementos.agregar(lista.get(i));
        }
        return ultimo;
    }

    public boolean estaVacia() {
        return elementos.tamaño() == 0;
    }
}

class Cola<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();

    public void encolar(T elemento) {
        elementos.agregar(elemento);
    }

    public T desencolar() {
        if (elementos.tamaño() == 0) {
            throw new NoSuchElementException();
        }

        List<T> lista = elementos.toList();
        T primero = lista.get(0);
        elementos = new ListaEnlazada<>();
        for (int i = 1; i < lista.size(); i++) {
            elementos.agregar(lista.get(i));
        }
        return primero;
    }

    public boolean estaVacia() {
        return elementos.tamaño() == 0;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        List<T> lista = elementos.toList();
        T primero = lista.get(0);
        elementos = new ListaEnlazada<>();
        for (int i = 1; i < lista.size(); i++) {
            elementos.agregar(lista.get(i));
        }
        return primero;
    }

    public void enqueue(T elemento) {
        elementos.agregar(elemento);
    }

    public boolean isEmpty() {
        return elementos.tamaño() == 0;
    }
}

class Estudiante {
    private String usuario;
    private String email;
    private String contraseña;
    private ListaEnlazada<HorarioClase> horario;
    private Cola<Tarea> tareasPendientes;
    private Pila<Tarea> tareasCompletadas;

    public Estudiante(String usuario, String email, String contraseña) {
        if (!Pattern.matches("^[A-Za-z0-9._%+-]+@poligran\\.edu\\.co$", email)) {
            throw new IllegalArgumentException("Email debe ser @poligran.edu.co");
        }

        this.usuario = usuario;
        this.email = email;
        this.contraseña = contraseña;
        this.horario = new ListaEnlazada<>();
        this.tareasPendientes = new Cola<>();
        this.tareasCompletadas = new Pila<>();
    }

    public void agregarClase(HorarioClase clase) {
        horario.agregar(clase);
    }

    public void agregarTarea(Tarea tarea) {
        tareasPendientes.encolar(tarea);
    }

    public void completarTarea() {
        if (!tareasPendientes.estaVacia()) {
            Tarea tarea = tareasPendientes.desencolar();
            tareasCompletadas.push(tarea);
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public List<HorarioClase> getHorario() {
        return horario.toList();
    }

    // Método para obtener lista de tareas pendientes (sin modificar la cola
    // original)
    public List<Tarea> obtenerListaTareasPendientes() {
        List<Tarea> lista = new ArrayList<>();
        Cola<Tarea> temp = new Cola<>();

        while (!tareasPendientes.isEmpty()) {
            Tarea t = tareasPendientes.dequeue();
            lista.add(t);
            temp.enqueue(t);
        }

        // Restaurar la cola original
        tareasPendientes = temp;
        return lista;
    }

    // Getter para la cola de tareas pendientes
    public Cola<Tarea> getTareasPendientes() {
        return this.tareasPendientes;
    }

    // Setter para la cola de tareas pendientes
    public void setTareasPendientes(Cola<Tarea> tareasPendientes) {
        this.tareasPendientes = tareasPendientes;
    }

    // Getter para la pila de tareas completadas
    public Pila<Tarea> getTareasCompletadas() {
        return this.tareasCompletadas;
    }
}

class HorarioClase {
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String materia;

    public HorarioClase(String dia, String horaInicio, String horaFin, String materia) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.materia = materia;
    }

    public String getDia() {
        return dia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getMateria() {
        return materia;
    }
}

class Tarea {
    private static int contadorIds = 1;

    private final int id;
    private final String descripcion;
    private final int prioridad;
    private boolean completada;
    private final String fechaCreacion;

    public Tarea(String descripcion, int prioridad) {
        this.id = contadorIds++;
        this.descripcion = descripcion;
        this.prioridad = validarPrioridad(prioridad);
        this.completada = false;
        this.fechaCreacion = java.time.LocalDate.now().toString();
    }

    private int validarPrioridad(int prioridad) {
        if (prioridad < 1 || prioridad > 10) {
            throw new IllegalArgumentException("La prioridad debe estar entre 1 y 10");
        }
        return prioridad;
    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public boolean isCompletada() {
        return completada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void marcarComoCompletada() {
        this.completada = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tarea tarea = (Tarea) o;
        return id == tarea.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String estado = completada ? "✓" : "✗";
        String colorPrioridad = getColorPrioridad();
        return String.format(
                "\u001B[36m[%d]\u001B[0m %s %s \u001B[37m(Prioridad: %s%d\u001B[0m) \u001B[90m[Creada: %s]\u001B[0m",
                id, estado, descripcion, colorPrioridad, prioridad, fechaCreacion);
    }

    private String getColorPrioridad() {
        if (prioridad <= 3)
            return "\u001B[31m";
        if (prioridad <= 7)
            return "\u001B[33m";
        return "\u001B[32m";
    }

}

public class SistemaEstudiantes {
    private Map<String, Estudiante> estudiantes = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SistemaEstudiantes sistema = new SistemaEstudiantes();
        sistema.iniciar();
    }

    public void iniciar() {
        while (true) {
            System.out.println("\nSistema de Planificación Estudiantil");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarEstudiante();
                        break;
                    case 2:
                        iniciarSesion();
                        break;
                    case 3:
                        System.out.println("Saliendo del sistema...");
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido");
            }
        }
    }

    private void registrarEstudiante() {
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine();

        // Verificar si el usuario ya existe
        if (estudiantes.containsKey(usuario)) {
            System.out.println("Error: El usuario ya está registrado");
            return;
        }

        System.out.print("Ingrese email (@poligran.edu.co): ");
        String email = scanner.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine();

        try {
            Estudiante estudiante = new Estudiante(usuario, email, contraseña);
            estudiantes.put(usuario, estudiante);
            System.out.println("Estudiante registrado exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void iniciarSesion() {
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine();

        // Verificar si el usuario existe
        if (!estudiantes.containsKey(usuario)) {
            System.out.println("Error: El usuario no está registrado");
            return;
        }

        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine();

        Estudiante estudiante = estudiantes.get(usuario);
        if (estudiante.getContraseña().equals(contraseña)) {
            menuEstudiante(estudiante);
        } else {
            System.out.println("Error: Contraseña incorrecta");
        }
    }

    private void menuEstudiante(Estudiante estudiante) {
        while (true) {
            System.out.println("\nBienvenido, " + estudiante.getUsuario());
            System.out.println("1. Ver horario");
            System.out.println("2. Agregar clase");
            System.out.println("3. Ver tareas pendientes");
            System.out.println("4. Agregar tarea");
            System.out.println("5. Completar tarea");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        verHorario(estudiante);
                        break;
                    case 2:
                        agregarClase(estudiante);
                        break;
                    case 3:
                        verTareasPendientes(estudiante);
                        break;
                    case 4:
                        agregarTarea(estudiante);
                        break;
                    case 5:
                        completarTareaPorNombre(estudiante);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido");
            }
        }
    }

    private void verHorario(Estudiante estudiante) {
        List<HorarioClase> horario = estudiante.getHorario();
        if (horario.isEmpty()) {
            System.out.println("\nNo hay clases en el horario");
            return;
        }

        System.out.println("\nHorario de clases:");
        for (HorarioClase clase : horario) {
            System.out.printf("%s: %s - %s (%s)\n",
                    clase.getDia(), clase.getHoraInicio(),
                    clase.getHoraFin(), clase.getMateria());
        }
    }

    private void agregarClase(Estudiante estudiante) {
        System.out.print("Día (Lunes-Viernes): ");
        String dia = scanner.nextLine();

        System.out.print("Hora inicio (HH:MM): ");
        String horaInicio = scanner.nextLine();

        System.out.print("Hora fin (HH:MM): ");
        String horaFin = scanner.nextLine();

        System.out.print("Materia: ");
        String materia = scanner.nextLine();

        HorarioClase nuevaClase = new HorarioClase(dia, horaInicio, horaFin, materia);
        estudiante.agregarClase(nuevaClase);
        System.out.println("Clase agregada al horario!");
    }

    private void verTareasPendientes(Estudiante estudiante) {
        if (estudiante.getTareasPendientes().isEmpty()) {
            System.out.println("\nNo hay tareas pendientes");
            return;
        }
    
        System.out.println("\nTareas pendientes:");
        
        // Convertimos la cola a lista temporal
        List<Tarea> tareas = estudiante.obtenerListaTareasPendientes();
        
        int contador = 1;
        for (Tarea tarea : tareas) {
            System.out.println(contador++ + ". " + tarea);
        }
    }

    private void agregarTarea(Estudiante estudiante) {
        System.out.print("Descripción de la tarea: ");
        String descripcion = scanner.nextLine();

        System.out.print("Prioridad (1-10, 1=alta): ");
        try {
            int prioridad = Integer.parseInt(scanner.nextLine());
            if (prioridad < 1 || prioridad > 10) {
                System.out.println("Error: La prioridad debe estar entre 1 y 10");
                return;
            }

            Tarea nuevaTarea = new Tarea(descripcion, prioridad);
            estudiante.agregarTarea(nuevaTarea);
            System.out.println("Tarea agregada!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido");
        }
    }

    private void completarTareaPorNombre(Estudiante estudiante) {
        List<Tarea> tareasMostrar = estudiante.obtenerListaTareasPendientes();

        if (tareasMostrar.isEmpty()) {
            System.out.println("\nNo hay tareas pendientes para completar");
            return;
        }

        System.out.println("\nTareas pendientes:");
        for (int i = 0; i < tareasMostrar.size(); i++) {
            System.out.println((i + 1) + ". " + tareasMostrar.get(i).getDescripcion());
        }

        System.out.print("\nIngrese el nombre de la tarea a completar: ");
        String nombreTarea = scanner.nextLine();

        Cola<Tarea> nuevaCola = new Cola<>();
        boolean tareaCompletada = false;

        Cola<Tarea> tareasActuales = estudiante.getTareasPendientes();
        while (!tareasActuales.isEmpty()) {
            Tarea tareaActual = tareasActuales.dequeue();

            if (!tareaCompletada && tareaActual.getDescripcion().equalsIgnoreCase(nombreTarea)) {
                tareaActual.marcarComoCompletada();
                estudiante.getTareasCompletadas().push(tareaActual);
                tareaCompletada = true;
                System.out.println("\nTarea '" + tareaActual.getDescripcion() + "' completada exitosamente!");
            } else {
                nuevaCola.enqueue(tareaActual);
            }
        }

        estudiante.setTareasPendientes(nuevaCola);

        if (!tareaCompletada) {
            System.out.println("\nError: No se encontró una tarea con ese nombre");
            System.out.println("\nTareas disponibles:");
            estudiante.obtenerListaTareasPendientes().forEach(t -> System.out.println("- " + t.getDescripcion()));
        }
    }
}