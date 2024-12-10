import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String NOMBRE_ARCHIVO = "estudiantes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== Sistema de Registro de Estudiantes ===");
            System.out.println("1. Verificar si el archivo existe.");
            System.out.println("2. Leer contenido del archivo.");
            System.out.println("3. Agregar un nuevo Estudiante.");
            System.out.println("4. Sobreescribir todo el contenido.");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");

            int opcion = 0;
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("ERR::Opcion invalida.");
                scanner.next(); // Consumir entrada inválida
                continue;
            }

            // Consumir el salto de línea pendiente después de nextInt()
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    verificarArchivo();
                    break;
                case 2:
                    leerArchivo();
                    break;
                case 3:
                    System.out.print("Estudiantes: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Numero de estudiante: ");
                    String numero = scanner.nextLine();
                    System.out.print("Fecha de asistencia (dd/mm/yyyy): ");
                    String fecha = scanner.nextLine();
                    agregarEstudiante(nombre, numero, fecha);
                    break;
                case 4:
                    System.out.print("Contenido: ");
                    String contenido = scanner.nextLine();
                    sobreescribirArchivo(contenido);
                    break;
                case 5:
                    System.out.println("Cerrando el sistema...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida, intente de nuevo.");
            }
        }
        scanner.close();
    }

    private static void verificarArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (archivo.exists()) {
            System.out.println("El archivo '" + NOMBRE_ARCHIVO + "' existe.");
        } else {
            System.out.println("El archivo '" + NOMBRE_ARCHIVO + "' no existe.");
            try {
                if (archivo.createNewFile()) {
                    System.out.println("El archivo ha sido creado.");
                }
            } catch (IOException e) {
                System.out.println("Error al crear el archivo: " + e.getMessage());
            }
        }
    }

    private static void leerArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe. Favor crearlo primero.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\nContenido del archivo:");
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3){
                    System.out.println("Nombre: " + datos[0] + ",Numero: " + datos[1] + ", Fecha: " + datos[2]);
                }else {
                    System.out.println("Error el los datos ingresados: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static void agregarEstudiante(String nombre, String numero, String fecha) {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe. Favor crearlo primero.");
            return;
        }

        try (FileWriter fw = new FileWriter(archivo, true)) {
            fw.write(nombre + "," + numero + "," + fecha + "\n");
            System.out.println("Estudiante '" + nombre + "' agregado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al agregar el Estudiante: " + e.getMessage());
        }
    }

    private static void sobreescribirArchivo(String contenido) {
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe. Favor crearlo primero.");
            return;
        }

        try (FileWriter fw = new FileWriter(archivo)) {
            fw.write(contenido + "\n");
            System.out.println("El archivo ha sido sobreescrito.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}