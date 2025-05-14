/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaestudiantes_front;

import java.util.List;
import java.util.ArrayList;


    //clase de la lista enlazada
public class ListaEnlazada<T> {
    //atributos de la clase 
    private Nodo<T> cabeza; //se crea un nodo inicial
    private int tamaño;

    //constructor 
    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    //metodo agregar 
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

    //agregar elementosala lista
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

