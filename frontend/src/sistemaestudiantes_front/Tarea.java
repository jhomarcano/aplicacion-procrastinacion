package sistemaestudiantes_front;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tarea {
    private final StringProperty descripcion = new SimpleStringProperty();
    private final IntegerProperty prioridad = new SimpleIntegerProperty();

    public Tarea(String descripcion, int prioridad) {
        setDescripcion(descripcion);
        setPrioridad(prioridad);
    }

    // Métodos para descripcion
    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String value) {
        descripcion.set(value);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    // Métodos para prioridad
    public int getPrioridad() {
        return prioridad.get();
    }

    public void setPrioridad(int value) {
        prioridad.set(value);
    }

    public IntegerProperty prioridadProperty() {
        return prioridad;
    }
}