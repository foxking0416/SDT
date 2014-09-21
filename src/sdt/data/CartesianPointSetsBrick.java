package sdt.data;

import java.util.ArrayList;
import sdt.stepb.stepb_cartesian_point;
import sdt.stepb.math_vector3d;
import sdt.stepb.stepb_cartesian_point_array;
import java.io.BufferedWriter;
import java.io.IOException;
import sdt.define.DefineSystemConstant;

public class CartesianPointSetsBrick
{
    private ArrayList _arrayConnectShell;
    private stepb_cartesian_point[][][]  _ptSets;
    private boolean[][][]             _isFromOthers;
    private ArrayList  _arrayOfOtherNodesIndex;
    private int  _sectionType;
    private int tUp = -1;
    private int iUp = -1;
    private int tDown = -1;
    private int iDown = -1;
    private int tInner = -1;
    private int iInner = -1;
    private int tOuter = -1;
    private int iOuter = -1;

    public void setTUp      (int t)  {tUp = t;}
    public void setIUp      (int i)  {iUp = i;}
    public void setTDown    (int t)  {tDown  = t;}
    public void setIDown    (int i)  {iDown  = i;}
    public void setTInner   (int t)  {tInner = t;}
    public void setIInner   (int i)  {iInner = i;}
    public void setTOuter   (int t)  {tOuter = t;}
    public void setIOuter   (int i)  {iOuter = i;}

    public int getTUp      ()  {return tUp;}
    public int getIUp      ()  {return iUp;}
    public int getTDown    ()  {return tDown ;}
    public int getIDown    ()  {return iDown ;}
    public int getTInner   ()  {return tInner;}
    public int getIInner   ()  {return iInner;}
    public int getTOuter   ()  {return tOuter;}
    public int getIOuter   ()  {return iOuter;}

    public int      getSectionType()                       {return this._sectionType;}
    public void     setSectionType(int sectionType)     {this._sectionType=sectionType; }




    public CartesianPointSetsBrick(stepb_cartesian_point[][][]  ptSets)
    {
        _ptSets = ptSets;
        _arrayConnectShell = new ArrayList();
        _arrayOfOtherNodesIndex= new ArrayList();
        _isFromOthers = new boolean[_ptSets.length][_ptSets[0].length][_ptSets[0][0].length];
    }
    public stepb_cartesian_point[][][] getPtSets()
    {
        return this._ptSets;
    }
    public ArrayList getArrayOfOtherNodesIndex()
    {
        return this._arrayOfOtherNodesIndex;
    }



    public void addContactNode(stepb_cartesian_point[][]  ptSetsNodes)
    {
        _arrayConnectShell.add(ptSetsNodes);
    }

