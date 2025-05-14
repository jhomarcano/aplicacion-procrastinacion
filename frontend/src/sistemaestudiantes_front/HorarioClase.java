package sistemaestudiantes_front;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HorarioClase {
    private final StringProperty dia ;
    private final StringProperty horaInicio ;
    private final StringProperty horaFin ;
    private final StringProperty materia ;

    public HorarioClase(String dia, String horaInicio, String horaFin, String materia) {
        this.dia= new SimpleStringProperty(dia);
        this.horaInicio= new SimpleStringProperty(horaInicio);
        this.horaFin= new SimpleStringProperty(horaFin);
        this.materia= new SimpleStringProperty(materia);
    }

    // Métodos para dia
    public String getDia() {return dia.get();}
    public void setDia(String value) {dia.set(value);}
    public StringProperty diaProperty() {return dia;}

    // Métodos para horaInicio
    public String getHoraInicio() {return horaInicio.get();}
    public void setHoraInicio(String value) {horaInicio.set(value);}
    public StringProperty horaInicioProperty() {return horaInicio;}

    // Métodos para horaFin
    public String getHoraFin() {return horaFin.get();}
    public void setHoraFin(String value) {horaFin.set(value);}
    public StringProperty horaFinProperty() {return horaFin;}

    // Métodos para materia
    public String getMateria() {return materia.get();}
    public void setMateria(String value) {materia.set(value);}
    public StringProperty materiaProperty() {return materia;}
}