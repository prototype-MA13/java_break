import javax.sound.sampled.Clip;

public class ControladorSonido {
    private Sonido colision;
    private Sonido victoria;
    private Sonido derrota;

    public ControladorSonido() {
        colision = new Sonido("colision.wav");
        victoria = new Sonido("victoria.wav");
        derrota = new Sonido("derrota.wav");
    }
    public void playColision() {
        colision.play();
    }
    public void playVictoria() {
        victoria.play();
    }
    public void playDerrota() {
        derrota.play();
    }
}