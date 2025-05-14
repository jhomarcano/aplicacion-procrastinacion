/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaestudiantes_front;

import java.util.EmptyStackException;
import java.util.List;

public class Pila<T> {
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