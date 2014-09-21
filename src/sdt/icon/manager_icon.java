package sdt.icon;

import javax.swing.*;
import sdt.define.DefineIcon;


public class manager_icon implements DefineIcon
{
//--------spring frame----------
    private ImageIcon i_modelPart;
    private ImageIcon i_modelSurface;
    private ImageIcon i_modelAssembly;
    private ImageIcon i_modelSketch;
    private ImageIcon i_mainBack;
//---------------------------------------
    private ImageIcon i_mainArrow;
    private ImageIcon x;
    private ImageIcon i_mainUpdate;
//--------------------------toolbar Standard----------------------------------------------------------------
    private ImageIcon i_mainNew;
    private ImageIcon i_mainOpen;
    private ImageIcon i_mainSave;
    private ImageIcon i_mainPrint;
    private ImageIcon i_mainCopy;
    private ImageIcon i_mainPaste;
    private ImageIcon i_mainCut;
    private ImageIcon i_mainContents;
//-------------------toolbar view--------------------------------------------------------------------------
    private ImageIcon i_mainXyView;
    private ImageIcon i_mainYzView;
    private ImageIcon i_mainXzView;
    private ImageIcon i_mainIsometricView;
    private ImageIcon i_mainShading;
    private ImageIcon i_mainShadingWithEdges;
    private ImageIcon i_mainWireframe;
    private ImageIcon i_mainZoomIn;
    private ImageIcon i_mainZoomOut;
    private ImageIcon i_mainFitItAll;
//--------------part tree------------------------
    private ImageIcon i_t_partSketch;
    private ImageIcon i_t_partPlane;
    private ImageIcon i_t_partPad;
    private ImageIcon i_t_partPocket;
    private ImageIcon i_t_partRevolution;
    private ImageIcon i_t_partGroove;
    private ImageIcon i_t_partSweep;
    private ImageIcon i_t_partSlot;
    private ImageIcon i_t_partLoft;
    private ImageIcon i_t_partRemovedLoft;
    private ImageIcon i_t_partRoundHole;
    private ImageIcon i_t_partCounterSunkHole;
    private ImageIcon i_t_partConuterBoreHole;
    private ImageIcon i_t_partIntersect;
    private ImageIcon i_t_partSubtract;
    private ImageIcon i_t_partUnion;
    private ImageIcon i_t_partEdgeRound;
    private ImageIcon i_t_partEdgeChamfer;
    private ImageIcon i_t_partShelling;
    private ImageIcon i_t_partSplit;
    private ImageIcon i_t_partBodyResult;
    private ImageIcon i_t_partBodyOpen;
    private ImageIcon i_t_partBodyFace;
    private ImageIcon i_t_partBodyEdge;
    private ImageIcon i_t_partBodySurface;
    private ImageIcon i_t_partResultBody;
    //-----assembly design tree-----------------------------------------------------------------------
    private ImageIcon i_t_asmPartDesign;
    private ImageIcon i_t_asmComponent;
    private ImageIcon i_t_asmCoplanar;
    private ImageIcon i_t_asmConnect;
    private ImageIcon i_t_asmOffset;
    private ImageIcon i_t_asmFixTogether;
    private ImageIcon i_t_asmFits;
    private ImageIcon i_t_asmFixed;
    private ImageIcon i_t_asmConstraint;
    private ImageIcon i_t_asmExplode;
    private ImageIcon i_t_asmLineConnect;
    private ImageIcon i_t_asmAngle;

    private ImageIcon i_t_dmuCylindrical;
    private ImageIcon i_t_dmuPrismatic;
    private ImageIcon i_t_dmuRevolute;
    private ImageIcon i_t_dmuRigid;
    private ImageIcon i_t_dmuSpherical;
    private ImageIcon i_t_dmuPlanar;
    private ImageIcon i_t_dmuScrew;
    private ImageIcon i_t_dmuFixedPart;
    private ImageIcon i_t_dmuSimulation;
    private ImageIcon i_t_dmuMechanism;
    //--------------------surface design  tree-------------------------------------------------------------
    private ImageIcon i_t_surfEdge;
    private ImageIcon i_t_surfFace;
    private ImageIcon i_t_surfVertex;
    private ImageIcon i_t_surfOpenBody;
    private ImageIcon i_t_surfPlane;
    private ImageIcon i_t_surfjoin;
    //---------------------sketch tree---------------------------------------------------------------------

