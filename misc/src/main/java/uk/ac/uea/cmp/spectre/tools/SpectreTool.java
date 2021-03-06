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

package uk.ac.uea.cmp.spectre.tools;

import org.apache.commons.cli.*;
import uk.ac.uea.cmp.spectre.core.ui.cli.CommandLineHelper;
import uk.ac.uea.cmp.spectre.core.util.LogConfig;
import uk.ac.uea.cmp.spectre.core.util.Service;

import java.io.IOException;

public abstract class SpectreTool implements Service {

    public static final String OPT_HELP = "help";
    public static final String OPT_VERSION = "version";

    protected abstract Options createInternalOptions();

    protected abstract void execute(CommandLine commandLine) throws IOException;

    public abstract String getDescription();
    public abstract String getPosArgs();


    public Options createOptions() {
        Options options = this.createInternalOptions();
        options.addOption(new Option("?", OPT_HELP, false, "Print this message."));
        options.addOption(new Option("V", OPT_VERSION, false, "Print the current version."));
        return options;
    }

    public void printUsage() {
        new HelpFormatter().printHelp(
                CommandLineHelper.DEFAULT_WIDTH,
                this.getName() + " [options] " + this.getPosArgs(),
                this.getDescription() + "\nOptions:",
                createOptions(),
                CommandLineHelper.DEFAULT_FOOTER,
                false);
    }

    public CommandLine parse(String[] args) throws ParseException {
        return new PosixParser().parse(createOptions(), args);
    }

    public void execute(String[] args) throws IOException {

        try {
            LogConfig.defaultConfig();

            CommandLine commandLine = this.parse(args);

            if (commandLine.hasOption(OPT_HELP) || commandLine.getArgs().length == 0) {
                printUsage();
            }
            else if (commandLine.hasOption(OPT_VERSION)) {
                System.out.println("spectre " + CommandLineHelper.class.getPackage().getImplementationVersion());
            }
            else {
                this.execute(commandLine);
            }
        } catch (ParseException p) {
            System.err.println(p.getMessage());
            printUsage();
        }
    }
}
