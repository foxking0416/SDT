package sdt.java3d.shape3d;

import javax.media.j3d.*;

public class SDT_Shape extends Shape3D
{
    private Appearance appear;
    private Appearance pickAppear;
    private Material material;
    private PointAttributes pointAttr;
    private LineAttributes lineAttr;
    private LineAttributes picklineAttr;
    private PointAttributes pickPointAttr;
    private PolygonAttributes polyAttr;
    private RenderingAttributes renderAttr;
    private TextureAttributes textureAttr;
    private TransparencyAttributes transAttr;
    private TransparencyAttributes pickTtransAttr;
    private int materialIndex = 0;

    public static final int MATAluminum = 0;
    public static final int MATBluePlastic = 1;
    public static final int MATCopper = 2;
    public static final int MATGold = 3;
    public static final int MATRedAlloy = 4;
    public static final int MATBlackOnyx = 5;

    public SDT_Shape()
    {
        super();
        this.initAttributes();
    }

    public SDT_Shape(Geometry geom)
    {
        super(geom);
        this.initAttributes();
    }

    public SDT_Shape(Geometry geom, Appearance appear)
    {
        super(geom, appear);

        this.initAttributes();
    }

    private void initAttributes()
    {
        this.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
        this.setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);
        this.setCapability(Shape3D.ALLOW_PICKABLE_READ);
        this.setCapability(Shape3D.ALLOW_PICKABLE_WRITE);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        this.setCapability(Shape3D.ALLOW_APPEARANCE_READ);

        this.setCapability(Shape3D.ALLOW_LOCAL_TO_VWORLD_READ); // 2006.03.01   bula

