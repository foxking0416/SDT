package sdt.stepb;

import java.io.*;
import java.util.*;

import sdt.data.*;
import sdt.java3d.*;
import sdt.define.DefineSystemConstant;
import sdt.FEM.SDT_InpFile;

public class stepb_manifold_solid_brep
{

    private stepb_curve_array crvArray;
    private stepb_cartesian_point_array cptArray;
    private stepb_vertex_point_array vertexArray;
    //stepb_edge_curve                      edgeCurve;
    private stepb_edge_curve_array ecArray;
    private stepb_direction_array dirArray;
    private stepb_vector_array vecArray;

    private boolean isMaterialWrittenCUWIRE = false;
    private boolean isMaterialWrittenPU = false;

    private SDT_Array3DMesh _arrayMeshCap = null;
    private SDT_Array3DMesh _arrayMeshTransition = null;
    private SDT_Array3DMesh _arrayMeshDiaphragm = null;
    private SDT_Array3DMesh _arrayMeshSurround = null;
    private SDT_Array3DMesh _arrayMeshCoil = null;
    private SDT_Array3DMesh _arrayMeshCoil2 = null;
    //private SDT_Array3DMesh _arrayMeshAir = null;
    private SDT_Array3DBrick _arrayBrickCoil = null;
    private stepb_cartesian_point[][] _ptSetsShellAll = null;
    private stepb_cartesian_point[] _ptSetsShellCenter = null;
    private ArrayCartesianPtSetsBrick _ptSetsSolidCone = null;
    private ArrayCartesianPtSetsBrick _ptSetsSolidAir = null;
    private ArrayList _airMeshArray = null;
    private ArrayList _arrayBrickAir = null;
    private int _lastIDpt = 1;
    private int _lastIDelement = 1;

    public stepb_cartesian_point_array getCarteisanPointArray()
    {
        return this.cptArray;
    }

    public stepb_curve_array getCurveArray()
    {
        return this.crvArray;
    }

    public stepb_edge_curve_array getEcArray()
    {
        return this.ecArray;
    }

    public stepb_vertex_point_array getVertexArray()
    {
        return this.vertexArray;
    }

    public stepb_manifold_solid_brep()
    {
        crvArray = new stepb_curve_array();
        cptArray = new stepb_cartesian_point_array();
        vertexArray = new stepb_vertex_point_array();
        ecArray = new stepb_edge_curve_array();
        dirArray = new stepb_direction_array();
        vecArray = new stepb_vector_array();
    }

    public void reset()
    {
        crvArray = new stepb_curve_array();
        cptArray = new stepb_cartesian_point_array();
        vertexArray = new stepb_vertex_point_array();
        ecArray = new stepb_edge_curve_array();
        dirArray = new stepb_direction_array();
        vecArray = new stepb_vector_array();
    }


    private void createLine3D(stepb_vertex_point startVertex, stepb_vertex_point endVertex, math_vector3d nodeVecUnit, double vecLength)
    {
        stepb_edge_curve edgeCurve;
        stepb_line line;
        stepb_direction dirLine;
        stepb_cartesian_point startCpt;

        startCpt = new stepb_cartesian_point(startVertex.GetCartesianPoint());
        if (nodeVecUnit != null)
        {
            dirLine = new stepb_direction(nodeVecUnit.X(), nodeVecUnit.Y(), nodeVecUnit.Z());
        }
        else
        {
            dirLine = new stepb_direction(0, 0, 1);
        }
        stepb_vector vecLine = new stepb_vector(dirLine, vecLength);

        this.dirArray.add(dirLine);
        this.vecArray.add(vecLine);
        this.cptArray.add(startCpt);

        line = new stepb_line(startCpt, vecLine);
        crvArray.add(line);

        edgeCurve = new stepb_edge_curve(startVertex, endVertex, (stepb_curve) line);
        ecArray.add(edgeCurve);
    }


    public static void read(String stepFilePath)
    {
        FileReader fr = null;
        BufferedReader br = null;

        Vector StringArray_CartesianPoint = new Vector();
        Vector StringArray_Direction = new Vector();
        Vector StringArray_Axis = new Vector();
        Vector StringArray_Vector = new Vector();
        Vector StringArray_Line = new Vector();
        Vector StringArray_Circle = new Vector();
        Vector StringArray_Plane = new Vector();
        Vector StringArray_CylindricalSurf = new Vector();

        Vector StringArray_Vertex = new Vector();
        Vector StringArray_EdgeCurve = new Vector();
        Vector StringArray_OrientEdge = new Vector();
        Vector StringArray_EdgeLoop = new Vector();
        Vector StringArray_FaceOuterBound = new Vector();
        Vector StringArray_ADVFace = new Vector();
        Vector StringArray_ClosedShell = new Vector();
        Vector StringArray_BRep = new Vector();
        boolean isData = false;
        String str = "";
        try
        {
            fr = new FileReader(stepFilePath);
            br = new BufferedReader(fr);
            for (int i = 0; i < 15; i++)
            {
                br.readLine().trim();
            }
            str = br.readLine();
            while (str != null)
            {
                isData = false;
                try
                {

                    int EquivalentIndex = str.indexOf("=");
                    if (EquivalentIndex > 0)
                    {
                        int numberIndex = Integer.parseInt(str.substring(1, EquivalentIndex - 1).trim());
                        String content = str.substring(EquivalentIndex + 1);
                        content = content.trim();
                        if (content.startsWith("CARTESIAN_POINT"))
                        {
                            StringArray_CartesianPoint.add(str);
                            isData = true;
                        }
                        if (content.startsWith("VECTOR"))
                        {
                            StringArray_Vector.add(str);
                            isData = true;
                        }
                        if (content.startsWith("AXIS2_PLACEMENT_3D"))
                        {
                            StringArray_Axis.add(str);
                            isData = true;
                        }
                        if (content.startsWith("DIRECTION"))
                        {
                            StringArray_Direction.add(str);
                            isData = true;
                        }
                        if (content.startsWith("CYLINDRICAL_SURFACE"))
                        {
                            StringArray_CylindricalSurf.add(str);
                            isData = true;
                        }
                        if (content.startsWith("PLANE"))
                        {
                            StringArray_Plane.add(str);
                            isData = true;
                        }
                        if (content.startsWith("LINE"))
                        {
                            StringArray_Line.add(str);
                            isData = true;
                        }
                        if (content.startsWith("CIRCLE"))
                        {
                            StringArray_Circle.add(str);
                            isData = true;
                        }

                        if (content.startsWith("VERTEX_POINT"))
                        {
                            StringArray_Vertex.add(str);
                            isData = true;
                        }
                        if (content.startsWith("EDGE_CURVE"))
                        {
                            StringArray_EdgeCurve.add(str);
                            isData = true;
                        }
                        if (content.startsWith("ORIENTED_EDGE"))
                        {
                            StringArray_OrientEdge.add(str);
                            isData = true;
                        }
                        if (content.startsWith("EDGE_LOOP"))
                        {
                            StringArray_EdgeLoop.add(str);
                            isData = true;
                        }
                        if (content.startsWith("FACE_OUTER_BOUND"))
                        {
                            StringArray_FaceOuterBound.add(str);
                            isData = true;
                        }
                        if (content.startsWith("ADVANCED_FACE"))
                        {
                            StringArray_ADVFace.add(str);
                            isData = true;
                        }
                        if (content.startsWith("CLOSED_SHELL"))
                        {
                            StringArray_ClosedShell.add(str);
                            isData = true;
                        }
                        if (content.startsWith("MANIFOLD_SOLID_BREP"))
                        {
                            StringArray_BRep.add(str);
                            isData = true;
                        }
                        if (!isData)
                        {
                            System.out.println(str);
                        }

                    }
                }
                catch (NumberFormatException ex1)
                {
                }
                str = br.readLine();
            }

            System.out.println("============Geometry Infomation==============");
            printStringArray(StringArray_CartesianPoint);
            printStringArray(StringArray_Direction);
            printStringArray(StringArray_Axis);
            printStringArray(StringArray_Vector);
            printStringArray(StringArray_Line);
            printStringArray(StringArray_Circle);
            printStringArray(StringArray_CylindricalSurf);
            printStringArray(StringArray_Plane);
            System.out.println("============Topology Infomation==============");
            printStringArray(StringArray_Vertex);
            printStringArray(StringArray_EdgeCurve);
            printStringArray(StringArray_OrientEdge);
            printStringArray(StringArray_EdgeLoop);
            printStringArray(StringArray_FaceOuterBound);
            printStringArray(StringArray_ADVFace);
            printStringArray(StringArray_ClosedShell);
            printStringArray(StringArray_BRep);
        }

        catch (FileNotFoundException ex)
        {
        }
        catch (IOException ex)
        {
            /** @todo Handle this exception */
        }

    }

