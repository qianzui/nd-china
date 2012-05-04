package com.hiapk.firewall;

import java.io.DataOutputStream;

public class GetRoot {

	public boolean cmdRoot(String cmd)
	{
		Process process = null;
		        DataOutputStream os = null;
		        try{
		            process = Runtime.getRuntime().exec("su");
		            os = new DataOutputStream(process.getOutputStream());
		            os.writeBytes(cmd+ "\n");
		            os.writeBytes("exit\n");
		            os.flush();
		            process.waitFor();
		        } catch (Exception e) {
		            return false;
		        } finally {
		            try {
		                if (os != null)   {
		                    os.close();
		                }
		                process.destroy();
		            } catch (Exception e) {
		            }
		        }
		        return true;
		    }
		
	}
