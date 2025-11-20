package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class SpriteManager implements Disposable {

    private static SpriteManager instance;

    public static SpriteManager getInstance() {
        if (instance == null) {
            instance = new SpriteManager();
        }
        return instance;
    }

    public Texture tarro;      // ← tu único tarro
    public Texture gotaBuena;
    public Texture gotaMala;
    public Texture gotaPower;
    public Texture fondoMenu;
    public Texture fondoJuegoTarde;
    public Texture fondoJuegoNoche;
    public Texture fondoJuegoMañana;
    public Texture pausa;
    public Sound sonidoGotaBuena;
    public Sound sonidoGotaMala;
    public Sound sonidoGotaPower;


    private SpriteManager() {
        cargarSprites();
    }

    private void cargarSprites() {
        gotaBuena = new Texture("drop.png");
        gotaMala = new Texture("dropBad.png");
        gotaPower = new Texture("gotaPower.jpg");

        fondoMenu = new Texture("menu_background1.png");
        fondoJuegoMañana = new Texture("background_day.png");
        fondoJuegoTarde = new Texture("background_evening.png");
        fondoJuegoNoche = new Texture("background_night.png");


        pausa = new Texture("pause_background.png");

        tarro = new Texture("bucket.png");    // ← tu textura única

        sonidoGotaBuena = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        sonidoGotaMala  = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        sonidoGotaPower = Gdx.audio.newSound(Gdx.files.internal("powerup.mp3"));

    }

    @Override
    public void dispose() {
        tarro.dispose();

        gotaBuena.dispose();
        gotaMala.dispose();
        gotaPower.dispose();

        fondoMenu.dispose();
        fondoJuegoMañana.dispose();
        fondoJuegoTarde.dispose();
        fondoJuegoNoche.dispose();

        pausa.dispose();
        sonidoGotaBuena.dispose();
        sonidoGotaMala.dispose();
        sonidoGotaPower.dispose();

    }
}

