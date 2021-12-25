package com.rockwell.Innovent.phase.ui;

import com.jgoodies.common.internal.IActionBean;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;
import org.apache.ecs.vxml.Return;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class gbn0100<selectBatchtype> extends JFrame {

    private JTextField equipmentId;
    private JTextField batchNumber;
    private JComboBox  selectBatchtype;
    private JButton generateBatchnumber;
    ActionEvent e;

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

        contentPanel.add( selectBatchtype, CC.xywh(2, 2, 4, 1, CC.FILL, CC.CENTER));

        contentPanel.add(equipmentId, CC.xywh(2, 3, 4, 1, CC.FILL, CC.CENTER));

        batchNumber.setEnabled(false);

        contentPanel.add(batchNumber, CC.xywh(2, 4, 4, 1, CC.FILL, CC.CENTER));

        //JButton confirmButton = ((PhaseColumnLayout) layoutPanel.getLayout()).getConfirmButton();
        contentPanel.add(generateBatchnumber, CC.xywh(3, 4, 4, 1, CC.RIGHT, CC.CENTER));

        actionListener(generateBatchnumber);

        add(contentPanel.getPanel());

        //layoutPanel.add(contentPanel);

    }
    public  void init(){

        String str1 = "--Please select type of batch number--";
        String str2 = "Wating for cleaning bathch number";
        String str3 = "Cleaning batch number";
        String str4 = "Sterilization batch number";
        String str5 = "Assembly batch number";
        equipmentId = new JTextField();
        batchNumber= new JTextField();

        //List<String> list = new ArrayList<String>();
        List list = new List(5,false);

        list.add(str1);
        list.add(str2);
        list.add(str3);
        list.add(str4);
        list.add(str5);

        selectBatchtype = new JComboBox();

        selectBatchtype.addItem(list.getItem(0));
        selectBatchtype.addItem(list.getItem(1));
        selectBatchtype.addItem(list.getItem(2));
        selectBatchtype.addItem(list.getItem(3));
        selectBatchtype.addItem(list.getItem(4));



        generateBatchnumber = new JButton("Genearte");


    }
    public void actionListener(JButton btn) {

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                generateBatchnumberbytype(selectBatchtype.getSelectedItem().toString());
                //System.out.println("你按下了" + btn.getText());
            }
        });

    }
    public void generateBatchnumberbytype(String batchType) {

        String type = batchType;
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyMM");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMM");
        Date date = new Date();

        switch (type) {
            case "Wating for cleaning bathch number":
                batchNumber.setText("M2-DHT" + dateFormat1.format(date));
            break;
            case "Cleaning batch number":
                if(equipmentId.getText()!=null){
                batchNumber.setText("M2"+"-"+equipmentId.getText()+"-" + dateFormat1.format(date));}
            break;
            case "Sterilization batch number":
                batchNumber.setText("M2"+dateFormat2.format(date)+"-"+equipmentId.getText());
            break;
            case "Assembly batch number":
                batchNumber.setText("M2-"+"AS"+ dateFormat1.format(date));
            break;

        }
    }
}

