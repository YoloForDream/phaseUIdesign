package com.rockwell.Innovent.phase.ui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;

import javax.swing.*;
import java.awt.*;

public class gbn0100 extends JFrame {

    private JTextField equipmentId;
    private JTextField batchNumber;
    private JButton generateBatchnumber;


    public gbn0100() throws HeadlessException{

        init();
        layoutComponents();
    }
    private void layoutComponents(){

        //JPanel layoutPanel;

        JLabel instructionlabel = new JLabel("Generate batch number");

        JLabel selectBatchtypelabel = new JLabel("Select batch number type");

        JLabel inputEquipmentidlbael = new JLabel("Equipment id");

        JLabel batchNumberlabel = new JLabel("Batch number");



        String columnSpec = String.format("%dpx,%dpx,%dpx,%dpx,%dpx, %dpx", UIConstants.DEFAULT_BUTTON_WIDTH,
                UIConstants.DEFAULT_GAP + 20, UIConstants.DEFAULT_BUTTON_WIDTH - 50,
                UIConstants.DEFAULT_BUTTON_WIDTH + 50, UIConstants.DEFAULT_GAP, UIConstants.DEFAULT_BUTTON_WIDTH);

        String rowSpec = String.format("%dpx,%dpx,%dpx,%dpx", UIConstants.DEFAULT_BUTTON_HEIGHT,
                UIConstants.DEFAULT_BUTTON_HEIGHT, UIConstants.DEFAULT_BUTTON_HEIGHT, UIConstants.DEFAULT_BUTTON_HEIGHT);

        FormLayout layout = new FormLayout(columnSpec, rowSpec);

        PanelBuilder contentPanel  = new PanelBuilder(layout,new FormDebugPanel());

        //contentPanel.setOpaque(false);
        //contentPanel.setLayout(layout);


        contentPanel.add(instructionlabel, CC.xywh(1, 1, 4, 1, CC.FILL, CC.CENTER));

        contentPanel.add(selectBatchtypelabel, CC.xywh(1, 2, 4, 1, CC.FILL, CC.CENTER));

        contentPanel.add(inputEquipmentidlbael, CC.xywh(1, 3, 4, 1, CC.FILL, CC.CENTER));

        contentPanel.add(batchNumberlabel, CC.xywh(1, 4, 4, 1, CC.FILL, CC.CENTER));

        JComboBox cmb=new JComboBox();    //创建JComboBox
        cmb.addItem("--Please select type of batch number--");//向下拉列表中添加一项
        cmb.addItem("Wating for cleaning bathch number");
        cmb.addItem("Cleaning batch number");
        cmb.addItem("Sterilization batch number");
        cmb.addItem("Assembly batch number");

        contentPanel.add(cmb, CC.xywh(2, 2, 4, 1, CC.FILL, CC.CENTER));

        contentPanel.add(equipmentId, CC.xywh(2, 3, 4, 1, CC.FILL, CC.CENTER));

        batchNumber.setEnabled(false);

        contentPanel.add(batchNumber, CC.xywh(2, 4, 4, 1, CC.FILL, CC.CENTER));

        //JButton confirmButton = ((PhaseColumnLayout) layoutPanel.getLayout()).getConfirmButton();
        contentPanel.add(generateBatchnumber, CC.xywh(3, 4, 4, 1, CC.RIGHT, CC.CENTER));

        add(contentPanel.getPanel());

        //layoutPanel.add(contentPanel);

    }
    public  void init(){
        equipmentId = new JTextField();
        batchNumber= new JTextField();
        generateBatchnumber = new JButton("Genearte");
    }
}

