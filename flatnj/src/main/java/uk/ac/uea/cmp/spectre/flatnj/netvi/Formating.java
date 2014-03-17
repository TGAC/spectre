/*
 * Suite of PhylogEnetiC Tools for Reticulate Evolution (SPECTRE)
 * Copyright (C) 2014  UEA School of Computing Sciences
 *
 * This program is free software: you can redistribute it and/or modify it under the term of the GNU General Public
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

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * @author balvociute
 */
public class Formating extends javax.swing.JFrame {

    JPanel drawing;
    boolean changeShape = false;
    boolean changeSize = false;
    boolean changeLineColor = false;
    boolean changeFillColor = false;
    boolean changeFontColor = false;
    private boolean changeFontSize;
    private boolean changeBold;
    private boolean changeItalic;

    /**
     * Creates new form Loading
     */
    public Formating(JPanel drawing) {
        initComponents();
        this.drawing = drawing;
        jSpinnerSize.setSize(jSpinnerSize.getSize().height,
                jComboBoxShape.getSize().width);
        jSpinnerSize.setModel(new SpinnerNumberModel(1, 0, 50, 1));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooserLine = new javax.swing.JColorChooser();
        jColorChooser2 = new javax.swing.JColorChooser();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxShape = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jSpinnerSize = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelLineColor = new javax.swing.JPanel();
        jPanelFillColor = new javax.swing.JPanel();
        jButtonFormat = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSpinnerFontSize = new javax.swing.JSpinner();
        jCheckBoxBold = new javax.swing.JCheckBox();
        jCheckBoxItalic = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Format nodes");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel2.setText("Shape");

        jComboBoxShape.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"", "circle", "square"}));
        jComboBoxShape.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxShapeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel1.setText("Size");

        jSpinnerSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSpinnerSizeMouseClicked(evt);
            }
        });
        jSpinnerSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerSizeStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Line color");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Fill color");

        jPanelLineColor.setBackground(new java.awt.Color(0, 0, 0));
        jPanelLineColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelLineColor.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanelLineColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelLineColorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelLineColorLayout = new javax.swing.GroupLayout(jPanelLineColor);
        jPanelLineColor.setLayout(jPanelLineColorLayout);
        jPanelLineColorLayout.setHorizontalGroup(
                jPanelLineColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 18, Short.MAX_VALUE)
        );
        jPanelLineColorLayout.setVerticalGroup(
                jPanelLineColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanelFillColor.setBackground(new java.awt.Color(51, 51, 51));
        jPanelFillColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelFillColor.setPreferredSize(new java.awt.Dimension(20, 20));
        jPanelFillColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelFillColorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelFillColorLayout = new javax.swing.GroupLayout(jPanelFillColor);
        jPanelFillColor.setLayout(jPanelFillColorLayout);
        jPanelFillColorLayout.setHorizontalGroup(
                jPanelFillColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 18, Short.MAX_VALUE)
        );
        jPanelFillColorLayout.setVerticalGroup(
                jPanelFillColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 18, Short.MAX_VALUE)
        );

        jButtonFormat.setText("OK");
        jButtonFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFormatActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel5.setText("Label style");

        jLabel6.setText("Node style");

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setText("Size");

        jSpinnerFontSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jSpinnerFontSizeMouseClicked(evt);
            }
        });
        jSpinnerFontSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerFontSizeStateChanged(evt);
            }
        });

        jCheckBoxBold.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBoxBold.setText("bold");
        jCheckBoxBold.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxBoldStateChanged(evt);
            }
        });
        jCheckBoxBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxBoldActionPerformed(evt);
            }
        });

        jCheckBoxItalic.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jCheckBoxItalic.setText("italic");
        jCheckBoxItalic.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBoxItalicStateChanged(evt);
            }
        });
        jCheckBoxItalic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxItalicActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jSpinnerSize)
                                                        .addComponent(jComboBoxShape, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel4)
                                                        .addComponent(jLabel3))
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanelLineColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanelFillColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jSpinnerFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCheckBoxBold)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBoxItalic, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jSeparator1)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButtonCancel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonFormat)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel5)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel7)
                                                                        .addComponent(jSpinnerFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jCheckBoxBold)
                                                                        .addComponent(jCheckBoxItalic))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jButtonCancel)
                                                                        .addComponent(jButtonFormat))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabel2)
                                                                .addComponent(jComboBoxShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel3))
                                                        .addComponent(jPanelLineColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanelFillColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(jLabel1)
                                                                .addComponent(jSpinnerSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(jLabel4)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setVisible() {
        Set<Point> points = ((Window) drawing).pointsToHighlight;
        Boolean round = null;
        String shape = "";
        Integer size = null;
        Integer currentSize = null;
        Color fg = null;
        Color currentFg = null;
        Color bg = null;
        Color currentBg = null;
        Color fColor = null;
        Color currentFColor = null;
        Integer fSize = null;
        Integer currentFSize = null;
        Boolean bold = null;
        Boolean currentBold = null;
        Boolean italic = null;
        Boolean currentItalic = null;

        for (Point point : points) {
            if (round == null) {
                round = point.round;
                shape = (round) ? "circle" : "square";
                size = point.height;
                currentSize = size;
                fg = point.getFg();
                currentFg = fg;
                bg = point.getBg();
                currentBg = bg;
                fColor = point.l.label.getFontColor();
                currentFColor = fColor;
                fSize = point.l.label.getFontSize();
                currentFSize = fSize;
                bold = (point.l.label.getFontStyle() == Font.BOLD);
                currentBold = bold;
                italic = (point.l.label.getFontStyle() == Font.ITALIC);
                currentItalic = italic;
            }
            if (round != point.round) {
                shape = "";
            }
            if (size != point.height) {
                currentSize = 0;
            }
            if (bg != null && bg.getRGB() != point.getBg().getRGB()) {
                currentBg = this.getBackground();
            }
            if (fg != null && fg.getRGB() != point.getFg().getRGB()) {
                currentFg = this.getBackground();
            }
            if (fColor != point.l.label.getFontColor()) {
                currentFColor = this.getBackground();
            }
            if (fSize != point.l.label.getFontSize()) {
                currentFSize = null;
            }
            if (bold != (point.l.label.getFontStyle() == Font.BOLD)) {
                currentBold = false;
            }
            if (italic != (point.l.label.getFontStyle() == Font.ITALIC)) {
                currentItalic = false;
            }
        }

        jComboBoxShape.setSelectedItem(shape);
        jSpinnerSize.setValue(currentSize);
        jPanelLineColor.setBackground(currentFg);
        jPanelFillColor.setBackground(currentBg);
        //jPanelFontColor.setBackground(currentFColor);
        jSpinnerFontSize.setValue(currentFSize);
        jCheckBoxBold.setSelected(currentBold);
        jCheckBoxItalic.setSelected(currentItalic);


        makeFormatingOptionsFalse();
        setLocationRelativeTo(drawing);
        repaint();
        setVisible(true);
        repaint();
    }

    private void jPanelLineColorMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelLineColorMouseClicked
    {//GEN-HEADEREND:event_jPanelLineColorMouseClicked
        Color lineColor = JColorChooser.showDialog(this, "Line color", jPanelLineColor.getBackground());
        if (lineColor != null) {
            jPanelLineColor.setBackground(lineColor);
            changeLineColor = true;
        }
    }//GEN-LAST:event_jPanelLineColorMouseClicked

    private void jPanelFillColorMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelFillColorMouseClicked
    {//GEN-HEADEREND:event_jPanelFillColorMouseClicked
        Color fillColor = JColorChooser.showDialog(this, "Line color", jPanelFillColor.getBackground());
        if (fillColor != null) {
            jPanelFillColor.setBackground(fillColor);
            changeFillColor = true;
        }
    }//GEN-LAST:event_jPanelFillColorMouseClicked

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
    {//GEN-HEADEREND:event_jButtonCancelActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonFormatActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonFormatActionPerformed
    {//GEN-HEADEREND:event_jButtonFormatActionPerformed
        ((Window) drawing).formatSelectedNodes(
                jPanelLineColor.getBackground(), changeLineColor,
                jPanelFillColor.getBackground(), changeFillColor,
                jComboBoxShape.getSelectedItem().toString(), changeShape,
                Integer.parseInt(jSpinnerSize.getValue().toString()), changeSize,
                Integer.parseInt(jSpinnerFontSize.getValue().toString()), changeFontSize,
                jCheckBoxBold.isSelected(), changeBold,
                jCheckBoxItalic.isSelected(), changeItalic
        );
        setVisible(false);
    }//GEN-LAST:event_jButtonFormatActionPerformed

    private void jComboBoxShapeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jComboBoxShapeActionPerformed
    {//GEN-HEADEREND:event_jComboBoxShapeActionPerformed
        changeShape = true;
    }//GEN-LAST:event_jComboBoxShapeActionPerformed

    private void jSpinnerSizeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSpinnerSizeStateChanged
    {//GEN-HEADEREND:event_jSpinnerSizeStateChanged
        changeSize = true;
    }//GEN-LAST:event_jSpinnerSizeStateChanged

    private void jSpinnerSizeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jSpinnerSizeMouseClicked
    {//GEN-HEADEREND:event_jSpinnerSizeMouseClicked

    }//GEN-LAST:event_jSpinnerSizeMouseClicked

    private void jSpinnerFontSizeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jSpinnerFontSizeMouseClicked
    {//GEN-HEADEREND:event_jSpinnerFontSizeMouseClicked
        changeFontSize = true;
    }//GEN-LAST:event_jSpinnerFontSizeMouseClicked

    private void jCheckBoxBoldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxBoldActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxBoldActionPerformed

    }//GEN-LAST:event_jCheckBoxBoldActionPerformed

    private void jCheckBoxItalicActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxItalicActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxItalicActionPerformed

    }//GEN-LAST:event_jCheckBoxItalicActionPerformed

    private void jSpinnerFontSizeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSpinnerFontSizeStateChanged
    {//GEN-HEADEREND:event_jSpinnerFontSizeStateChanged
        changeFontSize = true;
    }//GEN-LAST:event_jSpinnerFontSizeStateChanged

    private void jCheckBoxBoldStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jCheckBoxBoldStateChanged
    {//GEN-HEADEREND:event_jCheckBoxBoldStateChanged
        changeBold = true;
    }//GEN-LAST:event_jCheckBoxBoldStateChanged

    private void jCheckBoxItalicStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jCheckBoxItalicStateChanged
    {//GEN-HEADEREND:event_jCheckBoxItalicStateChanged
        changeItalic = true;
    }//GEN-LAST:event_jCheckBoxItalicStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonFormat;
    private javax.swing.JCheckBox jCheckBoxBold;
    private javax.swing.JCheckBox jCheckBoxItalic;
    private javax.swing.JColorChooser jColorChooser2;
    private javax.swing.JColorChooser jColorChooserLine;
    private javax.swing.JComboBox jComboBoxShape;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanelFillColor;
    private javax.swing.JPanel jPanelLineColor;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSpinner jSpinnerFontSize;
    private javax.swing.JSpinner jSpinnerSize;
    // End of variables declaration//GEN-END:variables

    private void makeFormatingOptionsFalse() {
        changeFillColor = false;
        changeLineColor = false;
        changeShape = false;
        changeSize = false;
        changeFontColor = false;
        changeFontSize = false;
        changeBold = false;
        changeItalic = false;
    }
}
