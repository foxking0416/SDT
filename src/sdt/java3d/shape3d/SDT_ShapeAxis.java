package sdt.java3d.shape3d;

import javax.media.j3d.*;
import javax.vecmath.*;

import java.awt.*;

import com.sun.j3d.utils.geometry.*;
import sdt.java3d.BTGgroup.*;

public class SDT_ShapeAxis extends SDT_BranchGroup
{
     private Point3f origin3f;
     private Font3D f3d;
     private float boundingBox[];
     private double modelLength;
     private SDT_TransformGroup XTextTg;
     private SDT_TransformGroup XConeTg;
     private SDT_TransformGroup YTextTg;
     private SDT_TransformGroup YConeTg;
     private SDT_TransformGroup ZTextTg;
     private SDT_TransformGroup ZConeTg;
     private Text3D txt;

     private Point3f secondpt;
     private LineArray axisXLines;
     private LineArray axisYLines;
     private LineArray axisZLines;
     private SDT_Shape lineX;
     private SDT_Shape lineY;
     private SDT_Shape lineZ;
     private Color3f color3f;
     private SDT_TransformGroup tg1;

    public SDT_ShapeAxis(float boundingBox[])
     {
         f3d = null;
         color3f = new Color3f(0.0F, 0.0F, 0.0F);
         this.boundingBox = boundingBox;
         origin3f = new Point3f(0.0f, 0.0f, 0.0f);
         modelLength = Math.sqrt(Math.pow(boundingBox[0] - boundingBox[3], 2D) + Math.pow(boundingBox[1] - boundingBox[4], 2D) + Math.pow(boundingBox[2] - boundingBox[5], 2D));
         Font font;
         if(modelLength < (double)1)
         {
             font = new Font("fontName", 0, 4);
         }
         else
         {
             font = new Font("fontName", 0, (int) modelLength * 3);
         }
         f3d = new Font3D(font, new FontExtrusion());
     }

     public TransformGroup CreateAxisNode()
     {
         tg1 = new SDT_TransformGroup();
         tg1.addChild(CreateGeometry());
         if(modelLength > 0.02D)
         {
             tg1.addChild(XTextTg);
             tg1.addChild(YTextTg);
             tg1.addChild(ZTextTg);
         }
         tg1.addChild(XConeTg);
         tg1.addChild(YConeTg);
         tg1.addChild(ZConeTg);
         return tg1;
     }

     public void SetScale(double scale)
     {
         Transform3D temp = new Transform3D();
         tg1.getTransform(temp);
         double initScale = temp.getScale();
         initScale = scale * initScale;
         temp.setScale(initScale);
         tg1.setTransform(temp);
     }

