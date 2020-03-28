package com.jayaprabahar.util.sftp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.AllArgsConstructor;

/**
 * <p> Project : com.jayaprabahar.util.sftp </p>
 * <p> Title : SFTPUtil.java </p>
 * <p> Description: This utility class handles SFTP file transfer transaction </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
@AllArgsConstructor
public final class SFTPUtil {

	private String hostname;
	private String login;
	private String password;
	private String destinationDirectory;
	private String destinationFile;
	private String sourceFilePath;

	/**
	 * @throws JSchException
	 * @throws SftpException
	 * @throws FileNotFoundException
	 */
	public void sendFile() throws JSchException, SftpException, FileNotFoundException {
		int permissionReadWrite = 0664;
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "yes");

		JSch ssh = new JSch();
		Session session = ssh.getSession(login, hostname, 22);
		session.setConfig(config);
		session.setPassword(password);
		session.connect();
		Channel channel = session.openChannel("SFTP");
		channel.connect();

		ChannelSftp sftp = (ChannelSftp) channel;

		try (FileInputStream fileInputStream = new FileInputStream(sourceFilePath)) {
			// Move to the destination directory
			sftp.cd(destinationDirectory);
			// Remove if any files exist
			/* sftp.rm(destinationFile); */
			// Copy the source file to the destination directory
			sftp.put(fileInputStream, destinationFile, ChannelSftp.OVERWRITE);
			// Change file permission
			sftp.chmod(permissionReadWrite, destinationFile);
		} catch (SftpException e) {
			if (e.id != ChannelSftp.SSH_FX_NO_SUCH_FILE) {
				throw e;
			}
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			sftp.exit();
			channel.disconnect();
			session.disconnect();
		}

	}

}
