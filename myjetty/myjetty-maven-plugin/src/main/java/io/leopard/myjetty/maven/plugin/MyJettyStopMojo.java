package io.leopard.myjetty.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * JettyStopMojo - stops a running instance of jetty.
 * 
 * The ff are required:
 * 
 * -DstopKey=someKey
 * 
 * -DstopPort=somePort
 * 
 * 
 * @goal stop
 * 
 * @description Stops jetty that is configured with &lt;stopKey&gt; and &lt;stopPort&gt;.
 */
public class MyJettyStopMojo extends AbstractMojo {

	// <stopPort>9084</stopPort>
	// <stopKey>shutdown9084</stopKey>
	// <stopWait>10</stopWait>

	/**
	 * Port to listen to stop jetty on sending stop command
	 * 
	 * @parameter
	 * @required
	 */
	protected int stopPort;

	/**
	 * Key to provide when stopping jetty on executing java -DSTOP.KEY=&lt;stopKey&gt; -DSTOP.PORT=&lt;stopPort&gt; -jar start.jar --stop
	 * 
	 * @parameter
	 * @required
	 */
	protected String stopKey;

	/**
	 * Max time in seconds that the plugin will wait for confirmation that jetty has stopped.
	 * 
	 * @parameter
	 */
	protected int stopWait;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

	}

}
