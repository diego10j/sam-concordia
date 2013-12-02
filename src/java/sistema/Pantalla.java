/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import framework.Barra;
import framework.Grupo;
import javax.faces.component.UIComponent;
import persistencia.Conexion;

/**
 *
 * @author Diego
 */
public abstract class Pantalla {

    public Utilitario utilitario = new Utilitario();
    public Barra bar_botones = new Barra();
    public Grupo gru_pantalla = new Grupo();

    public Pantalla() {
        gru_pantalla.setTransient(true);
        gru_pantalla.getChildren().add(bar_botones);
        crearPantalla(gru_pantalla);
    }

    private void crearPantalla(Grupo pantalla) {
        Grupo pan_dibuja = utilitario.getPantalla();
        if (pan_dibuja != null) {
            pan_dibuja.getChildren().add(pantalla);
        } else {
            System.out.println("No se pudo crear la Pantalla");
        }
    }

    public Conexion getConexion() {
        return utilitario.getConexion();
    }

    protected void agregarComponente(UIComponent componente) {
        gru_pantalla.getChildren().add(componente);
    }

    protected String guardarPantalla() {
        return utilitario.getConexion().guardarPantalla();
    }

    public abstract void insertar(); //Para el boton insertar

    public abstract void guardar(); //Para el boton guardar

    public abstract void eliminar(); //Para el boton eliminar

    public void abrir_reporte() { //Se ejecuta cuando da click en el boton de Reportes de la Barra    
    }

    public void aceptar_reporte() {//Se ejecuta cuando se selecciona un reporte de la lista
    }

    public void abrir_rango_fecha() {
        //Se ejecuta cuando da click en el boton Calendario de la Barra     
    }

    public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }
}