    private ImageIcon i_t_skBspline;
    private ImageIcon i_t_skCircle;
    private ImageIcon i_t_skCoincidence;
    private ImageIcon i_t_skConcentricity;
    private ImageIcon i_t_skConstraint;
    private ImageIcon i_t_skDiameter;
    private ImageIcon i_t_skDistance;
    private ImageIcon i_t_skFix;
    private ImageIcon i_t_skGeometry;
    private ImageIcon i_t_skHorizontal;
    private ImageIcon i_t_skPerpendicular;
    private ImageIcon i_t_skParallelism;
    private ImageIcon i_t_skSymmetry;
    private ImageIcon i_t_skLength;
    private ImageIcon i_t_skLine;
    private ImageIcon i_t_skPoint;
    private ImageIcon i_t_skRadiusFillet;
    private ImageIcon i_t_skTangency;
    private ImageIcon i_t_skUnknown;
    private ImageIcon i_t_skVertical;
    private ImageIcon i_t_skAngular;
    //--------------------surface design -------------------------------------------------------------
    private ImageIcon i_pdqPrepareGo;
    private ImageIcon i_pdqCheckItem;
    private ImageIcon i_pdqTolerance;
    private ImageIcon i_pdqGo;
    private ImageIcon i_pdqMacro;
    private ImageIcon i_pdqOptional;
    private ImageIcon i_pdqExport;
    private ImageIcon i_pdqReport;
    private ImageIcon i_pdqRecord;
    private ImageIcon i_pdqEnd;
    private ImageIcon i_pdqRelatedFace;
    private ImageIcon i_pdqTableForward;
    private ImageIcon i_pdqTableBackward;
    private ImageIcon i_pdqTableShow;
    private ImageIcon i_pdqTableZoomShow;
    private ImageIcon i_pdqTableShowAll;
    private ImageIcon i_pdqTableRepair;
    private ImageIcon i_pdqTableDescription;
    private ImageIcon i_pdqTableClear;


    public manager_icon()
    {

    }
    private ImageIcon CreateIcon(ImageIcon icon ,String url)
    {
        if(icon == null)
        {
            return new ImageIcon(this.getClass().getResource(url));
        }
        return icon;
    }

    public void CreateIconPDQ()
    {
        i_pdqPrepareGo = CreateIcon(i_pdqPrepareGo, "/SpringSolidGUI/Icon/PDQ/PrepareGo.png");
        i_pdqCheckItem = CreateIcon(i_pdqCheckItem, "/SpringSolidGUI/Icon/PDQ/CheckItem.png");
        i_pdqTolerance = CreateIcon(i_pdqTolerance, "/SpringSolidGUI/Icon/PDQ/Tolerance.png");
        i_pdqGo = CreateIcon(i_pdqGo, "/SpringSolidGUI/Icon/PDQ/Go.png");
        i_pdqMacro = CreateIcon(i_pdqMacro, "/SpringSolidGUI/Icon/PDQ/Macro.gif");
        i_pdqOptional = CreateIcon(i_pdqOptional, "/SpringSolidGUI/Icon/PDQ/Optional.gif");
        i_pdqExport = CreateIcon(i_pdqExport, "/SpringSolidGUI/Icon/PDQ/Export.png");
        i_pdqReport = CreateIcon(i_pdqReport, "/SpringSolidGUI/Icon/PDQ/Report.png");
        i_pdqRecord = CreateIcon(i_pdqRecord, "/SpringSolidGUI/Icon/PDQ/Record.png");
        i_pdqEnd = CreateIcon(i_pdqEnd, "/SpringSolidGUI/Icon/PDQ/End.gif");
        i_pdqRelatedFace = CreateIcon(i_pdqRelatedFace, "/SpringSolidGUI/Icon/PDQ/RelatedFace.gif");

        i_pdqTableForward = CreateIcon(i_pdqTableForward, "/SpringSolidGUI/Icon/PDQ/Table/Forward.png");
        i_pdqTableBackward = CreateIcon(i_pdqTableBackward, "/SpringSolidGUI/Icon/PDQ/Table/Backward.png");
        i_pdqTableShow = CreateIcon(i_pdqTableShow, "/SpringSolidGUI/Icon/PDQ/Table/Show.gif");
        i_pdqTableZoomShow = CreateIcon(i_pdqTableZoomShow, "/SpringSolidGUI/Icon/PDQ/Table/ZoomShow.png");
        i_pdqTableShowAll = CreateIcon(i_pdqTableShowAll, "/SpringSolidGUI/Icon/PDQ/Table/ShowAll.gif");
        i_pdqTableRepair = CreateIcon(i_pdqTableRepair, "/SpringSolidGUI/Icon/PDQ/Table/Repair.png");
        i_pdqTableDescription = CreateIcon(i_pdqTableDescription, "/SpringSolidGUI/Icon/PDQ/Table/Description.png");
        i_pdqTableClear = CreateIcon(i_pdqTableClear, "/SpringSolidGUI/Icon/PDQ/Table/Clear.gif");
    }

