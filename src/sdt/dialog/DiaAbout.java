package sdt.dialog;





import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sdt.framework.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DiaAbout extends DiaMain
{

    public DiaAbout(SDT_System system)
    {

        super(system, true);

        Font font1 = new Font("Verdana", Font.BOLD, 16);
        Font font2 = new Font("Verdana", Font.PLAIN, 14);
        Font font3 = new Font("Verdana", Font.PLAIN, 12);

        this._contentPane.setFont(font2);
        //this._contentPane.setLayout(new BorderLayout()) ;
        this._contentPane.setLayout(null) ;

        Icon iconSDT = new ImageIcon(this.getClass().getResource("/sdt/icon/AboutSDT.jpg"));

        JLabel logoLabel = new JLabel(iconSDT);
        //logoLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        //this._contentPane.add(logoLabel, BorderLayout.NORTH);
        logoLabel.setBounds(5,5,430,227);
        this._contentPane.add(logoLabel);

        String labelText = "<html>Copyright (c) 2002-2013 FOXCONN corporation. <br> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp All rights reserved.</html>";
        JLabel copyrightLabel = new JLabel(labelText, JLabel.CENTER);
        copyrightLabel.setBorder(BorderFactory.createTitledBorder(""));
        copyrightLabel.setFont(font3);
        copyrightLabel.setBounds(5,227,430,40);
        this._contentPane.add(copyrightLabel);

        labelText = "<html> Warning: <br>" +
                           " &nbsp&nbsp&nbsp&nbsp&nbsp This computer program is protected by copyright law and <br>" +
                           " &nbsp&nbsp&nbsp&nbsp&nbsp international treaties. Unauthorized reproduction or <br>" +
                           " &nbsp&nbsp&nbsp&nbsp&nbsp distribution of this program, or any portion of it, may result <br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp in severe civil and  criminal penalties, and will be prosecuted <br>" +
                           " &nbsp&nbsp&nbsp&nbsp&nbsp to the maximum extent possible under the law.<br> <br>" +
                           " &nbsp&nbsp&nbsp&nbsp&nbsp If any question, please contact <br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Roger S.N. Chao ,Fox W.H Tu  /FEA<br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp \u8D99\u7526\u8FB2 , \u675c\u7dad\u8b19/ \u5DE5\u7A0B\u5206\u6790 <br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp TEL:511-1260 / 511-2851<br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp E-mail:Roger S.N. Chao/NWInG/FOXCONN<br>"+
                           " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Fox W.H. Tu/NWInG/FOXCONN</html>";

        JLabel warningLabel = new JLabel(labelText, JLabel.CENTER);
        warningLabel.setBorder(BorderFactory.createTitledBorder(""));
        warningLabel.setFont(font3);
        warningLabel.setBounds(5,270,430,210);
        this._contentPane.add(warningLabel);
        //this._contentPane.add(warningLabel, BorderLayout.SOUTH);

        this._jButtonOk.setBounds(185,480,80,25);
        this._contentPane.add( this._jButtonOk);



        this.setSize(440,510) ;
        this.setTitle("SDT Information") ;
        this.setLocation();
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent e)
    {
        if ((e.getSource() == this._jButtonClose)
            || (e.getSource() == this._jButtonOk))
        {
            this.dispose();
        }
    }

    protected void createComponent()
    {
    }

    protected void setSizeComponent()
    {
    }

    protected void addComponent()
    {
    }

    protected void addListener()
    {
        /*this._jButtonCancel.addActionListener(this);
        this._jButtonOk.addActionListener(this);
        this._jButtonClose.addActionListener(this);*/
    }

    protected boolean checkTextfield()
    {
        return false;
    }

    protected void readDiaData()
    {
    }
}
