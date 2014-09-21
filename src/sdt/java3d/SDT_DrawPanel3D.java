package sdt.java3d;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;
import sdt.define.*;
import sdt.framework.*;
import sdt.java3d.BTGgroup.*;
import sdt.java3d.shape3d.*;


public class SDT_DrawPanel3D extends Canvas3D implements MouseListener, MouseMotionListener,MouseWheelListener,KeyListener,DefineSystemConstant
{
    public  SDT_BranchGroup          rootBG,backgroundBG;
    private Background              background;
    public  SimpleUniverse          simpleU            = null;
    public   SDT_TransformGroup       globalTG,axisTG;
    public   SDT_Object               meshTG;
    public   SDT_Object               wireframeTG;

    public   SDT_TransformGroup       endmillTG;
    private  SDT_TransformGroup       light1TG, light2TG,light3TG;

    private     int             _mouseDragedState   = this.MOUSE_NONE;
    private     int             _keyDownState       = this.KEY_NONE;
    private     Cursor          cursorMove          = new Cursor(Cursor.MOVE_CURSOR);
    private     Cursor          cursorHand          = new Cursor(Cursor.HAND_CURSOR);
    private     Cursor          cursorScale         = new Cursor(Cursor.S_RESIZE_CURSOR);

    private     Point           mousePosition;
    private     Point           mouseMoveStart;
    private     Point           mouseMoveEnd;

    private     double globalMaxPt[],globalMinPt[];
    private Color3f BGcolor;

    private Vector3d             tempVector       = new Vector3d(0.0,0.0,0.0);

  //  private Vector3d             axisTranslate    = new Vector3d(0.8, -0.55, 0.0);
    private Transform3D          mouseT3D2        = new Transform3D();

    private Transform3D          t3DBuffer        = new Transform3D();

    private int                  half_w, half_h;


    private double               factor;                          // the factor of Java 3D's coordinate
    private  double              zoomFactor = 1.0;                // the factor for zoom for View
  //  private boolean GAxis;
 //   private boolean OAxis;
    // for dynamic draw and pick
    private Point                start  ,end;

    private DirectionalLight     lightD1,lightD2,lightD3;
    public  Vector3f             lightDir,lightDir2,lightDir3;
    private   Color3f                colorLight;

    private SDT_System              _system;
    private int                     _showType = DefineSystemConstant.VIEW_SHADINGWITHEDGE;