        appear = new Appearance();
        appear.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_READ);
        appear.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
        appear.setCapability(Appearance.ALLOW_MATERIAL_READ);
        appear.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        appear.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_READ);
        appear.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        appear.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        appear.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        this.setAppearance(appear);

        material = new Material();
        material.setDiffuseColor(0.37f, 0.37f, 0.37f);
        material.setSpecularColor(0.89f, 0.89f, 0.89f);

        material.setShininess(17f);
        material.setCapability(Material.ALLOW_COMPONENT_READ);
        material.setCapability(Material.ALLOW_COMPONENT_WRITE);
        appear.setMaterial(material);

        pointAttr = new PointAttributes(0.5f, true);
        appear.setPointAttributes(pointAttr);

        lineAttr = new LineAttributes(1.5f, LineAttributes.PATTERN_SOLID, true);
        lineAttr.setCapability(LineAttributes.ALLOW_PATTERN_READ);
        lineAttr.setCapability(LineAttributes.ALLOW_PATTERN_WRITE);
        appear.setLineAttributes(lineAttr);

        polyAttr = new PolygonAttributes();
        polyAttr.setCullFace(0);
        polyAttr.setBackFaceNormalFlip(true);
        appear.setPolygonAttributes(polyAttr);

        transAttr = new TransparencyAttributes();
        transAttr.setCapability(TransparencyAttributes.ALLOW_MODE_READ);
        transAttr.setCapability(TransparencyAttributes.ALLOW_MODE_WRITE);
        transAttr.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        transAttr.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        transAttr.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_READ);
        transAttr.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
        transAttr.setTransparency(0.0f);
        transAttr.setTransparencyMode(TransparencyAttributes.NONE);
        appear.setTransparencyAttributes(transAttr);

        renderAttr = new RenderingAttributes();
        renderAttr.setCapability(RenderingAttributes.ALLOW_VISIBLE_READ);
        renderAttr.setCapability(RenderingAttributes.ALLOW_VISIBLE_WRITE);
        renderAttr.setCapability(RenderingAttributes.ALLOW_ALPHA_TEST_VALUE_READ);
        renderAttr.setCapability(RenderingAttributes.ALLOW_ALPHA_TEST_VALUE_WRITE);

        textureAttr = new TextureAttributes();
        textureAttr.setCapability(TextureAttributes.ALLOW_MODE_READ);
        textureAttr.setCapability(TextureAttributes.ALLOW_MODE_WRITE);
        textureAttr.setCapability(TextureAttributes.ALLOW_BLEND_COLOR_READ);
        textureAttr.setCapability(TextureAttributes.ALLOW_BLEND_COLOR_WRITE);
        appear.setTextureAttributes(textureAttr);

        pickTtransAttr = new TransparencyAttributes();
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_MODE_READ);
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_MODE_WRITE);
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_VALUE_READ);
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_READ);
        pickTtransAttr.setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE);
        pickTtransAttr.setTransparency(0.5f);
        pickTtransAttr.setTransparencyMode(TransparencyAttributes.NONE);

        picklineAttr = new LineAttributes(5f, LineAttributes.PATTERN_SOLID, true);
        picklineAttr.setCapability(LineAttributes.ALLOW_PATTERN_READ);
        picklineAttr.setCapability(LineAttributes.ALLOW_PATTERN_WRITE);

        pickPointAttr = new PointAttributes(7.0f, true);

        pickAppear = new Appearance();
        pickAppear.setPolygonAttributes(polyAttr);
        pickAppear.setLineAttributes(picklineAttr);
        pickAppear.setPointAttributes(pickPointAttr);
        pickAppear.setTransparencyAttributes(pickTtransAttr);
        pickAppear.setMaterial(material);
    }

    public String GetLineType()
    {
        if (this.lineAttr.getLinePattern() == LineAttributes.PATTERN_DASH_DOT)
            return "CENTER";
        return null;
    }

    public long GetStepId()
    {
        String str = this.getUserData().toString();
        int userdata = Integer.parseInt(str);
        switch (userdata)
        {
            case 0:
                return ((SDT_ShapeCurve)this.getGeometry()).GetStepId();
            case 1:
                return ((SDT_ShapeCurve)this.getGeometry()).GetStepId();
            case 2:
                return ((SDT_ShapeFace)this.getGeometry()).GetStepId();
            case 3:
                return ((SDT_ShapePoint)this.getGeometry()).GetStepId();
        }
        return -9999;
    }

    public void SetLineType(String type)
    {
        if (type.equals("Normal"))
        {
            this.lineAttr.setLinePattern(LineAttributes.PATTERN_SOLID);
        }
        else if (type.equals("Dash"))
        {
            this.lineAttr.setLinePattern(LineAttributes.PATTERN_DASH);
        }
        else if (type.equals("Center"))
        {
            this.lineAttr.setLinePattern(LineAttributes.PATTERN_DASH_DOT);
        }
    }

    public void SetLineThickness(float c)
    {
        this.lineAttr.setLineWidth(c);
    }

    public void SetTransparency(float trans)
    {
        if (trans == 0.0f)
        {
            this.transAttr.setTransparencyMode(TransparencyAttributes.NONE);
        }
        else
        {
            this.transAttr.setTransparencyMode(TransparencyAttributes.BLENDED);
        }
        TransparencyAttributes transAttr = appear.getTransparencyAttributes();
        transAttr.setTransparency(trans);
    }

    public void SetPickAppearance()
    {
        this.setAppearance(this.pickAppear);
    }

    public void ResetAppearance()
    {
        this.setAppearance(this.appear);
    }

    public void ResetColor()
    {
        if (this.getGeometry().getClass().getName() == "SpringSolidGUI.Viewer.shape3d.spring_curve")
        {
            ((SDT_ShapeCurve)this.getGeometry()).ResetColor();
        }
        else if (this.getGeometry().getClass().getName() == "SpringSolidGUI.Viewer.shape3d.spring_face")
        {
            ((SDT_ShapeFace)this.getGeometry()).ResetColor();
        }
        else
        {
            ((SDT_ShapePoint)this.getGeometry()).ResetColor();
        }
    }
}
