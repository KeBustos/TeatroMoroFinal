/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.teatromoro;

/**
 *
 * @author kevin
 */
import java.util.*;
import java.text.SimpleDateFormat;

public class TeatroMoro {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Definir zonas de asientos y sus precios
        String[] zonas = {"VIP", "Palco", "Platea Alta", "Platea Baja", "Galería"};
        double[] precios = {20000, 14000, 11000, 8000, 5000};
        String[][] asientos = new String[zonas.length][10];
        List<String> entradas = new ArrayList<>();

        // Inicializar todos los asientos como disponibles
        for (int i = 0; i < zonas.length; i++) {
            for (int j = 0; j < 10; j++) {
                asientos[i][j] = "Disponible";
            }
        }

        int opcion = 0;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Ingresar al sistema");
            System.out.println("2. Salir del sistema");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opción inválida. Intente nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1:
                    ingresarSistema(sc, zonas, precios, asientos, entradas);
                    break;
                case 2:
                    System.out.println("Saliendo del sistema. ¡Gracias por usar Teatro Moro!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 2);
    }

    // Método para ingresar al sistema
    public static void ingresarSistema(Scanner sc, String[] zonas, double[] precios, String[][] asientos, List<String> entradas) {
        int opcion = 0;
        do {
            System.out.println("\n--- SISTEMA DEL TEATRO ---");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Revisar asientos");
            System.out.println("3. Gestionar entradas");
            System.out.println("4. Volver");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente nuevamente.");
                continue;
            }

            switch (opcion) {
                case 1:
                    comprarEntrada(sc, zonas, precios, asientos, entradas);
                    break;
                case 2:
                    mostrarAsientos(zonas, asientos);
                    break;
                case 3:
                    gestionarEntradas(sc, entradas);
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    // Método para comprar una entrada
    public static void comprarEntrada(Scanner sc, String[] zonas, double[] precios, String[][] asientos, List<String> entradas) {
        try {
            System.out.print("Ingrese edad: ");
            int edad = Integer.parseInt(sc.nextLine());
            System.out.print("Ingrese género (M/F): ");
            String genero = sc.nextLine();
            System.out.print("¿Es estudiante? (s/n): ");
            String estudiante = sc.nextLine();

            double descuentoTotal = 0;

            // Descuento por edad
            if (edad < 12) descuentoTotal += 0.10;
            else if (edad >= 60) descuentoTotal += 0.25;

            // Descuento por género
            if (genero.equalsIgnoreCase("F")) descuentoTotal += 0.20;

            // Descuento por estudiante
            if (estudiante.equalsIgnoreCase("s")) descuentoTotal += 0.15;

            // Mostrar zonas y seleccionar asiento
            for (int i = 0; i < zonas.length; i++) {
                System.out.println(i + ". " + zonas[i] + " - $" + precios[i]);
            }
            System.out.print("Seleccione zona (0-" + (zonas.length - 1) + "): ");
            int zona = Integer.parseInt(sc.nextLine());

            // Mostrar asientos disponibles en esa zona
            for (int i = 0; i < 10; i++) {
                System.out.println("Asiento " + (i + 1) + ": " + asientos[zona][i]);
            }
            System.out.print("Seleccione número de asiento (1-10): ");
            int asiento = Integer.parseInt(sc.nextLine()) - 1;

            if (asientos[zona][asiento].equals("Ocupado")) {
                System.out.println("El asiento ya está ocupado. Intente nuevamente.");
                return;
            }

            double precioBase = precios[zona];
            double precioFinal = precioBase * (1 - descuentoTotal);

            asientos[zona][asiento] = "Ocupado";
            entradas.add("Zona: " + zonas[zona] + ", Asiento: " + (asiento + 1) + ", Precio: $" + precioFinal);

            // Mostrar boleta con formato
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println("\n=== BOLETA DE COMPRA ===");
            System.out.println("Teatro Moro");
            System.out.println("Zona: " + zonas[zona]);
            System.out.println("Asiento: " + (asiento + 1));
            System.out.printf("Precio original: $%.0f\n", precioBase);
            System.out.printf("Descuento total aplicado: %.0f%%\n", descuentoTotal * 100);
            System.out.printf("Total a pagar: $%.0f\n", precioFinal);
            System.out.println("Fecha y hora: " + formatoFecha.format(new Date()));
            System.out.println("========================");

        } catch (Exception e) {
            System.out.println("Error al procesar la compra. Intente nuevamente.");
        }
    }

    // Método para mostrar asientos
    public static void mostrarAsientos(String[] zonas, String[][] asientos) {
        for (int i = 0; i < zonas.length; i++) {
            System.out.println("\nZona: " + zonas[i]);
            for (int j = 0; j < asientos[i].length; j++) {
                System.out.println("Asiento " + (j + 1) + ": " + asientos[i][j]);
            }
        }
    }

    // Método para gestionar entradas
    public static void gestionarEntradas(Scanner sc, List<String> entradas) {
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas vendidas.");
            return;
        }

        System.out.println("\n--- ENTRADAS VENDIDAS ---");
        for (int i = 0; i < entradas.size(); i++) {
            System.out.println((i + 1) + ". " + entradas.get(i));
        }
        System.out.print("Ingrese el número de entrada a eliminar (0 para cancelar): ");
        try {
            int eliminar = Integer.parseInt(sc.nextLine());
            if (eliminar > 0 && eliminar <= entradas.size()) {
                entradas.remove(eliminar - 1);
                System.out.println("Entrada eliminada exitosamente.");
            } else if (eliminar != 0) {
                System.out.println("Número inválido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
}
