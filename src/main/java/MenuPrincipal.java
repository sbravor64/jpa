import model.Alumno;
import model.BBDDConnect;
import model.Clase;
import model.Instituto;

import javax.persistence.TypedQuery;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuPrincipal extends BBDDConnect{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    Scanner scanner = new Scanner(System.in);

    MenuActualizarDatos menuActualizarDatos = new MenuActualizarDatos();

    public void show() throws IOException, JAXBException {
        System.out.println("MENU");
        Scanner sc = new Scanner(System.in);
        int opcion;
        do{
            System.out.println("1. AÑADIR CLASE");
            System.out.println("2. AÑADIR INSTITUTO");
            System.out.println("3. AÑADIR ALUMNO");
            System.out.println("4. ACTUALIZAR DATOS");
            System.out.println("5 SALIR");
            opcion= sc.nextInt();
            switch (opcion) {
                case 1:
                    addClase();
                    break;
                case 2:
                    addInstituto();
                    break;
                case 3:
                    addAlumno();
                    break;
                case 4:
                    menuActualizarDatos.show();
                    break;
                case 5:
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

    void addClase(){
        Clase clase = new Clase();

        System.out.println("Añade el id de la clase");
        listaClase();
        int id = scanner.nextInt();

        System.out.println("Añade el id del instituto de la Clase");
        listaInstituto();
        int instituto_id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Añade el nombre de la Clase: ");
        String nombre = scanner.nextLine();

        System.out.println("Añade la rama de la Clase");
        String rama = scanner.nextLine();

        clase.setId(id);
        clase.setInstitutoId(instituto_id);
        clase.setNombre(nombre);
        clase.setRama(rama);

        em.getTransaction().begin();
        em.persist(clase);
        em.getTransaction().commit();
    }


    void addAlumno(){
        Alumno alumno = new Alumno();

        System.out.println("Añade el DNI del Alumno");
        int dni = scanner.nextInt();

        System.out.println("Añade el id de la clase del Alumno");
        listaClase();
        int clase_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Añade el nombre del Alumno: ");
        String nombre = scanner.nextLine();

        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setClaseId(clase_id);

        em.getTransaction().begin();
        em.persist(alumno);
        em.getTransaction().commit();
    }

    void addInstituto(){

        System.out.println("Añade el id del institulo");
        listaInstituto();
        int id = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Añade el nombre del instituto: ");
        String nombre = scanner.nextLine();

        Instituto instituto = new Instituto();
        instituto.setId(id);
        instituto.setNombre(nombre);

        em.getTransaction().begin();
        em.persist(instituto);
        em.getTransaction().commit();
    }

}
