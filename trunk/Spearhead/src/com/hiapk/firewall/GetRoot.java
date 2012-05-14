package com.hiapk.firewall;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.AlertDialog;
import android.content.Context;

public class GetRoot {

   private static boolean hasRoot = false;
   private static final String SCRIPT_FILE = "firewall.sh";
	//获取root权限
	public static boolean cmdRoot(String cmd)
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
	//是否root过
	public static boolean isRoot() {
		boolean blnResult = false;
		File su = new File("/tmp/su.txt");
		FileOutputStream fos = null;
		try {
		     fos = new FileOutputStream(su);
		} catch (Exception e) {
		     e.printStackTrace();
		   } finally {
	     if (fos != null) {
	 	    try {
		          fos.close();
		         } catch (Exception e) {
		        e.printStackTrace();
	              	}
	              }
	          	}
		if (su.exists()) {
		   blnResult = true;
		   try {
		     su.delete();
		       } catch (SecurityException e) {
	             	e.printStackTrace();
	           	}
	    	}
		return blnResult;
		}
		
     public static boolean hasRootAccess(Context context , boolean showErrors)
     {
    	if(hasRoot)
    	   return true;
    	final StringBuilder res = new StringBuilder();
    	try{
    		if(runScriptAsRoot(context,"exit 0",res) == 0)
    		{
    			hasRoot = true ;
    			return true;
    		}
    	}catch(Exception e){
    		}
    	if(showErrors)
    	{
    		new AlertDialog.Builder(context)
			.setNeutralButton("确定", null)
			.setMessage("Could not acquire root access.\n"
					+ "You need a rooted phone to run DroidWall.\n\n"
					+ "If this phone is already rooted, please make sure DroidWall has enough permissions to execute the \"su\" command.\n"
					+ "Error message: " + res.toString()).show();
    	}
    	return false;
     }
     
     public static int runScriptAsRoot(Context context ,String script , StringBuilder res) throws IOException
     {
		return runScriptAsRoot(context,script ,res,40000);
     }
     public static int runScriptAsRoot(Context context,String script , StringBuilder res,long timeout)
     {
		return runScript(context,script,res,timeout,true);
     }
     public static int runScript(Context context,String script ,StringBuilder res,long timeout, boolean asroot)
     {
    	 final File file = new File(context.getDir("bin", 0),SCRIPT_FILE);
    	 final ScriptRunner runner =  new ScriptRunner(file ,script ,res,asroot);
    	 runner.start();
    	 try{
    		 if(timeout > 0){
    			 runner.join(timeout);
    		 }else{
    			 runner.join();
    		 }
    		 if(runner.isAlive())
    		 {
    			 runner.interrupt();
    			 runner.join(150);
    			 runner.destroy();
    			 runner.join(50);
    		 }
    	 }catch(Exception e){
    		 
    	 }
		return runner.exitcode;
    	 
     }
     
     
     private static final class ScriptRunner extends Thread{
    	 private final File file ;
    	 private final String script;
    	 private final StringBuilder res;
 		 private final boolean asroot;
 		 public int exitcode = -1;
 		 private Process exec;
 		 
 	     public ScriptRunner(File file, String script, StringBuilder res,
				boolean asroot) {
			this.file = file;
			this.script = script;
			this.res = res;
			this.asroot = asroot;
		}

		@Override
		public void run() {
			try {
				file.createNewFile();
				final String abspath = file.getAbsolutePath();
				// make sure we have execution permission on the script file
				Runtime.getRuntime().exec("chmod 777 " + abspath).waitFor();
				// Write the script to be executed
				final OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(file));
				if (new File("/system/bin/sh").exists()) {
					out.write("#!/system/bin/sh\n");
				}
				out.write(script);
				if (!script.endsWith("\n"))
					out.write("\n");
				out.write("exit\n");
				out.flush();
				out.close();
				if (this.asroot) {
					// Create the "su" request to run the script
					exec = Runtime.getRuntime().exec("su -c " + abspath);
				} else {
					// Create the "sh" request to run the script
					exec = Runtime.getRuntime().exec("sh " + abspath);
				}
				InputStreamReader r = new InputStreamReader(
						exec.getInputStream());
				final char buf[] = new char[1024];
				int read = 0;
				// Consume the "stdout"
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
				// Consume the "stderr"
				r = new InputStreamReader(exec.getErrorStream());
				read = 0;
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
				// get the process exit code
				if (exec != null)
					this.exitcode = exec.waitFor();
			} catch (InterruptedException ex) {
				if (res != null)
					res.append("\nOperation timed-out");
			} catch (Exception ex) {
				if (res != null)
					res.append("\n" + ex);
			} finally {
				destroy();
			}
		}
		/**
		 * Destroy this script runner
		 */
		public synchronized void destroy() {
			if (exec != null)
				exec.destroy();
			exec = null;
		}
     }
     

}