    public  SDT_DrawPanel3D(SDT_System system)
    //-----------------------------------------------------//
    //   this constructor is design for main framework     //
    //-----------------------------------------------------//
    {
       // super(config);//父類別建構引數
       super(SimpleUniverse.getPreferredConfiguration());//父類別建構引數
       this._system = system;

        // SimpleUniverse is a Convenience Utility class
        this.simpleU = new SimpleUniverse(this);
        this.getView().setProjectionPolicy(View.PARALLEL_PROJECTION);//在此可以跟改平行或透視投影
        //this.getView().setProjectionPolicy(View.PERSPECTIVE_PROJECTION);//在此可以跟改平行或透視投影

        this.simpleU.getViewingPlatform().setNominalViewingTransform();

        this.simpleU.addBranchGraph(this.createSceneGraph());

        this.setBackground();




        //加入滑鼠的動作
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void SetProjectionPolicy(int projectionType)
    {
        this.getView().setProjectionPolicy(projectionType);
    }

    public int GetProjectionPolicy()
    {
        return this.getView().getProjectionPolicy();
    }

    public BranchGroup createSceneGraph()
    {

        Vector3d               axisTranslate    =   null;
        BoundingSphere         bounds           =   null;

        rootBG      = new SDT_BranchGroup();   // "Create the root of the branch graph"
        bounds      = new BoundingSphere(new Point3d(), Double.MAX_VALUE);

        rootBG.setBoundsAutoCompute(false);
        rootBG.setBounds(bounds);

        this.globalTG = new SDT_TransformGroup(); // "TG for global"
        this.meshTG = new SDT_Object();
        this.wireframeTG = new SDT_Object();

        rootBG.addChild(globalTG);
        globalTG.addChild(meshTG);
         globalTG.addChild(wireframeTG);

        SDT_ShapeAxis axis = new SDT_ShapeAxis(new float[]
                                           {0.0f, 0.0f, 0.0f, 50.0f, 50.0f, 50.0f}); // "TG for Axis"

        axisTranslate    = new Vector3d(0.8, -0.65, 0.0);
        this.t3DBuffer.setTranslation(axisTranslate);
        axisTG = (SDT_TransformGroup)axis.CreateAxisNode();
        axisTG.setTransform(this.t3DBuffer);
        rootBG.addChild(this.axisTG);

        light1TG = new SDT_TransformGroup(); //"TG for Light"
        light2TG = new SDT_TransformGroup();
        light3TG = new SDT_TransformGroup();

        this.SetLight(rootBG);

        return rootBG;
    }

    private void SetLight(SDT_BranchGroup rootBranch)
    {
        //initial the light direction
        BoundingSphere         bounds           =   null;
        bounds = new BoundingSphere(new Point3d(), Double.MAX_VALUE);
        lightDir   = new Vector3f(1.0f, 0.0f, 0.2f);
        lightDir2  = new Vector3f(-1.0f, 1.0f, -5.0f);
        lightDir3   = new Vector3f(0.0f, 0.0f, 1.0f);
        colorLight = new Color3f(0.8f, 0.8f, 0.8f);

        lightD1 = new DirectionalLight();
        lightD1.setInfluencingBounds(bounds);
        lightD1.setColor(colorLight);
        lightDir.normalize();
        lightD1.setDirection(lightDir);

        lightD2 = new DirectionalLight();
        lightD2.setInfluencingBounds(bounds);
        lightD2.setColor(colorLight);
        lightDir2.normalize();
        lightD2.setDirection(lightDir2);

        lightD3 = new DirectionalLight();
        lightD3.setInfluencingBounds(bounds);
        lightD3.setColor(colorLight);
        lightDir3.normalize();
        lightD3.setDirection(lightDir3);

        lightD1.setCapability(DirectionalLight.ALLOW_STATE_WRITE);
        lightD2.setCapability(DirectionalLight.ALLOW_STATE_WRITE);
        lightD3.setCapability(DirectionalLight.ALLOW_STATE_WRITE);

        light1TG.addChild(lightD1);
        rootBranch.addChild(light1TG);
        light2TG.addChild(lightD2);
        rootBranch.addChild(light2TG);
        light3TG.addChild(lightD3);
        rootBranch.addChild(light3TG);
    }


    private void setBackground()
    {
        BufferedImage           imageBuffer ;
        ImageComponent2D        backImage;
        BoundingSphere          bounds;
        Color                   colorUp;
        Color                   colorDown;

        colorUp     = this._system.getDataManager().getColorBackgroundUp();// new Color(0,27,55);
        colorDown   = this._system.getDataManager().getColorBackgroundDown();//new Color(0,74,105);
        background  = new Background();
        backgroundBG= new SDT_BranchGroup();
        imageBuffer = this.createBackground(colorUp,colorDown);
        backImage   = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA ,imageBuffer,true,false);
        bounds      = new BoundingSphere(new Point3d(), Double.MAX_VALUE);


        background.setCapability(Background.ALLOW_COLOR_WRITE);
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
        backImage.setCapability(ImageComponent2D.ALLOW_IMAGE_WRITE);

        background.setImage(backImage);
        background.setApplicationBounds(bounds);

        backgroundBG.addChild(background);
        this.simpleU.addBranchGraph(this.backgroundBG);
    }
    public void resetBackground()
    {
        this.backgroundBG.detach();
        this.setBackground();
    }