     public TransformGroup CreateGeometry()
     {
         int props[] =
         {
             17, 8, 0, 18, 1
         };
         axisXLines = new LineArray(2, 5);
         SetCapability(axisXLines, props);
         axisYLines = new LineArray(2, 5);
         SetCapability(axisYLines, props);
         axisZLines = new LineArray(2, 5);
         SetCapability(axisZLines, props);
         axisXLines.setCoordinate(0, origin3f);
         secondpt = new Point3f();
         secondpt.x = ((Tuple3f) (origin3f)).x + (boundingBox[3] - boundingBox[0]) / (float)2;
         secondpt.y = ((Tuple3f) (origin3f)).y;
         secondpt.z = ((Tuple3f) (origin3f)).z;
         axisXLines.setCoordinate(1, secondpt);
         SetTextX();
         AddXArrow();
         Color3f red = new Color3f(1.0F, 0.0F, 0.0F);
         axisXLines.setColor(0, red);
         axisXLines.setColor(1, red);
         Appearance a = new Appearance();
         axisYLines.setCoordinate(0, origin3f);
         secondpt.x = ((Tuple3f) (origin3f)).x;
         secondpt.y = ((Tuple3f) (origin3f)).y + (boundingBox[4] - boundingBox[1]) / (float)2;
         secondpt.z = ((Tuple3f) (origin3f)).z;
         axisYLines.setCoordinate(1, secondpt);
         SetTextY();
         AddYArrow();
         Color3f green = new Color3f(0.0F, 1.0F, 0.0F);
         axisYLines.setColor(0, green);
         axisYLines.setColor(1, green);
         axisZLines.setCoordinate(0, origin3f);
         secondpt.x = ((Tuple3f) (origin3f)).x;
         secondpt.y = ((Tuple3f) (origin3f)).y;
         secondpt.z = ((Tuple3f) (origin3f)).z + (boundingBox[5] - boundingBox[2]) / (float)2;
         axisZLines.setCoordinate(1, secondpt);
         SetTextZ();
         AddZArrow();
         Color3f blue = new Color3f(0.0F, 0.0F, 1.0F);
         axisZLines.setColor(0, blue);
         axisZLines.setColor(1, blue);
         SDT_TransformGroup tg = new SDT_TransformGroup();
         lineX = new SDT_Shape(axisXLines);
         lineX.setCapability(13);
         lineX.setCapability(15);
         lineX.setPickable(false);
         lineY = new SDT_Shape(axisYLines);
         lineY.setCapability(13);
         lineY.setCapability(15);
         lineY.setPickable(false);
         lineZ = new SDT_Shape(axisZLines);
         lineZ.setCapability(13);
         lineZ.setCapability(15);
         lineZ.setPickable(false);
         Geometry geomX = lineX.getGeometry();
         geomX.setCapability(18);
         int shapeProps[] = {
             14, 15, 12, 7, 5, 0, 1
         };
         SetCapability(lineX, shapeProps);
         Geometry geomY = lineY.getGeometry();
         geomY.setCapability(18);
         SetCapability(lineY, shapeProps);
         Geometry geomZ = lineZ.getGeometry();
         geomZ.setCapability(18);
         SetCapability(lineZ, shapeProps);
         tg.addChild(lineX);
         tg.addChild(lineY);
         tg.addChild(lineZ);
         return tg;
     }

