package sdt.action;

import java.util.*;

import javax.swing.*;

import sdt.action.computation.*;
import sdt.action.file.*;
import sdt.action.help.*;
import sdt.action.popup.*;
import sdt.action.tool.*;
import sdt.action.view.*;
import sdt.framework.*;

public class Actions
{
    private SDT_System _system;

    private Hashtable commandsTable;

    public Hashtable GetCommands() { return this.commandsTable; }

    public Actions(SDT_System inputSystem)
    {
        this._system = inputSystem;
        this.commandsTable = new Hashtable();

        Action[] actionSets =
        {
            //File
            new ActionFileExit(this._system), new ActionFileModulePreliminaryDesign(this._system),
            new ActionFileOpen(this._system),
            new ActionFileSave(this._system), new ActionFileSaveAs(this._system),
            new ActionFileExportInp(this._system),
            new ActionFileExportMac(this._system),
            new ActionFileRecent(this._system),
            //Popup
            new ActionPopupSectionRound(this._system), new ActionPopupSectionRunway(this._system),
            new ActionPopupConeDiaphragmNone(this._system),
            new ActionPopupConeDiaphragmType1(this._system),
            new ActionPopupConeDiaphragmType2(this._system),
            new ActionPopupConeSurroundSingleArc(this._system),
            new ActionPopupConeSurroundDoubleArc(this._system),
            new ActionPopupConeCapCapsule(this._system),
            new ActionPopupConeCapRegular(this._system),
            new ActionPopupConeCapRoundCut(this._system),
            new ActionPopupAsmView2D(this._system), new ActionPopupAsmView3D(this._system),
            new ActionPopupAirView2D(this._system), new ActionPopupAirView3D(this._system),
            new ActionPopupSkecthTopMeshDensity(this._system),
            new ActionPopupVCMMagnetTop(this._system),
            new ActionPopupVCMMagnetOuter(this._system),
            new ActionPopupCorrugation(this._system),
            new ActionPopupDimensionCap(this._system),
            new ActionPopupDimensionTransition(this._system),
            new ActionPopupDimensionDiaphragm(this._system),
            new ActionPopupDimensionSurround(this._system),
            new ActionPopupDimensionCoil(this._system),
            new ActionPopupDimensionMagnet(this._system),
            new ActionPopupDimensionMagnetTop(this._system),
            new ActionPopupDimensionMagnetOuter(this._system),
            new ActionPopupDimensionTopPlate(this._system),
            new ActionPopupDimensionYokeBase(this._system),
            new ActionPopupDimensionYokeStage1(this._system),
            new ActionPopupDimensionYokeStage2(this._system),
            new ActionPopupYoke(this._system),
            new ActionPopupVCMCoilTyp1(this._system),
            new ActionPopupVCMCoilTyp2(this._system),
            new ActionPopupThickness(this._system),
            new ActionPopupMaterial(this._system),
            //Tool
            new ActionToolLanguageEnglish(this._system),
            new ActionToolLanguageChinese(this._system),
            new ActionToolOption(this._system),
            //View
            new ActionViewPan(this._system),
            new ActionViewRotate(this._system),
            new ActionViewFitItAll(this._system),
            new ActionViewZoomIn(this._system),
            new ActionViewZoomOut(this._system),
            new ActionViewShading(this._system),
            new ActionViewShadingWithEdges(this._system),
            new ActionViewWireframe(this._system),
            //Computation
            new ActionComputationConeSolving(this._system),
            new ActionComputationConeAirSolving(this._system),
            new ActionComputationVCMSolving(this._system),
            new ActionComputationStepFrame(this._system),
            new ActionComputationEMMA(this._system),
            new ActionComputationKx(this._system),
            new ActionComputationBLx(this._system),
            new ActionViewXYView(this._system),
            new ActionViewXZView(this._system),
            //Help
            new ActionAbout(this._system)


            //Edit
            //View
           //Tools
            //Help
            //Debug
            //Part design
            //Assembly design

        };

        for (int i = 0; i < actionSets.length; i++)
        {
            Action action = actionSets[i];
            this.commandsTable.put(action.getValue("Name"), action);
        }
    }
}
