package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Texture gameOverImage;
    private ShapeRenderer shape;

    public GameOverScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Imagen del fondo de Game Over
        gameOverImage = new Texture(Gdx.files.internal("gameover.png"));

        // Para dibujar el rectángulo de fondo del texto
        shape = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);

        // Fondo con la imagen
        batch.begin();
        batch.draw(gameOverImage, 0, 0, 800, 480);
        batch.end();

        // Fondo del texto (semi-transparente)
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0, 0, 0, 0.5f)); // negro con 50% de transparencia
        shape.rect(180, 150, 440, 120); // posición y tamaño del rectángulo
        shape.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Texto encima del fondo
        batch.begin();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f); // letras grandes
        font.draw(batch, "GAME OVER", 270, 230);
        font.getData().setScale(1f); // tamaño normal
        font.draw(batch, "Toca para reiniciar", 300, 180);
        batch.end();

        // Reinicia el juego al tocar o presionar espacio
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (gameOverImage != null) gameOverImage.dispose();
        if (shape != null) shape.dispose();
    }
}

