package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "Alumno")
@Entity
public class Alumno implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "dni", insertable = false, nullable = false)
    private Integer dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "clase_id", nullable = false)
    private Integer claseId;

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getClaseId() {
        return claseId;
    }

    public void setClaseId(Integer claseId) {
        this.claseId = claseId;
    }

    public String toString() {
      return "Alumno{dni=" + dni + 
        ", nombre=" + nombre + 
        ", claseId=" + claseId + 
        "}";
    }
}