    private static void printStringArray(Vector StringArray)
    {
        int size = StringArray.size();
        for (int i = 0; i < size; i++)
        {
            String str = ((String) StringArray.get(i));
            System.out.println(str);
        }

    }

    public void setAirMesh(DataAir dataAir, int deformedIndex)
    {
        if (deformedIndex == -1)
            dataAir.setDataToElementXY();

        /***
         * Get Solid Points
         */
        this._ptSetsSolidAir = dataAir.getSolidPts(deformedIndex, 1);

      //   _lastIDpt = 1;
      //  _lastIDelement = 1;

        for(int i = 0 ; i < this._ptSetsSolidAir.Size() ; i++)
        {
            System.out.println("_ptSetsSolidAir:" + i);
            CartesianPointSetsBrick cptArray =  this._ptSetsSolidAir.get(i);

            _lastIDpt = this.assignIDToPoint(cptArray, _lastIDpt); //for input file
        }


        this._airMeshArray = new ArrayList();

        this._arrayBrickAir = new ArrayList();


        for(int i = 0 ; i < this._ptSetsSolidAir.Size() ; i++)//this._ptSetsArrayAir.Size()
        {
            CartesianPointSetsBrick ptSetsBrickAir = this._ptSetsSolidAir.get(i);
            stepb_cartesian_point[][][] ptSetsAir = ptSetsBrickAir.getPtSets();

            this.createDataLine(ptSetsAir);


            SDT_Array3DMesh arrayMeshAir = this.createDataMeshBrick(ptSetsAir, _lastIDelement);
            arrayMeshAir.setTypeID(DefineSystemConstant.TYPE_AIR);

            SDT_Array3DBrick arrayBrickAir = this.createDataBrick(ptSetsBrickAir, _lastIDelement);

            if(i == 20)
            {
                int a = 0;
            }

            _lastIDelement = arrayBrickAir.get(arrayBrickAir.Size()-1).getElementID();
            _lastIDelement++;

            this._airMeshArray.add(arrayMeshAir);
            this._arrayBrickAir.add(arrayBrickAir);

        }

    }