    private BufferedImage createBackground(Color colorUp, Color colorDown)
    {
        int                     backWidth,backHeight,windowCenterX,windowCenterY;
        GradientPaint           back  ;
        Rectangle2D.Double      rect ;
        BufferedImage           imageBuffer ;
        Graphics2D              g2D;


        backWidth       = Toolkit.getDefaultToolkit().getScreenSize().width;
        backHeight      = Toolkit.getDefaultToolkit().getScreenSize().height;
        windowCenterX   = 400;//theSystem.GetFrame().WIDTH/2 +theSystem.GetFrame().getLocation().x;
        windowCenterY   = 400;//theSystem.GetFrame().HEIGHT/2 +theSystem.GetFrame().getLocation().y;
        rect            = new Rectangle2D.Double();
        imageBuffer     = new BufferedImage(backWidth, backHeight, BufferedImage.TYPE_INT_ARGB);
        back            = new GradientPaint(windowCenterX, 0,  colorUp,//windowCenterY + 100
                                            windowCenterX, 500,  colorDown, false); //windowCenterY + 200
        g2D             = imageBuffer.createGraphics();        // Context for buffered image // Set best alpha interpolation quality

        g2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                             RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        // Clear image with transparent alpha by drawing a rectangle
        rect.setFrame(0,0,backWidth,backHeight);
        g2D.setPaint(back);
        g2D.fill(rect);
        g2D.setComposite(AlphaComposite.SrcOver);
        g2D.dispose();
        return imageBuffer;                                   // Return the finished image
    }


    public Color3f getBackGround()
    {
        return BGcolor;
    }



    public void RemoveShape()
    {

        this.rootBG.detach();

        //this.meshTG.removeAllChildren();
        //this.wireframeTG.removeAllChildren();

        //this.globalTG.removeAllChildren();
        this.rootBG.removeChild(this.globalTG);
        this.globalTG = new SDT_TransformGroup(); // "TG for global"
        this.wireframeTG = new SDT_Object();
        this.meshTG = new SDT_Object();
        this.globalTG.addChild(this.meshTG);
        this.globalTG.addChild(this.wireframeTG);
        this.rootBG.addChild(this.globalTG);

        this.simpleU.addBranchGraph(rootBG);
    }
    public void ShowOnlyMesh()
    {
          this.rootBG.detach();
          this.globalTG.removeAllChildren();
          this.globalTG.addChild(this.meshTG);
          this.simpleU.addBranchGraph(rootBG);
    }

    public void ShowOnlyWireframe()
    {
        this.rootBG.detach();
        this.globalTG.removeAllChildren();
        this.globalTG.addChild(this.wireframeTG);
        this.simpleU.addBranchGraph(rootBG);
    }

    public void ShowAll()
    {
        this.rootBG.detach();
        this.globalTG.removeAllChildren();
        this.globalTG.addChild(this.meshTG);
        this.globalTG.addChild(this.wireframeTG);
        this.simpleU.addBranchGraph(rootBG);
    }

    public void SetEEArray(SDT_Array3DEdge eeArray )
    {
        System.out.println("Adding Curve Data To 3D Canvas....." + eeArray.Size());
        this.wireframeTG.removeAllChildren();
        if(eeArray != null)
        for (int i = 0; i < eeArray.Size(); i++)
        {
            SDT_3DEdge ee = eeArray.get(i);
            this.wireframeTG.AddObjEdgeCurve(ee, Color.BLACK);
        }

    }
    public void DetachRoot()
    {
        this.rootBG.detach();

    }
    public void AttachRoot()
    {
        this.simpleU.addBranchGraph(rootBG);


    }


    public void SetMeshArray(SDT_Array3DMesh meshArray,Color color)
    {
        System.out.println("Adding Mesh Data To 3D Canvas....." + meshArray.Size());
        this.meshTG.removeAllChildren();

        for (int i = 0; i < meshArray.Size(); i++)
        {
            SDT_3DMesh mesh = meshArray.get(i);
            this.meshTG.AddObjMesh(mesh,color);
        }
    }
    public void AddMeshArray(SDT_Array3DMesh meshArray,Color color)
    {
        System.out.println("Adding Mesh Data To 3D Canvas....."+ meshArray.Size());
        for (int i = 0; i < meshArray.Size(); i++)
        {
            SDT_3DMesh mesh = meshArray.get(i);
            this.meshTG.AddObjMesh(mesh, color);
        }
    }