    public void CreateIconModel()
    {
        i_modelPart = CreateIcon(i_modelPart, "/SpringSolidGUI/Icon/File/PartModel.png");
        i_modelSurface = CreateIcon(i_modelSurface, "/SpringSolidGUI/Icon/File/SurfaceModel.png");
        i_modelAssembly = CreateIcon(i_modelAssembly, "/SpringSolidGUI/Icon/File/AssemblyModel.png");
        i_modelSketch = CreateIcon(i_modelSketch, "/SpringSolidGUI/Icon/File/SketchModel.png");
        i_mainBack = CreateIcon(i_mainBack, "/SpringSolidGUI/Icon/SolidBack.gif");
    }

    public void CreateIconMain()
    {
        i_mainArrow = CreateIcon(i_mainArrow, "/SpringSolidGUI/Icon/Arrow.gif");
        x = CreateIcon(x, "/SpringSolidGUI/Icon/x.png");
        i_mainUpdate = CreateIcon(i_mainUpdate, "/SpringSolidGUI/Icon/Update.gif");
//--------------------------toolbar Standard----------------------------------------------------------------
        i_mainNew = CreateIcon(i_mainNew, "/SpringSolidGUI/Icon/File/NewFile.gif");
        i_mainOpen = CreateIcon(i_mainOpen, "/SpringSolidGUI/Icon/File/Open.png");
        i_mainSave = CreateIcon(i_mainSave, "/SpringSolidGUI/Icon/File/SaveFile.png");
        i_mainPrint = CreateIcon(i_mainPrint, "/SpringSolidGUI/Icon/File/Print.png");
        i_mainCopy = CreateIcon(i_mainCopy, "/SpringSolidGUI/Icon/Edit/Copy.png");
        i_mainPaste = CreateIcon(i_mainPaste, "/SpringSolidGUI/Icon/Edit/Paste.png");
        i_mainCut = CreateIcon(i_mainCut, "/SpringSolidGUI/Icon/Edit/Cut.png");
        i_mainContents = CreateIcon(i_mainContents, "/SpringSolidGUI/Icon/Help/Help.png");
//-------------------toolbar view--------------------------------------------------------------------------
        i_mainXyView = CreateIcon(i_mainXyView, "/SpringSolidGUI/Icon/View/XYView.png");
        i_mainYzView = CreateIcon(i_mainYzView, "/SpringSolidGUI/Icon/View/YZView.png");
        i_mainXzView = CreateIcon(i_mainXzView, "/SpringSolidGUI/Icon/View/XZView.png");
        i_mainIsometricView = CreateIcon(i_mainIsometricView, "/SpringSolidGUI/Icon/View/IsometricView.png");
        i_mainShading = CreateIcon(i_mainShading, "/SpringSolidGUI/Icon/View/ShandingMode/Shading.png");
        i_mainShadingWithEdges = CreateIcon(i_mainShadingWithEdges, "/SpringSolidGUI/Icon/View/ShandingMode/ShadingWithEdges.png");
        i_mainWireframe = CreateIcon(i_mainWireframe, "/SpringSolidGUI/Icon/View/ShandingMode/Wireframe.png");
        i_mainZoomIn = CreateIcon(i_mainZoomIn, "/SpringSolidGUI/Icon/View/ZoomIn.png");
        i_mainZoomOut = CreateIcon(i_mainZoomOut, "/SpringSolidGUI/Icon/View/ZoomOut.png");
        i_mainFitItAll = CreateIcon(i_mainFitItAll, "/SpringSolidGUI/Icon/View/FitItAll.png");
    }

