package edu.ncsu.csc492.team8.ocr;

import java.awt.image.BufferedImage;

public interface HandlesOMR {
    public String OmrFromString(String input);
    public boolean isChecked(BufferedImage total, int top_left_x, int top_left_y, int width, int height, long threshhold);
}
