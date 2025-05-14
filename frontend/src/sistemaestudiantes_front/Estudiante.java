/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaestudiantes_front;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Estudiante {
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

    public String getUsuario() { return usuario; }
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    public List<HorarioClase> getHorario() { return horario.toList(); }
    public List<Tarea> getTareasPendientes() { 
        List<Tarea> lista = new ArrayList<>();
        Cola<Tarea> temp = new Cola<>();
        
        while (!tareasPendientes.estaVacia()) {
            Tarea t = tareasPendientes.desencolar();
            lista.add(t);
            temp.encolar(t);
        }
        
        this.tareasPendientes = temp;
        return lista;
    }
     public boolean modificarClase(int indice, HorarioClase nuevaClase) {
        List<HorarioClase> listaHorario = horario.toList();
        if (indice >= 0 && indice < listaHorario.size()) {
            // Creamos una nueva lista enlazada con la clase modificada
            ListaEnlazada<HorarioClase> nuevoHorario = new ListaEnlazada<>();
            for (int i = 0; i < listaHorario.size(); i++) {
                if (i == indice) {
                    nuevoHorario.agregar(nuevaClase);
                } else {
                    nuevoHorario.agregar(listaHorario.get(i));
                }
            }
            this.horario = nuevoHorario;
            return true;
        }
        return false;
    }
    
    public boolean eliminarClase(int indice) {
        List<HorarioClase> listaHorario = horario.toList();
        if (indice >= 0 && indice < listaHorario.size()) {
            listaHorario.remove(indice);
            // Reconstruimos la lista enlazada
            this.horario = new ListaEnlazada<>();
            for (HorarioClase clase : listaHorario) {
                this.horario.agregar(clase);
            }
            return true;
        }
        return false;
    }
}
