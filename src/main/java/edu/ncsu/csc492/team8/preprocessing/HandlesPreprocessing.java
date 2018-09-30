package edu.ncsu.csc492.team8.preprocessing;


public interface HandlesPreprocessing {
    public byte[] adjust(float contrast, float brightness, float scale, byte[] image);
}
