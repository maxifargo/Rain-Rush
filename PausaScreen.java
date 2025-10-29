package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PausaScreen implements Screen {

    private final GameLluviaMenu game;
    private final GameScreen juego;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture background;

    public PausaScreen(final GameLluviaMenu game, GameScreen juego) {
        this.game = game;
        this.juego = juego;
        this.batch = game.getBatch();
        this.font = game.getFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //  Fondo de pausa 
        background = new Texture(Gdx.files.internal("pause_background.png"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, 800, 480);
        font.draw(batch, "ahora estas en pausa", 300, 260);
        font.draw(batch, "Haz click izquierdo para continuar", 250, 200);
        batch.end();

        // Solo click izquierdo reanuda el juego
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(juego);
            dispose();
        }
    }

    @Override
    public void dispose() {
        background.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
