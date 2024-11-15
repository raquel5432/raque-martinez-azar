import javax.swing.*;
import java.util.Random;

public class Raquel_Martinez_azar {
    private static String[] palabras = {
            "HONDURAS", "JAVA", "PROGRAMAR", "SWING",
            "COMPUTADORA", "TECLADO", "MONITOR", "MOUSE",
            "INTELIGENCIA", "JUEGO"
    };

    private static String palabraActual;
    private static char[] progreso;
    private static int oportunidadesRestantes;

    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu() {
        String[] opciones = {"Jugar", "Cambiar Palabras", "Salir"};
        while (true) {
            int seleccion = JOptionPane.showOptionDialog(null,
                    "Selecciona una opción",
                    "Adivina la Palabra",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion == 0) {
                jugar();
            } else if (seleccion == 1) {
                cambiarPalabras();
            } else {
                break;
            }
        }
    }

    private static void jugar() {
        if (palabras.length == 0) {
            JOptionPane.showMessageDialog(null, "No hay palabras disponibles. Agrega palabras primero.");
            return;
        }

        seleccionarPalabraAleatoria();
        oportunidadesRestantes = 5;
        progreso = new char[palabraActual.length()];
        for (int i = 0; i < progreso.length; i++) {
            progreso[i] = '_';
        }

        while (oportunidadesRestantes > 0 && !esPalabraAdivinada()) {
            String entrada = JOptionPane.showInputDialog(null,
                    "Palabra: " + String.valueOf(progreso) +
                            "\nOportunidades restantes: " + oportunidadesRestantes +
                            "\nIngresa una letra:");

            if (entrada == null || entrada.length() != 1) {
                JOptionPane.showMessageDialog(null, "Por favor, ingresa una sola letra.");
                continue;
            }

            char letra = entrada.toUpperCase().charAt(0);
            boolean acierto = false;

            for (int i = 0; i < palabraActual.length(); i++) {
                if (palabraActual.charAt(i) == letra && progreso[i] == '_') {
                    progreso[i] = letra;
                    acierto = true;
                }
            }

            if (acierto) {
                JOptionPane.showMessageDialog(null, "¡Le pegaste a un carácter!");
            } else {
                oportunidadesRestantes--;
                JOptionPane.showMessageDialog(null, "Esa letra no está en la palabra.");
            }
        }

        if (esPalabraAdivinada()) {
            JOptionPane.showMessageDialog(null, "¡Felicidades! Adivinaste la palabra: " + palabraActual);
        } else {
            JOptionPane.showMessageDialog(null, "¡Perdiste! La palabra era: " + palabraActual);
        }
    }

    private static void cambiarPalabras() {
        StringBuilder listaPalabras = new StringBuilder("Palabras actuales:\n");
        for (int i = 0; i < palabras.length; i++) {
            listaPalabras.append(i + 1).append(". ").append(palabras[i]).append("\n");
        }

        String nuevaPalabra = JOptionPane.showInputDialog(null,
                listaPalabras +
                        "\nEscribe una nueva palabra para agregar (o deja en blanco para no agregar nada):");

        if (nuevaPalabra != null && !nuevaPalabra.trim().isEmpty()) {
            nuevaPalabra = nuevaPalabra.trim().toUpperCase();
            palabras = agregarPalabra(palabras, nuevaPalabra);
            JOptionPane.showMessageDialog(null, "¡Palabra agregada correctamente!");
        }

        int opcionEliminar = JOptionPane.showConfirmDialog(null,
                "¿Quieres eliminar alguna palabra?", "Eliminar Palabras", JOptionPane.YES_NO_OPTION);

        if (opcionEliminar == JOptionPane.YES_OPTION) {
            int indice = -1;
            while (indice < 0 || indice >= palabras.length) {
                try {
                    String indiceStr = JOptionPane.showInputDialog(null,
                            listaPalabras +
                                    "\nSelecciona el número de la palabra a eliminar (1-" + palabras.length + "):");
                    if (indiceStr == null) return;
                    indice = Integer.parseInt(indiceStr) - 1;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                }
            }
            palabras = eliminarPalabra(palabras, indice);
            JOptionPane.showMessageDialog(null, "¡Palabra eliminada correctamente!");
        }
    }

    private static void seleccionarPalabraAleatoria() {
        Random random = new Random();
        palabraActual = palabras[random.nextInt(palabras.length)];
    }

    private static boolean esPalabraAdivinada() {
        for (char c : progreso) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }

    private static String[] agregarPalabra(String[] arreglo, String nuevaPalabra) {
        String[] nuevoArreglo = new String[arreglo.length + 1];
        System.arraycopy(arreglo, 0, nuevoArreglo, 0, arreglo.length);
        nuevoArreglo[arreglo.length] = nuevaPalabra;
        return nuevoArreglo;
    }

    private static String[] eliminarPalabra(String[] arreglo, int indice) {
        String[] nuevoArreglo = new String[arreglo.length - 1];
        for (int i = 0, j = 0; i < arreglo.length; i++) {
            if (i != indice) {
                nuevoArreglo[j++] = arreglo[i];
            }
        }
        return nuevoArreglo;
    }
}
