package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final GameLluviaMenu game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    // Imagen de fondo
    private Texture background;

    // Textos del menú
    private String message1 = "Bienvenido a Rain Rush";
    private String message2 = "a click para comenzar!!!";

    // Layout para medir y centrar texto
    private GlyphLayout layout;

    public MainMenuScreen(final GameLluviaMenu game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Cargar imagen de fondo 
        background = new Texture(Gdx.files.internal("menu_background1.png"));

        // Configurar fuente
        layout = new GlyphLayout();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f); // texto principal más grande
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        //Dibujar fondo primero
        batch.draw(background, 0, 0, 800, 480);

        //Texto principal centrado
        layout.setText(font, message1);
        float x1 = (800 - layout.width) / 2;
        float y1 = (480 + layout.height) / 2;
        font.draw(batch, layout, x1, y1);

        //  Texto más pequeño debajo
        font.getData().setScale(1.2f);//más chico
        layout.setText(font, message2);
        float x2 = (800 - layout.width) / 2;
        float y2 = y1 - 70; // distancia vertical entre líneas
        font.draw(batch, layout, x2, y2);

        // volver a tamaño original para no afectar otros textos
        font.getData().setScale(2f);

        batch.end();

        // 🔹 Cambiar de pantalla si el jugador toca
        if (Gdx.input.isTouched()) {
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
        if (background != null) background.dispose();
    }
}
