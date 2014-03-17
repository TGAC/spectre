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

package uk.ac.uea.cmp.spectre.flatnj.netvi;

import uk.ac.uea.cmp.spectre.flatnj.fdraw.Vertex;

import java.awt.*;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author balvociute
 */
public class Point extends Element implements Selectable
{
    int id;
    Label l;
    boolean round;
    
    boolean suppressed = false;
    
    Point supp;
    
    int quarterNo;
    
    Set<Label> neighborhood;
    
    Cluster cluster;
    
    Vertex v;
    
    int width;
    int height;
    
    boolean selected = false;

    @Override
    public double getX()
    {
        if(!suppressed)
        {
            return super.getX();
        }
        else
        {
            return supp.getX();
        }
    }

    @Override
    public double getY()
    {
        if(!suppressed)
        {
            return super.getY();
        }
        else
        {
            return supp.getY();
        }
    }
    @Override
    public int getXint()
    {
        return (int) getX();
    }

    @Override
    public int getYint()
    {
        return (int) getY();
    }
    
    public void suppress(Point supp)
    {
        suppressed = true;
        this.supp = supp;
    }
    
    public void unSuppress()
    {
        suppressed = false;
    }
    
    public Point(Vertex v)
    {
        this.v = v;
        height = v.getHeight();
        width = v.getWidth();
        id = v.getNxnum();
        if(v.getShape() == null)
        {
            round = true;
        }
        else if(v.getShape().equals("r"))
        {
            round = false;
        }
        else if(v.getShape().contentEquals("n"))
        {
            round = true;
            height = 0;
            width = 0;
        }
    }

    public Point(double ix, double iy)
    {
        setX(ix);
        setY(iy);
    }

    public void setCoordinates(int x, int y, Collection<Label> labels)
    {
        setX(x);
        setY(y);
    }

    double distanceTo(Point p2)
    {
        return Math.sqrt((getX() - p2.getX())*(getX() - p2.getX()) +
                         (getY() - p2.getY())*(getY() - p2.getY()));
    }

    void setSize(int i)
    {
        //l.label.setOffsetX((int) (l.label.getOffsetX() - Math.signum(l.label.getOffsetX())*(width - i)*0.5));
        width = i;
        //l.setOffY((int) (l.label.getOffsetY() - Math.signum(l.label.getOffsetY())*(height - i)*0.5));
        height = i;
        v.setSize(i);
    }
    
    public int getSize()
    {
        return v.getHeight();
    }

    double distanceToBoundary(Rectangle bounds)
    {
        double dist1 = getX();
        double dist2 = getY();
        double dist3 = bounds.getWidth() - getX();
        double dist4 = bounds.getHeight() - getY();
        
        if(dist1 < dist2 && dist1 < dist3 && dist1 < dist4)
        {
            return dist1;
        }
        else if(dist2 < dist3 && dist2 < dist4)
        {
            return dist2;
        }
        else if(dist3 < dist4)
        {
            return dist3;
        }
        else
        {
            return dist4;
        }
    }

    void setBg(Color bg)
    {
        v.setBackgroundColor(bg);
    }

    void setFg(Color fg)
    {
        v.setLineColor(fg);
    }
    
    public Color getBg()
    {
        return v.getBackgroundColor();
    }
    
    public Color getFg()
    {
        return v.getLineColor();
    }

    void setCoordinates(int x, int y)
    {
        setX(x);
        setY(y);
    }

    boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    void setL(Label l)
    {
        this.l = l; 
        l.p = this;   
    }
}