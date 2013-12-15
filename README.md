Phygen Suite
============

Goals:
======

The aim of this project was to create a single project that contains a number of tools and reusable tools for manipulating and creating phylogenetic trees and networks.

Compilation:
============

Phygen is developed using Java 1.7.  Therefore to compile Phygen the developer needs Java JDK 1.7+ installed on their system. In addition, the projects have been setup as Maven projects.  The developer will therefore need maven 2+ installed on their system.

From the command line to compile, test, and package the whole phygen suite, change directory into the root of phygen suite (there should be a pom.xml in this directory).  Then type “mvn clean install”.  The packaged result will be executable jars, which live in each modules “target” directory.


Developing:
===========

Most modern IDEs support maven project structures, so there should be no reason to change IDEs, however, they may need a maven plugin installed.  The exact details of how to open a maven project will vary from IDE to IDE but it should be a simple process of just opening an existing project and pointing the IDE to the pom.xml in the root directory of the PhygenSuite.  This should open all child modules within the master project.


Modules:
========

Within the parent maven project for the PhygenSuite there is a single “pom.xml” which describes common properties for all child modules.  This file contains details such as project details, developer list, compiler settings, unit test configuration, common dependencies and some common jar packaging settings. Beyond the pom.xml there are the child modules themselves, which each have their own pom.xml describing their specific configuration.  A short summary of each module follows:


Core:
-----

Contains classes that are used by other modules, that contain some kind of general functionality which means they can be used in different situations.  These classes were broken down into sub groups based on their specific kind of functionality as follows:

ds - Data structures - Commonly used phylogenetic data structures relating to concepts such as: Splits, Trees, Networks, Distances and Quartets
io - Input and Output - Classes that help loading and saving common phylogenetic file formats.  Specifically, Nexus and Phylip format.
math - Maths - Classes related to common mathematical data structures and algorithms such as basic statistics, matrix algebra, optimisation and storing of tuples.  The optimisation classes were designed so that various methods could be swapped in and out with any change to client code.  There is support for using both external and internal optimisation methods such as: apache (internal dependency), gurobi (external), glpk (external) and phygen (or own optimisation methods).
ui - User interface - Supporting classes to help with both command line interfaces and graphical user interfaces
util - Miscellaneous utilities - Anything we might conceivably want to reuse that doesn’t fit elsewhere.


FlatNJ: 
--------

Constructs a flat split system from quardruples.



Netmake:
--------

Creates a compatible split system and a circular ordering from a distance matrix and either a single weighting or a hybrid weighting configuration.


Netme:
------

Constructs a minimum evolution tree from the specified network with its implied circular order.


Phygentools:
------------

Miscellaneous tools that might be useful for the user, and might be used directly by other modules.  These include:
Chopper - Breaks down trees into Quartets
Convertor - Converts phylip to nexus format and vice versa (note this can be a lossy conversion)
PhylipCorrector - Modifies phylip files.... ???
Random Distance Generator Tool - Creates phylip or nexus files with a randomly generated distance matrix
Scaler - Scales trees within a set of trees


Qnet:
-----

Constructs a circular split system from a set of quartets.


SuperQ:
-------

Constructs a circular split system from a set of weighted or unweighted partial trees by using quartets.



Quick Start:
============

Assuming the user has access to the compiled executable jars for phygen, then they should only need JRE 1.7+ installed on their system.  To run any of the jars type: “java -jar <jar_name>-<jar_version>.jar <options>”.  To discover the options available for each jar just type “java -jar <jar_name>-<jar_version>.jar --help”



Availability:
=============

Open source code available on github: https://github.com/sbastkowski/PhygenSuite

License: GPL v3