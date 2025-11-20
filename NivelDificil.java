package puppy.code;

public class NivelDificil implements NivelStrategy {

    @Override
    public float getVelocidadCaida() {
        return 450f;
    }

    @Override
    public long getTiempoEntreGotas() {
        return 350_000_000L;
    }

    @Override
    public float getProbMala() {
        return 0.35f;
    }

    @Override
    public float getProbBuena() {
        return 0.65f;
    }
}