    public void mouseMoved(MouseEvent e)
    {
        mousePosition = new Point(e.getX(), e.getY());
        this.requestFocus();
        if (mouseMoveStart == null || mouseMoveEnd == null)
            return;
        this.mouseMoveStart.setLocation(this.mouseMoveEnd);
        this.mouseMoveEnd.setLocation(e.getPoint());


        this.end = this.mouseMoveEnd;
        this.start = this.mouseMoveStart;
     /*   if (this.MouseMoveState != DefineSystemConstant.MOUSE_NONE)
        {
            //    System.out.println("Mouse Move Test");
            switch (this.MouseMoveState)
            {
                case DefineSystemConstant.MOUSE_TRANSLATE:
                    this.SetTranslate();
                    break;
                case DefineSystemConstant.MOUSE_SCALE:
                    this.SetZoom();
                    break;
                case DefineSystemConstant.MOUSE_ROTATE:
                    this.SetRotation();
                    break;

            }

        }*/
        this.requestFocus();

    }

    public void mouseDragged(MouseEvent e)
    {
        mousePosition = new Point(e.getX(), e.getY());
        this.start.setLocation(this.end);
        this.end.setLocation(e.getPoint());
        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {
            switch (this._system.getModeler().getControlType())
            {
                case DefineSystemConstant.MOUSE_TRANSLATE:
                    this.setCursor(this.cursorMove);
                    SetTranslate();
                    break;
                case DefineSystemConstant.MOUSE_SCALE:
                    this.setCursor(this.cursorScale);
                    SetZoom();
                    break;
                case DefineSystemConstant.MOUSE_ROTATE:
                    this.setCursor(this.cursorHand);
                    this.SetRotationArbitary();
                    break;
            }
        }



        if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {

            switch (this._mouseDragedState)
            {
                case DefineSystemConstant.MOUSE_TRANSLATE:
                    this.setCursor(this.cursorMove);
                    SetTranslate();
                    break;
                case DefineSystemConstant.MOUSE_SCALE:
                    this.setCursor(this.cursorScale);
                    SetZoom();
                    break;
                case DefineSystemConstant.MOUSE_ROTATE:
                    this.setCursor(this.cursorHand);
                    this.SetRotationArbitary();
                    break;
                case DefineSystemConstant.MOUSE_ROTATEZ:
                    this.setCursor(this.cursorHand);
                    this.SetRotationZ();
                    break;
            }


        }

    }





    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseReleased(MouseEvent e)
    {
        if ((e.getModifiers() & e.BUTTON1_MASK) != 0)
        {
            this.setCursor(null);
        }



        if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {
  //          this.Mouse2DragState = this.MOUSE_NONE;
            this.setCursor(null);
            this._mouseDragedState = this.MOUSE_NONE;
        }


    }

    public void mousePressed(MouseEvent e)
    {
        this.start = new Point(e.getPoint() );
        this.end   = new Point(e.getPoint() );
        this.globalTG.getTransform(this.mouseT3D2);
        if ((e.getModifiers() & e.BUTTON2_MASK) != 0)
        {

           if(_keyDownState == this.KEY_CTRL)
           {
               System.out.println("test_move");
               this.setCursor(this.cursorMove);
               _mouseDragedState = this.MOUSE_TRANSLATE;

           }
           else if(_keyDownState == this.KEY_SHIFT)
           {
               System.out.println("test_shift");
               this.setCursor(this.cursorScale);
               _mouseDragedState = this.MOUSE_SCALE;
           }
           else if(_keyDownState == this.KEY_ALT)
           {
               System.out.println("test_alt");
               this.setCursor(this.cursorHand);
               _mouseDragedState = this.MOUSE_ROTATEZ;
           }
           else
           {
               System.out.println("test_rotate");
                this.setCursor(this.cursorHand);
               _mouseDragedState = this.MOUSE_ROTATE;
           }

    //        this.Mouse2DragState = MOUSE_TRANSLATE;
        }
       /* if((e.getModifiers() == e.BUTTON1_MASK) && (this.Mouse2DragState == MOUSE_TRANSLATE || MOUSE_TRANSLATE == MOUSE_SCALE))
        {
            this.Mouse2DragState = this.MOUSE_ROTATE;
        }*/



    }

