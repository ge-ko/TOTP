public interface TotpAlgorithm extends HotpAlgorithm {
    
    public abstract void setTimeStep(long timeStep);
    
    public abstract long getTimeStep();
    
    public abstract void setEpochStart(long epochStart);
    
    public abstract long getEpochStart();
    
    public abstract String generate(long unixTime);
    
}
