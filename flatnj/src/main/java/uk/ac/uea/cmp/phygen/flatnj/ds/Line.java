/*
 * Phylogenetics Tool suite
 * Copyright (C) 2013  UEA CMP Phylogenetics Group
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package uk.ac.uea.cmp.phygen.flatnj.ds;

import java.awt.*;

/**
 * @author balvociute
 */
public class Line {
    Point a;
    Point b;

    int width = 1;

    Color color;

    public Line(Point a, Point b, Color c) {
        this.a = a;
        this.b = b;
        this.color = c;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }
}
