package io.leopard.myjetty.maven.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import io.leopard.myjetty.MyJettyServer;

/**
 * @goal run
 * @requiresDependencyResolution test
 * @execute phase="compile"
 * @description Runs jetty directly from a maven project
 */
public class MyJettyRunMojo extends AbstractMojo {

	/**
	 * The maven project.
	 *
	 * @parameter default-value="${project}"
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * server port
	 * 
	 * @parameter default-value="80"
	 */
	protected int port;

	/**
	 * The directory containing generated classes.
	 *
	 * @parameter default-value="${project.build.outputDirectory}"
	 * @required
	 * 
	 */
	protected File classesDirectory;

	/**
	 * @parameter
	 */
	private List<Vhost> vhosts;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		File target = classesDirectory.getParentFile();
		String artifactId = project.getArtifactId();

		String projectId = project.getParent().getArtifactId();

		String rootDir = project.getFile().getAbsolutePath();
		File webappDir = new File(target, artifactId);

		System.out.println("MyJettyRunMojo execute port:" + port);
		System.out.println("MyJettyRunMojo execute projectId:" + projectId);
		System.out.println("MyJettyRunMojo execute artifactId:" + artifactId);
		System.out.println("MyJettyRunMojo execute rootDir:" + rootDir);
		System.out.println("MyJettyRunMojo execute webappDir:" + webappDir.getAbsolutePath());

		MyJettyServer jetyServer = new MyJettyServer();
		try {
			jetyServer.start(port);

			List<String> hostList = new ArrayList<String>();
			hostList.add("localhost");
			jetyServer.startWebapp(webappDir.getAbsolutePath(), hostList);
			this.startVhosts(jetyServer);
			jetyServer.join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void startVhosts(MyJettyServer jetyServer) throws Exception {
		if (vhosts == null) {
			return;
		}
		for (Vhost vhost : vhosts) {
			String war = vhost.getWar();
			File dir = new File(war);
			if (!dir.exists()) {
				System.out.println("MyJettyRunMojo execute vhost:" + vhost + " 不存在.");
				continue;
			}
			System.out.println("MyJettyRunMojo execute vhost:" + vhost + " 启动中...");
			List<String> hostList = new ArrayList<String>();
			hostList.add(vhost.getHost());
			jetyServer.startWebapp(vhost.getWar(), hostList);
		}

	}

}
