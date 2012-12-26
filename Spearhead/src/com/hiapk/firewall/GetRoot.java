package com.hiapk.firewall;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.hiapk.spearhead.R;

import android.app.AlertDialog;
import android.content.Context;

public class GetRoot {

	public static boolean hasRoot = false;
	private static final String SCRIPT_FILE = "firewall.sh";

	public static boolean cmdRoot(String cmd) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}
	
	

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

	public static boolean hasRootAccess(Context context, boolean showErrors) {
		if (hasRoot)
			return true;
		final StringBuilder res = new StringBuilder();
		try {
			if (assertBinaries(context, true)) {
				hasRoot = true;
//				assertBinaries(context, true);
				return true;
			}
		} catch (Exception e) {
		}
		if (showErrors) {
			new AlertDialog.Builder(context).setNeutralButton("确定", null)
					.setMessage("无法获取root权限.\n"
							+ "错误信息: " + res.toString()).show();
		}
		return false;
	}


	public static int runScript(Context context, String script,
			StringBuilder res, long timeout, boolean asroot) {
		final File file = new File(context.getDir("bin", 0), SCRIPT_FILE);
		final ScriptRunner runner = new ScriptRunner(file, script, res, asroot);
		runner.start();
		try {
			if (timeout > 0) {
				runner.join(timeout);
			} else {
				runner.join();
			}
			if (runner.isAlive()) {
				runner.interrupt();
				runner.join(150);
				runner.destroy();
				runner.join(50);
			}
		} catch (Exception e) {

		}
		return runner.exitcode;

	}

	/**
	 * Internal thread used to execute scripts (as root or not).
	 */
	private static final class ScriptRunner extends Thread {
		private final File file;
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
					exec = Runtime.getRuntime().exec("su -c " + abspath);
				} else {
					exec = Runtime.getRuntime().exec("sh " + abspath);
				}
				InputStreamReader r = new InputStreamReader(
						exec.getInputStream());
				final char buf[] = new char[1024];
				int read = 0;
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
				r = new InputStreamReader(exec.getErrorStream());
				read = 0;
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
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


	/**
	 * Asserts that the binary files are installed in the cache directory.
	 * 
	 * @param ctx
	 *            context
	 * @param showErrors
	 *            indicates if errors should be alerted
	 * @return false if the binary files could not be installed
	 */
	public static boolean assertBinaries(Context ctx, boolean showErrors) {
		boolean changed = false;
		try {
			File file = new File(ctx.getDir("bin", 0), "iptables_armv5");
			if (!file.exists()) {
				copyRawFile(ctx, R.raw.iptables_armv5, file, "755");
				changed = true;
			}
			file = new File(ctx.getDir("bin", 0), "busybox_g1");
			if (!file.exists()) {
				copyRawFile(ctx, R.raw.busybox_g1, file, "755");
				changed = true;
			}
			if (changed) {
			}
		} catch (Exception e) {
			if (showErrors)
				alert(ctx, "Error installing binary files: " + e);
			return false;
		}
		return true;
	}

	/**
	 * Copies a raw resource file, given its ID to the given location
	 * 
	 * @param ctx
	 *            context
	 * @param resid
	 *            resource id
	 * @param file
	 *            destination file
	 * @param mode
	 *            file permissions (E.g.: "755")
	 * @throws IOException
	 *             on error
	 * @throws InterruptedException
	 *             when interrupted
	 */
	private static void copyRawFile(Context ctx, int resid, File file,
			String mode) throws IOException, InterruptedException {
		final String abspath = file.getAbsolutePath();
		final FileOutputStream out = new FileOutputStream(file);
		final InputStream is = ctx.getResources().openRawResource(resid);
		byte buf[] = new byte[1024];
		int len;
		while ((len = is.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();
		is.close();
		Runtime.getRuntime().exec("chmod " + mode + " " + abspath).waitFor();
	}


	/**
	 * Display a simple alert box
	 * 
	 * @param ctx
	 *            context
	 * @param msg
	 *            message
	 */
		public static void alert(Context ctx, CharSequence msg) {
			if (ctx != null) {
				new AlertDialog.Builder(ctx)
						.setNeutralButton(android.R.string.ok, null)
						.setMessage(msg).show();
			}
		}
}