    /**
     * setConeMesh  , the method enable Data turns into MeshArray & BrickArray
     *
     * @param dataCap DataCap
     * @param dataTran DataTransition
     * @param dataSurround DataSurround
     * @param dataDiaphragm DataDiaphragm
     * @param dataCoil DataCoil
     * @param deformedIndex int , if index == -1 means no deformed displacement
     */
    public void setConeMesh(DataCap dataCap,
                            DataTransition dataTran,
                            DataSurround dataSurround,
                            DataDiaphragm dataDiaphragm,
                            DataCoil dataCoil,
                            int deformedIndex)
    {
        if (deformedIndex == -1)
        {
            dataSurround.setDataToElementXY();
            dataCap.setDataToElementXY();
            dataTran.setDataToElementXY();
            if (dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
            {
                dataDiaphragm.setDataToElementXY();
            }
            dataCoil.setDataToElementXY();
        }
        /***
         * Get Shell Points
         */

        //將變形後的振膜的變形量調整一下使之容易觀看
        double scale = 1;
        if (deformedIndex != -1)
        {
            stepb_cartesian_point_array displacementArray = dataSurround.getDataManager().getDisplacementArray(deformedIndex);

            double maxDisplace = 0;
            for (int count = 0; count < displacementArray.size(); count++)
            {
                stepb_cartesian_point ptDisplacement = displacementArray.get(count);
                if (ptDisplacement.GetMathVector3d().Length() > maxDisplace)
                    maxDisplace = ptDisplacement.GetMathVector3d().Length();
            }
            double coneDiameter = dataSurround.getElementPointEnd(DefineSystemConstant.XZView).X();
            scale = coneDiameter * 0.2 / maxDisplace;
        }



        stepb_cartesian_point[][] ptSetsSurround = dataSurround.getShellPts(deformedIndex, scale);
        stepb_cartesian_point[][] ptSetsCap = dataCap.getShellPts(deformedIndex, scale);
        stepb_cartesian_point[][] ptSetsTransition = dataTran.getShellPts(deformedIndex, scale);
        stepb_cartesian_point[][] ptSetsDiaphragm = null;


        if (dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
        {
            ptSetsDiaphragm = dataDiaphragm.getShellPts(deformedIndex, scale);
        }

        /***
         * Get Solid Points
         */
        stepb_cartesian_point[][][] ptSetsCoil = null;
        stepb_cartesian_point[][][] ptSetsCoil2 = null;

        ArrayCartesianPtSetsBrick ptSetsArrayCoil = dataCoil.getSolidPts(deformedIndex, scale);
        CartesianPointSetsBrick ptSetsBrickCoil1 = null;
        CartesianPointSetsBrick ptSetsBrickCoil2 = null;
        this._ptSetsSolidCone = new ArrayCartesianPtSetsBrick();
        this._ptSetsSolidCone.addArray(ptSetsArrayCoil);

        ptSetsBrickCoil1 = ptSetsArrayCoil.get(0);
        ptSetsCoil = ptSetsBrickCoil1.getPtSets();


        if (dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE2)
        {
            ptSetsBrickCoil2 = ptSetsArrayCoil.get(1);
            ptSetsCoil2 = ptSetsBrickCoil2.getPtSets();
        }
        /***
         * Shell element 先進行點計算 幾何重合點, 以共用為主
         */

        this.makeConnectPtsIdentical(ptSetsCap, ptSetsTransition);
        if (dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
        {
            this.makeConnectPtsIdentical(ptSetsTransition, ptSetsDiaphragm);
            this.makeConnectPtsIdentical(ptSetsDiaphragm, ptSetsSurround);
        }
        else
        {
            this.makeConnectPtsIdentical(ptSetsTransition, ptSetsSurround);
        }
        /***
         * Solid element 進行點計算 幾何重合點, 以共用為主
         */
        ptSetsBrickCoil1.setContactNodeFromOther(dataTran.getPtSetsContact());

        /***
        *combine all ptSets and erase the redundant pts
        */
        int numberFan = dataCap.getNumberFan();
        int numberTri = dataCap.getNumberTri();
        boolean isCapsule = (dataCap.getGeometryType() == DefineSystemConstant.CAP_TYPE_CAPSULE);
        _ptSetsShellCenter = this.getPtSetsCenter(ptSetsCap, numberFan, numberTri, isCapsule);

        this.mergePtSetsShell(  ptSetsCap ,  ptSetsTransition , ptSetsDiaphragm,ptSetsSurround );

        _lastIDpt = 1;
        _lastIDelement = 1;

        if (isCapsule)
        {
            _lastIDpt = _ptSetsShellCenter[_ptSetsShellCenter.length - 1].IDNumber;
        }
        else
            _lastIDpt = _ptSetsShellCenter[0].IDNumber;

        _lastIDpt = this.assignIDToPoint(_ptSetsShellAll, _lastIDpt); //for input file
        _lastIDpt = this.assignIDToPoint(ptSetsBrickCoil1, _lastIDpt); //for input file
        if (ptSetsBrickCoil2 != null)
            _lastIDpt = this.assignIDToPoint(ptSetsBrickCoil2, _lastIDpt); //for input file


        stepb_cartesian_point[][] ptSetsCapWithoutCenter = this.eraseFirstLoopOfPts(ptSetsCap); //for input file

        System.out.println("=============================");
        System.out.println("Start Creating Data Line.....");
        long timeMillis = System.currentTimeMillis();

        if (this.ecArray.size() != 0)
        {
            this.reset();
        }

        this.createDataLine(ptSetsCap);
        this.createDataLine(ptSetsTransition);
        if (dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
        {
            this.createDataLine(ptSetsDiaphragm);
        }
        this.createDataLine(ptSetsSurround);
        this.createDataLine(ptSetsCoil);


        //計算時間  跟秀圖功能無關
        long timeCreatingDataLine = System.currentTimeMillis() - timeMillis;
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeCreatingDataLine);
        String timeStrCreatingDataLine = dateTime.toString().substring(14) + "";
        System.out.println("End Creating Data Line....." + timeStrCreatingDataLine);
        System.out.println("=============================");
        System.out.println("Start Creating Data Mesh.....");
        timeMillis = System.currentTimeMillis();
        /////////////////////


        _arrayMeshCap = this.createDataMeshCap(ptSetsCapWithoutCenter, _ptSetsShellCenter, this._lastIDelement, numberFan, numberTri, isCapsule);
        _lastIDelement = _arrayMeshCap.getLastID();
        _lastIDelement++;
        _arrayMeshCap.setTypeID(DefineSystemConstant.TYPE_CAP);

        _arrayMeshTransition = this.createDataMesh(ptSetsTransition, _lastIDelement);
        _lastIDelement = _arrayMeshTransition.getLastID();
        _lastIDelement++;
        _arrayMeshTransition.setTypeID(DefineSystemConstant.TYPE_TRANSITION);

        _arrayMeshDiaphragm = null;
        if (dataDiaphragm.getGeometryType() != DefineSystemConstant.DIAPHRAGM_NONE)
        {
            _arrayMeshDiaphragm = this.createDataMesh(ptSetsDiaphragm, _lastIDelement);
            _lastIDelement = _arrayMeshDiaphragm.getLastID();
        _lastIDelement++;
            _arrayMeshDiaphragm.setTypeID(DefineSystemConstant.TYPE_DIAPHRAGM);
        }
        _arrayMeshSurround = this.createDataMesh(ptSetsSurround, _lastIDelement);
        _lastIDelement = _arrayMeshSurround.getLastID();
        _lastIDelement++;
        _arrayMeshSurround.setTypeID(DefineSystemConstant.TYPE_SURROUND);

        _arrayMeshCoil = this.createDataMeshBrick(ptSetsCoil, _lastIDelement);
        // mesh has the same ID with Brick element, only brick element will be output
        _arrayMeshCoil.setTypeID(DefineSystemConstant.TYPE_COIL);

        ptSetsBrickCoil1.setSectionType(DefineSystemConstant.TYPE_COIL);
        _arrayBrickCoil = this.createDataBrick(ptSetsBrickCoil1, _lastIDelement);

        _lastIDelement = _arrayMeshCoil.getLastID();
        _lastIDelement++;

        _arrayMeshCoil2 = null;
        if (dataCoil.getGeometryType() == DefineSystemConstant.COIL_TYPE2)
        {
            _arrayMeshCoil2 = this.createDataMeshBrick(ptSetsCoil2, _lastIDelement);
            _arrayMeshCoil2.setTypeID(DefineSystemConstant.TYPE_FORMER);

            ptSetsBrickCoil2.setSectionType(DefineSystemConstant.TYPE_COIL);
            SDT_Array3DBrick brickCoil2 = this.createDataBrick(ptSetsBrickCoil2, _lastIDelement);
            _arrayBrickCoil.addArray(brickCoil2);
            _lastIDelement = _arrayMeshCoil2.getLastID();
            _lastIDelement++;

        }
        long timeCreatingDataMesh = System.currentTimeMillis() - timeMillis;
        dateTime = new java.sql.Timestamp(timeCreatingDataMesh);
        String timeStrCreatingDataMesh = dateTime.toString().substring(14) + "";
        System.out.println("End Creating Data Mesh....." + timeStrCreatingDataMesh);


    }

    public SDT_Array3DMesh[] getSDTAirArrayMeshes()
    {
        SDT_Array3DMesh[] meshEleArraySet = new SDT_Array3DMesh[this._airMeshArray.size()];



        for (int i = 0; i < this._airMeshArray.size(); i++)
        {
            meshEleArraySet[i] = (SDT_Array3DMesh) this._airMeshArray.get(i);
        }
        return meshEleArraySet;
    }

    public SDT_Array3DMesh[] getSDTArrayMeshes()
    {
        SDT_Array3DMesh[] meshEleArraySet = null;

        ArrayList arrayMeshArray = new ArrayList();

        arrayMeshArray.add(_arrayMeshCap);
        arrayMeshArray.add(_arrayMeshTransition);
        if (_arrayMeshDiaphragm != null)
            arrayMeshArray.add(_arrayMeshDiaphragm);
        arrayMeshArray.add(_arrayMeshSurround);
        arrayMeshArray.add(_arrayMeshCoil);
        if (_arrayMeshCoil2 != null)
            arrayMeshArray.add(_arrayMeshCoil2);

        meshEleArraySet = new SDT_Array3DMesh[arrayMeshArray.size()];
        for (int i = 0; i < arrayMeshArray.size(); i++)
        {
            meshEleArraySet[i] = (SDT_Array3DMesh) arrayMeshArray.get(i);

        }
        return meshEleArraySet;
    }

    public boolean writeInpFile(String path,
                                String elsetNameSurround, double thickSurround, String[] materialSurround,
                                String elsetNameCap, double thickCap, String[] materialCap,
                                String elsetNameTransition, double thickTransition, String[] materialTransition,
                                String elsetNameDiaphragm, double thickDiaphragm, String[] materialDiaphragm,
                                String elsetNameCoil, double thickCoil, String[] materialCoil,
                                boolean isWriteAir, boolean isPortSealed, int portLength)
    {
        SDT_InpFile inpFile = new SDT_InpFile(_ptSetsShellCenter,
                                              _ptSetsShellAll,
                                              _ptSetsSolidCone,
                                              _arrayMeshCap,
                                              _arrayMeshTransition,
                                              _arrayMeshDiaphragm,
                                              _arrayMeshSurround,
                                              _arrayBrickCoil,
                                              _ptSetsSolidAir,
                                              _arrayBrickAir );
        boolean isWritten;

         if (isWriteAir == true)
         {
             isWritten = inpFile.writeInpFileConeAir(path, elsetNameSurround, thickSurround, materialSurround,
                                                     elsetNameCap, thickCap, materialCap,
                                                     elsetNameTransition, thickTransition, materialTransition,
                                                     elsetNameDiaphragm, thickDiaphragm, materialDiaphragm,
                                                     elsetNameCoil, thickCoil, materialCoil,
                                                     "AIR", "AIR",
                                                     isPortSealed, portLength);


         }
         else
         {
             isWritten = inpFile.writeInpFileCone(path, elsetNameSurround, thickSurround, materialSurround,
                                        elsetNameCap, thickCap, materialCap,
                                        elsetNameTransition, thickTransition, materialTransition,
                                        elsetNameDiaphragm, thickDiaphragm, materialDiaphragm,
                                        elsetNameCoil, thickCoil, materialCoil);

         }
         boolean isWritten2 = inpFile.writeInpFileConeStatic(path, elsetNameSurround, thickSurround, materialSurround,
                                     elsetNameCap, thickCap, materialCap,
                                     elsetNameTransition, thickTransition, materialTransition,
                                     elsetNameDiaphragm, thickDiaphragm, materialDiaphragm,
                                     elsetNameCoil, thickCoil, materialCoil);
         return isWritten;
    }


    private void writeSectionShell(String elsetName, double thickness, String materialName, BufferedWriter bw) throws IOException
    {
        String strToWrite = "";

        strToWrite += "**" + "\n";
        strToWrite += "**" + "\n";
        strToWrite += "*Shell Section, elset=" + elsetName + ", material=" + materialName + ", offset=" + 0.5 + "\n";
        strToWrite += thickness + "\n";
        bw.write(strToWrite, 0, strToWrite.length());
        bw.flush();
    }

    private void writeSectionSolid(String elsetName, double thickness, String materialName, BufferedWriter bw) throws IOException
    {
        String strToWrite = "";

        strToWrite += "**" + "\n";
        strToWrite += "**" + "\n";
        strToWrite += "*Solid Section, elset=" + elsetName + ", material=" + materialName + "\n";
        strToWrite += 1 + "\n";
        bw.write(strToWrite, 0, strToWrite.length());
        bw.flush();
    }


    private void writeMaterialByName(String materialName, BufferedWriter bw) throws IOException
    {
        String strToWrite = "";
        if (materialName.equals("CUWIRE") && !isMaterialWrittenCUWIRE)
        {
            strToWrite += "**" + "\n";
            strToWrite += "**" + "\n";
            strToWrite += "*Material, name=CUWIRE" + "\n";
            strToWrite += "*Density" + "\n";
            strToWrite += "4.194e-10," + "\n";
            strToWrite += "*Elastic" + "\n";
            strToWrite += " 5000, 0.3" + "\n";
            isMaterialWrittenCUWIRE = true;
        }
        else if (materialName.equals("PU") && !isMaterialWrittenPU)
        {
            strToWrite += "**" + "\n";
            strToWrite += "**" + "\n";
            strToWrite += "*Material, name=PU" + "\n";
            strToWrite += "*Density" + "\n";
            strToWrite += "1.2e-10," + "\n";
            strToWrite += "*Elastic" + "\n";
            strToWrite += " 10, 0.3" + "\n";
            isMaterialWrittenPU = true;
        }

        bw.write(strToWrite, 0, strToWrite.length());
        bw.flush();
    }


    private void writeElementShell(SDT_Array3DMesh arrayMeshSurround, String elsetName, BufferedWriter bw) throws IOException
    {
        elsetName = elsetName.trim();
        String strToWrite = "";
        strToWrite += "**\n";
        strToWrite += "**\n";
        strToWrite += "*ELEMENT, TYPE=S4R, ELSET=" + elsetName + " \n";

        boolean isTriangleExist = false;

        System.out.println("          Writing Mesh Array ......" + elsetName + ":4 Node");
        for (int i = 0; i < arrayMeshSurround.Size(); i++)
        {
            SDT_3DMesh mesh = arrayMeshSurround.get(i);
            if (mesh.GetPoint4() == null)
            {
                isTriangleExist = true;
                continue;
            }
            String str = mesh.printStrCW();
            strToWrite += str + "\n";
            if ((i - arrayMeshSurround.Size() / 4) < 1 ||
                (i - arrayMeshSurround.Size() / 2) < 1 ||
                (i - 3 * arrayMeshSurround.Size() / 4) < 1 ||
                i == arrayMeshSurround.Size() - 1)
            {
                bw.write(strToWrite, 0, strToWrite.length());
                strToWrite = "";
            }
        }

        if (isTriangleExist)
        {
            strToWrite += "*ELEMENT, TYPE=S3R, ELSET=" + elsetName + " \n";
            System.out.println("          Writing Mesh Array ....." + elsetName + ":3 Node");
            for (int i = 0; i < arrayMeshSurround.Size(); i++)
            {
                SDT_3DMesh mesh = arrayMeshSurround.get(i);
                if (mesh.GetPoint4() != null)
                {
                    continue;
                }

                String str = mesh.printStrCW();
                strToWrite += str + "\n";
                if ((i - arrayMeshSurround.Size() / 4) < 1 ||
                    (i - arrayMeshSurround.Size() / 2) < 1 ||
                    (i - 3 * arrayMeshSurround.Size() / 4) < 1 ||
                    i == arrayMeshSurround.Size() - 1)
                {
                    bw.write(strToWrite, 0, strToWrite.length());
                    strToWrite = "";
                }
            }
        }
        bw.flush();
    }

    private void writeElementBrick(SDT_Array3DBrick arrayMeshBrick, String elsetName, BufferedWriter bw) throws IOException
    {
        elsetName = elsetName.trim();
        String strToWrite = "";
        strToWrite += "**\n";
        strToWrite += "**\n";
        strToWrite += "*ELEMENT, TYPE=C3D8R, ELSET=" + elsetName + " \n";

        boolean isTriangleExist = false;

        System.out.println("          Writing Brick Array ......" + elsetName + ":8 Node");
        for (int i = 0; i < arrayMeshBrick.Size(); i++)
        {
            SDT_3DBrick brick = arrayMeshBrick.get(i);

            String str = brick.PrintStrCW();
            strToWrite += str + "\n";
            if ((i - arrayMeshBrick.Size() / 4) < 1 ||
                (i - arrayMeshBrick.Size() / 2) < 1 ||
                (i - 3 * arrayMeshBrick.Size() / 4) < 1 ||
                i == arrayMeshBrick.Size() - 1)
            {
                bw.write(strToWrite, 0, strToWrite.length());
                strToWrite = "";
            }
        }

        bw.flush();
    }


    private SDT_Array3DMesh createDataMeshCap(stepb_cartesian_point[][] ptSets, stepb_cartesian_point[] centerPtArray, int startElementID, int numberFan, int numberTri, boolean isCapsule)
    {
        SDT_Array3DMesh arrayMeshTriangle = new SDT_Array3DMesh();
        SDT_Array3DMesh arrayMeshRectangle = new SDT_Array3DMesh();
        SDT_Array3DMesh arrayMesh;
        int iCount = ptSets[0].length;
        int kjCount = ptSets.length;

        arrayMesh = this.createDataMesh(ptSets, startElementID);

        for (int kj = 0; kj < kjCount; kj++)
        {
            int i = 0;
            stepb_cartesian_point vertexRD = null;
            stepb_cartesian_point vertexRU = null;

            if (kj != kjCount - 1)
            {
                vertexRD = ptSets[kj][i];
                vertexRU = ptSets[kj + 1][i];
            }
            else
            {
                vertexRD = ptSets[kj][i];
                vertexRU = ptSets[0][i];
            }
            if (!isCapsule)
            {
                SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[0], vertexRD, vertexRU);
                arrayMeshTriangle.add(mesh);
            }
            else
            {
                int anotherCenterIndex = (int) (numberFan / 2) + numberTri + 1;
                if (kj < numberFan / 2) //1st Area (First Half Fan)
                {
                    SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[0], vertexRD, vertexRU);
                    arrayMeshTriangle.add(mesh);
                }
                else if (kj >= numberFan / 2 && kj < numberFan / 2 + numberTri) //2nd Area (Tri)
                {
                    SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[kj + 1], centerPtArray[kj], vertexRD, vertexRU);
                    arrayMeshRectangle.add(mesh);

                }
                else if (kj >= numberFan / 2 + numberTri && kj < numberFan / 2 + numberTri + numberFan) //3rd Area Secod Fan
                {
                    SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[anotherCenterIndex], vertexRD, vertexRU);
                    arrayMeshTriangle.add(mesh);
                }

                else if (kj >= numberFan / 2 + numberTri + numberFan && kj < numberFan / 2 + numberTri * 2 + numberFan)
                {
                    SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[kj + 1], centerPtArray[kj], vertexRD, vertexRU);
                    arrayMeshRectangle.add(mesh);
                }
                else if (kj >= numberFan / 2 + numberTri * 2 + numberFan && kj < kjCount)
                {
                    SDT_3DMesh mesh = new SDT_3DMesh(centerPtArray[0], vertexRD, vertexRU);
                    arrayMeshTriangle.add(mesh);
                }
            }
        }
        int nextStartElementID = -1;

        if (isCapsule)
        {
            nextStartElementID = arrayMesh.Size() + startElementID;
            for (int i = 0; i < arrayMeshRectangle.Size(); i++)
            {
                arrayMeshRectangle.get(i).SetElementID(nextStartElementID + i);
            }
            arrayMesh.addArray(arrayMeshRectangle);
        }

        nextStartElementID = arrayMesh.Size() + startElementID;
        for (int i = 0; i < arrayMeshTriangle.Size(); i++)
        {
            arrayMeshTriangle.get(i).SetElementID(nextStartElementID + i);
        }
        arrayMesh.addArray(arrayMeshTriangle);
        return arrayMesh;
    }

    /**
     * 將所有node，每四個建立成一塊mesh
     * @param ptSets stepb_cartesian_point[][]
     * @param startElementID mesh起始ID值
     * @return SDT_Array3DMesh 利用node點建立出來的mesh aray
     */
    private SDT_Array3DMesh createDataMesh(stepb_cartesian_point[][] ptSets, int startElementID)
    {
        int iCount = ptSets[0].length;
        int jCount = ptSets.length;

        SDT_Array3DMesh arrayMesh = new SDT_Array3DMesh();

        //  int jIntervalCount = jCount - 1;


        for (int j = 0; j < jCount; j++)
        {
            for (int i = 0; i < iCount - 1; i++)
            {
                stepb_cartesian_point vertexLD = null;
                stepb_cartesian_point vertexRD = null;
                stepb_cartesian_point vertexRU = null;
                stepb_cartesian_point vertexLU = null;

                vertexLD = ptSets[j][i];
                vertexRD = ptSets[j][i + 1];
                if ((j + 1) == jCount)
                {

                    vertexLU = ptSets[0][i];
                    vertexRU = ptSets[0][i + 1];
                }
                else
                {
                    vertexLU = ptSets[j + 1][i];
                    vertexRU = ptSets[j + 1][i + 1];
                }

                SDT_3DMesh mesh = new SDT_3DMesh(vertexLD, vertexRD, vertexRU, vertexLU);
                arrayMesh.add(mesh);
            }

        }
        for (int i = 0; i < arrayMesh.Size(); i++)
        {
            arrayMesh.get(i).SetElementID(startElementID + i);
        }

        return arrayMesh;
    }

    /**
     * 將所有node，每4個建立成一塊Mesh for Show,  被遮蓋的地方沒有建mesh  //
     * @param ptSets stepb_cartesian_point[][]
     * @param startElementID mesh起始ID值
     * @return SDT_Array3DMesh 利用node點建立出來的mesh aray
     */
    private SDT_Array3DMesh createDataMeshBrick(stepb_cartesian_point[][][] ptSets, int startElementID)
    {
        int tCount = ptSets[0][0].length;
        int iCount = ptSets[0].length;
        int jCount = ptSets.length;

        SDT_Array3DMesh arrayMesh = new SDT_Array3DMesh();

        for (int k = 0; k < 2; k++)
        {
            int t;
            if(k == 0)
                t = 0;
            else
                t= tCount - 1;

            for (int j = 0; j < jCount; j++) //  1;j++)//
            {
                for (int i = 0; i < iCount - 1; i++) //1;i++)//
                {
                    stepb_cartesian_point vertexLD = null;
                    stepb_cartesian_point vertexRD = null;
                    stepb_cartesian_point vertexRU = null;
                    stepb_cartesian_point vertexLU = null;

                    vertexLD = ptSets[j][i][t];
                    vertexRD = ptSets[j][i + 1][t];
                    if ((j + 1) == jCount)
                    {
                        vertexLU = ptSets[0][i][t];
                        vertexRU = ptSets[0][i + 1][t];
                    }
                    else
                    {
                        vertexLU = ptSets[j + 1][i][t];
                        vertexRU = ptSets[j + 1][i + 1][t];
                    }
                    SDT_3DMesh mesh = new SDT_3DMesh(vertexLD, vertexRD, vertexRU, vertexLU);
                    arrayMesh.add(mesh);
                }
            }
        }

        for (int k = 0; k < 2; k++)
        {
            int i;
            if(k == 0)
                i = 0;
            else
                i= iCount - 1;


            for (int j = 0; j < jCount; j++) //  1;j++)//
            {
                for (int t = 0; t < tCount - 1; t++)
                {
                    stepb_cartesian_point vertexLD = null;
                    stepb_cartesian_point vertexRD = null;
                    stepb_cartesian_point vertexRU = null;
                    stepb_cartesian_point vertexLU = null;

                    vertexLD = ptSets[j][i][t];
                    vertexRD = ptSets[j][i][t + 1];
                    if ((j + 1) == jCount)
                    {
                        vertexLU = ptSets[0][i][t];
                        vertexRU = ptSets[0][i][t + 1];
                    }
                    else
                    {
                        vertexLU = ptSets[j + 1][i][t];
                        vertexRU = ptSets[j + 1][i][t + 1];
                    }

                    SDT_3DMesh mesh = new SDT_3DMesh(vertexLD, vertexRD, vertexRU, vertexLU);
                    arrayMesh.add(mesh);

                }

            }
        }

/*
        for (int t = 0; t < tCount; t++) // same layer 1;t++)//
        {
            for (int j = 0; j < jCount; j++) //  1;j++)//
            {
                for (int i = 0; i < iCount - 1; i++) //1;i++)//
                {
                    stepb_cartesian_point vertexLD = null;
                    stepb_cartesian_point vertexRD = null;
                    stepb_cartesian_point vertexRU = null;
                    stepb_cartesian_point vertexLU = null;

                    vertexLD = ptSets[j][i][t];
                    vertexRD = ptSets[j][i + 1][t];
                    if ((j + 1) == jCount)
                    {
                        vertexLU = ptSets[0][i][t];
                        vertexRU = ptSets[0][i + 1][t];
                    }
                    else
                    {
                        vertexLU = ptSets[j + 1][i][t];
                        vertexRU = ptSets[j + 1][i + 1][t];
                    }

                    SDT_3DMesh mesh = new SDT_3DMesh(vertexLD, vertexRD, vertexRU, vertexLU);
                    arrayMesh.add(mesh);
                }
            }
        }

        for (int j = 0; j < jCount; j++)
        {
            for (int t = 0; t < tCount - 1; t++)
            {
                stepb_cartesian_point vertexLD = null;
                stepb_cartesian_point vertexRD = null;
                stepb_cartesian_point vertexRU = null;
                stepb_cartesian_point vertexLU = null;

                vertexLD = ptSets[j][iCount - 1][t];
                vertexRD = ptSets[j][iCount - 1][t + 1];
                if ((j + 1) == jCount)
                {
                    vertexLU = ptSets[0][iCount - 1][t];
                    vertexRU = ptSets[0][iCount - 1][t + 1];
                }
                else
                {
                    vertexLU = ptSets[j + 1][iCount - 1][t];
                    vertexRU = ptSets[j + 1][iCount - 1][t + 1];
                }

                SDT_3DMesh mesh = new SDT_3DMesh(vertexLD, vertexRD, vertexRU, vertexLU);
                arrayMesh.add(mesh);

            }
        }*/

        for (int i = 0; i < arrayMesh.Size(); i++)
        {
            arrayMesh.get(i).SetElementID(startElementID + i);
        }

        return arrayMesh;
    }

    /**
     * 將所有node，每8個建立成一塊Brick
     * @param ptSets stepb_cartesian_point[][]
     * @param startElementID mesh起始ID值
     * @return SDT_Array3DMesh 利用node點建立出來的mesh aray
     */
    /*private SDT_Array3DBrick createDataBrick(stepb_cartesian_point[][][] ptSets, int startElementID)
    {
        return this.createDataBrick(ptSets, startElementID, -1);
    }*/



    /**
     * 將所有node，每8個建立成一塊Brick
     * @param ptSets stepb_cartesian_point[][]
     * @param startElementID mesh起始ID值
     * @param sectionAirType Can use this to assign the contactFace when building a AirBrick/ CoilBrick
     * @return SDT_Array3DMesh 利用node點建立出來的mesh aray
     */
    /*private SDT_Array3DBrick createDataBrick(stepb_cartesian_point[][][] ptSets, int startElementID, int sectionType)
    {*/
    private SDT_Array3DBrick createDataBrick(CartesianPointSetsBrick ptSetsBrick, int startElementID)
    {

        stepb_cartesian_point[][][] ptSets = ptSetsBrick.getPtSets();
        int sectionType = ptSetsBrick.getSectionType();
        SDT_Array3DBrick arrayBrick = new SDT_Array3DBrick();

        int tCount = ptSets[0][0].length;
        int iCount = ptSets[0].length;
        int jCount = ptSets.length;

        int tUp     = ptSetsBrick.getTUp();
        int iUp     = ptSetsBrick.getIUp();
        int tDown   = ptSetsBrick.getTDown();
        int iDown   = ptSetsBrick.getIDown();
        int tInner  = ptSetsBrick.getTInner();
        int iInner  = ptSetsBrick.getIInner();
        int tOuter  = ptSetsBrick.getTOuter();
        int iOuter  = ptSetsBrick.getIOuter();

        int brickType = 1;

        for (int i = 0; i < iCount - 1; i++) //1;i++)//
        {
            for (int j = 0; j < jCount; j++) //  1;j++)//
            {
                for (int t = 0; t < tCount - 1; t++) // same layer 1;t++)//
                {
                    stepb_cartesian_point vertex1 = null;
                    stepb_cartesian_point vertex2 = null;
                    stepb_cartesian_point vertex3 = null;
                    stepb_cartesian_point vertex4 = null;
                    stepb_cartesian_point vertex5 = null;
                    stepb_cartesian_point vertex6 = null;
                    stepb_cartesian_point vertex7 = null;
                    stepb_cartesian_point vertex8 = null;

                    vertex1 = ptSets[j][i][t];
                    vertex2 = ptSets[j][i][t + 1];
                    vertex3 = ptSets[j][i + 1][t + 1];
                    vertex4 = ptSets[j][i + 1][t];

                    if ((j + 1) != jCount)
                    {
                        vertex5 = ptSets[j + 1][i][t];
                        vertex6 = ptSets[j + 1][i][t + 1];
                        vertex7 = ptSets[j + 1][i + 1][t + 1];
                        vertex8 = ptSets[j + 1][i + 1][t];


                    }
                    else
                    {
                        vertex5 = ptSets[0][i][t];
                        vertex6 = ptSets[0][i][t + 1];
                        vertex7 = ptSets[0][i + 1][t + 1];
                        vertex8 = ptSets[0][i + 1][t];

                    }

                    SDT_3DBrick brick = new SDT_3DBrick(vertex1, vertex2, vertex3, vertex4, vertex5, vertex6, vertex7, vertex8);
                    arrayBrick.add(brick);

                    if (t == tDown || i ==iDown)
                    {
                        if(brickType % DefineSystemConstant.BRICKARRAY_TIE_DOWN !=  0)
                            brickType *= DefineSystemConstant.BRICKARRAY_TIE_DOWN;
                        switch (sectionType)
                        {
                            case DefineSystemConstant.SECTION_AIR_CAP:
                            case DefineSystemConstant.SECTION_AIR_TRAN_IN:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2COARSE_INNER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_TRAN_OUT:
                            case DefineSystemConstant.SECTION_AIR_DIAPHRAGM:
                            case DefineSystemConstant.SECTION_AIR_SURROUND:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2COARSE_OUTER);
                                break;
                        }
                    }
                    if (t  == tUp || i == iUp)
                    {
                         if(brickType % DefineSystemConstant.BRICKARRAY_TIE_UP !=  0)
                             brickType *= DefineSystemConstant.BRICKARRAY_TIE_UP;
                        switch (sectionType)
                        {
                            case DefineSystemConstant.SECTION_AIR_CAP:
                            case DefineSystemConstant.SECTION_AIR_TRAN_IN:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2CONE_INNER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_TRAN_OUT:
                            case DefineSystemConstant.SECTION_AIR_DIAPHRAGM:
                            case DefineSystemConstant.SECTION_AIR_SURROUND:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2CONE_OUTER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_ZONE01:
                            case DefineSystemConstant.SECTION_AIR_ZONE02:
                            case DefineSystemConstant.SECTION_AIR_ZONE03:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRCOARSE2FINE_INNER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_ZONE16:
                            case DefineSystemConstant.SECTION_AIR_ZONE19:
                            case DefineSystemConstant.SECTION_AIR_ZONE21:
                            case DefineSystemConstant.SECTION_AIR_ZONE22:
                            case DefineSystemConstant.SECTION_AIR_ZONE15:
                            case DefineSystemConstant.SECTION_AIR_ZONE18:
                            case DefineSystemConstant.SECTION_AIR_ZONE20:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRCOARSE2FINE_OUTER);
                                break;

                        }
                    }

                    if ( t == tInner || i == iInner)
                    {
                        if (brickType % DefineSystemConstant.BRICKARRAY_TIE_INNER != 0)
                            brickType *= DefineSystemConstant.BRICKARRAY_TIE_INNER;
                        switch (sectionType)
                        {
                            case DefineSystemConstant.SECTION_AIR_TRAN_OUT:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2COIL_OUTER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_ZONE13:
                            case DefineSystemConstant.SECTION_AIR_ZONE14:
                            case DefineSystemConstant.SECTION_AIR_ZONE15:
                            case DefineSystemConstant.SECTION_AIR_ZONE16:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRCOARSE2COIL_OUTER);
                                break;
                        }
                    }

                    if ( t == tOuter || i == iOuter)
                    {
                        if (brickType % DefineSystemConstant.BRICKARRAY_TIE_OUTER != 0)
                            brickType *= DefineSystemConstant.BRICKARRAY_TIE_OUTER;
                        switch (sectionType)
                        {
                            case DefineSystemConstant.SECTION_AIR_TRAN_IN:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRFINE2COIL_INNER);
                                break;
                            case DefineSystemConstant.SECTION_AIR_ZONE03:
                            case DefineSystemConstant.SECTION_AIR_ZONE05:
                            case DefineSystemConstant.SECTION_AIR_ZONE06:
                            case DefineSystemConstant.SECTION_AIR_ZONE08:
                                brick.setPosition(DefineSystemConstant.TIEFACE_AIRCOARSE2COIL_INNER);
                                break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < arrayBrick.Size(); i++)
        {
            arrayBrick.get(i).SetElementID(startElementID + i);
        }
        arrayBrick.setBrickType(brickType);
        arrayBrick.setTypeID(sectionType);
        return arrayBrick;
    }


    private void createDataLine(stepb_cartesian_point[][] ptSets)
    {

        int iCount = ptSets[0].length;
        int jCount = ptSets.length;

        stepb_vertex_point preVertex = null, nextVertex;
        stepb_cartesian_point cPt = null;
        int jIntervalCount = jCount - 1;

        for (int j = 0; j <= jIntervalCount; j++) //j 先不變 畫 橫軸
        {
            for (int i = 0; i < iCount; i++)
            {
                cPt = ptSets[j][i];
                if (i == 0) //指定第一點
                {
                    preVertex = new stepb_vertex_point(cPt);
                }
                else if (i != 0) //只要不是起頭 就進來
                {
                    nextVertex = new stepb_vertex_point(cPt);
                    double dx = cPt.X() - preVertex.GetCartesianPoint().X();
                    double dy = cPt.Y() - preVertex.GetCartesianPoint().Y();
                    double dz = cPt.Z() - preVertex.GetCartesianPoint().Z();

                    math_vector3d nodeVecUnit = new math_vector3d(dx, dy, dz);
                    double vecLength = nodeVecUnit.Length();
                    nodeVecUnit = nodeVecUnit.Normalize();
                    this.createLine3D(preVertex, nextVertex, nodeVecUnit, vecLength);

                    preVertex = nextVertex;
                }
            }
        }

        for (int i = 0; i < iCount; i++) //i 先不變 畫 縱軸
        {
            for (int j = 0; j <= jCount; j++)
            {

                if (j == 0) //指定第一點
                {
                    cPt = ptSets[j][i];
                    preVertex = new stepb_vertex_point(cPt);
                }
                else if (j != 0) //只要不是起頭 就進來
                {
                    if (j != jCount)
                    {
                        cPt = ptSets[j][i];
                    }
                    else
                    {
                        cPt = ptSets[0][i];
                    }
                    nextVertex = new stepb_vertex_point(cPt);
                    double dx = cPt.X() - preVertex.GetCartesianPoint().X();
                    double dy = cPt.Y() - preVertex.GetCartesianPoint().Y();
                    double dz = cPt.Z() - preVertex.GetCartesianPoint().Z();

                    math_vector3d nodeVecUnit = new math_vector3d(dx, dy, dz);
                    double vecLength = nodeVecUnit.Length();
                    nodeVecUnit = nodeVecUnit.Normalize();

                    this.createLine3D(preVertex, nextVertex, nodeVecUnit, vecLength);

                    preVertex = nextVertex;
                }

            }

        }
    }

    private void createDataLine(stepb_cartesian_point[][][] ptSets)
    {

        int tCount = ptSets[0][0].length;
        int iCount = ptSets[0].length;
        int jCount = ptSets.length;

        stepb_vertex_point preVertex = null, nextVertex;
        stepb_cartesian_point cPt = null;
        int jIntervalCount = jCount - 1;

        for (int t = 0; t < tCount; t++) //t 先不變 畫 同層
        {
            for (int j = 0; j <= jIntervalCount; j++) //j 先不變 畫 橫軸
            {
                for (int i = 0; i < iCount; i++)
                {
                    cPt = ptSets[j][i][t];
                    if (i == 0) //指定第一點
                    {
                        preVertex = new stepb_vertex_point(cPt);
                    }
                    else if (i != 0) //只要不是起頭 就進來
                    {
                        nextVertex = new stepb_vertex_point(cPt);
                        double dx = cPt.X() - preVertex.GetCartesianPoint().X();
                        double dy = cPt.Y() - preVertex.GetCartesianPoint().Y();
                        double dz = cPt.Z() - preVertex.GetCartesianPoint().Z();

                        math_vector3d nodeVecUnit = new math_vector3d(dx, dy, dz);
                        double vecLength = nodeVecUnit.Length();
                        nodeVecUnit = nodeVecUnit.Normalize();
                        this.createLine3D(preVertex, nextVertex, nodeVecUnit, vecLength);

                        preVertex = nextVertex;
                    }
                }
            }

            for (int i = 0; i < iCount; i++) //i 先不變 畫 縱軸
            {
                for (int j = 0; j <= jCount; j++)
                {

                    if (j == 0) //指定第一點
                    {
                        cPt = ptSets[j][i][t];
                        preVertex = new stepb_vertex_point(cPt);
                    }
                    else if (j != 0) //只要不是起頭 就進來
                    {
                        if (j != jCount)
                        {
                            cPt = ptSets[j][i][t];
                        }
                        else
                        {
                            cPt = ptSets[0][i][t];
                        }
                        nextVertex = new stepb_vertex_point(cPt);
                        double dx = cPt.X() - preVertex.GetCartesianPoint().X();
                        double dy = cPt.Y() - preVertex.GetCartesianPoint().Y();
                        double dz = cPt.Z() - preVertex.GetCartesianPoint().Z();

                        math_vector3d nodeVecUnit = new math_vector3d(dx, dy, dz);
                        double vecLength = nodeVecUnit.Length();
                        nodeVecUnit = nodeVecUnit.Normalize();

                        this.createLine3D(preVertex, nextVertex, nodeVecUnit, vecLength);

                        preVertex = nextVertex;
                    }

                }
            }
        }

        for (int i = 0; i < iCount; i++) // 畫 層與層之間
        {
            for (int j = 0; j < jCount; j++)
            {
                for (int t = 0; t < tCount ; t++)
                {
                    cPt = ptSets[j][i][t];
                    if (t == 0) //指定第一點
                    {
                        preVertex = new stepb_vertex_point(cPt);
                    }
                    else if (t != 0) //只要不是起頭 就進來
                    {

                        nextVertex = new stepb_vertex_point(cPt);
                        double dx = cPt.X() - preVertex.GetCartesianPoint().X();
                        double dy = cPt.Y() - preVertex.GetCartesianPoint().Y();
                        double dz = cPt.Z() - preVertex.GetCartesianPoint().Z();

                        math_vector3d nodeVecUnit = new math_vector3d(dx, dy, dz);
                        double vecLength = nodeVecUnit.Length();
                        nodeVecUnit = nodeVecUnit.Normalize();

                        this.createLine3D(preVertex, nextVertex, nodeVecUnit, vecLength);

                        preVertex = nextVertex;

                    }

                }
            }
        }

    }

    private void mergePtSetsShell(   stepb_cartesian_point ptSetsCap[][]
                                    ,stepb_cartesian_point ptSetsTransition[][]
                                    ,stepb_cartesian_point ptSetsDiaphragm[][]
                                    ,stepb_cartesian_point ptSetsSurround[][] )
    {
        int girthCount          = ptSetsCap.length;
        int radialCountCap      = ptSetsCap[0].length - 2 ;
        int radialCountTran     = ptSetsTransition[0].length - 1 ;
        int radialCountDiaphragm= 0;
        int radialCountSurround = ptSetsSurround[0].length  ;
            if(ptSetsDiaphragm != null) radialCountDiaphragm = ptSetsDiaphragm[0].length  ;

        int radialCount = radialCountCap + radialCountTran + radialCountDiaphragm + radialCountSurround;

        this._ptSetsShellAll = new stepb_cartesian_point[girthCount][radialCount];
        for(int j = 0 ; j < girthCount ; j++)
        {
            for(int i = 0 ; i <radialCount; i++)
            {
                int iCap = i + 1;
                int iTran = i + 1;
                int iSurround = i + 1;


                if (i < radialCountCap )
                {
                    //int iCap = i + 1;
                    this._ptSetsShellAll[j][i] = ptSetsCap[j][i+1];
                }
                else if (i >= radialCountCap && i < (radialCountCap + radialCountTran))
                {
                    this._ptSetsShellAll[j][i] = ptSetsTransition[j][i - radialCountCap];
                }
                else if (i >= (radialCountCap + radialCountTran) && i < radialCountCap + radialCountTran + radialCountDiaphragm)
                    this._ptSetsShellAll[j][i] = ptSetsDiaphragm[j][i - radialCountCap - radialCountTran  ];

                else
                    this._ptSetsShellAll[j][i] = ptSetsSurround[j][i - (radialCountCap + radialCountTran + radialCountDiaphragm)];
            }
        }
        int a = 0;
    }









    /**
     * <pre>Make sure the points on outter loop of inner Point Sets and the points on first loop of outter Point Sets are identical<br>
     *      If they have same value but are not same object, the inner points will be remained ;
     * </pre>
     * @param ptsInner  DataPoint Sets -> inner
     * @param ptsOutter DataPoint Sets -> Outter
     * @return Merged DataPoint Sets
     */

    private stepb_cartesian_point[][] mergePoint(stepb_cartesian_point ptsInner[][], stepb_cartesian_point ptsOutter[][])
    {
        int girthInner = ptsInner.length;
        int radialInner = ptsInner[0].length;
        int radialOutter = ptsOutter[0].length;
        boolean isOverlap = false;
        if (ptsInner[0][radialInner - 1].isEqualValue(ptsOutter[0][0]) &&
            ptsInner[girthInner - 1][radialInner - 1].isEqualValue(ptsOutter[girthInner - 1][0])
                )
        {
            isOverlap = true;
            System.out.println("=======================>overlap");
        }
        else
        {
            System.out.println("=======================>Not Overlap");
        }

        stepb_cartesian_point[][] resultPts = null;
        int girth = ptsInner.length;
        int radial = radialInner + radialOutter;

        if (isOverlap)
        {
            radial -= 1;
        }
        resultPts = new stepb_cartesian_point[girth][radial];

        for (int kj = 0; kj < girth; kj++)
        {
            for (int i = 0; i < radial; i++)
            {
                if (i < ptsInner[0].length)
                {
                    resultPts[kj][i] = ptsInner[kj][i];
                }
                else
                {
                    if (!isOverlap)
                    {
                        resultPts[kj][i] = ptsOutter[kj][i - radialInner];
                    }
                    else
                    {
                        resultPts[kj][i] = ptsOutter[kj][i - radialInner + 1]; // start from second i
                    }
                }
            }
        }

        if (isOverlap)
        {
            for (int kj = 0; kj < girth; kj++)
            {
                ptsOutter[kj][0] = ptsInner[kj][radialInner - 1];
            }
        }
        return resultPts;
    }

    private void makeConnectPtsIdentical(stepb_cartesian_point ptsInner[][], stepb_cartesian_point ptsOutter[][])
    {
        int girthInner = ptsInner.length;
        int radialInner = ptsInner[0].length;
        int radialOutter = ptsOutter[0].length;
        boolean isOverlap = false;
        for (int kj = 0; kj < girthInner; kj++)
        {
            ptsInner[kj][radialInner - 1] = ptsOutter[kj][0];
        }
    }




    /**
     * <pre>Make sure the points on certain loop of horizontal Point Sets and the points on first loop of vertical Point Sets are identical<br>
     *      If they have same value but are not same object, the horizontal points will be remained ;
     * </pre>
     * @param ptsHorizontal  DataPoint Sets -> horizontal
     * @param ptsVertical DataPoint Sets -> vertical
     * @return Merged DataPoint Sets
     */
    private stepb_cartesian_point[][] mergePointVertical(stepb_cartesian_point ptsHorizontal[][], stepb_cartesian_point ptsVertical[][])
    {
        int girthHorizontal = ptsHorizontal.length;
        int radialHorizontal = ptsHorizontal[0].length;
        int radialVertical = ptsVertical[0].length;
        boolean isOverlap = false;
        int theI = -1;
        for (int i = 0; i < radialHorizontal; i++)
        {
            if (ptsHorizontal[0][i].isEqualValue(ptsVertical[0][0]) &&
                ptsHorizontal[girthHorizontal - 1][i].isEqualValue(ptsVertical[girthHorizontal - 1][0])
                    )
            {
                theI = i;
                isOverlap = true;
                System.out.println("=======================>overlap");
            }
        }
        if (!isOverlap)
        {
            System.out.println("=======================>Not Overlap");
        }

        stepb_cartesian_point[][] resultPts = null;
        int girth = ptsHorizontal.length;
        int radial = radialHorizontal + radialVertical;

        if (isOverlap)
        {
            radial -= 1;
        }
        resultPts = new stepb_cartesian_point[girth][radial];

        for (int kj = 0; kj < girth; kj++)
        {
            for (int i = 0; i < radial; i++)
            {
                if (i < ptsHorizontal[0].length)
                {
                    resultPts[kj][i] = ptsHorizontal[kj][i];
                }
                else
                {
                    if (!isOverlap)
                    {
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal];
                    }
                    else
                    {
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal + 1]; // start from second i
                    }

                }
            }
        }

        if (isOverlap)
        {
            for (int kj = 0; kj < girth; kj++)
            {
                ptsVertical[kj][0] = ptsHorizontal[kj][theI];
            }
        }
        return resultPts;
    }


    /**
     * <pre>Make sure the points on certain two loops of horizontal Point Sets and the points on first loop of vertical Point Sets are identical<br>
     *      If they have same value but are not same object, the horizontal points will be remained ;
     * </pre>
     * @param ptsHorizontal DataPoint Sets -> horizontal
     * @param ptsVertical DataPoint Sets -> vertical
     * @return Merged DataPoint Sets
     */

    private stepb_cartesian_point[][] mergePointVertical(stepb_cartesian_point ptsHorizontal[][], stepb_cartesian_point ptsVertical[][][])
    {
        int girthHorizontal = ptsHorizontal.length;
        int radialHorizontal = ptsHorizontal[0].length;
        int radialVertical = ptsVertical[0].length;
        int thicknessVertical = ptsVertical[0][0].length;

        boolean isOverlapInner = false;
        boolean isOverlapOuter = false;
        int theInnerI = -1;
        int theOuterI = -1;
        System.out.println("========================================================");
        System.out.println("ToCompare: (" + ptsVertical[0][0][0].X() + ", " + ptsVertical[0][0][0].Y() + ", " + ptsVertical[0][0][0].Z() + " )");

        for (int i = 0; i < radialHorizontal; i++)
        {
            System.out.println("Objective: (" + ptsHorizontal[0][i].X() + ", " + ptsHorizontal[0][i].Y() + ", " + ptsHorizontal[0][i].Z() + " )");

            if (ptsHorizontal[0][i].isEqualValue(ptsVertical[0][0][0]) &&
                ptsHorizontal[girthHorizontal - 1][i].isEqualValue(ptsVertical[girthHorizontal - 1][0][0]))
            {
                theInnerI = i;
                isOverlapInner = true;
            }
            if (ptsHorizontal[0][i].isEqualValue(ptsVertical[0][0][thicknessVertical - 1]) &&
                ptsHorizontal[girthHorizontal - 1][i].isEqualValue(ptsVertical[girthHorizontal - 1][0][thicknessVertical - 1]))
            {
                theOuterI = i;
                isOverlapOuter = true;
            }
        }

        /***
         *  Assign the idendtical point on horizontal Array to the vertical array.
         */
        if (isOverlapInner)
        {
            for (int kj = 0; kj < ptsHorizontal.length; kj++)
            {
                ptsVertical[kj][0][0] = ptsHorizontal[kj][theInnerI];
            }
        }
        if (isOverlapOuter)
        {
            for (int kj = 0; kj < ptsHorizontal.length; kj++)
            {
                ptsVertical[kj][0][thicknessVertical - 1] = ptsHorizontal[kj][theOuterI];
            }
        }

        int girth = ptsHorizontal.length;
        stepb_cartesian_point[][] resultPts = null;
        int radial = radialHorizontal + radialVertical * thicknessVertical;

        if (isOverlapInner && isOverlapOuter)
        {
            radial -= thicknessVertical; //thicknessVertical should  be 2 temporarily. if more than 2 ,  the____I should be more than 2
        }
        resultPts = new stepb_cartesian_point[girth][radial];

        for (int kj = 0; kj < girth; kj++)
        {
            for (int i = 0; i < radial; i++)
            {
                if (i < ptsHorizontal[0].length)
                {
                    resultPts[kj][i] = ptsHorizontal[kj][i];
                }
                else if (i >= ptsHorizontal[0].length && i < ptsHorizontal[0].length + radialVertical - 1)
                {
                    System.out.println("i :" + i);
                    System.out.println("radialHorizontal :" + radialHorizontal);
                    System.out.println("First Group :" + (i - radialHorizontal + 1));

                    if (!isOverlapInner || !isOverlapOuter)
                    {
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal][0];
                    }
                    else
                    {
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal + 1][0]; // start from second i
                    }
                }
                else if (i >= ptsHorizontal[0].length + radialVertical - 1 && i < ptsHorizontal[0].length + 2 * radialVertical - 2)
                {

                    System.out.println("Second Group :" + (i - radialHorizontal - radialVertical + 2));
                    if (!isOverlapInner || !isOverlapOuter)
                    {
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal - radialVertical][1];
                    }
                    else
                    {
                        System.out.println(i - radialHorizontal - radialVertical + 2);
                        resultPts[kj][i] = ptsVertical[kj][i - radialHorizontal - radialVertical + 2][1]; // start from second i
                    }
                }
            }
        }

        return resultPts;
    }
    /**
     *   Get Identical centerPointArray
     **/

    private stepb_cartesian_point[] getPtSetsCenter(stepb_cartesian_point ptSetsCap[][], int numberFan, int numberTri, boolean isCapsule)
    {
        stepb_cartesian_point[] centerPointArray = new stepb_cartesian_point[ptSetsCap.length]; // = ptsAll[0];
        for (int k = 0; k < ptSetsCap.length; k++)
        {
            centerPointArray[k] = ptSetsCap[k][0];
        }
        int ID = 10001;

        if (!isCapsule)
        {
            for (int k = 1; k < ptSetsCap.length; k++)
            {
                ptSetsCap[k][0] = centerPointArray[0]; // make all center points change to same point
            }
            centerPointArray[0].SetIDNumber(ID);
        }
        else
        {
            int anotherCenterIndex = (int) (numberFan / 2) + numberTri;
            int oppsiteCount = 0;
            for (int k = 0; k < ptSetsCap.length; k++)
            {
                if (k == 0) //1st Area (First Half Fan)
                {
                    centerPointArray[0].SetIDNumber(ID);
                    ID++;
                }
                else if (k > 0 && k <= numberFan / 2)
                {
                    centerPointArray[k] = centerPointArray[0];
                }

                else if (k > numberFan / 2 && k < numberFan / 2 + numberTri) //2nd Area (Tri)
                {
                    centerPointArray[k].SetIDNumber(ID);
                    ID++;
                }
                else if (k == numberFan / 2 + numberTri) //3rd Area Secod Fan
                {
                    centerPointArray[anotherCenterIndex].SetIDNumber(ID);
                    ID++;
                }
                else if (k > numberFan / 2 + numberTri && k <= numberFan / 2 + numberTri + numberFan)
                {
                    centerPointArray[k] = centerPointArray[anotherCenterIndex];
                }

                else if (k > numberFan / 2 + numberTri + numberFan && k < numberFan / 2 + numberTri * 2 + numberFan) //4th Area (Tri)
                {
                    //ptsAll[k][0] = topPointArray[k];
                    oppsiteCount += 2;
                    centerPointArray[k] = centerPointArray[k - numberFan - oppsiteCount];
                    //ID++;
                    //topPointArray[k].SetIDNumber(ID);
                }
                else if (k >= numberFan / 2 + numberTri * 2 + numberFan) //5th Area (Fan)
                {
                    centerPointArray[k] = centerPointArray[0];
                }

            }
        }
         return centerPointArray;
    }
    /**
     *
     * @param ptsAll all the points are collected
     * @return a array of cartesian point with the first point is top of cap
     */

    private int assignIDToPoint(stepb_cartesian_point ptsAll[][], int ID)
    {

        for (int k = 0; k < ptsAll.length; k++)
        {
            for (int i = 0; i < ptsAll[0].length; i++)
            {
                ID++;
                ptsAll[k][i].SetIDNumber(ID);
            }
        }
        return ID;
    }
    /**
     *
     * @param CartesianPointSetsBrick all the points are collected
     * @return a array of cartesian point with the first point is top of cap
     */

    private int assignIDToPoint(CartesianPointSetsBrick ptSetsBrick, int ID)
    {
        stepb_cartesian_point[][][] pt = ptSetsBrick.getPtSets();
        for (int k = 0; k < pt.length; k++)
        {
            for (int i = 0; i < pt[0].length; i++)
            {
                for (int j = 0; j < pt[0][0].length; j++)
                {
                    ID++;
                    if(pt[k][i][j] .IDNumber == - 1)
                        pt[k][i][j].SetIDNumber(ID);
                    else
                        ID--;
                }
            }
        }
        return ID;
    }






    /**
     * 把cap最內圈的點去除掉
     *
     * @param ptsCap stepb_cartesian_point[][]  原始的CAP點集合
     * @return stepb_cartesian_point[][]  回傳的CAP點集合
     */

    private stepb_cartesian_point[][] eraseFirstLoopOfPts(stepb_cartesian_point ptsCap[][])
    {
        stepb_cartesian_point[][] ptsCapNew;
        int kCount = ptsCap.length;
        int iCount = ptsCap[0].length;

        ptsCapNew = new stepb_cartesian_point[kCount][iCount - 1];
        for (int k = 0; k < kCount; k++)
        {
            for (int i = 0; i < iCount - 1; i++)
            {
                ptsCapNew[k][i] = new stepb_cartesian_point(ptsCap[k][i + 1]);
                ptsCapNew[k][i].SetIDNumber(ptsCap[k][i + 1].IDNumber);
            }
        }
        return ptsCapNew;
    }
}

