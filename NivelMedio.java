package puppy.code;

public class NivelMedio implements NivelStrategy {

    @Override
    public float getVelocidadCaida() {
        return 380f;
    }

    @Override
    public long getTiempoEntreGotas() {
        return 500_000_000L;
    }

    @Override
    public float getProbMala() {
        return 0.25f;
    }

    @Override
    public float getProbBuena() {
        return 0.75f;
    }
}
