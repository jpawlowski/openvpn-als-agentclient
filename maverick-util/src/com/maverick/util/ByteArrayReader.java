
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.maverick.util;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.math.BigInteger;

/**
 *
 * <p>Utiltiy class to read common parameter types from a byte array.</p>
 *
 * @author Lee David Painter
 */
public class ByteArrayReader
    extends ByteArrayInputStream {

  private static String CHARSET_ENCODING = "UTF8";

  public static boolean encode;

  static {
      setCharsetEncoding(CHARSET_ENCODING);
  }
  /**
   * Construct a reader.
   * @param buffer
   * @param start
   * @param len
   */
  public ByteArrayReader(byte[] buffer, int start, int len) {
    super(buffer, start, len);
  }

  public ByteArrayReader(byte[] buffer) {
    super(buffer, 0, buffer.length);
  }

  /**
   * Provides access to the underlying array
   * @return byte[]
   */
  public byte[] array() {
      return this.buf;
  }

  /**
   * Allows the default encoding to be overriden for String variables processed
   * by the class. This currently defaults to UTF-8.
   *
   * @param charset
   * @throws UnsupportedEncodingException
   */
  public static void setCharsetEncoding(String charset) {
    try {

      String test = "123456890";
      test.getBytes(charset);
      CHARSET_ENCODING = charset;
      encode = true;
    }
    catch (UnsupportedEncodingException ex) {
      // Reset the encoding to default
      CHARSET_ENCODING = "";
      encode = false;
    }
  }

  /**
   * Get the current encoding being used for Strings variables.
   * @return
   */
  public static String getCharsetEncoding() {
    return CHARSET_ENCODING;
  }

  /**
   * Read until the buffer supplied is full.
   * @param b
   * @param off
   * @param len
   * @throws IOException
   */
  public void readFully(byte b[], int off, int len) throws IOException {
   if (len < 0)
       throw new IndexOutOfBoundsException();
   int n = 0;
   while (n < len) {
       int count = read(b, off + n, len - n);
       if (count < 0)
        throw new EOFException();
       n += count;
   }
  }
  
  /**
   * Write a boolean value to the array.
   * @param b
   * @throws IOException
   */
  public boolean readBoolean() throws IOException {
    return read()==1;
  }  

  public void readFully(byte[] b) throws IOException {
    readFully(b,0,b.length);
  }

  /**
   * Read a BigInteger from the array.
   * @return the BigInteger value.
   * @throws IOException
   */
  public BigInteger readBigInteger() throws IOException {
    int len = (int) readInt();
    byte[] raw = new byte[len];
    readFully(raw);
    return new BigInteger(raw);
  }

  public UnsignedInteger64 readUINT64() throws IOException {
    byte[] raw = new byte[8];
    readFully(raw);
    return new UnsignedInteger64(raw);
  }

  public UnsignedInteger32 readUINT32() throws IOException {
    return new UnsignedInteger32(readInt());
  }

  /**
   * Read an integer (4 bytes) from the array. This is returned as a long
   * as we deal with unsigned ints so the value may be higher than the
   * standard java int.
   * @param data
   * @param start
   * @return the value represent by a long.
   */
  public static long readInt(byte[] data, int start) {
    long ret = ( ( (long) (data[start] & 0xFF) << 24) & 0xFFFFFFFFL)
        | ( (data[start + 1] & 0xFF) << 16)
        | ( (data[start + 2] & 0xFF) << 8) | ( (data[start + 3] & 0xFF) << 0);

    return ret;
  }
  
  
  public static short readShort(byte[] data, int start) {
	  short ret = (short)((data[start+1] & 0xFF) << 8 | (data[start] & 0xFF) << 0);

	  return ret;
  }
/*public static int readInt(InputStream in) throws IOException {
   int ch1 = in.read();
    int ch2 = in.read();
    int ch3 = in.read();
    int ch4 = in.read();
    if((ch1 | ch2 | ch3 | ch4) < 0)
     throw new EOFException();
    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
     }*/

  /*public static void readFully(InputStream in, byte[] buf, int off, int len) throws IOException {
   if(len < 0)
    throw new IndexOutOfBoundsException();
   int count = 0;
   int read;
   while(count < len) {
    read = in.read(buf, off + count, len - count);
    if(read < 0)
     throw new EOFException();
    count += read;
   }
     }*/

  /**
   * Read a binary string from the array.
   * @return the byte array.
   * @throws IOException
   */
  public byte[] readBinaryString() throws IOException {
    int len = (int) readInt();
    byte[] buf = new byte[len];
    readFully(buf);
    return buf;
  }

  /**
   * Read an integer (4 bytes) from the array. This is returned as a long
   * as we deal with unsigned ints so the value may be higher than the
   * standard java int.
   * @return the integer value as a long.
   * @throws IOException
   */
  public long readInt() throws IOException {
    int ch1 = read();
    int ch2 = read();
    int ch3 = read();
    int ch4 = read();
    if ((ch1 | ch2 | ch3 | ch4) < 0)
        throw new EOFException();
    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0)) & 0xFFFFFFFFL;

  }

  /**
   * Read a String from the array.
   * @return the String value.
   * @throws IOException
   */
  public String readString() throws IOException {
    return readString(CHARSET_ENCODING);
  }

  /**
   * Read a String from the array converting using the given character set.
   * @param charset
   * @return
   * @throws IOException
   */
  public String readString(String charset) throws IOException {
    long len = readInt();

    if(len > available())
        throw new IOException("Cannot read string of length " + len
                              + " bytes when only " + available()
                              + " bytes are available");

    byte[] raw = new byte[ (int) len];
    readFully(raw);
    if (encode) {
      return new String(raw, charset);
    }
    else {
      return new String(raw);
    }

  }




  public short readShort() throws IOException {
    int ch1 = read();
    int ch2 = read();

    if ( (ch1 | ch2) < 0) {
      throw new EOFException();
    }

    return (short) ( (ch1 << 8) + (ch2 << 0));
  }

  /**
   * Reads an MPINT using the first 32 bits as the length prefix
   * @return
   * @throws IOException
   */
  public BigInteger readMPINT32() throws IOException {
    int bits = (int)readInt();

    byte[] raw = new byte[ (bits + 7) / 8 + 1];

    raw[0] = 0;
    readFully(raw, 1, raw.length - 1);

    return new BigInteger(raw);

  }

  /**
   * Reads a standard SSH1 MPINT using the first 16 bits as the length prefix
   * @return
   * @throws IOException
   */
  public BigInteger readMPINT() throws IOException {
    short bits = readShort();

    byte[] raw = new byte[ (bits + 7) / 8 + 1];

    raw[0] = 0;
    readFully(raw, 1, raw.length - 1);

    return new BigInteger(raw);
  }

  /**
   * Get the current position within the array.
   * @return the current position within the array
   */
  public int getPosition() {
    return this.pos;
  }
}