    public void CreateIconTreePart()
    {
        i_t_partSketch = CreateIcon(i_t_partSketch, "/SpringSolidGUI/Icon/TreeIcon/Part/Sketch.png");
        i_t_partPlane = CreateIcon(i_t_partPlane, "/SpringSolidGUI/Icon/TreeIcon/Part/Plane.png");
        i_t_partPad = CreateIcon(i_t_partPad, "/SpringSolidGUI/Icon/TreeIcon/Part/Pad.png");
        i_t_partPocket = CreateIcon(i_t_partPocket, "/SpringSolidGUI/Icon/TreeIcon/Part/Pocket.png");
        i_t_partRevolution = CreateIcon(i_t_partRevolution, "/SpringSolidGUI/Icon/TreeIcon/Part/Revolution.png");
        i_t_partGroove = CreateIcon(i_t_partGroove, "/SpringSolidGUI/Icon/TreeIcon/Part/Groove.png");
        i_t_partSweep = CreateIcon(i_t_partSweep, "/SpringSolidGUI/Icon/TreeIcon/Part/Sweep.png");
        i_t_partSlot = CreateIcon(i_t_partSlot, "/SpringSolidGUI/Icon/TreeIcon/Part/Slot.png");
        i_t_partLoft = CreateIcon(i_t_partLoft, "/SpringSolidGUI/Icon/TreeIcon/Part/Loft.png");
        i_t_partRemovedLoft = CreateIcon(i_t_partRemovedLoft, "/SpringSolidGUI/Icon/TreeIcon/Part/RemovedLoft.png");
        i_t_partRoundHole = CreateIcon(i_t_partRoundHole, "/SpringSolidGUI/Icon/TreeIcon/Part/RoundHole.png");
        i_t_partCounterSunkHole = CreateIcon(i_t_partCounterSunkHole, "/SpringSolidGUI/Icon/TreeIcon/Part/CounterSunkHole.png");
        i_t_partConuterBoreHole = CreateIcon(i_t_partConuterBoreHole, "/SpringSolidGUI/Icon/TreeIcon/Part/CounterBoreHole.png");
        i_t_partIntersect = CreateIcon(i_t_partIntersect, "/SpringSolidGUI/Icon/TreeIcon/Part/Intersect.png");
        i_t_partSubtract = CreateIcon(i_t_partSubtract, "/SpringSolidGUI/Icon/TreeIcon/Part/Subtract.png");
        i_t_partUnion = CreateIcon(i_t_partUnion, "/SpringSolidGUI/Icon/TreeIcon/Part/Union.png");
        i_t_partEdgeRound = CreateIcon(i_t_partEdgeRound, "/SpringSolidGUI/Icon/TreeIcon/Part/EdgeRound.png");
        i_t_partEdgeChamfer = CreateIcon(i_t_partEdgeChamfer, "/SpringSolidGUI/Icon/TreeIcon/Part/EdgeChamfer.png");
        i_t_partShelling = CreateIcon(i_t_partShelling, "/SpringSolidGUI/Icon/TreeIcon/Part/Shelling.png");
        i_t_partSplit = CreateIcon(i_t_partSplit, "/SpringSolidGUI/Icon/TreeIcon/Part/Split.png");
        i_t_partBodyResult = CreateIcon(i_t_partBodyResult, "/SpringSolidGUI/Icon/TreeIcon/Part/PartBody.png");
        i_t_partBodyOpen = CreateIcon(i_t_partBodyOpen, "/SpringSolidGUI/Icon/TreeIcon/Part/PartBody.png");
        i_t_partBodyFace = CreateIcon(i_t_partBodyFace, "/SpringSolidGUI/Icon/TreeIcon/Part/PartBody.png");
        i_t_partBodyEdge = CreateIcon(i_t_partBodyEdge, "/SpringSolidGUI/Icon/TreeIcon/Part/PartBody.png");
        i_t_partBodySurface = CreateIcon(i_t_partBodySurface, "/SpringSolidGUI/Icon/TreeIcon/Part/PartBody.png");
        i_t_partResultBody = CreateIcon(i_t_partResultBody, "/SpringSolidGUI/Icon/TreeIcon/Part/ResultBody.png");
    }