    public void setContactNodeFromOther(stepb_cartesian_point[][]  nodesFromOthers)
    {
        int iCount = _ptSets.length;
        int jCount = _ptSets[0].length;
        int kCount = _ptSets[0][0].length;

        int iiCount = nodesFromOthers.length;
        int jjCount = nodesFromOthers[0].length;


        for (int ii = 0; ii < iiCount; ii++)
        {
            for (int jj = 0; jj < jjCount; jj++)
            {

                for (int i = 0; i < iCount; i++)
                {
                    for (int j = 0; j < jCount; j++)
                    {
                        for (int k = 0; k < kCount; k++)
                        {
                            if (_ptSets[i][j][k].isEqualValue(nodesFromOthers[ii][jj]))
                            {
                                _ptSets[i][j][k]= nodesFromOthers[ii][jj];
                                _isFromOthers[i][j][k] = true;

                                i = iCount - 1;
                                j = jCount - 1;
                                k = kCount - 1;
                            }
                        }
                    }
                }


            }
        }
        int a = 0;
    }
    public void setContactNodeFromOtherH(stepb_cartesian_point[][][] ptSetsOthers, int faceIndex)
    {
        int iCount = _ptSets.length;
        int jCount = _ptSets[0].length;
        int kCount = _ptSets[0][0].length;

        int jjCount = ptSetsOthers.length;
        int iiCount = ptSetsOthers[0].length;
        int ttCount = ptSetsOthers[0][0].length;

        int iiStart = 0;
        int ttStart = 0;

        switch (faceIndex)
        {

            case DefineSystemConstant.BRICK_FACE_INNER:
                iiCount = 1;
                break;
            case DefineSystemConstant.BRICK_FACE_OUTER:
                iiStart = iiCount - 1;
                break;
            case DefineSystemConstant.BRICK_FACE_UP:
                ttCount = 1;
                break;
            case DefineSystemConstant.BRICK_FACE_DOWN:
                ttStart = ttCount - 1;
                break;

        }
        for (int jj = 0; jj < jjCount; jj++)
        {
            for (int ii = iiStart; ii < iiCount; ii++)
            {
                for (int tt = ttStart; tt < ttCount; tt++)
                {

                    for (int i = 0; i < iCount; i++)
                    {
                        for (int j = 0; j < jCount; j++)
                        {
                            for (int k = 0; k < kCount; k++)
                            {
                                if(_ptSets[i][j][k]!= ptSetsOthers[jj][ii][tt])  //比較來源點 與 被設點 是否同點
                                {
                                    if (_ptSets[i][j][k].isEqualValue(ptSetsOthers[jj][ii][tt]))  //來源點 與被設點 是否幾何位置相同
                                    {
                                        if (!_ptSets[i][j][k].getIsConnectionPt()) //被設點非連接點
                                        {
                                            _ptSets[i][j][k] = ptSetsOthers[jj][ii][tt];
                                            _ptSets[i][j][k].setIsConnectionPt(true);
                                        }
                                        else //被設點為連接點
                                        {
                                            if (!ptSetsOthers[jj][ii][tt].getIsConnectionPt()) //來源點非連接點==> 反設
                                            {
                                                ptSetsOthers[jj][ii][tt] = _ptSets[i][j][k];
                                                //ptSetsOthers[jj][ii][tt].setIsConnectionPt(true);
                                            }
                                            else //設點亦為連接點==> 接合順序錯誤...Error!!
                                            {
                                                System.err.println("Connection Point Problem!!!!");
                                            }
                                        }

                                        _isFromOthers[i][j][k] = true;

                                        i = iCount - 1;
                                        j = jCount - 1;
                                        k = kCount - 1;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public void setContactNodeFromOther(stepb_cartesian_point[][][] ptSetsOthers, int faceIndex)
    {
        int iCount = _ptSets.length;
        int jCount = _ptSets[0].length;
        int kCount = _ptSets[0][0].length;

        int jjCount = ptSetsOthers.length;
        int iiCount = ptSetsOthers[0].length;
        int ttCount = ptSetsOthers[0][0].length;

        int iiStart = 0;
        int ttStart = 0;

        switch (faceIndex)
        {

            case DefineSystemConstant.BRICK_FACE_INNER:
                ttCount = 1;
                break;
            case DefineSystemConstant.BRICK_FACE_OUTER:
                ttStart = ttCount - 1;
                break;
            case DefineSystemConstant.BRICK_FACE_UP:
                iiCount = 1;
                break;
            case DefineSystemConstant.BRICK_FACE_DOWN:
                iiStart = iiCount - 1;
                break;

        }
        for (int jj = 0; jj < jjCount; jj++)
        {
            for (int ii = iiStart; ii < iiCount; ii++)
            {
                for (int tt = ttStart; tt < ttCount; tt++)
                {

                    for (int i = 0; i < iCount; i++)
                    {
                        for (int j = 0; j < jCount; j++)
                        {
                            for (int k = 0; k < kCount; k++)
                            {
                                if(_ptSets[i][j][k]!= ptSetsOthers[jj][ii][tt])  //比較來源點 與 被設點 是否同點
                                {
                                    if (_ptSets[i][j][k].isEqualValue(ptSetsOthers[jj][ii][tt]))  //來源點 與被設點 是否幾何位置相同
                                    {
                                        if (!_ptSets[i][j][k].getIsConnectionPt()) //被設點非連接點
                                        {
                                            _ptSets[i][j][k] = ptSetsOthers[jj][ii][tt];
                                            _ptSets[i][j][k].setIsConnectionPt(true);
                                        }
                                        else //被設點為連接點
                                        {
                                            if (!ptSetsOthers[jj][ii][tt].getIsConnectionPt()) //來源點非連接點==> 反設
                                            {
                                                ptSetsOthers[jj][ii][tt] = _ptSets[i][j][k];
                                                //ptSetsOthers[jj][ii][tt].setIsConnectionPt(true);
                                            }
                                            else //設點亦為連接點==> 接合順序錯誤...Error!!
                                            {
                                                System.err.println("Connection Point Problem!!!!");
                                            }
                                        }

                                        _isFromOthers[i][j][k] = true;

                                        i = iCount - 1;
                                        j = jCount - 1;
                                        k = kCount - 1;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    public CartesianPointSetsBrick setDisplacementArray(stepb_cartesian_point_array displacementArray, double scale)
    {
        displacementArray.rearrangeTable();

        int iiLength = _ptSets.length;
        int jjLength = _ptSets[0].length;
        int kkLength = _ptSets[0][0].length;

        stepb_cartesian_point[][][] deformedPtsets = new stepb_cartesian_point[iiLength][jjLength][kkLength];
        for (int ii = 0; ii < iiLength; ii++)
        {
            for (int jj = 0; jj < jjLength; jj++)
            {
                for (int kk = 0; kk < kkLength; kk++)
                {
                    int IDNumber = _ptSets[ii][jj][kk].GetIDNumber();
                    stepb_cartesian_point ptDisplacement = displacementArray.getByID(IDNumber);
                    math_vector3d v3d = null;
                    if (ptDisplacement != null)
                    {
                        v3d = _ptSets[ii][jj][kk].GetMathVector3d().Add(ptDisplacement.GetMathVector3dScaled(scale));
                        deformedPtsets[ii][jj][kk] = new stepb_cartesian_point(v3d);
                        deformedPtsets[ii][jj][kk].SetIDNumber(_ptSets[ii][jj][kk].GetIDNumber());
                    }
                    else
                    {
                        deformedPtsets[ii][jj][kk] = new stepb_cartesian_point(_ptSets[ii][jj][kk]);
                        deformedPtsets[ii][jj][kk].SetIDNumber(_ptSets[ii][jj][kk].GetIDNumber());
                    }
                }
            }

        }
        CartesianPointSetsBrick deformedBrick = new CartesianPointSetsBrick(deformedPtsets);
        return deformedBrick;

    }

    public void writePoints(BufferedWriter bw) throws IOException
    {

        String strToWrite = "";

        int kCount = this._ptSets.length;
        int jCount = this._ptSets[0].length;
        int iCount = this._ptSets[0][0].length;
        int totalSize = kCount * jCount * iCount;

        for (int k = 0; k < kCount; k++)
        {
            for (int j = 0; j < jCount; j++)
            {
                for (int i = 0; i < iCount; i++)
                {
                    if(!_isFromOthers[k][j][i])
                    {
                        stepb_cartesian_point cpt = this._ptSets[k][j][i];
                        String str = cpt.PrintStr();

                        strToWrite += str + "\n";
                        int ii = k * jCount * iCount + j * iCount + i;

                        if ((ii - totalSize / 4) < 1 ||
                            (ii - totalSize / 2) < 1 ||
                            (ii - 3 * totalSize / 4) < 1 ||
                            ii == totalSize - 1)
                        {
                            bw.write(strToWrite, 0, strToWrite.length());
                            strToWrite = "";
                        }
                    }
                }
            }
        }
        bw.write(strToWrite, 0, strToWrite.length());
    }

    public void offsetPoints(double dx,double dy,double dz)
    {

        String strToWrite = "";

        int kCount = this._ptSets.length;
        int jCount = this._ptSets[0].length;
        int iCount = this._ptSets[0][0].length;
        int totalSize = kCount * jCount * iCount;
        System.out.println("=================Coil pt ID=======================");
        int idCount = 0;

        for (int k = 0; k < kCount; k++)
        {
            for (int j = 0; j < jCount; j++)
            {
                for (int i = 0; i < iCount; i++)
                {
                    stepb_cartesian_point cpt = this._ptSets[k][j][i];
                    cpt.GetMathVector3d().translate(dx,dy,dz);
                    System.out.print("Coil: " + cpt.GetIDNumber());
                    idCount++;
                    if(idCount % 8 == 0)
                        System.out.print(" \n");
                    else
                        System.out.print(" ,");

                }
            }
        }
    }
}
