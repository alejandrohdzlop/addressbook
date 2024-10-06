package addressbook;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBook {
    private Map<String, String> contacts;

    // Constructor para inicializar el HashMap
    public AddressBook() {
        contacts = new HashMap<>();
    }

    // Método para cargar los contactos desde un archivo CSV
    public void load(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    contacts.put(values[0], values[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    // Método para guardar los contactos en un archivo CSV
    public void save(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    // Método para listar los contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // Método para crear un nuevo contacto
    public void create(String number, String name) {
        if (contacts.containsKey(number)) {
            System.out.println("El número ya existe en la agenda.");
        } else {
            contacts.put(number, name);
            System.out.println("Contacto agregado: " + number + " : " + name);
        }
    }

    // Método para eliminar un contacto por su número
    public void delete(String number) {
        if (contacts.containsKey(number)) {
            contacts.remove(number);
            System.out.println("Contacto eliminado: " + number);
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Método principal para ejecutar el programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();

        // Cargar contactos desde un archivo CSV al iniciar el programa
        String filename = "contacts.csv";
        addressBook.load(filename);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar y salir");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    addressBook.list();
                    break;
                case "2":
                    System.out.print("Ingrese el número de contacto: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre de contacto: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case "3":
                    System.out.print("Ingrese el número del contacto a eliminar: ");
                    number = scanner.nextLine();
                    addressBook.delete(number);
                    break;
                case "4":
                    addressBook.save(filename);
                    System.out.println("Contactos guardados en el archivo. Saliendo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
