package puppy.code;

public class NivelFacil implements NivelStrategy {

    @Override
    public float getVelocidadCaida() {
        return 280f;
    }

    @Override
    public long getTiempoEntreGotas() {
        return 700_000_000L;
    }

    @Override
    public float getProbMala() {
        return 0.20f;
    }

    @Override
    public float getProbBuena() {
        return 0.80f;
    }
}
