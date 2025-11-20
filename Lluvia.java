package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;

/**
 * Clase Lluvia con Patrón Strategy.
 */
public class Lluvia extends Entidad {

	private Array<Gota> gotas;
	private Music rainMusic;

	private float velocidadCaida;
	private long tiempoEntreGotas;
	private long lastDrop;

	private int nivelActual = 1;
	private float progresoNivel = 0f;

	private GotaFactory gotaFactory;

	// === STRATEGY ===
	private NivelStrategy strategy;

	public Lluvia(Music rainMusic) {
		super(0, 0);
		this.rainMusic = rainMusic;

		// Nivel inicial
		setNivelStrategy(new NivelFacil(), 1);
		this.gotaFactory = new DefaultGotaFactory();

	}

	@Override
	public void crear() {
		gotas = new Array<>();
		rainMusic.setLooping(true);
		rainMusic.play();
	}

	@Override
	public void actualizarMovimiento() {
		// no usado en esta clase
	}

	public boolean actualizarMovimiento(Tarro tarro) {
		float delta = Gdx.graphics.getDeltaTime();

		// Crear gotas según dificultad (strategy)
		if (TimeUtils.nanoTime() - lastDrop > tiempoEntreGotas) {
			crearGota();
		}

		// Mover gotas + detectar colisiones
		for (int i = 0; i < gotas.size; i++) {
			Gota g = gotas.get(i);
			g.mover(delta);

			if (g.fueraPantalla()) {
				gotas.removeIndex(i);
				i--;
				continue;
			}

			if (g.getArea().overlaps(tarro.getArea())) {
				g.onColision(tarro);
				gotas.removeIndex(i);
				i--;

				if (tarro.getVidas() <= 0)
					return false;
			}
		}

		// Acumular progreso para subir nivel
		progresoNivel += delta * 0.05f;
		if (progresoNivel >= 1) {
			progresoNivel = 0;
			subirNivel();
		}

		return true;
	}

	// =====================================================
	// =============== CAMBIO DE NIVEL ===================
	// =====================================================

	private void subirNivel() {
		nivelActual++;

		// máximo nivel 3
		if (nivelActual > 3)
			nivelActual = 3;

		switch (nivelActual) {
		case 1:
			setNivelStrategy(new NivelFacil(), 1);
			break;
		case 2:
			setNivelStrategy(new NivelMedio(), 2);
			break;
		case 3:
			setNivelStrategy(new NivelDificil(), 3);
			break;
		}
	}

	public void setNivelStrategy(NivelStrategy strategy, int nivel) {
		this.strategy = strategy;
		this.nivelActual = nivel;

		// aplicar valores de la estrategia al juego
		this.velocidadCaida = strategy.getVelocidadCaida();
		this.tiempoEntreGotas = strategy.getTiempoEntreGotas();
	}

	// =====================================================
	// =============== CREAR GOTAS =======================
	// =====================================================

	private void crearGota() {
		float x = MathUtils.random(0, 800 - 48);
		float prob = MathUtils.random();

		if (prob < strategy.getProbMala())
			gotas.add(gotaFactory.crearGotaMala(x, 480, velocidadCaida));
		else if (prob < strategy.getProbBuena())
			gotas.add(gotaFactory.crearGotaBuena(x, 480, velocidadCaida));
		else
			gotas.add(gotaFactory.crearGotaPower(x, 480, velocidadCaida));

		lastDrop = TimeUtils.nanoTime();
	}

	@Override
	public void dibujar(SpriteBatch batch) {
		for (Gota g : gotas)
			g.dibujar(batch);
	}

	// ================= MOSTRAR NIVEL ======================
	public int getNivel() {
		return nivelActual;
	}

	// ================= COMPATIBILIDAD =====================
	public Array<Rectangle> getRaindrops() {
		Array<Rectangle> areas = new Array<>();
		for (Gota g : gotas) {
			areas.add(g.getArea());
		}
		return areas;
	}

	public void pausar() {
		if (rainMusic != null)
			rainMusic.pause();
	}

	public void continuar() {
		if (rainMusic != null)
			rainMusic.play();
	}

	@Override
	public void destruir() {
		if (rainMusic != null)
			rainMusic.dispose();
	}
}
