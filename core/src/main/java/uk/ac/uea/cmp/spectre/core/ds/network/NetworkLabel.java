/*
 * Suite of PhylogEnetiC Tools for Reticulate Evolution (SPECTRE)
 * Copyright (C) 2017  UEA School of Computing Sciences
 *
 * This program is free software: you can redistribute it and/or modify it under the term of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package uk.ac.uea.cmp.spectre.core.ds.network;

import java.awt.*;


/**
 * @author balvociute
 */
public class NetworkLabel {

    private String name;

    private int vertexId;

    private Vertex vertex;

    private double offsetX = 1;
    private double offsetY = 1;

    private int height;
    private int width;
    private Color bgColor;
    private Color fontColor = Color.BLACK;
    private String fontFamily = Font.DIALOG;
    private String fontStyle = "plain";
    private int intStyle = Font.PLAIN;
    private int fontSize = 10;

    private Font font;
    private FontMetrics fontMetrics;

    public Boolean movable = true;


    public NetworkLabel() {
        initFont();
    }

    public NetworkLabel(String label) {
        setName(label);
        initFont();
    }


    @Override
    public String toString() {
        return (name + " [" + offsetX + "," + offsetY + "]; [" + fontFamily + ", " + fontStyle + ", " + fontSize + "]; [" + fontColor.getRed() + " " + fontColor.getGreen() + " " + fontColor.getBlue() + "]");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public String getName() {
        return name;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public int getVertexId() {
        return vertexId;
    }

    public void setVertexId(int vertexId) {
        this.vertexId = vertexId;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public void setBackgoundColor(Color c) {
        bgColor = c;
    }

    public Color getBackgroundColor() {
        return bgColor;
    }

    public void setFontColor(Color c) {
        fontColor = c;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontFamily(String family) {
        fontFamily = family;
        initFont();
    }

    public void setFontStyle(String style) {
        fontStyle = style.toLowerCase();
        intStyle = convertFontStyle();
        initFont();
    }

    public void setFontSize(int size) {
        fontSize = size;
        updateOffsets();
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getFontSize() {
        return fontSize;
    }

    public int convertFontStyle() {
        int style = 0;
        if (fontStyle.contains("plain")) {
            style = Font.PLAIN;
        } else {
            if (fontStyle.contains("bold")) {
                style += Font.BOLD;
            }
            if (fontStyle.contains("italic")) {
                style += Font.ITALIC;
            }
        }
        return style;
    }

    public int getFontStyle() {
        return intStyle;
    }

    public Font getFont() {
        return font;
    }

    private void initFont() {
        font = new Font(fontFamily, getFontStyle(), fontSize);
        fontMetrics = new Canvas().getFontMetrics(font);
        if (name != null) {
            width = fontMetrics.stringWidth(name);
            height = fontMetrics.getHeight();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }


    private void updateOffsets() {
        int prevWidth = width;
        int prevHeight = height;
        initFont();
        if (offsetX < 0) {
            offsetX += (prevWidth - width);
        }
        if (offsetY > 0) {
            offsetY -= (prevHeight - height);
        }
    }

    public void makeBold(boolean bold) {
        if (bold && (intStyle == 0 || intStyle == 2)) {
            intStyle++;
        } else if (!bold && (intStyle == 1 || intStyle == 3)) {
            intStyle--;
        }
        updateOffsets();
    }

    public void makeItalic(boolean italic) {
        if (italic && (intStyle <= 1)) {
            intStyle += 2;
        } else if (!italic && intStyle >= 2) {
            intStyle -= 2;
        }
        updateOffsets();
    }
}
