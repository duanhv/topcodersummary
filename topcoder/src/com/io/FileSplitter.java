package com.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class FileSplitter {
	public static final String CONFIG_FILE = "/com/io/Splitter.properties";
	public static final java.util.Properties appConfig = new java.util.Properties();

	public static String 	inputpatch 		= "";
	public static String 	outputpatch 	= "";
	public static long 		filesize 		= 0;
	public static String 	filename 		= "";
	public static int 		buffersize 		= 0;

	public FileSplitter() {
		try {
			InputStream configStream = this.getClass().getResourceAsStream(
					CONFIG_FILE);
			if (configStream != null) {
				appConfig.load(configStream);
			} else {
				throw new Exception("config stream is null");
			}
		} catch (Exception ex) {
			System.err.print("can not load config file " + CONFIG_FILE);
			ex.printStackTrace();
		}
		inputpatch = appConfig.getProperty("input.path");
		outputpatch = appConfig.getProperty("output.path");
		filesize = Long.parseLong(appConfig.getProperty("split.size"))* 1048576l;
		filename = appConfig.getProperty("split.file.name");
		buffersize = Integer.parseInt(appConfig.getProperty("split.buffer.size"))* 1048576; // 2^20 <=> convert M to Byte
		

	}

	public static void main(String args[]) throws Exception {
		FileSplitter splitter = new FileSplitter();

//		int buffersize = 2 * 1048576;
		FileChannel sourceChanel = null;
		try {
			sourceChanel = new FileInputStream(inputpatch).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(buffersize);

			FileChannel outputChanel = null;

			long totalBytesRead = 0; // total bytes read from channel
			long totalBytesWritten = 0; // total bytes written to output
			long outputPartNumber = 0;
			long outputPartWriten = 0;
			try {
				System.out.println("Position 1st: " + sourceChanel.position());
				for (int byteRead = sourceChanel.read(buffer); byteRead != -1; byteRead = sourceChanel.read(buffer)) {
					totalBytesRead += byteRead;
					
					System.out.println("Position: " + sourceChanel.position());
					
					System.out.println(String.format("already read %d byte, total byte read %d/%d",byteRead,totalBytesRead,sourceChanel.size()));
					buffer.flip();
					int byteWritenFromBuffer = 0;
					
					while (buffer.hasRemaining()) {
						if (outputChanel == null) {
							outputPartNumber++;
							outputPartWriten = 0;
							
							String outputName = filename+outputPartNumber;
							
							System.out.println(String.format("crate new output chanel %s", outputName));
							outputChanel = new FileOutputStream(outputpatch
									+ File.separator + outputName).getChannel();
							
						}
						
						long bytePartFree = (filesize - outputPartWriten);
						int byteToWrite = (int) Math.min(buffer.remaining(),
								bytePartFree);
						
						buffer.limit(byteWritenFromBuffer + byteToWrite);
						int byteWriten = outputChanel.write(buffer);
						
						outputPartWriten += byteWriten;
						byteWritenFromBuffer += byteWriten;
						totalBytesWritten += byteWriten;
						// System.out.println("Wore %d to part file, %d will be write part, %d ");
						buffer.limit(byteRead);
						
						if (totalBytesWritten == sourceChanel.size()) {
							System.out.println("finished");
							closeChannel(outputChanel);
							outputChanel = null;
							break;
							
						} else if (outputPartWriten == filesize) {
							closeChannel(outputChanel);
							outputChanel = null;
						}
					}
					buffer.clear();
				}
			} finally {
				System.out.println("finally 1");
				closeChannel(outputChanel);
			}
		} finally {
			System.out.println("finally 2");
			closeChannel(sourceChanel);
		}
	}
	private static void closeChannel(FileChannel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
		}
	}
}