    public void SetSize(Dimension d)
    {
        super.setSize(d);

        this.half_w = this.getWidth() / 2;
        this.half_h = this.getHeight() / 2;
        // window scale change

        this.factor = 1.0 / this.half_w;

        Transform3D t3d = new Transform3D();
        this.globalTG.getTransform(t3d);
        t3d.setScale(this.factor);
        this.globalTG.setTransform(t3d);

        this.axisTG.getTransform(t3d);
        t3d.setScale(this.factor);
        this.axisTG.setTransform(t3d);


    }
    private void jbInit() throws Exception
    {
        this.addKeyListener(this) ;

    }

    private void SetTranslate()
    {
        Vector3d vect = new Vector3d();
        Vector3d dVect = new Vector3d();
        double dx = this.end.x - this.start.x;
        double dy = -(this.end.y - this.start.y);
        double dz = 0.0;
        dVect.set(dx * this.factor, dy * this.factor, dz * this.factor);

        this.globalTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.get(vect);
        vect.add(dVect);
        this.t3DBuffer.setTranslation(vect);
        this.globalTG.setTransform(this.t3DBuffer);
    }

    private void SetRotationZ()
    {
        double dx = (this.end.x - this.start.x) * Math.PI / 180.0; //??
        double dy = (this.end.y - this.start.y) * Math.PI / 180.0;

        Matrix3d rotate = new Matrix3d();
        Matrix3d m3d = new Matrix3d();

        this.globalTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.get(m3d);
        double r = 0.8 * this.half_h;
        if (this.half_h > this.half_w)
        {
            r = 0.8 * this.half_w;
        }
        double dist = Math.sqrt(Math.pow(this.end.x - this.half_w, 2.0) + Math.pow(this.end.y - this.half_h, 2.0));

        Transform3D ms = new Transform3D();
        Transform3D ms2 = new Transform3D();

            ms.rotZ(dx - dy);

        this.t3DBuffer.mul(ms, this.t3DBuffer);
        this.t3DBuffer.get(m3d);

        this.globalTG.setTransform(this.t3DBuffer);
        this.axisTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.setRotation(m3d);
        this.axisTG.setTransform(this.t3DBuffer);

    }
    private void SetRotationArbitary()
    {
        double dx = (this.end.x - this.start.x) * Math.PI / 180.0; //??
        double dy = (this.end.y - this.start.y) * Math.PI / 180.0;

        Matrix3d rotate = new Matrix3d();
        Matrix3d m3d = new Matrix3d();

        this.globalTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.get(m3d);
        double r = 0.8 * this.half_h;
        if (this.half_h > this.half_w)
        {
            r = 0.8 * this.half_w;
        }
        double dist = Math.sqrt(Math.pow(this.end.x - this.half_w, 2.0) + Math.pow(this.end.y - this.half_h, 2.0));

        Transform3D ms = new Transform3D();
        Transform3D ms2 = new Transform3D();

            ms.rotX(dy);
            ms2.rotY(dx);
            ms.mul(ms2, ms);

        this.t3DBuffer.mul(ms, this.t3DBuffer);
        this.t3DBuffer.get(m3d);

        this.globalTG.setTransform(this.t3DBuffer);
        this.axisTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.setRotation(m3d);
        this.axisTG.setTransform(this.t3DBuffer);
    }
    /**
     *  this function is called by mouseDragged
     */

    public void SetZoom()
    {
        double s = 1.0 + 2.0 * (this.end.y - this.start.y) / (this.half_h * 2.0);
        this.SetZoom(s);

    }
    /**
     *  This function is called by mouseWheelMove
     * @param scale double ZoomFactor *= scale
     */
    public void SetZoom(double scale)
    {

        this.globalTG.getTransform(this.t3DBuffer);
        this.zoomFactor *= scale;

        Transform3D ms = new Transform3D();
        ms.setScale(scale);
        this.t3DBuffer.mul(ms, this.t3DBuffer);
        this.globalTG.setTransform(this.t3DBuffer);
    }




