import javax.swing.*;
import java.awt.*;

public class Bola extends Sprite{
    private int xdir;
    private int ydir;
    private int velocidad = 2;
    public Bola(){
        iniciarBola();
    }

    private void iniciarBola() {
        xdir = 1;
        ydir = -1;

        cargarImagen();
        getDimensiones();
        reinciarEstado();
    }

    private void cargarImagen() {
        var ii = new ImageIcon("src/recursos/imagenes/ball.png");
        img = ii.getImage();
    }

    // Mueve la bola en el tablero, si la bola toca los bordes
    // su direccion cambia hicia esa direccion (rebota).
    void mover(){
        x += xdir * velocidad;
        y += ydir * velocidad;

        if (x <= 0 || x >= Ajustes.ANCHO - anchoImg) {
            xdir = -xdir;
        }

        if (y <= 0) {
            ydir = -ydir;
        }
    }

    public void masVelocidad (int incremento) {
        velocidad += incremento;
    }

    private void reinciarEstado() {
        x = Ajustes.BOLA_X;
        y = Ajustes.BOLA_Y;
    }

    public void setXdir(int x) {
        xdir = x;
    }

    public int getYdir() {
        return ydir;
    }
    public void setYdir(int y) {
        ydir = y;
    }
}
