package agenda;

import javax.swing.JOptionPane;

public class ValidacionDatos {

    private String name;
    private String telefono;
    private int validNombre;
    private double validNumber;
    private boolean errorTel;
    private String datoIngresado;

    public void ValidarDatos() {
        validNombre = 0;

        while (validNombre == 0) {
            //SE COMPRUEBA SI EL NOMBRE ES V�LIDO
            try {
                datoIngresado = null;
                while (datoIngresado == null) {
                    datoIngresado = JOptionPane.showInputDialog("Ingrese el nombre del nuevo contacto:\n\n",
                            "Pepito Perez");
                }
                name = pedirNombre(datoIngresado);
                if (!name.isEmpty()) {
                    validNombre = 1;
                }
            } catch (NoEsLetraExcepcion ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage() + "\n");
                validNombre = 0;
                datoIngresado = JOptionPane.showInputDialog("Ingrese el nombre del nuevo contacto:\n\n",
                        "Pepito Perez");
            }
        }
        name = datoIngresado;

        name = name.toUpperCase(); //COLOCAMOS EN MAY�SCULAS EL NOMBRE
        errorTel = true;//SE COMPRUEBA SI EL N�MERO ES VALIDO		

        while (errorTel) {
            while (telefono == null) {
                telefono = JOptionPane.showInputDialog("Ingrese el número del nuevo contacto:\n\n",
                        "1234567809");
            }

            try {
                validNumber = Double.parseDouble(telefono);
                if (validNumber <= 999999 || validNumber > 9999999 && validNumber <= 999999999) {
                    JOptionPane.showMessageDialog(null, "Un número telefonico debe tener 7 o 10 digitos.");
                    telefono = JOptionPane.showInputDialog("Ingrese el número del nuevo contacto:\n\n",
                            "1234567809");
                    errorTel = true;
                } else {

                    errorTel = false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Número contiene letras o caracteres incorrectos");
                errorTel = true;
                telefono = JOptionPane.showInputDialog("Ingrese el número del nuevo contacto:\n\n",
                        "1234567809");
            }

        }
    }

    public String getName() {
        return name;
    }

    public String getTelefono() {
        return telefono;
    }

    static boolean comprobarLetras(String cadena) {
        //SE COMPRUEBA SI TODOS SUS VALORES SON LETRAS
        for (int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.toUpperCase().charAt(i);
            int valorASCII = (int) caracter;
            if (valorASCII != 165 && (valorASCII < 65 || valorASCII > 90)) {
                return false;
            }
        }
        return true;
    }

    static String pedirNombre(String cadena) throws NoEsLetraExcepcion {
        cadena = cadena.replaceAll("\\s", "");
        if (comprobarLetras(cadena)) {
            return cadena;
        } else {
            throw new NoEsLetraExcepcion("UN NOMBRE NO DEBE CONTENER NÚMEROS");
        }
    }

    static class NoEsLetraExcepcion extends Exception {

        public NoEsLetraExcepcion(String mensajeExcepcion) {
            super(mensajeExcepcion);
        }

    }
}
