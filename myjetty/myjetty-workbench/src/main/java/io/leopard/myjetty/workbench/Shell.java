package io.leopard.myjetty.workbench;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Shell {

	public static String execute(String command, String dir) throws IOException {
		return execute(command, dir, System.out);
	}

	public static String execute(String command, String dir, OutputStream output) throws IOException {
		output.write((dir + "# " + command + "\n").getBytes());
		output.flush();

		String charset;
		if (System.getProperty("os.name").startsWith("Windows")) {
			charset = "GBK";
			command = "cmd /c " + command;
		}
		else {
			charset = "UTF-8";
		}
		// long start = System.currentTimeMillis();
		Process ps = Runtime.getRuntime().exec(command, null, new File(dir));

		// Process ps = runtime.exec(cmd);

		// long time = System.currentTimeMillis() - start;
		// System.out.print("time:" + time);

		StringBuffer sb = new StringBuffer();
		InputStream is = ps.getInputStream();
		print(is, output, sb, charset);

		InputStream error = ps.getInputStream();
		print(error, output, sb, charset);

		String result = sb.toString();
		ps.destroyForcibly();
		// try {
		// ps.waitFor();
		// }
		// catch (InterruptedException e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		// System.out.println("##############################");
		return result;
	}

	protected static void print(InputStream input, OutputStream output, StringBuffer sb, String charset) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input, charset));
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			if (output != null) {
				output.write(line.getBytes());
				output.write("\n".getBytes());
				output.flush();
			}
			sb.append(line).append("\n");
		}
	}

	private void start() throws IOException, InterruptedException {
		String str;
		str = execute("mvn -version", "/work/olla/zhongcao/");
		System.out.println("str:" + str);
		//
		// str = this.execute("cmd /c cd /work/olla/zhongcao/");
		// System.out.println("str:" + str);

		// str = execute("cmd /c dir", "/work/olla/zhongcao/");
		str = execute("mvn -version", "/work/olla/zhongcao/");
		System.out.println("str:" + str);

	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Shell().start();
	}

}
