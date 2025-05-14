/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaestudiantes_front;

/**
 *
 * @author USER
 */
public class Nodo<T> {
    //atributos de la lista 
    T dato;
    Nodo<T> siguiente;//puntero

    //constructor de la clase
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