    public void SetFititAll()
    {

        double minX, minY, minZ, maxX, maxY, maxZ;
        Transform3D t3d;

        t3d = new Transform3D();

        minX = 999999999;
        minY = 999999999;
        minZ = 999999999;
        maxX = -999999999;
        maxY = -999999999;
        maxZ = -999999999;

        globalMaxPt = this.meshTG.max;
        globalMinPt = this.meshTG.min;

        if(globalMaxPt[0] - globalMinPt[0] < 10E-3)
            return;

        double scale = 2;
        double x = (globalMaxPt[0] + globalMinPt[0]) / 2.0;
        double y = (globalMaxPt[1] + globalMinPt[1]) / 2.0;
        double z = (globalMaxPt[2] + globalMinPt[2]) / 2.0;
        Point3d rotCenter = new Point3d(x, y, z);

        while ((scale - 1 <= -10e-3) || (scale - 1 >= 10e-3) || (minX + maxX >= 10e-3) || (minX + maxX <= -10e-3))
        {

            this.globalTG.getTransform(t3d);
            //this.SetGlobalBoundingBox();

            double[] maxPt2d =
                               { -1, -1, -1};
            double[] minPt2d =
                               {1, 1, 1};

            this.FindBoundingBoxIn2D(maxPt2d, minPt2d, t3d);

            minX = minPt2d[0];
            minY = minPt2d[1];
            minZ = minPt2d[2];
            maxX = maxPt2d[0];
            maxY = maxPt2d[1];
            maxZ = maxPt2d[2];

            double oldDiffX = maxX - minX;
            double oldDiffY = maxY - minY;
            double oldDiffZ = maxZ - minZ;
            double oldCencterX = minX + oldDiffX / 2.0;
            double oldCencterY = minY + oldDiffY / 2.0;
            double oldCencterZ = minZ + oldDiffZ / 2.0;

            double scaleX = 2.0 * 0.85 / oldDiffX;
            double scaleY = 2.0 * 0.75 / oldDiffY;

            if (scaleX > scaleY)
                scaleX = scaleY; //以放大倍數較小的為主
            scale = scaleX;

            double step = 10.0;

            scaleX = Math.pow(scaleX, 1.0 / step);
            oldCencterX = oldCencterX / step;
            oldCencterY = oldCencterY / step;
            oldCencterZ = oldCencterZ / step;

            Vector3d v3d = new Vector3d();
            t3d.get(v3d);

            this.SetZoomAndTranslte(scaleX, v3d.x - oldCencterX, v3d.y - oldCencterY, v3d.z - oldCencterZ);
        }

    }

    private void SetZoomAndTranslte(double s, double movX, double movY, double movZ)
    {
        Vector3d origin = new Vector3d();
        Vector3d v3d = new Vector3d();

        this.globalTG.getTransform(this.t3DBuffer);
        this.t3DBuffer.get(v3d);

        this.zoomFactor *= s;
        this.t3DBuffer.setScale(this.zoomFactor * this.factor);
        this.globalTG.setTransform(this.t3DBuffer);

        this.globalTG.getTransform(this.t3DBuffer);

        double disX = s * movX - v3d.x;
        double disY = s * movY - v3d.y;
        double disZ = s * movZ - v3d.z;
        Vector3d diffV3d = new Vector3d(disX, disY, disZ);

        v3d.add(diffV3d);
        this.t3DBuffer.setTranslation(v3d);
        this.globalTG.setTransform(this.t3DBuffer);

    }

    private void FindBoundingBoxIn2D(double[] maxPt, double[] minPt, Transform3D t3d)
    {
        Point3d v1, v2, v3, v4, v5, v6, v7, v8;

        v1 = new Point3d(this.globalMaxPt[0], this.globalMaxPt[1], this.globalMaxPt[2]);
        v2 = new Point3d(this.globalMaxPt[0], this.globalMaxPt[1], this.globalMinPt[2]);
        v3 = new Point3d(this.globalMaxPt[0], this.globalMinPt[1], this.globalMaxPt[2]);
        v4 = new Point3d(this.globalMinPt[0], this.globalMaxPt[1], this.globalMaxPt[2]);
        v5 = new Point3d(this.globalMaxPt[0], this.globalMinPt[1], this.globalMinPt[2]);
        v6 = new Point3d(this.globalMinPt[0], this.globalMinPt[1], this.globalMaxPt[2]);
        v7 = new Point3d(this.globalMinPt[0], this.globalMaxPt[1], this.globalMinPt[2]);
        v8 = new Point3d(this.globalMinPt[0], this.globalMinPt[1], this.globalMinPt[2]);

        t3d.transform(v1);
        t3d.transform(v2);
        t3d.transform(v3);
        t3d.transform(v4);
        t3d.transform(v5);
        t3d.transform(v6);
        t3d.transform(v7);
        t3d.transform(v8);

        this.ComparePt3d(maxPt, minPt, v1);
        this.ComparePt3d(maxPt, minPt, v2);
        this.ComparePt3d(maxPt, minPt, v3);
        this.ComparePt3d(maxPt, minPt, v4);
        this.ComparePt3d(maxPt, minPt, v5);
        this.ComparePt3d(maxPt, minPt, v6);
        this.ComparePt3d(maxPt, minPt, v7);
        this.ComparePt3d(maxPt, minPt, v8);
    }

