package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen implements Screen {
    final GameLluviaMenu game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Tarro tarro;
    private Lluvia lluvia;
    private Music musicIntro;
    private Music musicLoop;
    private boolean musicaActiva = false;

    //Fondos (día/tarde/noche)
    private Texture backgroundDay;
    private Texture backgroundEvening;
    private Texture backgroundNight;
    private Texture[] backgrounds;
    private int bgCurrent = 0;
    private int bgNext = 1;
    private float bgTimer = 0f;
    private float bgAlpha = 0f;
    private boolean bgTransition = false;

    private static final float BG_CHANGE_EVERY = 20f;
    private static final float BG_FADE_TIME = 3f;

    // Nubes 
    private Texture nube1;
    private Texture nube2;
    private float nube1X, nube2X;
    private float nube1Y, nube2Y;

    // Debug 
    private boolean debugHitboxes = false;
    private ShapeRenderer shape;

    public GameScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        // sonidos e imágenes
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        // 🎵 música
        musicIntro = Gdx.audio.newMusic(Gdx.files.internal("musicintro.ogg"));
        musicLoop = Gdx.audio.newMusic(Gdx.files.internal("musicloop.ogg"));
        musicLoop.setLooping(true);

        // 🔉 establecer volumen 40%
        musicIntro.setVolume(0.3f);
        musicLoop.setVolume(0.2f);

        // lluvia
        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);

        // cámara y shape
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        shape = new ShapeRenderer();

        // creación de objetos
        tarro.crear();
        lluvia.crear();

        // fondos
        backgroundDay = new Texture(Gdx.files.internal("background_day.png"));
        backgroundEvening = new Texture(Gdx.files.internal("background_evening.png"));
        backgroundNight = new Texture(Gdx.files.internal("background_night.png"));
        backgrounds = new Texture[]{backgroundDay, backgroundEvening, backgroundNight};

        // nubes
        nube1 = new Texture(Gdx.files.internal("nube1.png"));
        nube2 = new Texture(Gdx.files.internal("nube2.png"));
        nube1X = 100;
        nube1Y = 300;
        nube2X = 520;
        nube2Y = 270;
    }


    // 🎵 MÚSICA DE INTRO + LOOP

    @Override
    public void show() {
        if (!musicaActiva) {
            musicaActiva = true;
            musicIntro.play();
            musicIntro.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    if (!musicLoop.isPlaying()) {
                        musicLoop.play();
                    }
                }
            });
        }
        lluvia.continuar();
    }

    
    // 🎮 LÓGICA PRINCIPAL
    
    @Override
    public void render(float delta) {
        // Pausa con tecla ESC
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pause();
            return;
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Fondo con transición
        bgTimer += delta;
        if (!bgTransition && bgTimer >= BG_CHANGE_EVERY) {
            bgTransition = true;
            bgTimer = 0f;
            bgAlpha = 0f;
            bgNext = (bgCurrent + 1) % backgrounds.length;
        }
        if (bgTransition) {
            bgAlpha = Math.min(1f, bgTimer / BG_FADE_TIME);
            if (bgAlpha >= 1f) {
                bgCurrent = bgNext;
                bgTransition = false;
                bgAlpha = 0f;
                bgTimer = 0f;
            }
        }

        batch.begin();

        // Fondo actual
        batch.draw(backgrounds[bgCurrent], 0, 0, 800, 480);
        if (bgTransition) {
            batch.setColor(1f, 1f, 1f, bgAlpha);
            batch.draw(backgrounds[bgNext], 0, 0, 800, 480);
            batch.setColor(1f, 1f, 1f, 1f);
        }

        // Nubes
        nube1X -= 20 * delta;
        nube2X -= 25 * delta;
        if (nube1X < -200) nube1X = 800;
        if (nube2X < -200) nube2X = 800;
        batch.draw(nube1, nube1X, nube1Y, 200, 110);
        batch.draw(nube2, nube2X, nube2Y, 150, 85);

        // HUD
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
        font.draw(batch, "Gotas: " + tarro.getPuntos(), 10, 470);
        font.draw(batch, "Vidas: " + tarro.getVidas(), 670, 470);
        font.draw(batch, "HighScore: " + game.getHigherScore(), 340, 450);

        // Nivel
        switch (bgCurrent) {
            case 0: font.setColor(Color.GOLD); break;
            case 1: font.setColor(Color.ORANGE); break;
            case 2: font.setColor(Color.SKY); break;
            default: font.setColor(Color.WHITE); break;
        }

        font.draw(batch, "Nivel: " + lluvia.getNivel(), 20, 40);
        font.setColor(Color.WHITE);

        // Lógica del juego
        tarro.actualizarMovimiento();
        boolean sigueJugando = lluvia.actualizarMovimiento(tarro);

        if (!sigueJugando) {
            if (game.getHigherScore() < tarro.getPuntos())
                game.setHigherScore(tarro.getPuntos());
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);
        batch.end();

        // Debug 
        if (debugHitboxes) {
            shape.setProjectionMatrix(camera.combined);
            shape.begin(ShapeRenderer.ShapeType.Line);
            shape.setColor(Color.RED);
            Rectangle r = tarro.getArea();
            shape.rect(r.x, r.y, r.width, r.height);
            shape.setColor(Color.CYAN);
            for (Rectangle drop : lluvia.getRaindrops())
                shape.rect(drop.x, drop.y, drop.width, drop.height);
            shape.end();
        }
    }

    // Ciclo de vida
    @Override public void resize(int width, int height) {}
    @Override public void hide() {}
    @Override public void resume() {}

    @Override
    public void pause() {
        lluvia.pausar();
        game.setScreen(new PausaScreen(game, this));
    }

    @Override
    public void dispose() {
        tarro.destruir();
        lluvia.destruir();
        if (nube1 != null) nube1.dispose();
        if (nube2 != null) nube2.dispose();
        if (backgroundDay != null) backgroundDay.dispose();
        if (backgroundEvening != null) backgroundEvening.dispose();
        if (backgroundNight != null) backgroundNight.dispose();
        if (shape != null) shape.dispose();
        if (musicIntro != null) musicIntro.dispose();
        if (musicLoop != null) musicLoop.dispose();
    }
}

