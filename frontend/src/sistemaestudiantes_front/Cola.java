/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaestudiantes_front;

import java.util.NoSuchElementException;
import java.util.List;

public class Cola<T> {
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
}
