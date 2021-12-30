package com.rockwell.Innovent.phase.ui;

import com.datasweep.compatibility.client.*;
import com.datasweep.plantops.common.dataobjects.DUserSequenceValue;
import com.jgoodies.common.internal.IActionBean;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.PhaseErrorDialog;
import com.rockwell.mes.apps.ebr.ifc.phase.ui.UIConstants;
import com.rockwell.mes.apps.ebr.ifc.swing.PhaseColumnLayout;
import com.rockwell.mes.commons.base.ifc.services.PCContext;
import org.apache.ecs.vxml.Return;
import com.datasweep.compatibility.pnutsfunctions.utility.println;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;



public class gbn0100<selectBatchtype> extends JFrame {

    private JTextField equipmentId;
    private JTextField batchNumber;
    private JComboBox  selectBatchtype;
    private JButton generateBatchnumber;
    private Date date;//用于获取当前日期
    private String sequencenameUsedforwc;//待清洗批号的流水号
    private String sequencenameUsedfocb;//清洗批号的流水号
    private String sequencenameUsedfosb;//灭菌批号的流水号
    private String sequencenameUsedfoab;//组装批号的流水号
    String sequenceResult;
    ActionEvent e;

    public gbn0100() throws HeadlessException{

        init();
        layoutComponents();
        //intiAllsequencenumber();
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
        date = new Date();
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


    public UserSequence createSquencename(String sequenceName) {
        UserSequence sequence = PCContext.getFunctions().createUserSequence(sequenceName);
        sequence.setInitialValue(0);
        sequence.save();
        return sequence;

    }
    public UserSequence resetSquencenumber(String sequenceName) {
        UserSequence sequence = PCContext.getFunctions().createUserSequence(sequenceName);
        sequence.resetSequence("Reset Per Month");
        return sequence;
    }


    public UserSequence checkSquencename(String sequenceName){
       // sequencenameUsedforwc="watingForclen";
        //Object result ;
        UserSequence  sequenceObj =  PCContext.getFunctions().getUserSequenceByName(sequenceName);

        return sequenceObj ;
    }
    public void intiAllsequencenumber(){

        sequencenameUsedforwc = getSquencenumber("watingForCleaning",isFirstdayofmonth(date));
        sequencenameUsedfocb =  getSquencenumber("cleaningBatchnumber",isFirstdayofmonth(date));
        sequencenameUsedfosb =  getSquencenumber("steriLizationbatchnumber",isFirstdayofmonth(date));
        sequencenameUsedfoab =  getSquencenumber("assemblyBatchnumber",isFirstdayofmonth(date));
    }

    public String getSquencenumber(String sequenceName, boolean isFirstdayofMaonth) {

        UserSequence sequence = checkSquencename(sequenceName);//验证该类型的流水对象是否存在
        String sequenceResult = null;
        if(Objects.nonNull(sequence)&&!isFirstdayofMaonth){

            UserSequenceValue object = (UserSequenceValue)sequence.getNextValue().getResult();
            sequenceResult = String.valueOf(object.getValue());
        }
        else if(Objects.isNull(sequence))//如果系统中不存在改sequence，则先创建
        {
            sequence = createSquencename(sequenceName);
            @SuppressWarnings("null")
            UserSequenceValue object = (UserSequenceValue) sequence.getNextValue().getResult();
            sequenceResult = String.valueOf(object.getValue());
        }
        else if(isFirstdayofMaonth)//如果系统中存在该Sequence,但是系统当前时间为当月第一天，则需要重置计数
        {
            sequence= resetSquencenumber(sequenceName);
            UserSequenceValue object = (UserSequenceValue) sequence.getNextValue().getResult();
            sequenceResult = String.valueOf(object.getValue());
        }
        if (Integer.parseInt(sequenceResult) <= 9)
        {
            sequenceResult="0"+sequenceResult;
        }

        return sequenceResult;
    }


    public boolean isFirstdayofmonth(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
       // int t = calendar.DAY_OF_MONTH
        return  calendar.get(calendar.DAY_OF_MONTH)==1;
    }

    public void generateBatchnumberbytype(String batchType) {

        batchNumber.setText("");
        String type = batchType;
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyMM");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMM");


        switch (type) {
            case "Wating for cleaning bathch number":
                batchNumber.setText("M2-DHT" + dateFormat1.format(date));
            break;
            case "Cleaning batch number":
                if(!equipmentId.getText().isEmpty()){

                    batchNumber.setText("M2"+"-"+equipmentId.getText()+"-" + dateFormat1.format(date));
                }
                else {
                    PhaseErrorDialog errorDialog = new PhaseErrorDialog();
                    errorDialog.showDialog("The Euqipment ID is null,please check it and try aganin!");
                    //JOptionPane.showMessageDialog(null,"The Euqipment ID is null,please check it and try aganin!","",JOptionPane.INFORMATION_MESSAGE);

                }

            break;
            case "Sterilization batch number":
                if(!equipmentId.getText().isEmpty()){
                    batchNumber.setText("M2"+dateFormat2.format(date)+"-"+equipmentId.getText());}
                else {
                    JOptionPane.showMessageDialog(null,"The Euqipment ID is null,please check it and try aganin!","",JOptionPane.INFORMATION_MESSAGE);

                }
            break;
            case "Assembly batch number":
                if(!equipmentId.getText().isEmpty()){
                    batchNumber.setText("M2-"+"AS"+ dateFormat1.format(date));}
                else {
                    JOptionPane.showMessageDialog(null,"The Euqipment ID is null,please check it and try aganin!","",JOptionPane.INFORMATION_MESSAGE);

                }
            break;

        }

    }
}

