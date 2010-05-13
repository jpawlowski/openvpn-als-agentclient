package com.maverick.multiplex.channels;

import java.io.IOException;
import java.net.Socket;

//import com.adito.applications.ApplicationShortcutDatabaseFactory;
//import com.adito.extensions.ShortcutParameterItem;
import com.maverick.util.ByteArrayReader;
import com.maverick.util.ByteArrayWriter;
import com.maverick.multiplex.Channel;
import com.maverick.multiplex.ChannelOpenException;
import com.maverick.multiplex.IOStreamConnector;
import com.maverick.multiplex.MultiplexedSocketFactory;

import java.util.Date;
import java.util.Hashtable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class LocalForwardingChannel extends Channel {

	public static final String CHANNEL_TYPE = "direct-tcpip";
	
	// Protected instance variables
	
	protected String hostname = null;
	protected int port;	
	protected Socket socket = null;
	
	protected Hashtable params;
	//
	IOStreamConnector input;
	IOStreamConnector output;
	
	public LocalForwardingChannel(String channelType, String hostname, int port) {
		super(channelType, 32768, 35000);
		this.hostname = hostname;
		this.port = port;
	}

	public LocalForwardingChannel(String hostname, int port, Hashtable params) {
		super(CHANNEL_TYPE, 32768, 35000);
		this.hostname = hostname;
		this.port = port;
		this.params = params;
	} 
	public LocalForwardingChannel(String hostname, int port) {
		super(CHANNEL_TYPE, 32768, 35000);
		this.hostname = hostname;
		this.port = port;
	}
	
	public LocalForwardingChannel(String channelType) {
		super(channelType, 32768, 35000);
	}
	
	public LocalForwardingChannel() {
		this(CHANNEL_TYPE);
	}
	
	public byte[] create() throws IOException {
		ByteArrayWriter msg = new ByteArrayWriter();
		msg.writeString(hostname);
		msg.writeInt(port);
		//add for aduti by will.zhou
		if(params != null)
			msg.writeString(params.toString());
		else
			msg.writeString("");
		return msg.toByteArray();
	}

	public void onChannelClose() {
		if(input!=null)
			input.close();
		if(output!=null)
			output.close();
		try {
			if(socket!=null)
				socket.close();
		} catch (IOException e) {
		}
	}

	public void onChannelOpen(byte[] data) {
		
		String fileid = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		if(!this.hostname.equals("127.0.0.1")){
			fileid = this.getConnection().getFileid()+"-"+this.hostname+"-"+dateFormat.format(date)+".rdp";
			/*			Map parameterMap = new HashMap();
	        parameterMap.put("hostname", "127.0.0.1");
	        parameterMap.put("port", "3389");
	        parameterMap.put("username", fileid);
	        try{
	        	ApplicationShortcutDatabaseFactory.getInstance().createApplicationShortcut(
					"adito-application-properjavardp", fileid, "", parameterMap, false, 1);
	        }catch(Exception e){
	        	fileid = null;
	        }*/
		}
		if(socket!=null) {
			try {
				input = new IOStreamConnector(socket.getInputStream(),
						getOutputStream(), fileid);
				output = new IOStreamConnector(getInputStream(),
						socket.getOutputStream());
			} catch(IOException ex) {
				close();
			}
	    }
	}

	public byte[] open(byte[] data) throws IOException, ChannelOpenException {
		ByteArrayReader msg = new ByteArrayReader(data);
		this.hostname = msg.readString();
		this.port = (int) msg.readInt();
        this.socket = MultiplexedSocketFactory.getDefault().createSocket(hostname, port);
		return null;
	}
}