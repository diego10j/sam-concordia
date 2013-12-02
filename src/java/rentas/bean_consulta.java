/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rentas;

import framework.Framework;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import persistencia.Conexion;
import sistema.Utilitario;

/**
 *
 * @author Diego
 */
@ManagedBean
@RequestScoped
public class bean_consulta {

    private String cedula = "";
    private int consulto = 1;
    private List lis_consulta = new ArrayList();
    private String nombre = "";
    private String ide_cliente = "-1";
    private String mensaje = "";
    private String total = "0";

    /**
     * Creates a new instance of bean_consulta
     */
    public bean_consulta() {
    }

    public void metodo1() {
        cedula += "1";
    }

    public void metodo2() {
        cedula += "2";
    }

    public void metodo3() {
        cedula += "3";
    }

    public void metodo4() {
        cedula += "4";
    }

    public void metodo5() {
        cedula += "5";
    }

    public void metodo6() {
        cedula += "6";
    }

    public void metodo7() {
        cedula += "7";
    }

    public void metodo8() {
        cedula += "8";
    }

    public void metodo9() {
        cedula += "9";
    }

    public void metodo0() {
        cedula += "0";
    }

    public void atras() {
        try {
            cedula = cedula.substring(0, (cedula.length() - 1));
        } catch (Exception e) {
            cedula = "";
        }
    }

    public void limpiar() {
        cedula = "";
        consulto = 1;
        lis_consulta.clear();
        mensaje = "";
        nombre = "";
        ide_cliente = "-1";
        total = "0";
    }

    public void consultar() {
        Conexion con = new Conexion();
        con.setUnidad_persistencia("sam");
        List lis_usuario = con.consultar("select ide_cliente,nombre from rec_clientes where cedula='" + cedula + "'");
        if (lis_usuario != null && !lis_usuario.isEmpty()) {
            Object obj_fila[] = (Object[]) lis_usuario.get(0);
            if (obj_fila[1] == null) {
                obj_fila[1] = "";
            }
            nombre = obj_fila[1] + "";
            ide_cliente = obj_fila[0] + "";

            lis_consulta = con.consultar("select des_ingreso,valor,observaciones from tes_ingreso where ide_estado=1 and ide_titulo in(select ide_titulo from rec_valores where ide_cliente=" + ide_cliente + ")");
            if (lis_consulta != null && !lis_consulta.isEmpty()) {
                consulto = 3;
                Utilitario utilitario = new Utilitario();
                total = utilitario.getFormatoNumero(con.consultar("select sum(valor) from tes_ingreso where ide_estado=1 and ide_titulo in(select ide_titulo from rec_valores where ide_cliente=" + ide_cliente + ")").get(0));

            } else {
                mensaje = "El Contribuyente " + nombre + " con documento de identificación N. " + cedula + " no tiene valores a pagar ";
                consulto = 2;
            }
        } else {
            mensaje = "El Contribuyente ingresado no existe en la base de datos del Municipio";
            consulto = 2;
        }
        con.desconectar();
    }

    public void inicio() {
        cedula = "";
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;

    }

    public int getConsulto() {
        return consulto;
    }

    public void setConsulto(int consulto) {
        this.consulto = consulto;
    }

    public List getLis_consulta() {
        return lis_consulta;
    }

    public void setLis_consulta(List lis_consulta) {
        this.lis_consulta = lis_consulta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
