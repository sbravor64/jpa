import model.Alumno;
import model.BBDDConnect;
import model.Clase;
import model.Instituto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuActualizarDatos {

    protected EntityManagerFactory emf;
    protected EntityManager em;

    public MenuActualizarDatos(){
        emf= Persistence.createEntityManagerFactory("damPersistence");
        em = emf.createEntityManager();
    }

    public void show() throws IOException, JAXBException {
        System.out.println("ACTUALIZAR DATOS");
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            System.out.println("1. ACTUALIZAR ALUMNOS DE UNA CLASE");
            System.out.println("2. ACTUALIZAR ALUMNOS DE UN INSTITULO");
            System.out.println("5  VOLVER");
            opcion= sc.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Escribe el id de la clase que quieres actualizar su lista de Alumnos");
                    listaClase();
                    actualizarAlumnosClase(sc.nextInt());
                    break;
                case 2:
                    System.out.println("Escribe el id del institulo que quieres actualizar su lista de Alumnos");
                    listaInstituto();
                    actualizarAlumnosInstituto(sc.nextInt());
                    break;
                case 3:
                    show();
                    break;
            }
        } while (opcion!=5);
    }

    void listaClase(){
        int i = 0;
        boolean eof=true;
        while (eof){
            try {
                i++;
                Clase clase=em.find(Clase.class, i);
                System.out.println(clase.toString());
            }catch (NullPointerException e){
                eof=false;
            }
        }
    }

    void listaInstituto(){
        TypedQuery<Instituto> query =
                em.createQuery("SELECT i FROM Instituto i", Instituto.class);
        List<Instituto> results = query.getResultList();
        results.forEach(System.out::println);
    }

    private void actualizarAlumnosInstituto(int idInstitulo) {
        Instituto instituto = em.find(Instituto.class, idInstitulo);

        TypedQuery<Clase> query = em.createQuery("SELECT c FROM Clase c", Clase.class);
        List<Clase> results = query.getResultList();

        AtomicInteger numAlumnos = new AtomicInteger();

        results.stream().filter(clase -> clase.getInstitutoId() == instituto.getId())
                .forEach(clase -> {
                    numAlumnos.set(numAlumnos.get() + clase.getNAlumnos());
                });

        int num = numAlumnos.get();
        em.getTransaction().begin();
        instituto.setNAlumnos(num);
        em.getTransaction().commit();

    }

    private void actualizarAlumnosClase(int idClase){
            Clase clase=em.find(Clase.class, idClase);

            TypedQuery<Alumno> query = em.createQuery("SELECT a FROM Alumno a", Alumno.class);
            List<Alumno> results = query.getResultList();

            long numAlumnos=results.stream().filter(alumno -> alumno.getClaseId()==clase.getId()).count();

            em.getTransaction().begin();
            clase.setNAlumnos((int) numAlumnos);
            em.getTransaction().commit();
    }
}
