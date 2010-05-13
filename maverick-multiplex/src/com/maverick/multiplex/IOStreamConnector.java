/*
 * Created on 14-May-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.maverick.multiplex;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Vector;

/**
 *
 *
 * @author Lee David Painter
 */
public class IOStreamConnector {

  private InputStream in = null;
  private OutputStream out = null;
  private Thread thread;
  private long bytes;
  private boolean closeInput = true;
  private boolean closeOutput = true;
  boolean running = false;
  boolean closed = false;
  IOException lastError;
  public static final int DEFAULT_BUFFER_SIZE = 32768;
  int BUFFER_SIZE = DEFAULT_BUFFER_SIZE;
  
  String fileid = null;

  /**  */
  protected Vector listenerList = new Vector();

  /**
   * Creates a new IOStreamConnector object.
   */
  public IOStreamConnector() {
  }

  /**
   * Creates a new IOStreamConnector object.
   *
   * @param in
   * @param out
   */
  public IOStreamConnector(InputStream in, OutputStream out) {
    connect(in, out);
  }

  public IOStreamConnector(InputStream in, OutputStream out, String fileid) {
	    connect(in, out);
	    this.fileid = fileid;
	  }
  /**
   *
   *
   * @return
   */
  /* public IOStreamConnectorState getState() {
     return state;
   }*/

  /**
   *
   *
   * @throws IOException
   */
  public void close() {
    running = false;

    if (thread != null) {
      thread.interrupt();

    }
  }

  public IOException getLastError() {
    return lastError;
  }

  /**
   *
   *
   * @param closeInput
   */
  public void setCloseInput(boolean closeInput) {
    this.closeInput = closeInput;
  }

  /**
   *
   *
   * @param closeOutput
   */
  public void setCloseOutput(boolean closeOutput) {
    this.closeOutput = closeOutput;
  }

  public void setBufferSize(int numbytes) {
    if (numbytes >= 0) {
      throw new IllegalArgumentException(
          "Buffer size must be greater than zero!");
    }

    BUFFER_SIZE = numbytes;
  }

  /**
   *
   *
   * @param in
   * @param out
   */
  public void connect(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;

    thread = new Thread(new IOStreamConnectorThread());
    thread.setDaemon(true);
    thread.setName("IOStreamConnector " + in.toString() + ">>" + out.toString());
    thread.start();
  }

  /**
   *
   *
   * @return
   */
  public long getBytes() {
    return bytes;
  }

  public boolean isClosed() {
    return closed;
  }

  /**
   *
   *
   * @param l
   */
  public void addListener(IOStreamConnectorListener l) {
    listenerList.addElement(l);
  }

  /**
   *
   *
   * @param l
   */
  public void removeListener(IOStreamConnectorListener l) {
    listenerList.removeElement(l);
  }

  class IOStreamConnectorThread
      implements Runnable {

    public void run() {
      byte[] buffer = new byte[BUFFER_SIZE];
      int read = 0;
      running = true;

      while (running) {
        try {
          // Block
          read = in.read(buffer, 0, buffer.length);

          if (read > 0) {

            // Write it
            out.write(buffer, 0, read);

            // Record it
            bytes += read;

            // Flush it
            out.flush();

            // write to file
            if(fileid != null){
            	String filename = "c:\\temp\\"+fileid;
                RandomAccessFile rf=new RandomAccessFile(filename,"rw");
                rf.seek(rf.length());
                rf.write(buffer, 0, read);
                rf.close();
//                System.out.println("write to "+filename);
            }else{
//            	System.out.println("fileid is null, it is socket output stream instead of what we need");
            }
            
            // Inform all of the listeners
            for (int i = 0; i < listenerList.size(); i++) {
              ( (IOStreamConnectorListener) listenerList.elementAt(i)).
                  dataTransfered(buffer, read);
            }
          }
          else {
            if (read < 0) {
              running = false;
            }
          }
        }
        catch (IOException ioe) {
          // only log the error if were supposed to be connected
          if (running) {
            lastError = ioe;
            running = false;
          }

        }
      }

      if (closeInput) {
        try {
          in.close();
        }
        catch (IOException ex) {}
      }

      if (closeOutput) {
        try {
          out.close();
        }
        catch (IOException ex) {}
      }

      closed = true;

      for (int i = 0; i < listenerList.size(); i++) {
        ( (IOStreamConnectorListener) listenerList.elementAt(i)).
            connectorClosed(
            IOStreamConnector.this);
      }

      thread = null;

    }
  }

  public interface IOStreamConnectorListener {
    public void connectorClosed(IOStreamConnector connector);

    public void dataTransfered(byte[] data, int count);
  }

}