     private void AddXArrow()
     {
         Transform3D coneT3d = new Transform3D();
         coneT3d.set(2.0D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         Vector3d zvec = new Vector3d(0.0D, 0.0D, 1.0D);
         AxisAngle4d ax = new AxisAngle4d(zvec, -Math.PI/2);
         coneT3d.setRotation(ax);

         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(new Color3f(1.0F, 0.0F, 0.0F));
         app.setColoringAttributes(col);

         XConeTg = new SDT_TransformGroup(coneT3d);
         Cone cone = new Cone((float)(modelLength / (double)80), (float)(modelLength / (double)30), 111, new Appearance());
         cone.setAppearance(app);
         cone.setPickable(false);
         XConeTg.addChild(cone);
     }

     private void AddYArrow()
     {
         Transform3D coneT3d = new Transform3D();
         coneT3d.set(2.0D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         Vector3d xvec = new Vector3d(1.0D, 0.0D, 0.0D);
         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(new Color3f(0.0F, 1.0F, 0.0F));
         app.setColoringAttributes(col);
         AxisAngle4d ax = new AxisAngle4d(xvec, 0.0);
         coneT3d.setRotation(ax);
         YConeTg = new SDT_TransformGroup(coneT3d);
         Cone cone = new Cone((float)(modelLength / (double)80), (float)(modelLength / (double)30), 111, new Appearance());
         cone.setAppearance(app);
         cone.setPickable(false);
         YConeTg.addChild(cone);
     }

     private void AddZArrow()
     {
         Transform3D coneT3d = new Transform3D();
         coneT3d.set(2.0D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         Vector3d xvec = new Vector3d(1.0D, 0.0D, 0.0D);
         AxisAngle4d ax = new AxisAngle4d(xvec, Math.PI/2);
         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(new Color3f(0.0F, 0.0F, 1.0F));
         app.setColoringAttributes(col);
         coneT3d.setRotation(ax);
         ZConeTg = new SDT_TransformGroup(coneT3d);
         Cone cone = new Cone((float)(modelLength / (double)80), (float)(modelLength / (double)30), 111, new Appearance());
         cone.setAppearance(app);
         cone.setPickable(false);
         ZConeTg.addChild(cone);
     }

     private void SetTextX()
     {
         txt = new Text3D(f3d, new String("X"), new Point3f(0.0F, 0.0F, 0.0F));
         OrientedShape3D textShapeX = new OrientedShape3D();
         textShapeX.setCapability(14);
         textShapeX.setCapability(15);
         textShapeX.setCapability(12);
         textShapeX.setCapability(13);
         textShapeX.setAlignmentMode(1);
         textShapeX.setGeometry(txt);
         textShapeX.setPickable(false);
         textShapeX.setRotationPoint(secondpt);
         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(color3f);
         app.setColoringAttributes(col);
         textShapeX.setAppearance(app);
         Transform3D textT3d = new Transform3D();
         if(modelLength < (double)1)
             textT3d.set(0.01D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         else
             textT3d.set(0.05D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         XTextTg = new SDT_TransformGroup(textT3d);
         XTextTg.addChild(textShapeX);
     }

     private void SetTextY()
     {
         txt = new Text3D(f3d, new String("Y"), new Point3f(0.0F, 0.0F, 0.0F));
         OrientedShape3D textShapeY = new OrientedShape3D();
         textShapeY.setAlignmentMode(1);
         textShapeY.setCapability(14);
         textShapeY.setCapability(15);
         textShapeY.setCapability(12);
         textShapeY.setCapability(13);
         textShapeY.setGeometry(txt);
         textShapeY.setPickable(false);
         textShapeY.setRotationPoint(secondpt);
         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(color3f);
         app.setColoringAttributes(col);
         textShapeY.setAppearance(app);
         Transform3D textT3d = new Transform3D();
         if(modelLength < (double)1)
             textT3d.set(0.01D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         else
             textT3d.set(0.05D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         YTextTg = new SDT_TransformGroup(textT3d);
         YTextTg.addChild(textShapeY);
     }

     private void SetTextZ()
     {
         txt = new Text3D(f3d, new String("Z"), new Point3f(0.0F, 0.0F, 0.0F));
         OrientedShape3D textShapeZ = new OrientedShape3D();
         textShapeZ.setCapability(14);
         textShapeZ.setCapability(15);
         textShapeZ.setCapability(12);
         textShapeZ.setCapability(13);
         textShapeZ.setAlignmentMode(1);
         textShapeZ.setGeometry(txt);
         textShapeZ.setPickable(false);
         textShapeZ.setRotationPoint(secondpt);
         Appearance app = new Appearance();
         ColoringAttributes col = new ColoringAttributes();
         col.setColor(color3f);
         app.setColoringAttributes(col);
         textShapeZ.setAppearance(app);
         Transform3D textT3d = new Transform3D();
         if(modelLength < (double)1)
             textT3d.set(0.01D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         else
             textT3d.set(0.05D, new Vector3d(new Vector3f(((Tuple3f) (secondpt)).x, ((Tuple3f) (secondpt)).y, ((Tuple3f) (secondpt)).z)));
         ZTextTg = new SDT_TransformGroup(textT3d);
         ZTextTg.addChild(textShapeZ);
     }

     private void SetCapability(LineArray lineArray, int props[])
     {
         for(int i = 0; i < props.length; i++)
             lineArray.setCapability(props[i]);
     }

     private void SetCapability(Shape3D shape, int props[])
     {
         for(int i = 0; i < props.length; i++)
             shape.setCapability(props[i]);
     }

     public void Destroy()
     {
         origin3f = null;
         f3d = null;
         boundingBox = null;
         tg1 = null;
         txt = null;
         XConeTg = null;
         YConeTg = null;
         ZConeTg = null;
         XTextTg = null;
         YTextTg = null;
         ZTextTg = null;
         secondpt = null;
         axisXLines = null;
         axisYLines = null;
         axisZLines = null;
         lineX.setGeometry(null);
         lineX.setAppearance(null);
         lineX = null;
         lineY.setGeometry(null);
         lineY.setAppearance(null);
         lineY = null;
         lineZ.setGeometry(null);
         lineZ.setAppearance(null);
         lineZ = null;
     }



}
