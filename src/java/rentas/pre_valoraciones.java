/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import sistema.*;
import framework.*;

/**
 *
 * @author user
 */
public class pre_valoraciones extends Pantalla{

    //private Utilitario utilitario = new Utilitario();
    //private Barra bar_botones = new Barra();
    private Division div_division = new Division();
   // private Grupo gru_pantalla = new Grupo();
    private Boton bot_calcular = new Boton();
    private Calendario cal_fecha = new Calendario();

    public pre_valoraciones() {
        bar_botones.limpiar();

        bot_calcular.setValue("Calcular");
        bot_calcular.setMetodo("calcular");

        bar_botones.agregarBoton(bot_calcular);

        Grid gri_matriz = new Grid();

        gri_matriz.setColumns(2);

        Etiqueta eti = new Etiqueta();
        eti.setValue("INGRESE LA FECHA PARA CALCULAR EL INTERES, MULTAS Y DESCUENTOS");

        cal_fecha.setFechaActual();
        cal_fecha.setNavigator(true);
        gri_matriz.getChildren().add(eti);
        gri_matriz.getChildren().add(cal_fecha);

        div_division.setId("div_division");
        div_division.dividir1(gri_matriz);

       // gru_pantalla.getChildren().add(bar_botones);
        agregarComponente(div_division);
      
    }

    public void calcular() {
        if (utilitario.isFechaValida(cal_fecha.getFecha())) {
            cls_valorar cls_valora = new cls_valorar();
            if (cls_valora.valorar_Interes_Multa_Descuento(cal_fecha.getFecha())) {
                utilitario.agregarMensaje("Se calculo correctamente","");
            }
        } else {
        }

    }

  /*  public Barra getBar_botones() {
        return bar_botones;
    }

    public void setBar_botones(Barra bar_botones) {
        this.bar_botones = bar_botones;
    }*/

    @Override
    public void insertar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
