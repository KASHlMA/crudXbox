package org.example.crudxbox;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Tarea {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty  titulo = new SimpleStringProperty();
    private final StringProperty  descripcion = new SimpleStringProperty();
    private final StringProperty  prioridad = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fechaLimite = new SimpleObjectProperty<>();

    public Tarea(int id, String titulo, String descripcion, String prioridad, LocalDate fechaLimite) {
        setId(id); setTitulo(titulo); setDescripcion(descripcion);
        setPrioridad(prioridad); setFechaLimite(fechaLimite);
    }

    public int getId() { return id.get(); }
    public void setId(int v) { id.set(v); }
    public IntegerProperty idProperty() { return id; }

    public String getTitulo() { return titulo.get(); }
    public void setTitulo(String v) { titulo.set(v); }
    public StringProperty tituloProperty() { return titulo; }

    public String getDescripcion() { return descripcion.get(); }
    public void setDescripcion(String v) { descripcion.set(v); }
    public StringProperty descripcionProperty() { return descripcion; }

    public String getPrioridad() { return prioridad.get(); }
    public void setPrioridad(String v) { prioridad.set(v); }
    public StringProperty prioridadProperty() { return prioridad; }

    public LocalDate getFechaLimite() { return fechaLimite.get(); }
    public void setFechaLimite(LocalDate v) { fechaLimite.set(v); }
    public ObjectProperty<LocalDate> fechaLimiteProperty() { return fechaLimite; }
}