    private void ComparePt3d(double[] maxPt, double[] minPt, Point3d v)
    {
        if (minPt[0] > v.x)
            minPt[0] = v.x;
        if (minPt[1] > v.y)
            minPt[1] = v.y;
        if (minPt[2] > v.z)
            minPt[2] = v.z;
        if (maxPt[0] < v.x)
            maxPt[0] = v.x;
        if (maxPt[1] < v.y)
            maxPt[1] = v.y;
        if (maxPt[2] < v.z)
            maxPt[2] = v.z;
    }


    public void keyTyped(KeyEvent e)
    {

    }

    public void keyPressed(KeyEvent e)
    {
        mouseMoveStart = new Point(mousePosition);
        mouseMoveEnd = new Point(mousePosition);
        int keyEventNumber = e.getKeyCode();
        int keyModifier = e.getModifiers();

        switch (keyModifier)
        {
            case KeyEvent.CTRL_MASK:
                System.out.println("key down ctrl");
                _keyDownState = this.KEY_CTRL;
                if(this._mouseDragedState == this.MOUSE_ROTATE)
                {
                    this.setCursor(this.cursorMove);
                    this._mouseDragedState = this.MOUSE_TRANSLATE;
                }
                break;
            case KeyEvent.ALT_MASK:
                System.out.println("key down alt");
                _keyDownState = this.KEY_ALT;
                if(this._mouseDragedState == this.MOUSE_ROTATE)
                {

                    this.setCursor(this.cursorHand);
                  this._mouseDragedState = this.MOUSE_ROTATEZ;
                }

                break;
            case KeyEvent.SHIFT_MASK:
                System.out.println("key down shift");
                _keyDownState = this.KEY_SHIFT;
                if(this._mouseDragedState == this.MOUSE_ROTATE)
                {
                    this.setCursor(this.cursorHand);
                    this._mouseDragedState = this.MOUSE_SCALE;
                }

                break;
        }
       /* switch (keyEventNumber)
        {
            case KeyEvent.VK_F1:

                //   System.out.println("F1");
                this.MouseMoveState = this.MOUSE_TRANSLATE;
                break;
            case KeyEvent.VK_F2:

                // System.out.println("F2");
                this.MouseMoveState = this.MOUSE_SCALE;
                break;
            case KeyEvent.VK_F3:

                // System.out.println("F2");
                this.MouseMoveState = this.MOUSE_ROTATE;
                break;
            case KeyEvent.VK_F4:
                SetFititAll();
                break;
        }*/

    }

    public void keyReleased(KeyEvent e)
    {
         this._keyDownState = this.KEY_NONE;
    }

    public void mouseWheelMoved(MouseWheelEvent e)
    {
       if(e.getWheelRotation() > 0)
       {
           this.SetZoom(1.3);
       }
       else
       {
           this.SetZoom(0.77);
       }
    }

    public void setShowType(int type)
    {
        this._showType = type;
        this.ShowType();
    }
    public int getShowType()
    {
        return this._showType;
    }

    public void ShowType()
    {
        switch (this._showType)
        {
            case DefineSystemConstant.VIEW_SHADING:
                this.ShowOnlyMesh();
                break;
            case DefineSystemConstant.VIEW_SHADINGWITHEDGE:
                this.ShowAll();
                break;
            case DefineSystemConstant.VIEW_WIREFRAME:
                this.ShowOnlyWireframe();
                break;
        }
    }
}
