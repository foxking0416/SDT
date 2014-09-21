package sdt.framework;

import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;

import javax.swing.*;

//import org.tempuri.*;

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
public class SDT_App
{
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public SDT_App()
    {
        SDT_System system = new SDT_System();

    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable() {
           public void run() {
               try {
                   UIManager.setLookAndFeel(UIManager.
                                            getSystemLookAndFeelClassName());
               } catch (Exception exception) {
                   exception.printStackTrace();
               }
               if (SDT_App.CheckLicense())
               {
                   new SDT_App();
               }
               else
                   System.out.println("no license");
           }
       });
   }

   static public boolean CheckLicense()
   {/*
       String clientName = "";
       String clientIPaddress = "";
       String clientHostName = "";
       String SoftwareVersionName = "SDT-20130423";
       try
       {
           clientName = System.getProperty("user.name");
           clientIPaddress = InetAddress.getLocalHost().getHostAddress();
           clientHostName = InetAddress.getLocalHost().getHostName();
       }
       catch (UnknownHostException ex)
       {
       }

       boolean isServer1Work = false;
       boolean isServer2Work = false;

       BasicHttpBinding_ILicenseServerFunctionStub binding = null;
       org.tempuri.LicenseServerFunctionLocator locator = null;

       try
       {
           locator = new org.tempuri.LicenseServerFunctionLocator();
           binding = (org.tempuri.BasicHttpBinding_ILicenseServerFunctionStub) locator.getBasicHttpBinding_ILicenseServerFunction();
           isServer1Work = binding.getPermission(SoftwareVersionName, clientName + "@" + clientIPaddress + "@" + clientHostName);
           if (!isServer1Work)
           {
               System.err.println("License Server OA Connected!!");
               System.err.println("License Server OA Doesn't Agree!!");
           }

       }
       catch (javax.xml.rpc.ServiceException jre)
       {
           //if (jre.getLinkedCause() != null)
           //    jre.getLinkedCause().printStackTrace();
           System.err.println("Error in Connecting First Server!!");
           //System.err.println("JAX-RPC ServiceException caught: " + jre);
           //throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
           isServer1Work = false;
       }
       catch (RemoteException ex)
       {
           //ex.printStackTrace();
           System.err.println("License Server OA Doesn't Response!!");
           isServer1Work = false;
       }

       if (!isServer1Work)
       {
           try
           {
               if (locator == null)
                   locator = new org.tempuri.LicenseServerFunctionLocator();
               locator.setBasicHttpBinding_ILicenseServerFunctionEndpointAddress("http://10.35.8.80/LicenseHttpCAD/LicenseServerFunction.svc");
               binding = (org.tempuri.BasicHttpBinding_ILicenseServerFunctionStub) locator.getBasicHttpBinding_ILicenseServerFunction();
               isServer2Work = binding.getPermission(SoftwareVersionName, clientName + "@" + clientIPaddress + "@" + clientHostName);
               if (!isServer2Work)
               {
                   System.err.println("License Server CAD Connected!!");
                   System.err.println("License Server CAD Doesn't Response!!");
               }
           }
           catch (javax.xml.rpc.ServiceException jreAgain)
           {
               if (jreAgain.getLinkedCause() != null)
                   jreAgain.getLinkedCause().printStackTrace();
               System.err.println("Error in Connecting License Server CAD!!");
               isServer2Work = false;
           }
           catch (RemoteException ex)
           {
               System.err.println("License Server CAD Doesn't Response!!");
               isServer2Work = false;
           }
       }

       if (isServer1Work)
       {
           System.out.println("Get License From Server 'OA'!!");
           return true;
       }
       if (isServer2Work)
       {
           System.out.println("Get License From Server 'CAD'!!");
           return true;
       }*/

       return true;

   }


}
