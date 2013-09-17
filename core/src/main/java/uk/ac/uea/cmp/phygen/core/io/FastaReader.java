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
package uk.ac.uea.cmp.phygen.core.io;

import uk.ac.uea.cmp.phygen.core.ds.Alignment;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author balvociute
 */
public class FastaReader
{
    private Map<String,String> aln;
    private BufferedReader bufferedReader;
    
    public Alignment readAlignment(String alignmentFilePath) throws IOException {
        File alignmentFile = new File(alignmentFilePath);
        
        aln = new LinkedHashMap();

        //Open alignment file
        FileReader fileReader = new FileReader(alignmentFile);
        bufferedReader = new BufferedReader(fileReader);

        /* file reading is done in method readAlignmentFile which is
         * different depending on a particular AlignmentReader that is used.
         */
        readAlignmentFromFile();
        return new Alignment(aln);
    }

    protected void readAlignmentFromFile() throws IOException {
        //id - last read sequence identifier
        String id = null;

        while(bufferedReader.ready())
        {
            String line = bufferedReader.readLine();
            line = line.trim();
            line = line.replaceAll("\\s", "");
            if(line.startsWith(">"))
            {
                id = line.substring(1);
            }
            else if(id != null)
            {
                String sequence = ((String)(aln.get(id) != null ? aln.get(id) : "")).concat(line);
                aln.put(id, sequence);
            }
        }

    }
    
}
