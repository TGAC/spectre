#!/bin/bash

/usr/local/java/jdk1.8.0_31/bin/javapackager -deploy -native dmg -name spectre -title "Suite of PhylogEnetiC Tools for Reticulate Evolution" -description "Spectre a suite of tools for inferring evolutionary patterns associated with Reticulate Evolution that primarily either create or use split systems (a collection of bipartitions of the taxa) representable in two dimensions such as SuperQ FlatNJ NetME and several NeighborNet variants." -vendor "Earlham Institute" -appclass uk.ac.uea.cmp.spectre.viewer.Spectre -srcdir "/home/dan/dev/spectre/dist/../build/spectre-1.0.1" -outdir /home/dan/dev/spectre/dist/../build/dist -outfile spectre-1.0.1 -v -BlicenseFile=LICENSE -BmainJar=jar/viewer-1.0.1.jar -BclassPath="etc/*:jar/*" -BappVersion=1.0.1 -Bicon=/home/dan/dev/spectre/dist/../etc/logo.png -Bcategory=Education
