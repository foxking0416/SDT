package sdt.tree;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import sdt.define.*;
import sdt.framework.*;


/**
 * <p>Title: </p>
 * Tree Assembly
 *
 * <p>Description: </p>
 * ∞wπÔSpeaker Customized Tree Structure
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class SDT_TreeAssembly extends SDT_TreeMain implements DefineIcon, DefineSystemConstant
{
    /**
     * Icon §W¶‚Class
     */
    private SDT_IconNodeRenderer _render;

    private SDT_ObjectNode     _nodeRoot;
    private SDT_ObjectNode     _nodeAsmCone;
    private SDT_ObjectNode     _nodeAsmVCM;
    private SDT_ObjectNode     _nodeAsmFrame;
    private SDT_ObjectNode     _nodeAsmEnclosure;
    private SDT_ObjectNode     _nodeAsmAir;

    //private SDT_ObjectNode     nodeConeSketch;
    private SDT_ObjectNode     _nodePrtSurround;
    private SDT_ObjectNode     _nodeSktTopSurround;
    private SDT_ObjectNode     _nodeSktSectionSurround;
    private SDT_ObjectNode     _nodeSktSectionSurround2;

    private SDT_ObjectNode     _nodePrtDiaphragm;
    private SDT_ObjectNode     _nodeSktTopDiaphragm;
    private SDT_ObjectNode     _nodeSktSectionDiaphragm;
    private SDT_ObjectNode     _nodeSktSectionDiaphragm2;


    private SDT_ObjectNode     _nodePrtTransition;
    private SDT_ObjectNode     _nodeSktTopTransition;
    private SDT_ObjectNode     _nodeSktSectionTransition;
    private SDT_ObjectNode     _nodeSktSectionTransition2;
    private SDT_ObjectNode     _nodePrtCap;
    private SDT_ObjectNode     _nodeSktTopCap;
    private SDT_ObjectNode     _nodeSktSectionCap;
    private SDT_ObjectNode     _nodeSktSectionCap2;


    private SDT_ObjectNode     _nodePrtMagnet;
    private SDT_ObjectNode     _nodeSktSectionMagnet;
    private SDT_ObjectNode     _nodeSktSectionMagnet2;
    private SDT_ObjectNode     _nodeSktTopMagnet;
    private SDT_ObjectNode     _nodePrtTopPlate;
    private SDT_ObjectNode     _nodeSktSectionTopPlate;
    private SDT_ObjectNode     _nodeSktSectionTopPlate2;
    private SDT_ObjectNode     _nodeSktTopTopPlate;
    private SDT_ObjectNode     _nodePrtMagnetTop;
    private SDT_ObjectNode     _nodeSktSectionMagnetTop;
    private SDT_ObjectNode     _nodeSktSectionMagnetTop2;
    private SDT_ObjectNode     _nodeSktTopMagnetTop;
    private SDT_ObjectNode     _nodePrtMagnetOuter;
    private SDT_ObjectNode     _nodeSktSectionMagnetOuter;
    private SDT_ObjectNode     _nodeSktSectionMagnetOuter2;
    private SDT_ObjectNode     _nodeSktTopMagnetOuter;

    private SDT_ObjectNode     _nodeAsmYoke;
    //private SDT_ObjectNode     _nodePrtBobbin;
    //private SDT_ObjectNode     _nodeSktSectionBobbin;
    //private SDT_ObjectNode     _nodeSktSectionBobbin2;
    //private SDT_ObjectNode     _nodeSktTopBobbin;

    private SDT_ObjectNode     _nodePrtCoil;
    private SDT_ObjectNode     _nodeSktSectionCoil;
    private SDT_ObjectNode     _nodeSktSectionCoil2;
    private SDT_ObjectNode     _nodeSktTopCoil;

    private SDT_ObjectNode     _nodePrtYokeBase;
    private SDT_ObjectNode     _nodeSktSectionYokeBase;
    private SDT_ObjectNode     _nodeSktSectionYokeBase2;
    private SDT_ObjectNode     _nodeSktTopYokeBase;

    private SDT_ObjectNode     _nodePrtYokeStage1;
    private SDT_ObjectNode     _nodeSktSectionYokeStage1;
    private SDT_ObjectNode     _nodeSktSectionYokeStage1_2;
    private SDT_ObjectNode     _nodeSktTopYokeStage1;

    private SDT_ObjectNode     _nodePrtYokeStage2;
    private SDT_ObjectNode     _nodeSktSectionYokeStage2;
    private SDT_ObjectNode     _nodeSktSectionYokeStage2_2;
    private SDT_ObjectNode     _nodeSktTopYokeStage2;




     private   JPopupMenu        _popupMenuAsmCone;
     private   JPopupMenu        _popupMenuAsmVCM;
     private   JPopupMenu        _popupMenuAsmYoke;
     private   JPopupMenu        _popupMenuAsmAir;

     private   JPopupMenu        _popupMenuSolidPrt;
     private   JPopupMenu        _popupMenuShellPrt;

     private   JPopupMenu        _popupMenuSktSectionCap;
     private   JPopupMenu        _popupMenuSktSectionSurround;
     private   JPopupMenu        _popupMenuSktSectionTransition;
     private   JPopupMenu        _popupMenuSktSectionDiaphragm;
     private   JPopupMenu        _popupMenuSktSectionCoil;
     private   JPopupMenu        _popupMenuSktSectionMagnet;
     private   JPopupMenu        _popupMenuSktSectionMagnetTop;
     private   JPopupMenu        _popupMenuSktSectionMagnetOuter;
     private   JPopupMenu        _popupMenuSktSectionTopPlate;
     private   JPopupMenu        _popupMenuSktSectionYokeBase;
     private   JPopupMenu        _popupMenuSktSectionYokeStage1;
     private   JPopupMenu        _popupMenuSktSectionYokeStage2;







     private JPopupMenu _popupMenuSktTop;

     // private boolean   _isSurroundRunway = false;   //defualt select--> Round
     // private boolean   _isCapTransitionRunway = false;       //defualt select--> Round
     private boolean _isVCMRunway = false; //defualt select--> Round
     //private int       _diaphragmStatus = DefineSystemConstant.DIAPHRAGM_TYPE1;
     private boolean _isMagnetTopOn = true;
     private boolean _isMagnetOuterOn = true;
     private boolean _isBobbinOn = true;
     private boolean _isStage1On = true;
     private boolean _isStage2On = true;


     private boolean _is2D = true; //defualt select--> 2D;
     private boolean _isAir2D = true;
     private boolean _isCoilType1 = true;

 //   public boolean           isDiaphragmOn()                                {return this._isDiaphragmOn;}
 //   public boolean           isSurroundRunway()                             {return this._isSurroundRunway;}

    public void              setMenuPopupShellPrt(JPopupMenu pop)              { _popupMenuShellPrt = pop;}
    public void              setMenuPopupSolidPrt(JPopupMenu pop)              { _popupMenuSolidPrt = pop;}
    public void              setMenuPopupAsmCone(JPopupMenu pop)               { _popupMenuAsmCone = pop;}
    public void              setMenuPopupSktTop(JPopupMenu pop)                { _popupMenuSktTop = pop;}
    public void              setMenuPopupAsmVCM(JPopupMenu pop)                { _popupMenuAsmVCM = pop;}
    public void              setMenuPopupAsmYoke(JPopupMenu pop)               { _popupMenuAsmYoke = pop;}
    public void              setMenuPopupAsmAir(JPopupMenu pop)                { _popupMenuAsmAir = pop;}
    public void              setMenuPopupSktSectionCap(JPopupMenu pop)         { _popupMenuSktSectionCap = pop;}
    public void              setMenuPopupSktSectionSurround(JPopupMenu pop)    { _popupMenuSktSectionSurround = pop;}
    public void              setMenuPopupSktSectionTransition(JPopupMenu pop)  { _popupMenuSktSectionTransition = pop;}
    public void              setMenuPopupSktSectionDiaphragm(JPopupMenu pop)   { _popupMenuSktSectionDiaphragm = pop;}
    public void              setMenuPopupSktSectionCoil(JPopupMenu pop)        { _popupMenuSktSectionCoil = pop;}
    public void              setMenuPopupSktSectionMagnet(JPopupMenu pop)      { _popupMenuSktSectionMagnet = pop;}
    public void              setMenuPopupSktSectionMagnetTop(JPopupMenu pop)   { _popupMenuSktSectionMagnetTop = pop;}
    public void              setMenuPopupSktSectionMagnetOuter(JPopupMenu pop) { _popupMenuSktSectionMagnetOuter = pop;}
    public void              setMenuPopupSktSectionTopPlate(JPopupMenu pop)    { _popupMenuSktSectionTopPlate = pop;}
    public void              setMenuPopupSktSectionYokeBase(JPopupMenu pop)    { _popupMenuSktSectionYokeBase = pop;}
    public void              setMenuPopupSktSectionYokeStage1(JPopupMenu pop)  { _popupMenuSktSectionYokeStage1 = pop;}
    public void              setMenuPopupSktSectionYokeStage2(JPopupMenu pop)  { _popupMenuSktSectionYokeStage2 = pop;}


    public SDT_ObjectNode    getSelectedNode()                                 {return this._selectedNode;}

    //public int               getDiaphragmStatus()                              {return this._diaphragmStatus;}

    //public DefaultTreeModel  getDefaultTreeModel() { return this.defaultTreeModel;}
    //public SDT_ObjectNode    getRootNode()         { return this.nodeRoot;}




    public SDT_TreeAssembly(SDT_System system)
    {
        super(system);
        this._render = new SDT_IconNodeRenderer();
        String showNameSpeaker = this._system.GetInterfaceString(this.ASM_SPEAKER);
        this.newRootNode(this.ASM_SPEAKER,showNameSpeaker,1,DefineIcon.ICON_T_PART_BODY_OPEN);
        this.createSpeakerTree();

    }
    private void newRootNode(String nodeName,String showName,int nodeId,int iconId)
    {
        ImageIcon icon = this._managerIcon.GetIcon(iconId);
        this._nodeRoot = new SDT_ObjectNode(nodeName, showName, nodeId, iconId, icon);

        this._tree = new JTree(this._nodeRoot);
        this._tree.setCellRenderer(this._render);
        this._tree.setRowHeight(28);

        defaultTreeModel = (DefaultTreeModel) _tree.getModel();
        treeSelectionModel = _tree.getSelectionModel();
        treeSelectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        this._selectedPath = _tree.getPathForRow(0);
        treeSelectionModel.setSelectionPath(this._selectedPath);
        try
        {
            _tree.addMouseListener(this);
            _tree.addMouseMotionListener(this);
            _tree.addKeyListener(this);
            this.getViewport().add(_tree, null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void createSpeakerTree()
    {
        _nodeAsmCone                  = insertNode(this.ASM_Cone, this.TYPE_CONE, DefineIcon.ICON_MODEL_ASSEMBLY, 0, this._nodeRoot);
        _nodeAsmVCM                   = insertNode(this.ASM_VCM, this.TYPE_VCM, DefineIcon.ICON_MODEL_ASSEMBLY, 1, this._nodeRoot);
        _nodeAsmFrame                 = insertNode(this.ASM_Frame, this.TYPE_FRAME, DefineIcon.ICON_MODEL_PART, 2, this._nodeRoot);
        _nodeAsmEnclosure             = insertNode(this.ASM_Enclosure, this.TYPE_ENCLOSURE, DefineIcon.ICON_MODEL_PART, 3, this._nodeRoot);
       // _nodeAsmAir                   = insertNode(this.ASM_Air, this.TYPE_AIR, DefineIcon.ICON_MODEL_PART, 4, this._nodeRoot);


        _nodePrtCap                   = insertNode(this.PRT_Cap, this.TYPE_CAP, DefineIcon.ICON_MODEL_PART, 0, this._nodeAsmCone);
        _nodePrtTransition            = insertNode(this.PRT_Transition, this.TYPE_TRANSITION, DefineIcon.ICON_MODEL_PART, 1, this._nodeAsmCone);
        _nodePrtDiaphragm             = insertNode(this.PRT_Diaphragm, this.TYPE_DIAPHRAGM, DefineIcon.ICON_MODEL_PART, 2, this._nodeAsmCone);
        _nodePrtSurround              = insertNode(this.PRT_Surround, this.TYPE_SURROUND, DefineIcon.ICON_MODEL_PART, 3, this._nodeAsmCone);

        _nodeSktTopSurround           = insertNode(this.SKT_Top, this.TYPE_SURROUND, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtSurround);
        _nodeSktSectionSurround       = insertNode(this.SKT_Section1, this.TYPE_SURROUND, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtSurround);
        _nodeSktSectionSurround2      = insertNode(this.SKT_Section2, this.TYPE_SURROUND, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtSurround);

        _nodeSktTopDiaphragm          = insertNode(this.SKT_Top,  this.TYPE_DIAPHRAGM, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtDiaphragm);
        _nodeSktSectionDiaphragm      = insertNode(this.SKT_Section1,  this.TYPE_DIAPHRAGM, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtDiaphragm);
        _nodeSktSectionDiaphragm2     = insertNode(this.SKT_Section2,  this.TYPE_DIAPHRAGM, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtDiaphragm);

        _nodeSktTopCap                = insertNode(this.SKT_Top, this.TYPE_CAP, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtCap);
        _nodeSktSectionCap            = insertNode(this.SKT_Section1, this.TYPE_CAP, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtCap);
        _nodeSktSectionCap2           = insertNode(this.SKT_Section2, this.TYPE_CAP, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtCap);


        _nodeSktTopTransition         = insertNode(this.SKT_Top, this.TYPE_TRANSITION, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtTransition);
        _nodeSktSectionTransition     = insertNode(this.SKT_Section1, this.TYPE_TRANSITION, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtTransition);
        _nodeSktSectionTransition2    = insertNode(this.SKT_Section2, this.TYPE_TRANSITION, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtTransition);

        boolean ifOff = (this._system.getDataManager().getDataDiaphragm().getGeometryType() == DefineSystemConstant.DIAPHRAGM_NONE);
        this.setDiaphragmNode(ifOff);
        this.setShapeCapTransition();
        this.setShapeSurroundDiaphgram();

        this._nodePrtMagnet               = insertNode( this.PRT_Magnet, this.TYPE_MAGNET, DefineIcon.ICON_MODEL_PART, 0, this._nodeAsmVCM);
        this._nodeSktSectionMagnet        = insertNode( this.SKT_Section1, this.TYPE_MAGNET, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtMagnet);
        this._nodeSktSectionMagnet2       = insertNode( this.SKT_Section2, this.TYPE_MAGNET, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtMagnet);
        this._nodeSktTopMagnet            = insertNode( this.SKT_Top, this.TYPE_MAGNET, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtMagnet);

        this._nodePrtTopPlate             = insertNode( this.PRT_TopPlate, this.TYPE_TOPPLATE, DefineIcon.ICON_MODEL_PART, 1, this._nodeAsmVCM);
        this._nodeSktSectionTopPlate      = insertNode( this.SKT_Section1, this.TYPE_TOPPLATE, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtTopPlate);
        this._nodeSktSectionTopPlate2     = insertNode( this.SKT_Section2, this.TYPE_TOPPLATE, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtTopPlate);
        this._nodeSktTopTopPlate          = insertNode( this.SKT_Top, this.TYPE_TOPPLATE, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtTopPlate);

        this._nodePrtMagnetTop            = insertNode( this.PRT_MagnetTop, this.TYPE_MAGNETTOP, DefineIcon.ICON_MODEL_PART, 2, this._nodeAsmVCM);
        this._nodeSktSectionMagnetTop     = insertNode( this.SKT_Section1, this.TYPE_MAGNETTOP, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtMagnetTop);
        this._nodeSktSectionMagnetTop2    = insertNode( this.SKT_Section2, this.TYPE_MAGNETTOP, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtMagnetTop);
        this._nodeSktTopMagnetTop         = insertNode( this.SKT_Top, this.TYPE_MAGNETTOP, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtMagnetTop);

        this._nodePrtMagnetOuter          = insertNode( this.PRT_MagnetOuter, this.TYPE_MAGNETOUTER, DefineIcon.ICON_MODEL_PART, 3, this._nodeAsmVCM);
        this._nodeSktSectionMagnetOuter   = insertNode( this.SKT_Section1, this.TYPE_MAGNETOUTER, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtMagnetOuter);
        this._nodeSktSectionMagnetOuter2  = insertNode( this.SKT_Section2, this.TYPE_MAGNETOUTER, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtMagnetOuter);
        this._nodeSktTopMagnetOuter       = insertNode( this.SKT_Top, this.TYPE_MAGNETOUTER, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtMagnetOuter);


        //_nodePrtBobbin                      = insertNode( this.PRT_Bobbin, this.TYPE_BOBBIN, DefineIcon.ICON_MODEL_PART, 4, this._nodeAsmVCM);
        //this._nodeSktSectionBobbin       = insertNode( this.SKT_Section1, this.TYPE_BOBBIN, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtBobbin);
        //this._nodeSktSectionBobbin2      = insertNode( this.SKT_Section2, this.TYPE_BOBBIN, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtBobbin);
        //this._nodeSktTopBobbin           = insertNode( this.SKT_Top, this.TYPE_BOBBIN, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtBobbin);


        this._nodePrtCoil                        = insertNode( this.PRT_Coil, this.TYPE_COIL, DefineIcon.ICON_MODEL_PART, 4, this._nodeAsmVCM);
        this._nodeSktSectionCoil       = insertNode( this.SKT_Section1, this.TYPE_COIL, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtCoil);
        this._nodeSktSectionCoil2      = insertNode( this.SKT_Section2, this.TYPE_COIL, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtCoil);
        this._nodeSktTopCoil           = insertNode( this.SKT_Top, this.TYPE_COIL, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtCoil);




        _nodeAsmYoke                        = insertNode( this.ASMPRT_Yoke, this.TYPE_YOKE, DefineIcon.ICON_MODEL_ASSEMBLY, 5, this._nodeAsmVCM);
        _nodePrtYokeBase                    = insertNode( this.PRT_YokeBase, this.TYPE_YOKEBASE, DefineIcon.ICON_MODEL_PART, 0, this._nodeAsmYoke);
        this._nodeSktSectionYokeBase       = insertNode( this.SKT_Section1, this.TYPE_YOKEBASE, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtYokeBase);
        this._nodeSktSectionYokeBase2      = insertNode( this.SKT_Section2, this.TYPE_YOKEBASE, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtYokeBase);
        this._nodeSktTopYokeBase           = insertNode( this.SKT_Top, this.TYPE_YOKEBASE, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtYokeBase);


        _nodePrtYokeStage1                  = insertNode( this.PRT_YokeStage1, this.TYPE_YOKESTAGE1, DefineIcon.ICON_MODEL_PART, 1, this._nodeAsmYoke);
        this._nodeSktSectionYokeStage1        = insertNode( this.SKT_Section1, this.TYPE_YOKESTAGE1, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtYokeStage1);
        this._nodeSktSectionYokeStage1_2      = insertNode( this.SKT_Section2, this.TYPE_YOKESTAGE1, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtYokeStage1);
        this._nodeSktTopYokeStage1            = insertNode( this.SKT_Top, this.TYPE_YOKESTAGE1, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtYokeStage1);

        _nodePrtYokeStage2                  = insertNode( this.PRT_YokeStage2, this.TYPE_YOKESTAGE2, DefineIcon.ICON_MODEL_PART, 2, this._nodeAsmYoke);
        this._nodeSktSectionYokeStage2       = insertNode( this.SKT_Section1, this.TYPE_YOKESTAGE2, DefineIcon.ICON_MODEL_SKETCH, 0, this._nodePrtYokeStage2);
        this._nodeSktSectionYokeStage2_2      = insertNode( this.SKT_Section2, this.TYPE_YOKESTAGE2, DefineIcon.ICON_MODEL_SKETCH, 1, this._nodePrtYokeStage2);
        this._nodeSktTopYokeStage2           = insertNode( this.SKT_Top, this.TYPE_YOKESTAGE2, DefineIcon.ICON_MODEL_SKETCH, 2, this._nodePrtYokeStage2);

        this._isMagnetTopOn = (this._system.getDataManager().getDataMagnetTop().getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE);
        this._isMagnetOuterOn = (this._system.getDataManager().getDataMagnetOuter().getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE);
        this._isStage1On = (this._system.getDataManager().getDataYokeStage1().getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE);
        this._isStage2On = (this._system.getDataManager().getDataYokeStage2().getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE);

        this.setMagnetTop(this._isMagnetTopOn);
        this.setMagnetOut(this._isMagnetOuterOn);

        this.setYokeStage1(this._isStage1On);
        this.setYokeStage2(this._isStage2On);

        this.setShapeVCM();
        //this.setMagnetOuter(this._isMagnetOuterOn);
    }

    public void reloadSpeakerTree()
    {
        reloadNode(this._nodeRoot                    );

        reloadNode(_nodeAsmCone                         );
        reloadNode(_nodeAsmVCM                          );
        reloadNode(_nodeAsmFrame                        );
        reloadNode(_nodeAsmEnclosure                    );
        //reloadNode(_nodeAsmAir                          );

        reloadNode(_nodePrtCap                          );
        reloadNode(_nodePrtTransition                   );
        reloadNode(_nodePrtDiaphragm                    );
        reloadNode(_nodePrtSurround                     );

        reloadNode(_nodeSktTopSurround            );
        reloadNode(_nodeSktSectionSurround        );
        reloadNode(_nodeSktSectionSurround2       );

        reloadNode(_nodeSktTopDiaphragm           );
        reloadNode(_nodeSktSectionDiaphragm       );
        reloadNode(_nodeSktSectionDiaphragm2      );

        reloadNode(_nodeSktTopCap                 );
        reloadNode(_nodeSktSectionCap             );
        reloadNode(_nodeSktSectionCap2            );

        reloadNode(_nodeSktTopTransition          );
        reloadNode(_nodeSktSectionTransition      );
        reloadNode(_nodeSktSectionTransition2     );

        boolean ifOff = (this._system.getDataManager().getDataDiaphragm().getGeometryType() == DefineSystemConstant.DIAPHRAGM_NONE);
        this.setDiaphragmNode(ifOff);
        this.setShapeCapTransition();
        this.setShapeSurroundDiaphgram();

       reloadNode(this._nodePrtMagnet                    );
       reloadNode(this._nodeSktSectionMagnet       );
       reloadNode(this._nodeSktSectionMagnet2      );
       reloadNode(this._nodeSktTopMagnet           );

       reloadNode(this._nodePrtTopPlate                  );
       reloadNode(this._nodeSktSectionTopPlate     );
       reloadNode(this._nodeSktSectionTopPlate2    );
       reloadNode(this._nodeSktTopTopPlate         );

       reloadNode(this._nodePrtMagnetTop                 );
       reloadNode(this._nodeSktSectionMagnetTop    );
       reloadNode(this._nodeSktSectionMagnetTop2   );
       reloadNode(this._nodeSktTopMagnetTop        );

       reloadNode(this._nodePrtMagnetOuter               );
       reloadNode(this._nodeSktSectionMagnetOuter  );
       reloadNode(this._nodeSktSectionMagnetOuter2 );
       reloadNode(this._nodeSktTopMagnetOuter      );

       //reloadNode(this._nodePrtBobbin                    );
       //reloadNode(this._nodeSktSectionBobbin       );
       //reloadNode(this._nodeSktSectionBobbin2      );
       //reloadNode(this._nodeSktTopBobbin           );

       reloadNode(this._nodePrtCoil                      );
       reloadNode(this._nodeSktSectionCoil         );
       reloadNode(this._nodeSktSectionCoil2        );
       reloadNode(this._nodeSktTopCoil             );

       reloadNode(this._nodeAsmYoke                      );
       reloadNode(this._nodePrtYokeBase                  );
       reloadNode(this._nodeSktSectionYokeBase     );
       reloadNode(this._nodeSktSectionYokeBase2    );
       reloadNode(this._nodeSktTopYokeBase         );


       reloadNode(this._nodePrtYokeStage1                 );
       reloadNode(this._nodeSktSectionYokeStage1    );
       reloadNode(this._nodeSktSectionYokeStage1_2  );
       reloadNode(this._nodeSktTopYokeStage1        );

       reloadNode(this._nodePrtYokeStage2                 );
       reloadNode(this._nodeSktSectionYokeStage2    );
       reloadNode(this._nodeSktSectionYokeStage2_2  );
       reloadNode(this._nodeSktTopYokeStage2        );

       this._isMagnetTopOn = (this._system.getDataManager().getDataMagnetTop().getGeometryType() != DefineSystemConstant.MAGNETTOP_NONE);
       this._isMagnetOuterOn = (this._system.getDataManager().getDataMagnetOuter().getGeometryType() != DefineSystemConstant.MAGNETOUTER_NONE);
       this._isStage1On = (this._system.getDataManager().getDataYokeStage1().getGeometryType() != DefineSystemConstant.YOKESTAGE1_NONE);
       this._isStage2On = (this._system.getDataManager().getDataYokeStage2().getGeometryType() != DefineSystemConstant.YOKESTAGE2_NONE);



       this.setMagnetTop(this._isMagnetTopOn);
       this.setMagnetOut(this._isMagnetOuterOn);
       this.setYokeStage1(this._isStage1On);
       this.setYokeStage2(this._isStage2On);
       this.setShapeVCM();

    }

    public void setDiaphragmNode(boolean isOff)
    {
        _nodeAsmCone.removeAllChildren();

        if (isOff)
        {
            this.linkNode(_nodePrtCap, _nodeAsmCone, 0);
            this.linkNode(_nodePrtTransition, _nodeAsmCone, 1);
            this.linkNode(_nodePrtSurround, _nodeAsmCone, 2);
        }
        else
        {
            this.linkNode(_nodePrtCap, _nodeAsmCone, 0);
            this.linkNode(_nodePrtTransition, _nodeAsmCone, 1);
            this.linkNode(_nodePrtDiaphragm, _nodeAsmCone, 2);
            this.linkNode(_nodePrtSurround, _nodeAsmCone, 3);
        }
        this._tree.updateUI();
    }

    public void setMagnetTop(boolean isMagnetTopOn)
    {
        this._isMagnetTopOn = isMagnetTopOn;
        this.setMagnetTop_Outer();
    }

    public void setMagnetOut(boolean isMagnetOutOn)
    {
        this._isMagnetOuterOn = isMagnetOutOn;
        this.setMagnetTop_Outer();
    }


    private void setMagnetTop_Outer()
    {
        this._nodeAsmVCM.removeAllChildren();
        int i = 2;

        this.linkNode(this._nodePrtMagnet, this._nodeAsmVCM, 0);//Magnet
        this.linkNode(this._nodePrtTopPlate, this._nodeAsmVCM, 1);//Top Plate

        if(this._isMagnetTopOn)
        {
            this.linkNode(this._nodePrtMagnetTop, this._nodeAsmVCM, i);//Magnet Top
            i++;
        }

        if(this._isMagnetOuterOn)
        {
           this.linkNode(this._nodePrtMagnetOuter, this._nodeAsmVCM, i);//Magnet Outer
           i++;
        }

        this.linkNode(this._nodePrtCoil, this._nodeAsmVCM, i++);//Coil

        this.linkNode(this._nodeAsmYoke, this._nodeAsmVCM, i);//Yoke

        this._tree.updateUI();
    }


     public void setYokeStage1(boolean isYokeStage1On)
     {
         this._isStage1On = isYokeStage1On;
         this.setYoke();
     }

     public void setYokeStage2(boolean isYokeStage2On)
     {
         this._isStage2On = isYokeStage2On;
         this.setYoke();
     }

     private void setYoke()
     {
         this._nodeAsmYoke.removeAllChildren();
         int i = 0;
         this.linkNode(this._nodePrtYokeBase, this._nodeAsmYoke, i);
         i++;
         if (this._isStage1On)
         {
             this.linkNode(this._nodePrtYokeStage1, this._nodeAsmYoke, i);
             i++;
             if (this._isStage2On)
             {
                 this.linkNode(this._nodePrtYokeStage2, this._nodeAsmYoke, i);
             }
         }
         this._tree.updateUI();
     }



    public void setAsmView2D(boolean is2D)
    {
        this._is2D = is2D;
    }

    public void setShapeCapTransition()
    {
        boolean isRunway = this._system.getDataManager().getCapTransitionRunway();
        _nodePrtCap.removeAllChildren();
        _nodePrtTransition.removeAllChildren();
        if (isRunway)
        {
            this.linkNode(_nodeSktTopCap, _nodePrtCap, 0);
            this.linkNode(_nodeSktSectionCap, _nodePrtCap, 1);
            this.linkNode(_nodeSktSectionCap2, _nodePrtCap, 2);

            String strSktSection1 = this._system.GetInterfaceString(this.SKT_Section1);
            _nodeSktSectionCap.setUserObject(strSktSection1);
            _nodeSktSectionCap.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopTransition, _nodePrtTransition, 0);
            this.linkNode(_nodeSktSectionTransition, _nodePrtTransition, 1);
            this.linkNode(_nodeSktSectionTransition2, _nodePrtTransition, 2);

            _nodeSktSectionTransition.setUserObject(strSktSection1);
            _nodeSktSectionTransition.setNodeName(this.SKT_Section1);

        }
        else
        {
            this.linkNode(_nodeSktTopCap, _nodePrtCap, 0);
            this.linkNode(_nodeSktSectionCap, _nodePrtCap, 1);

            String strSktSection = this._system.GetInterfaceString(this.SKT_Section);
            _nodeSktSectionCap.setUserObject(strSktSection);
            _nodeSktSectionCap.setNodeName(this.SKT_Section);

            this.linkNode(_nodeSktTopTransition, _nodePrtTransition, 0);
            this.linkNode(_nodeSktSectionTransition, _nodePrtTransition, 1);

            _nodeSktSectionTransition.setUserObject(strSktSection);
            _nodeSktSectionTransition.setNodeName(this.SKT_Section);
        }
        this._tree.updateUI();
    }

    public void setShapeSurroundDiaphgram()
    {
        boolean isRunway = this._system.getDataManager().getSurroundDiaphragmRunway();
        this._system.getDataManager().setSurroundDiaphragmRunway(isRunway);
        _nodePrtSurround.removeAllChildren();
        _nodePrtDiaphragm.removeAllChildren();
        if (isRunway)
        {
             this.linkNode(_nodeSktTopSurround ,_nodePrtSurround,0);
             this.linkNode(_nodeSktSectionSurround ,_nodePrtSurround,1);
             this.linkNode(_nodeSktSectionSurround2, _nodePrtSurround, 2);

             String strSktSection1 = this._system.GetInterfaceString(this.SKT_Section1);
             _nodeSktSectionSurround.setUserObject(strSktSection1);
             _nodeSktSectionSurround.setNodeName(this.SKT_Section1);

             if(this._system.getDataManager().getDataDiaphragm().getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
             {
                 this.linkNode(_nodeSktTopDiaphragm ,_nodePrtDiaphragm,0);
                 this.linkNode(_nodeSktSectionDiaphragm ,_nodePrtDiaphragm,1);
                 this.linkNode(_nodeSktSectionDiaphragm2, _nodePrtDiaphragm, 2);

                 _nodeSktSectionDiaphragm.setUserObject(strSktSection1);
                 _nodeSktSectionDiaphragm.setNodeName(this.SKT_Section1);
             }
        }
        else
        {
            this.linkNode(_nodeSktTopSurround ,_nodePrtSurround,0);
            this.linkNode(_nodeSktSectionSurround ,_nodePrtSurround,1);

            String strSktSection = this._system.GetInterfaceString(this.SKT_Section);
            _nodeSktSectionSurround.setUserObject(strSktSection);
            _nodeSktSectionSurround.setNodeName(this.SKT_Section);


            if (this._system.getDataManager().getDataDiaphragm().getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            {
                this.linkNode(_nodeSktTopDiaphragm, _nodePrtDiaphragm, 0);
                this.linkNode(_nodeSktSectionDiaphragm, _nodePrtDiaphragm, 1);

                _nodeSktSectionDiaphragm.setUserObject(strSktSection);
                _nodeSktSectionDiaphragm.setNodeName(this.SKT_Section);
            }
        }
        this._tree.updateUI();
    }

    public void setShapeVCM()
    {
        boolean isRunway = this._system.getDataManager().getVCMRunway();
        this._nodePrtMagnet.removeAllChildren();
        this._nodePrtMagnetOuter.removeAllChildren();
        this._nodePrtTopPlate.removeAllChildren();
        this._nodePrtMagnetTop.removeAllChildren();
        this._nodePrtYokeBase.removeAllChildren();
        this._nodePrtYokeStage1.removeAllChildren();
        this._nodePrtYokeStage2.removeAllChildren();
        this._nodePrtCoil.removeAllChildren();

        if (isRunway)
        {
            this.linkNode(_nodeSktTopMagnet, _nodePrtMagnet, 0);
            this.linkNode(_nodeSktSectionMagnet, _nodePrtMagnet, 1);
            this.linkNode(_nodeSktSectionMagnet2, _nodePrtMagnet, 2);

            String strSktSection1 = this._system.GetInterfaceString(this.SKT_Section1);
            _nodeSktSectionMagnet.setUserObject(strSktSection1);
            _nodeSktSectionMagnet.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopMagnetOuter, _nodePrtMagnetOuter, 0);
            this.linkNode(_nodeSktSectionMagnetOuter, _nodePrtMagnetOuter, 1);
            this.linkNode(_nodeSktSectionMagnetOuter2, _nodePrtMagnetOuter, 2);

            _nodeSktSectionMagnetOuter.setUserObject(strSktSection1);
            _nodeSktSectionMagnetOuter.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopTopPlate, _nodePrtTopPlate, 0);
            this.linkNode(_nodeSktSectionTopPlate, _nodePrtTopPlate, 1);
            this.linkNode(_nodeSktSectionTopPlate2, _nodePrtTopPlate, 2);

            _nodeSktSectionTopPlate.setUserObject(strSktSection1);
            _nodeSktSectionTopPlate.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopMagnetTop, _nodePrtMagnetTop, 0);
            this.linkNode(_nodeSktSectionMagnetTop, _nodePrtMagnetTop, 1);
            this.linkNode(_nodeSktSectionMagnetTop2, _nodePrtMagnetTop, 2);

            _nodeSktSectionMagnetTop.setUserObject(strSktSection1);
            _nodeSktSectionMagnetTop.setNodeName(this.SKT_Section1);

            this.linkNode(this._nodeSktTopCoil, this._nodePrtCoil, 0);
            this.linkNode(this._nodeSktSectionCoil, this._nodePrtCoil, 1);
            this.linkNode(this._nodeSktSectionCoil2, this._nodePrtCoil, 2);

            _nodeSktSectionCoil.setUserObject(strSktSection1);
            _nodeSktSectionCoil.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeBase, _nodePrtYokeBase, 0);
            this.linkNode(_nodeSktSectionYokeBase, _nodePrtYokeBase, 1);
            this.linkNode(_nodeSktSectionYokeBase2, _nodePrtYokeBase, 2);

            _nodeSktSectionYokeBase.setUserObject(strSktSection1);
            _nodeSktSectionYokeBase.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeStage1, _nodePrtYokeStage1, 0);
            this.linkNode(_nodeSktSectionYokeStage1, _nodePrtYokeStage1, 1);
            this.linkNode(_nodeSktSectionYokeStage1_2, _nodePrtYokeStage1, 2);

            _nodeSktSectionYokeStage1.setUserObject(strSktSection1);
            _nodeSktSectionYokeStage1.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeStage2, _nodePrtYokeStage2, 0);
            this.linkNode(_nodeSktSectionYokeStage2, _nodePrtYokeStage2, 1);
            this.linkNode(_nodeSktSectionYokeStage2_2, _nodePrtYokeStage2, 2);

            _nodeSktSectionYokeStage2.setUserObject(strSktSection1);
            _nodeSktSectionYokeStage2.setNodeName(this.SKT_Section1);
        }
        else
        {
            this.linkNode(_nodeSktTopMagnet, _nodePrtMagnet, 0);
            this.linkNode(_nodeSktSectionMagnet, _nodePrtMagnet, 1);

            String strSktSection1 = this._system.GetInterfaceString(this.SKT_Section);
            _nodeSktSectionMagnet.setUserObject(strSktSection1);
            _nodeSktSectionMagnet.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopMagnetOuter, _nodePrtMagnetOuter, 0);
            this.linkNode(_nodeSktSectionMagnetOuter, _nodePrtMagnetOuter, 1);

            _nodeSktSectionMagnetOuter.setUserObject(strSktSection1);
            _nodeSktSectionMagnetOuter.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopTopPlate, _nodePrtTopPlate, 0);
            this.linkNode(_nodeSktSectionTopPlate, _nodePrtTopPlate, 1);

            _nodeSktSectionTopPlate.setUserObject(strSktSection1);
            _nodeSktSectionTopPlate.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopMagnetTop, _nodePrtMagnetTop, 0);
            this.linkNode(_nodeSktSectionMagnetTop, _nodePrtMagnetTop, 1);

            _nodeSktSectionMagnetTop.setUserObject(strSktSection1);
            _nodeSktSectionMagnetTop.setNodeName(this.SKT_Section1);

            this.linkNode(this._nodeSktTopCoil, this._nodePrtCoil, 0);
            this.linkNode(this._nodeSktSectionCoil, this._nodePrtCoil, 1);

            _nodeSktSectionCoil.setUserObject(strSktSection1);
            _nodeSktSectionCoil.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeBase, _nodePrtYokeBase, 0);
            this.linkNode(_nodeSktSectionYokeBase, _nodePrtYokeBase, 1);

            _nodeSktSectionYokeBase.setUserObject(strSktSection1);
            _nodeSktSectionYokeBase.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeStage1, _nodePrtYokeStage1, 0);
            this.linkNode(_nodeSktSectionYokeStage1, _nodePrtYokeStage1, 1);

            _nodeSktSectionYokeStage1.setUserObject(strSktSection1);
            _nodeSktSectionYokeStage1.setNodeName(this.SKT_Section1);

            this.linkNode(_nodeSktTopYokeStage2, _nodePrtYokeStage2, 0);
            this.linkNode(_nodeSktSectionYokeStage2, _nodePrtYokeStage2, 1);

            _nodeSktSectionYokeStage2.setUserObject(strSktSection1);
            _nodeSktSectionYokeStage2.setNodeName(this.SKT_Section1);
        }
        this._tree.updateUI();
    }

    public SDT_ObjectNode insertNode(String nodeName, int nodeId, int iconId, int nodeIndex, SDT_ObjectNode parentNode) //InsertNode for edge and vertex 2007.10.24 ramen
    {
        ImageIcon icon = this._managerIcon.GetIcon(iconId);
        String showName = this._system.GetInterfaceString(nodeName);
        SDT_ObjectNode childNode = new SDT_ObjectNode(nodeName,showName, nodeId, iconId, icon);
        this.defaultTreeModel.insertNodeInto(childNode, parentNode, nodeIndex);

        return childNode;
    }

    private void reloadNode(SDT_ObjectNode node)
    {
        String nodeName = node.getNodeName();
        String showName = this._system.GetInterfaceString(nodeName);
        node.setUserObject(showName);
    }



    public void linkNode(SDT_ObjectNode childNode, SDT_ObjectNode parentNode,int nodeIndex ) //InsertNode for edge and vertex 2007.10.24 ramen
    {
        this.defaultTreeModel.insertNodeInto(childNode, parentNode, nodeIndex);
    }

    public void mousePressed(MouseEvent e)
    {
        TreePath path = this.getTreePath(e);
        if (path == null)
        {
            this.treeSelectionModel.setSelectionPath(_selectedPath);
            return;
        }
        this.treeSelectionModel.setSelectionPath(path);
        _selectedNode = (SDT_ObjectNode) path.getLastPathComponent();
        _selectedPath = path;
    }


    public void mouseClicked(MouseEvent e)
    {
        if (e.getClickCount() == 1)
        {
            mouseClickedOnce(e);

        }
        else if (e.getClickCount() == 2)
        {
            this.mouseClickedTwice(e);
        }

    }

    public void mouseReleased(MouseEvent e)
    {
        int clickType = e.getModifiers();
        if (clickType == MouseEvent.BUTTON3_MASK)
        {
            this.selectNode();
            this.popupMenu(e.getComponent(), e.getX(), e.getY());
        }
    }

   private void mouseClickedOnce(MouseEvent e)
   {
       int clickType = e.getModifiers();
       if (clickType == MouseEvent.BUTTON3_MASK)
       {
           //this._system.getFrame().SetStatus("Tree: "+"MouseClicked_Right--->"+(String)this._selectedNode.getUserObject() );
       }
       if (clickType == MouseEvent.BUTTON1_MASK)
       {
           this.selectNode();
       }

   }

   private void mouseClickedTwice(MouseEvent e)
   {
       int clickType = e.getModifiers();
       if (clickType == MouseEvent.BUTTON3_MASK)
       {
           this._system.getFrame().SetStatus("Tree: " + "MouseDoubleClicked_Right--->" + (String) this._selectedNode.getUserObject());
       }
       if (clickType == MouseEvent.BUTTON1_MASK)
       {
          // selectNode(node);
       }
   }



   public void selectNode()
   {
       if(this._selectedNode == null)
           return;
       String nodeName = (String) _selectedNode.getNodeName();
       if(_selectedNode.getParentNode() == null)
           return;
       String nodeParentName = (String) _selectedNode.getParentNode().getNodeName();
       int nodeID = _selectedNode.getNodeID();

       this._system.getDataManager().setCurrentDataType(nodeID);
       if (nodeName.equals(this.ASM_Cone))
       {
           if (this._is2D)
           {
               //this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_CONE, this.XZView);
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID, this.XZView);
           }
           else if (!this._is2D )
           {
               this._system.changeTo3D();
           }
           //else do nothing!!
       }
       if (nodeName.equals(this.ASM_VCM))
       {
           if (this._is2D)
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID, this.XZView);
           else
           {

           }
       }
      if (nodeName.equals(this.ASMPRT_Yoke))
      {
          if (this._is2D)
              this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID, this.XZView);
          else
          {

          }

      }

      if (nodeName.equals(this.ASM_Air))
      {
          if (this._is2D)
              this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID, this.XZView);
          else
          {

          }

      }

       if (nodeName.equals(this.SKT_Section) || nodeName.equals(this.SKT_Section1))
       {
           if (!this._is2D)
           {
               this._is2D = true;
               this._system.changeTo2D();
           }

           this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID,this.XZView);
       }
       if (nodeName.equals(this.SKT_Section2))
       {
           if (!this._is2D)
           {
               this._is2D = true;
               this._system.changeTo2D();
           }

           this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(nodeID,this.YZView);
       }
       if (nodeName.equals(this.SKT_Top))
       {
           if (!this._is2D)
           {
               this._is2D = true;
               this._system.changeTo2D();
           }

           if (nodeParentName.equals(this.PRT_Cap))
           {
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_CAP ,this.XYView);
            }
           else if (nodeParentName.equals(this.PRT_Transition))
           {
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_TRANSITION ,this.XYView);
           }
           else if (nodeParentName.equals(this.PRT_Diaphragm))
           {
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_DIAPHRAGM ,this.XYView);
            }
           else if (nodeParentName.equals(this.PRT_Surround))
           {
               this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_SURROUND ,this.XYView);
            }
           else if (nodeParentName.equals(this.PRT_Coil))
          {
              this._system.getModeler().getPanel2D().GetDrawPanel().showElementBySection(this.TYPE_COIL ,this.XYView);
           }


       }
   }
   public void popupMenu(Component parent, int positionX, int positionY)
   {
       if(_selectedNode == null)
           return;
       String nodeName = (String) _selectedNode.getNodeName();
       if(_selectedNode.getParentNode() == null)
           return;
       String nodeParentName = (String) _selectedNode.getParentNode().getNodeName();
       if (nodeName.equals(this.PRT_Cap) || nodeName.equals(this.PRT_Transition))

       {
           boolean isCapTransitionRunway = this._system.getDataManager().getCapTransitionRunway();

           JMenu menu = this._system.getMenuPopup().getMenuFromName("SectionType");
           this._popupMenuShellPrt.add(menu);
           //2012.11.07 Release
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRunway", isCapTransitionRunway);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRound", !isCapTransitionRunway);
           this._popupMenuShellPrt.show(parent, positionX, positionY);
       }
       else if (nodeName.equals(this.PRT_Diaphragm) || nodeName.equals(this.PRT_Surround))
       {
           boolean isSurroundRunway = this._system.getDataManager().getSurroundDiaphragmRunway();

           JMenu menu = this._system.getMenuPopup().getMenuFromName("SectionType");
           this._popupMenuShellPrt.add(menu);
           //2012.11.07 Release
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRunway", isSurroundRunway);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRound", !isSurroundRunway);
           this._popupMenuShellPrt.show(parent, positionX, positionY);
       }

       else if (nodeName.equals(this.PRT_Magnet) || nodeName.equals(this.PRT_TopPlate) ||
                nodeName.equals(this.PRT_MagnetTop) || nodeName.equals(this.PRT_MagnetOuter) ||
                nodeName.equals(this.PRT_Coil) ||
                nodeName.equals(this.PRT_YokeBase) ||
                nodeName.equals(this.PRT_YokeStage1) || nodeName.equals(this.PRT_YokeStage2))
       {
           boolean isVCMRunway = this._system.getDataManager().getVCMRunway();

           JMenu menu = this._system.getMenuPopup().getMenuFromName("SectionType");
           this._popupMenuSolidPrt.add(menu);
           //2012.11.07 Release
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRunway", isVCMRunway);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSectionRound", !isVCMRunway);

           this._popupMenuSolidPrt.show(parent, positionX, positionY);
       }
       else if (nodeName.equals(this.ASM_Cone))
       {

           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBox2D", this._is2D);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBox3D", !this._is2D);

           JMenu menuSurround = this._system.getMenuPopup().getMenuFromName("Surround");
           menuSurround.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSurroundSingleArc"));
           menuSurround.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxSurroundDoubleArc"));

           switch(this._system.getDataManager().getDataSurround().getGeometryType())
           {
               case DefineSystemConstant.SURROUND_DOUBLE_ARC:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSurroundSingleArc", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSurroundDoubleArc", true);
                   break;
               case DefineSystemConstant.SURROUND_SINGLE_ARC:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSurroundSingleArc", true);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxSurroundDoubleArc", false);
                   break;
           }

           JMenu menuDiaphragm = this._system.getMenuPopup().getMenuFromName("Diaphragm");
           menuDiaphragm.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmNone"));
           menuDiaphragm.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmType1"));
           menuDiaphragm.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxDiaphragmType2"));

           switch(this._system.getDataManager().getDataDiaphragm().getGeometryType())
           {
               case DefineSystemConstant.DIAPHRAGM_NONE:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmNone", true);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType1", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType2", false);
                   break;
               case DefineSystemConstant.DIAPHRAGM_TYPE1:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmNone", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType1", true);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType2", false);
                   break;
               case DefineSystemConstant.DIAPHRAGM_TYPE2:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmNone", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType1", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxDiaphragmType2", true);

                   break;
           }

           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxTransition", true);

            JMenu menuCap = this._system.getMenuPopup().getMenuFromName("Cap");
            menuCap.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapRegular"));
            //2012.11.07 Release
            menuCap.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapCapsule"));
            menuCap.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCapRoundCut"));
            //switch (this._system.getModeler().getCapStatus())
            switch (this._system.getDataManager().getDataCap().getGeometryType())
            {
                case DefineSystemConstant.CAP_TYPE_REGULAR:
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRegular"   , true);
                    //2012.11.07 Release
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapCapsule" , false);
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRoundCut", false);
                    break;
                case DefineSystemConstant.CAP_TYPE_CAPSULE:
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRegular"   , false);
                    //2012.11.07 Release
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapCapsule" , true);
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRoundCut", false);
                    break;
                case DefineSystemConstant.CAP_TYPE_ROUNDCUT:
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRegular"   , false);
                    //2012.11.07 Release
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapCapsule" , false);
                    this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCapRoundCut", true);

                    break;
            }



           this._popupMenuAsmCone.show(parent, positionX, positionY);
       }
       else if (nodeName.equals(this.ASM_VCM))
       {
           this._popupMenuAsmVCM.removeAll();
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxVCM2D"));
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxVCM3D"));
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnet"));
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxTopPlate"));
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetTop"));
           this._popupMenuAsmVCM.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxMagnetOuter"));


           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxVCM2D", this._is2D);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxVCM3D", !this._is2D);

           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxMagnet", true);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxTopPlate", true);

           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxMagnetTop", this._isMagnetTopOn);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxMagnetOuter", this._isMagnetOuterOn);


           JMenu menuCoil = this._system.getMenuPopup().getMenuFromName("Coil");
           menuCoil.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCoilType1"));
           menuCoil.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxCoilType2"));
           this._popupMenuAsmVCM.add( menuCoil);

           switch(this._system.getDataManager().getDataCoil().getGeometryType())
           {
               case DefineSystemConstant.COIL_TYPE1:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCoilType1", true);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCoilType2", false);
                   break;
               case DefineSystemConstant.COIL_TYPE2:
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCoilType1", false);
                   this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxCoilType2", true);
                   break;
           }
           this._popupMenuAsmVCM.show(parent, positionX, positionY);

       }
       else if (nodeName.equals(this.ASMPRT_Yoke))
       {
           this._popupMenuAsmYoke.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage1"));
           this._popupMenuAsmYoke.add( this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxStage2"));


           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxStage1", this._isStage1On);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxStage2", this._isStage2On);
           this._popupMenuAsmYoke.show(parent, positionX, positionY);
       }
       else if (nodeName.equals(this.ASM_Air))
       {
            //2012.11.07 Release
           /*this._popupMenuAsmAir.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxAir2D"));
           this._popupMenuAsmAir.add(this._system.getMenuPopup().getMenuItemCheckBoxFromName("ChkBoxAir3D"));

           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxAir2D", true);
           this._system.getMenuPopup().setMenuItemCheckBoxSelected("ChkBoxAir3D", true);
           this._popupMenuAsmAir.show(parent, positionX, positionY);
*/
       }

       else if (nodeName.equals(this.SKT_Top))
       {
           Component[] menuItems = (Component[])this._popupMenuSktTop.getComponents();

           if (nodeParentName.equals(this.PRT_MagnetOuter) ||
               nodeParentName.equals(this.PRT_Magnet) ||
               nodeParentName.equals(this.PRT_MagnetTop) ||
               nodeParentName.equals(this.PRT_TopPlate) ||
               nodeParentName.equals(this.PRT_YokeBase) ||
               nodeParentName.equals(this.PRT_YokeStage1) ||
               nodeParentName.equals(this.PRT_YokeStage2)
                   )
           {
               menuItems[0].setEnabled(false);
               menuItems[1].setEnabled(false);
           }
           else if(nodeParentName.equals(this.PRT_Surround))
           {
               menuItems[0].setEnabled(true);
               menuItems[1].setEnabled(true);
           }
           else
           {
               menuItems[0].setEnabled(true);
               menuItems[1].setEnabled(false);
           }
        //   else
        //       menuItems[1].setEnabled(true);

           this._popupMenuSktTop.show(parent, positionX, positionY);
       }
       else if (nodeName.equals(this.SKT_Section)|| nodeName.equals(this.SKT_Section1) || nodeName.equals(this.SKT_Section2))
       {
           if (nodeParentName.equals(this.PRT_Cap))
               this._popupMenuSktSectionCap.show(parent, positionX, positionY);
           if(nodeParentName.equals(this.PRT_Surround))
           {
               if (this._popupMenuSktSectionSurround.getComponentCount() > 1)
                   this._popupMenuSktSectionSurround.remove(1);
               this._popupMenuSktSectionSurround.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_Transition))
               this._popupMenuSktSectionTransition.show(parent, positionX, positionY);
           if(nodeParentName.equals(this.PRT_Diaphragm))
           {
               if (this._popupMenuSktSectionDiaphragm.getComponentCount() > 1)
                   this._popupMenuSktSectionDiaphragm.remove(1);
               this._popupMenuSktSectionDiaphragm.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_Coil))
           {
               if (this._popupMenuSktSectionCoil.getComponentCount() > 1)
                   this._popupMenuSktSectionCoil.remove(1);
               this._popupMenuSktSectionCoil.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_Magnet))
           {
               if (this._popupMenuSktSectionMagnet.getComponentCount() > 1)
                   this._popupMenuSktSectionMagnet.remove(1);
               this._popupMenuSktSectionMagnet.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_MagnetTop))
           {
               if (this._popupMenuSktSectionMagnetTop.getComponentCount() > 1)
                   this._popupMenuSktSectionMagnetTop.remove(1);
               this._popupMenuSktSectionMagnetTop.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_MagnetOuter))
           {
               if (this._popupMenuSktSectionMagnetOuter.getComponentCount() > 1)
                   this._popupMenuSktSectionMagnetOuter.remove(1);
               this._popupMenuSktSectionMagnetOuter.show(parent, positionX, positionY);
           }

           if(nodeParentName.equals(this.PRT_TopPlate))
           {
               if (this._popupMenuSktSectionTopPlate.getComponentCount() > 1)
                   this._popupMenuSktSectionTopPlate.remove(1);
               this._popupMenuSktSectionTopPlate.show(parent, positionX, positionY);
           }
           if(nodeParentName.equals(this.PRT_YokeBase))
          {
              if (this._popupMenuSktSectionYokeBase.getComponentCount() > 1)
                  this._popupMenuSktSectionYokeBase.remove(1);
              this._popupMenuSktSectionYokeBase.show(parent, positionX, positionY);
          }
          if(nodeParentName.equals(this.PRT_YokeStage1))
          {
              if (this._popupMenuSktSectionYokeStage1.getComponentCount() > 1)
                  this._popupMenuSktSectionYokeStage1.remove(1);
              this._popupMenuSktSectionYokeStage1.show(parent, positionX, positionY);
          }
          if(nodeParentName.equals(this.PRT_YokeStage2))
          {
              if (this._popupMenuSktSectionYokeStage2.getComponentCount() > 1)
                  this._popupMenuSktSectionYokeStage2.remove(1);
              this._popupMenuSktSectionYokeStage2.show(parent, positionX, positionY);
          }
       }

   }
   public String getSelectedNodeName() //Lowest To Part level , not sketch level
   {
       String strLevel = "";
       String nodeName = this._selectedNode.getNodeName();
       String nodeParentName = this._selectedNode.getParentNode().getNodeName();

       if(nodeName.equals(this.SKT_Section) ||
          nodeName.equals(this.SKT_Section1)||
          nodeName.equals(this.SKT_Section2)||
          nodeName.equals(this.SKT_Top))
       {
           strLevel =  nodeParentName;
       }
       else
           strLevel =  nodeName;
       return strLevel;
   }

   public String getSelectedNodeNameLowestLevel()
   {
       if (this._selectedNode != null)
       {
           return this._selectedNode.getNodeName();
       }
       else
           return null;
   }

   public void setSelectedNode(int type, int sectionNum)
   {
       switch (type)
       {
           case DefineSystemConstant.TYPE_TOP:

               break;

           case DefineSystemConstant.TYPE_CAP:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionCap;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionCap2;
               break;
           case DefineSystemConstant.TYPE_TRANSITION:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionTransition;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionTransition2;
               break;
           case DefineSystemConstant.TYPE_DIAPHRAGM:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionDiaphragm;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionDiaphragm2;
               break;
           case DefineSystemConstant.TYPE_SURROUND:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionSurround;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionSurround2;
               break;
           case DefineSystemConstant.TYPE_COIL:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionCoil;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionCoil2;
               break;

           case DefineSystemConstant.TYPE_YOKEBASE:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionYokeBase;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionYokeBase2;
               break;

           case DefineSystemConstant.TYPE_YOKESTAGE1:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionYokeStage1;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionYokeStage1_2;
               break;
           case DefineSystemConstant.TYPE_YOKESTAGE2:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionYokeStage2;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionYokeStage2_2;
               break;




           case DefineSystemConstant.TYPE_MAGNET:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionMagnet;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionMagnet2;
               break;
           case DefineSystemConstant.TYPE_TOPPLATE:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionTopPlate;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionTopPlate2;
               break;
           case DefineSystemConstant.TYPE_MAGNETTOP:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionMagnetTop;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionMagnetTop2;
               break;
           case DefineSystemConstant.TYPE_MAGNETOUTER:
               if (sectionNum == DefineSystemConstant.XZView)
                   this._selectedNode = this._nodeSktSectionMagnetOuter;
               else if (sectionNum == DefineSystemConstant.YZView)
                   this._selectedNode = this._nodeSktSectionMagnetOuter2;
               break;


       }
   }
   public void highLightSelectedNode()
   {
       this.highLightNode(this._selectedNode);
   }



}


