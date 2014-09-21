package sdt.action.popup;

import java.awt.event.*;

import javax.swing.*;

import sdt.action.*;
import sdt.framework.*;
import sdt.tree.*;
import sdt.define.DefineSystemConstant;
import sdt.data.*;


public class ActionPopupYoke extends ActionMain
{
    public ActionPopupYoke(SDT_System system)
    {
        super(system, "ChkBoxYoke");
    }

    public void actionPerformed(ActionEvent e)
    {
        this._system.getFrame().SetStatus("ChkBoxYoke");

        JCheckBoxMenuItem item1 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage1");
        JCheckBoxMenuItem item2 = this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage2");


        SDT_Modeler modeler = this._system.getModeler();
        SDT_TreeAssembly tree = modeler.GetAssemTree();


        DataYokeStage1 dataYokeStage1 = this._system.getDataManager().getDataYokeStage1();
        DataYokeStage2 dataYokeStage2 = this._system.getDataManager().getDataYokeStage2();

        // SDT_DrawPanel2D  panel2D = modeler.getPanel2D().GetDrawPanel();
        int stage1Status = DefineSystemConstant.YOKESTAGE1_TYPE1;
        int stage2Status = DefineSystemConstant.YOKESTAGE2_TYPE1;

        boolean isStage1On = item1.isSelected();
        boolean isStage2On = item2.isSelected();

        if (!isStage1On)
        {
            isStage2On = isStage1On;
            item2.setSelected(isStage2On);
        }

       tree.setYokeStage1(isStage1On);
       tree.setYokeStage2(isStage2On);

       if (!isStage1On)
       {
           stage1Status = DefineSystemConstant.YOKESTAGE1_NONE;

           //如果沒有yokeStage1的話就沒有magentOuter
           DataMagnetOuter dataMagnetOuter = this._system.getDataManager().getDataMagnetOuter();
           dataMagnetOuter.setGeometryType(MAGNETOUTER_NONE);
           tree.setMagnetOut(false);
       }
       dataYokeStage1.setGeometryType(stage1Status);


       if (!isStage2On)
       {
           stage2Status = DefineSystemConstant.YOKESTAGE2_NONE;
       }
       dataYokeStage2.setGeometryType(stage2Status);
       this._system.getModeler().getPanel2D().GetDrawPanel().showElement();









        //tree.setShapeSurroundDiaphgram(tree.isSurroundRunway());
       // panel2D.setDiaphragm(item2.isSelected());
    }
}
