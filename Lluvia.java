package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia extends Entidad implements Colisionable {

    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaPower;
    private Sound dropSound;
    private Sound powerSound;
    private Music rainMusic;

    private float velocidadCaida = 300f;
    private float tiempoEntreGotas = 700_000_000; // nanosegundos
    private int nivelActual = 1;
    private float progresoNivel = 0f;

    public Lluvia(Texture gotaBuena, Texture gotaMala, Sound dropSound, Music rainMusic) {
        super(0, 0);
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
        this.gotaPower = new Texture(Gdx.files.internal("gotaPower.jpg"));
        this.powerSound = Gdx.audio.newSound(Gdx.files.internal("powerup.mp3"));
    }

    @Override
    public void crear() {
        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.2f);
        rainMusic.play();
    }

    // Obligatorio por Entidad 
    @Override
    public void actualizarMovimiento() {
    }

   
    public boolean actualizarMovimiento(Tarro tarro) {
        float delta = Gdx.graphics.getDeltaTime();

        // Crear gotas nuevas
        if (TimeUtils.nanoTime() - lastDropTime > tiempoEntreGotas) {
            crearGotaDeLluvia();
        }

        // Movimiento de gotas
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle drop = rainDropsPos.get(i);
            int tipo = rainDropsType.get(i);

            drop.y -= velocidadCaida * delta;

            // Si cae fuera de pantalla
            if (drop.y + 32 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--;
                continue;
            }

            // Si toca el tarro
            if (drop.overlaps(tarro.getArea())) {
                if (tipo == 1) { // mala
                    tarro.restarVida();
                    if (tarro.getVidas() <= 0) return false;
                } else if (tipo == 2) { // buena
                    tarro.sumarPuntos(10);
                    dropSound.play();
                } else if (tipo == 3) { // powerup
                    tarro.activarBoostVelocidad();
                    powerSound.play();
                }
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
                i--;
            }
        }

        // Subir nivel con el tiempo
        progresoNivel += delta * 0.05f;
        if (progresoNivel >= 1f) {
            progresoNivel = 0f;
            nivelActual++;
            velocidadCaida *= 1.1f;
            tiempoEntreGotas *= 0.9f;
            if (tiempoEntreGotas < 150_000_000) tiempoEntreGotas = 150_000_000;
        }

        return true;
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 48);
        raindrop.y = 480;
        raindrop.width = 48;
        raindrop.height = 48;
        rainDropsPos.add(raindrop);

        float prob = MathUtils.random();
        if (prob < 0.25f) rainDropsType.add(1); // mala
        else if (prob < 0.95f) rainDropsType.add(2); // buena
        else rainDropsType.add(3); // powerup

        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        actualizarDibujoLluvia(batch);
    }

    //Métodos que GameScreen necesita

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            int tipo = rainDropsType.get(i);
            if (tipo == 1)
                batch.draw(gotaMala, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
            else if (tipo == 2)
                batch.draw(gotaBuena, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
            else
                batch.draw(gotaPower, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
        }
    }

    public void pausar() {
        rainMusic.pause();
    }

    public void continuar() {
        rainMusic.play();
    }

    public int getNivel() {
        return nivelActual;
    }

    public Array<Rectangle> getRaindrops() {
        return rainDropsPos;
    }

    

    @Override
    public Rectangle getArea() {
        return new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void onColision(Colisionable otro) {
        
    }

    @Override
    public void destruir() {
        if (gotaBuena != null) gotaBuena.dispose();
        if (gotaMala != null) gotaMala.dispose();
        if (gotaPower != null) gotaPower.dispose();
        if (dropSound != null) dropSound.dispose();
        if (powerSound != null) powerSound.dispose();
        if (rainMusic != null) rainMusic.dispose();
    }
}