    public void CreateIconTreeAsm()
    {
        i_t_asmPartDesign = CreateIcon(i_t_asmPartDesign, "/SpringSolidGUI/Icon/TreeIcon/Assembly/AsmPart.png");
        i_t_asmComponent = CreateIcon(i_t_asmComponent, "/SpringSolidGUI/Icon/TreeIcon/Assembly/AsmComponent.png");
        i_t_asmConstraint = CreateIcon(i_t_asmConstraint, "/SpringSolidGUI/Icon/TreeIcon/Assembly/AsmConstraint.png");
        i_t_asmCoplanar = CreateIcon(i_t_asmCoplanar, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Coplane.png");
        i_t_asmConnect = CreateIcon(i_t_asmConnect, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Connect.png");
        i_t_asmOffset = CreateIcon(i_t_asmOffset, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Offset.png");
        i_t_asmFixTogether = CreateIcon(i_t_asmFixTogether, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/FixTogether.png");
        i_t_asmFits = CreateIcon(i_t_asmFits, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Fits.png");
        i_t_asmFixed = CreateIcon(i_t_asmFixed, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Fixed.png");
        i_t_asmExplode = CreateIcon(i_t_asmExplode, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Explode.png");
        i_t_asmLineConnect = CreateIcon(i_t_asmLineConnect, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/LineConnect.png");
        i_t_asmAngle = CreateIcon(i_t_asmAngle, "/SpringSolidGUI/Icon/TreeIcon/Assembly/Constraint/Angular.png");

        i_t_dmuCylindrical = CreateIcon(i_t_dmuCylindrical, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/CylindricalJoint.png");
        i_t_dmuPrismatic = CreateIcon(i_t_dmuPrismatic, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/PrismaticJoint.png");
        i_t_dmuRevolute = CreateIcon(i_t_dmuRevolute, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/RevoluteJoint.png");
        i_t_dmuRigid = CreateIcon(i_t_dmuRigid, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/RigidJoint.png");
        i_t_dmuSpherical = CreateIcon(i_t_dmuSpherical, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/SphericalJoint.png");
        i_t_dmuPlanar = CreateIcon(i_t_dmuPlanar, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/PlanarJoint.png");
        i_t_dmuScrew = CreateIcon(i_t_dmuScrew, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/ScrewJoint.png");
        i_t_dmuFixedPart = CreateIcon(i_t_dmuFixedPart, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/FixedPart.png");
        i_t_dmuSimulation = CreateIcon(i_t_dmuSimulation, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/Simulation.png");
        i_t_dmuMechanism = CreateIcon(i_t_dmuMechanism, "/SpringSolidGUI/Icon/TreeIcon/Assembly/DMU/Mechanism.png");
    }

    public void CreateIconTreeSurf()
    {
        i_t_surfEdge = CreateIcon(i_t_surfEdge, "/SpringSolidGUI/Icon/TreeIcon/Surface/Edge.png");
        i_t_surfFace = CreateIcon(i_t_surfFace, "/SpringSolidGUI/Icon/TreeIcon/Surface/Face.png");
        i_t_surfVertex = CreateIcon(i_t_surfVertex, "/SpringSolidGUI/Icon/TreeIcon/Surface/Vertex.png");
        i_t_surfOpenBody = CreateIcon(i_t_surfOpenBody, "/SpringSolidGUI/Icon/TreeIcon/Surface/OpenBody.png");
        i_t_surfPlane = CreateIcon(i_t_surfPlane, "/SpringSolidGUI/Icon/TreeIcon/Surface/Plane.png");
        i_t_surfjoin = CreateIcon(i_t_surfjoin, "/SpringSolidGUI/Icon/SurfaceDesign/Join.png");
    }

    public void CreateIconTreeSk()
    {
        i_t_skBspline = CreateIcon(i_t_skBspline, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Bspline.png");
        i_t_skCircle = CreateIcon(i_t_skCircle, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Circle.png");
        i_t_skCoincidence = CreateIcon(i_t_skCoincidence, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Coincidence.png");
        i_t_skConcentricity = CreateIcon(i_t_skConcentricity, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Concentricity.png");
        i_t_skConstraint = CreateIcon(i_t_skConstraint, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Constraint.png");
        i_t_skDiameter = CreateIcon(i_t_skDiameter, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Diameter.png");
        i_t_skDistance = CreateIcon(i_t_skDistance, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Distance.png");
        i_t_skFix = CreateIcon(i_t_skFix, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Fix.png");
        i_t_skGeometry = CreateIcon(i_t_skGeometry, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Geometry.png");
        i_t_skHorizontal = CreateIcon(i_t_skHorizontal, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Horizontal.png");
        i_t_skLength = CreateIcon(i_t_skLength, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Length.png");
        i_t_skLine = CreateIcon(i_t_skLine, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Line.png");
        i_t_skPoint = CreateIcon(i_t_skPoint, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Point.png");
        i_t_skRadiusFillet = CreateIcon(i_t_skRadiusFillet, "/SpringSolidGUI/Icon/TreeIcon/Sketch/EdgeRound.png");
        i_t_skTangency = CreateIcon(i_t_skTangency, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Tangency.png");
        i_t_skUnknown = CreateIcon(i_t_skUnknown, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Unknown.png");
        i_t_skVertical = CreateIcon(i_t_skVertical, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Vertical.png");
        i_t_skPerpendicular = CreateIcon(i_t_skPerpendicular, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Perpendicular.png");
        i_t_skParallelism = CreateIcon(i_t_skParallelism, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Parallelism.png");
        i_t_skSymmetry = CreateIcon(i_t_skSymmetry, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Symmetry.png");
        i_t_skAngular = CreateIcon(i_t_skAngular, "/SpringSolidGUI/Icon/TreeIcon/Sketch/Angular.png");
    }




    public ImageIcon GetIcon(int name)
    {
        ImageIcon  icon = null;
        ImageIcon  tempIcon = null;

        tempIcon = this.GetIconModel(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconMain(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconTreePart(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconTreeAsm(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconTreeSurf(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconTreeSk(name);
        if(tempIcon != null)                     { icon = tempIcon;   }
        tempIcon = this.GetIconPDQ(name);
        if(tempIcon != null)                     { icon = tempIcon;   }

        if(icon == null)                         { icon = x;        }

        return icon;
    }

    private ImageIcon GetIconPDQ(int name)
    {
        ImageIcon icon = null;
        switch (name)
        {
            case ICON_PDQ_PREPAREGO:
                return  i_pdqPrepareGo;
            case ICON_PDQ_CHECKITEM:
                return  i_pdqCheckItem;
            case ICON_PDQ_EXPORT:
                return  i_pdqExport;
            case ICON_PDQ_GO:
                return  i_pdqGo;
            case ICON_PDQ_MACRO:
                return  i_pdqMacro;
            case ICON_PDQ_OPTIONAL:
                return  i_pdqOptional;
            case ICON_PDQ_REPORT:
                return  i_pdqReport;
            case ICON_PDQ_END:
                return  i_pdqEnd;
            case ICON_PDQ_RELATEDFACE:
                return  i_pdqRelatedFace;
            case ICON_PDQ_TOLERANCE:
                return  i_pdqTolerance;
            case ICON_PDQ_RECORD:
                return  i_pdqRecord;
            case ICON_PDQ_TABLE_FORWARD:
                return  i_pdqTableForward;
            case ICON_PDQ_TABLE_BACKWARD:
                return  i_pdqTableBackward;
            case ICON_PDQ_TABLE_SHOW:
                return  i_pdqTableShow;
            case ICON_PDQ_TABLE_ZOOMSHOW:
                return  i_pdqTableZoomShow;
            case ICON_PDQ_TABLE_SHOWALL:
                return  i_pdqTableShowAll;
            case ICON_PDQ_TABLE_REPAIR:
                return  i_pdqTableRepair;
            case ICON_PDQ_TABLE_DESCRIPTION:
                return  i_pdqTableDescription;
            case ICON_PDQ_TABLE_CLEAR:
                return  i_pdqTableClear;
        }
        return icon;
    }

    private ImageIcon GetIconModel(int name)
    {
        ImageIcon icon = null;
        switch (name)
        {
            case ICON_MODEL_PART:
                return  i_modelPart;
            case ICON_MODEL_SURFACE:
                return  i_modelSurface;
            case ICON_MODEL_ASSEMBLY:
                return  i_modelAssembly;
            case ICON_MODEL_SKETCH:
                return  i_modelSketch;

        }
        return icon;
    }


    private ImageIcon GetIconMain(int name)
    {
        ImageIcon icon = null;
        switch (name)
        {
            case ICON_MAIN_ARROW:
                return  i_mainArrow;
            case ICON_MAIN_BACK:
                return  i_mainBack;
            case ICON_MAIN_X:
                return  x;
            case ICON_MAIN_UPDATE:
                return  i_mainUpdate;
            case ICON_MAIN_NEW:
                return  i_mainNew;
            case ICON_MAIN_OPEN:
                return  i_mainOpen;
            case ICON_MAIN_SAVE:
                return  i_mainSave;
            case ICON_MAIN_PRINT:
                return  i_mainPrint;
            case ICON_MAIN_COPY:
                return  i_mainCopy;
            case ICON_MAIN_PASTE:
                return  i_mainPaste;
            case ICON_MAIN_CUT:
                return  i_mainCut;
            case ICON_MAIN_CONTENTS:
                return  i_mainContents;
            case ICON_MAIN_XYVIEW:
                return i_mainXyView;
            case ICON_MAIN_YZVIEW:
                return i_mainYzView;
            case ICON_MAIN_XZVIEW:
                return i_mainXzView;
            case ICON_MAIN_ISOMETRICVIEW:
                return i_mainIsometricView;
            case ICON_MAIN_SHADING:
                return i_mainShading;
            case ICON_MAIN_SHADINGWITHEDGES:
                return i_mainShadingWithEdges;
            case ICON_MAIN_WIREFRAME:
                return i_mainWireframe;
            case ICON_MAIN_ZOOMIN:
                return i_mainZoomIn;
            case ICON_MAIN_ZOOMOUT:
                return i_mainZoomOut;
            case ICON_MAIN_FITITALL:
                return i_mainFitItAll;

        }
        return icon;
    }

    private ImageIcon GetIconTreePart(int name)
    {
        ImageIcon icon = null;
        switch (name)
        {
            case ICON_T_PART_MODEL:
                return i_modelPart;
            case ICON_T_PART_SKETCH:
                return i_t_partSketch;
            case ICON_T_PART_PLANE:
                return i_t_partPlane;
            case ICON_T_PART_PAD:
                return i_t_partPad;
            case ICON_T_PART_POCKET:
                return i_t_partPocket;
            case ICON_T_PART_REVOLUTION:
                return i_t_partRevolution;
            case ICON_T_PART_GROOVE:
                return i_t_partGroove;
            case ICON_T_PART_SWEEP:
                return i_t_partSweep;
            case ICON_T_PART_SLOT:
                return i_t_partSlot;
            case ICON_T_PART_LOFT:
                return i_t_partLoft;
            case ICON_T_PART_REMOVELOFT:
                return i_t_partRemovedLoft;
            case ICON_T_PART_INTERSECT:
                return i_t_partIntersect;
            case ICON_T_PART_SUBTRACT:
                return i_t_partSubtract;
            case ICON_T_PART_UNION:
                return i_t_partUnion;
            case ICON_T_PART_ROUNDHOLE:
                return i_t_partRoundHole;
            case ICON_T_PART_COUNTERSUNKHOLE:
                return i_t_partCounterSunkHole;
            case ICON_T_PART_COUNTERBOREHOLE:
                return i_t_partConuterBoreHole;
            case ICON_T_PART_EDGEROUND:
                return i_t_partEdgeRound;
            case ICON_T_PART_EDGECHAMFER:
                return i_t_partEdgeChamfer;
            case ICON_T_PART_SHELLING:
                return i_t_partShelling;
            case ICON_T_PART_SPLIT:
                return i_t_partSplit;
            case ICON_T_PART_BODY_RESULT:
                return i_t_partBodyResult;
            case ICON_T_PART_BODY_OPEN:
                return i_t_partBodyOpen;
            case ICON_T_PART_BODY_FACE:
                return i_t_partBodyFace;
            case ICON_T_PART_BODY_EDGE:
                return i_t_partBodyEdge;
            case ICON_T_PART_BODY_SURFACE:
                return i_t_partBodySurface;

            case ICON_T_PART_RESULTBODY:
                return i_t_partResultBody;
        }
        return icon;
    }


    private ImageIcon GetIconTreeAsm(int name)
    {
        switch (name)
        {
            case ICON_T_ASM_MODEL:
               return  i_modelAssembly;
            case ICON_T_ASM_COMPONENT:
                return i_t_asmComponent;
            case ICON_T_ASM_PART:
                return i_t_asmPartDesign;
            case ICON_T_ASM_CONSTRAINT:
                return i_t_asmConstraint;
            case ICON_T_ASM_COPLANAR:
                return i_t_asmCoplanar;
            case ICON_T_ASM_CONNECT:
                return i_t_asmConnect;
            case ICON_T_ASM_OFFSET:
                return i_t_asmOffset;
            case ICON_T_ASM_FIXTOGETHER:
                return i_t_asmFixTogether;
            case ICON_T_ASM_FITS:
                return i_t_asmFits;
            case ICON_T_ASM_FIXED:
                return i_t_asmFixed;
            case ICON_T_ASM_LINECONNECT:
                return this.i_t_asmLineConnect;
            case ICON_T_ASM_ANGLE:
                return i_t_asmAngle;
            case ICON_T_ASM_EXPLODE:
                return i_t_asmExplode;

            case ICON_T_DMU_CLINDRICAL:
                return i_t_dmuCylindrical;
            case ICON_T_DMU_PRISMATIC:
                return i_t_dmuPrismatic;
            case ICON_T_DMU_REVOLUTE:
                return i_t_dmuRevolute;
            case ICON_T_DMU_RIGID:
                return i_t_dmuRigid;
            case ICON_T_DMU_SPHERICAL:
                return i_t_dmuSpherical;
            case ICON_T_DMU_PLANAR:
                return i_t_dmuPlanar;
            case ICON_T_DMU_SCREW:
                return i_t_dmuScrew;
            case ICON_T_DMU_FIXEDPART:
                return i_t_dmuFixedPart;
            case ICON_T_DMU_SIMULATION:
                return i_t_dmuSimulation;
            case ICON_T_DMU_MECHANISM:
                return i_t_dmuMechanism;
        }
        return null;
    }


    private ImageIcon GetIconTreeSurf(int name)
    {
        switch (name)
        {
            case ICON_T_SURF_EDGE:
                return i_t_surfEdge;
            case ICON_T_SURF_FACE:
                return i_t_surfFace;
            case ICON_T_SURF_VERTEX:
                return i_t_surfVertex;
            case ICON_T_SURF_OPENBODY:
                return i_t_surfOpenBody;
            case ICON_T_SURF_JOIN:
                return i_t_surfjoin;

        }
        return null;
    }

    private ImageIcon GetIconTreeSk(int name)
   {
       switch (name)
       {
           case ICON_T_SK_BSPLINE:
               return i_t_skBspline;
           case ICON_T_SK_CIRCLE:
               return i_t_skCircle;
           case ICON_T_SK_COINCIDENCE:
               return i_t_skCoincidence;
           case ICON_T_SK_CONCENTRICITY:
               return i_t_skConcentricity;
           case ICON_T_SK_CONSTRAINT:
               return i_t_skConstraint;
           case ICON_T_SK_DIAMETER:
               return i_t_skDiameter;
           case ICON_T_SK_DISTANCE:
               return i_t_skDistance;
           case ICON_T_SK_FIX:
               return i_t_skFix;
           case ICON_T_SK_GEOMETRY:
               return i_t_skGeometry;
           case ICON_T_SK_HORIZONTAL:
               return i_t_skHorizontal;
           case ICON_T_SK_LENGTH:
               return i_t_skLength;
           case ICON_T_SK_LINE:
               return i_t_skLine;

           case ICON_T_SK_SYMMETRY:
               return i_t_skSymmetry;
           case ICON_T_SK_PARALLELISM:
               return i_t_skParallelism;
           case ICON_T_SK_PERPENDICULAR:
               return i_t_skPerpendicular;
           case ICON_T_SK_ANGULAR:
               return i_t_skAngular;

           case ICON_T_SK_POINT:
               return i_t_skPoint;
           case ICON_T_SK_RADIUS_FILLET:
               return i_t_skRadiusFillet;
           case ICON_T_SK_TANGENCY:
               return i_t_skTangency;
           case ICON_T_SK_UNKNOWN:
               return i_t_skUnknown;
           case ICON_T_SK_VERTICAL:
               return i_t_skVertical;
       }
       return null;
   }
}
