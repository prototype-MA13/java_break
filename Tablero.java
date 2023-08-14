import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Tablero extends JPanel {
    private Timer tiempo;
    private String msg = "Fin del juego";
    private Bola bola;
    private Paleta paleta;
    private Bloque[] bloques;
    private boolean enJuego = true;
    final ControladorSonido controladorSonido;
    private Image imagenFondo;
    private JButton reiniciarButton;

    public Tablero() {
        controladorSonido = new ControladorSonido();
        IniciarTablero();
    }

    private void IniciarTablero() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Ajustes.ANCHO, Ajustes.ALTO));
        ImageIcon fondo = new ImageIcon("src/recursos/imagenes/fondo.png");
        imagenFondo = fondo.getImage();
        iniciarJuego();
    }

    // Al iniciar el juego se crea una bola, una paleta y la cantidad definida
    // de bloques en Ajustes (30), se crea e inicia un temporizador
    private void iniciarJuego() {
        bloques = new Bloque[Ajustes.BLOQUES];

        bola = new Bola();
        paleta = new Paleta();

        int k = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                int x = (400 - (6 * 61)) / 2 + j * 61;
                int y = i * 28 + 17;
                bloques[k] = new Bloque(x, y);
                k++;
            }
        }
        tiempo = new Timer(Ajustes.TIEMPO, new CicloJuego());
        tiempo.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        if (enJuego) {
            dibujarObjetos(g2d);
        } else {
            juegoTerminado(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    // dibuja en pantalla todos los componentes en el juego.
    // los Sprites se crean con el metodo drawImage()
    private void dibujarObjetos(Graphics2D g2d) {
        g2d.drawImage(bola.getImg(), bola.getX(), bola.getY(),
                bola.getAnchoImg(), bola.getAltoImg(), this);
        g2d.drawImage(paleta.getImg(), paleta.getX(), paleta.getY(),
                paleta.getAnchoImg(), paleta.getAltoImg(), this);

        for (int i = 0; i < Ajustes.BLOQUES; i++) {
            if (!bloques[i].isDestruido()) {
                g2d.drawImage(bloques[i].getImg(), bloques[i].getX(),
                        bloques[i].getY(), bloques[i].getAnchoImg()
                        , bloques[i].getAltoImg(), this);
            }
        }
    }

    private void juegoTerminado(Graphics2D g2d) {
        // crea un boton para reiniciar el juego
        reiniciarButton = new JButton("Reiniciar");
        reiniciarButton.setFocusable(false);
        reiniciarButton.addActionListener(e -> reiniciarJuego());

        // Agregar el botón al panel
        add(reiniciarButton);
        reiniciarButton.setBounds((Ajustes.ANCHO - 100) / 2, Ajustes.ALTO / 2 + 30, 100, 30);

        var letra = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fm = this.getFontMetrics(letra);

        g2d.setColor(Color.WHITE);
        g2d.setFont(letra);
        g2d.drawString(msg,
                (Ajustes.ANCHO - fm.stringWidth(msg)) / 2,
                Ajustes.ANCHO / 2);
    }

    private void reiniciarJuego() {
        // Restablecer elementos del juego
        bola = new Bola();
        paleta = new Paleta();

        for (int i = 0; i < Ajustes.BLOQUES; i++) {
            bloques[i].setDestruido(false);
        }

        enJuego = true;

        // Iniciar el temporizador
        tiempo.start();

        // Eliminar el botón de reinicio y repinta el panel
        remove(reiniciarButton);
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            paleta.keyLiberada(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paleta.keyPresionada(e);
        }
    }

    // este metodo llama periodicamente al metodo actionPerformed(),
    // el cual llama a iniciarCiclo para crear un ciclo de juego
    private class CicloJuego implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            iniciarCiclo();
        }
    }

    // este metodo mueve la bola y la paleta, comprobando colisiones y
    // actualizando los elementos en pantalla.
    private void iniciarCiclo() {
        bola.mover();
        paleta.mover();
        colision();
        repaint();
    }

    private void detenerJuego() {
        enJuego = false;
        tiempo.stop();
    }

    private void colision() {
        // si la pelota toca el fondo, se termina el juego
        if (bola.getRec().getMaxY() > Ajustes.LIMITE){
            // sonido de derrota
            controladorSonido.playDerrota();
            detenerJuego();
        }

        // se comprueba la cantidad de bloques eliminados, si todos
        // son destruidos, ganamos el juego.
        for (int i = 0, j = 0; i < Ajustes.BLOQUES; i++){
            if (bloques[i].isDestruido()){
                j++;
            }
            if(j == Ajustes.BLOQUES){
                msg = "Ganaste";
                controladorSonido.playVictoria();
                detenerJuego();
            }
        }
        if (bola.getRec().intersects(paleta.getRec())){
            int paletaPos = (int) paleta.getRec().getMinX();
            int bolaPos = (int) bola.getRec().getMinX();

            int primero = paletaPos + 8;
            int segundo = paletaPos + 16;
            int tercero = paletaPos + 24;
            int cuarto = paletaPos + 32;

            // si la bola golpea la primera parte de la paleta,
            // la pelota se mueve hacia el noroeste.
            if (bolaPos < primero){
                bola.setXdir(-1);
                bola.setYdir(-1);
            }
            if (bolaPos >= primero && bolaPos < segundo){
                bola.setXdir(-1);
                bola.setYdir(-1 * bola.getYdir());
            }
            if (bolaPos >= segundo && bolaPos < tercero){
                bola.setXdir(0);
                bola.setYdir(-1);
            }
            if (bolaPos >= tercero && bolaPos < cuarto){
                bola.setXdir(1);
                bola.setYdir(-1 * bola.getYdir());
            }
            if (bolaPos > cuarto){
                bola.setXdir(1);
                bola.setYdir(-1);
            }
        }
        for (int i = 0;  i < Ajustes.BLOQUES; i++){
            if ((bola.getRec().intersects(bloques[i].getRec()))) {
                int bolaIzq = (int) bola.getRec().getMinX();
                int bolaAlto = (int) bola.getRec().getHeight();
                int bolaAncho = (int) bola.getRec().getWidth();
                int bolaTop = (int) bola.getRec().getMinY();

                var pDerecho = new Point(bolaIzq + bolaAncho + 1, bolaTop);
                var pIzquierdp = new Point(bolaIzq - 1, bolaTop);
                var pArriba = new Point(bolaIzq, bolaTop - 1);
                var pAbajo = new Point(bolaIzq, bolaTop + bolaAlto + 1);

                if (!bloques[i].isDestruido()) {
                    if (bloques[i].getRec().contains(pDerecho)){
                        bola.setXdir(-1);
                    } else if (bloques[i].getRec().contains(pIzquierdp)) {
                        bola.setXdir(1);
                    }
                    // si la bola golpea la parte inferior del bloque, cambia
                    // de direccion hacia abajo.
                    if (bloques[i].getRec().contains(pArriba)) {
                        bola.setYdir(1);
                    } else if (bloques[i].getRec().contains(pAbajo)) {
                        bola.setYdir(-1);
                    }
                    bloques[i].setDestruido(true);
                    controladorSonido.playColision();
                }
            }
        }
    }
}