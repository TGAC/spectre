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
package uk.ac.uea.cmp.phygen.core.ds;

import java.util.Iterator;
import java.util.Map;

/**
 * Class for storing multiple sequence alignment (MSA).
 * @author balvociute
 */
public class Alignment
{    
    //Taxa labels
    private String[] taxaLabels;
    //Sequences
    private String[] sequences;
    
    /**
     * Initializes alignment from the map that maps taxa labels to corresponding
     * sequences
     * 
     * @param aln multiple sequence alignment
     */
    public Alignment(Map<String, String> aln) 
    {
        taxaLabels = new String[aln.size()];
        sequences = new String[aln.size()];
        Iterator<String> alnIterator = aln.keySet().iterator();
        int i = 0;
        while(alnIterator.hasNext())
        {
            taxaLabels[i] = alnIterator.next();
            sequences[i] = aln.get(taxaLabels[i]);
            i++;
        }
        aln.clear();
    }

    //Initializes alignment from two arrays
    public Alignment(String[] n, String[] seq)
    {
        taxaLabels = new String[n.length];
        System.arraycopy(n, 0, taxaLabels, 0, n.length);
        sequences = new String[seq.length];
        System.arraycopy(seq, 0, sequences, 0, seq.length);
        if(!sameLength())
        {
            taxaLabels = null;
            sequences = null;
        }
    }
    
    //Prints alignment in the fasta format to the screen
    public void printAlignmentInFasta()
    {
        for(int i = 0; i < taxaLabels.length; i++)
        {
            System.out.println(">" + taxaLabels[i] + "\n" + sequences[i]);
        }
    }

    //Returns labels of taxa
    public String[] getTaxaLabels()
    {
        return taxaLabels;
    }

    public void setTaxaLabels(String[] taxaLabels)
    {
        this.taxaLabels = taxaLabels;
    }

    //Returns sequences
    public String[] getSequences()
    {
        return sequences;
    }
    
    //Transforms sequences from strings to the arrays of characters
    public char[][] getSequencesAsCharArray()
    {
        char[][] s = new char[sequences.length][sequences[0].length()];
        for (int i = 0; i < sequences.length; i++)
        {
            s[i] = sequences[i].toCharArray();
        }
        return s;
    }
    
    //Returns number of sequences
    public int size()
    {
        return sequences.length;
    }
    
    //Checks if all sequences are the same length
    public boolean sameLength()
    {
        for (int i = 0; i < sequences.length-1; i++)
        {
            if(sequences[i].length() != sequences[i+1].length())
            {
                return false;
            }
        }
        return true;
    }
}
