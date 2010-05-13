
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

import java.io.*;

/**
 *
 * <p>This class processes text data and corrects line endings according to
 * the settings provided. Specifically you can set the input style to process
 * so that lone \r and \n characters within a \r\n encoded file are written
 * through without amendments (the SFTP protocol states that this must be
 * the case when processing text files).</p>
 *
 * @author Lee David Painter
 */
public class EOLProcessor {

        /**
         * This output style specifies that the text will have line endings set to
         * the current system setting.
         */
        public final static int TEXT_SYSTEM = 0;
        /**
         * This indicates a CRLF line ending combinarion and can be used
         * for both input and output style parameters.
         */
        public final static int TEXT_WINDOWS = 1;
        /**
         * This indicates a CRLF line ending combinarion and can be used
         * for both input and output style parameters.
         */
        public final static int TEXT_DOS = 1;
        /*
         * This indicates a CRLF line ending combinarion and can be used
         * for both input and output style parameters.
         */
        public final static int TEXT_CRLF = 1;
        /**
         * This indicates a LF line ending style and can be
         * used for either input or output style parameters
         */
        public final static int TEXT_UNIX = 2;
        /**
         * This indicates a single LF line ending style and can be used for
         * either input or output style parameters
         */
        public final static int TEXT_LF = 2;
        /**
         * This indicates a MAC line ending and can be used in either the
         * input or output style parameters
         */
        public final static int TEXT_MAC = 3;
        /**
         * This indicates a CR line ending style and can be used for either
         * input or output style parameters
         */
        public final static int TEXT_CR = 3;

        /**
         * This input style instructs the conversion to strip
         * all line ending type characters and replace with the output style
         * line ending
         */
        public final static int TEXT_ALL = 4;

        byte[] lineEnding;
        String systemNL = System.getProperty("line.separator");
        boolean stripCR = false;;
        boolean stripLF = false;
        boolean stripCRLF = false;
        boolean encounteredBinary = false;

        boolean lastCharacterWasCR = false;

        OutputStream out;

        public EOLProcessor(int inputStyle, int outputStyle, OutputStream out) throws IOException {

            this.out = out;

            switch (inputStyle) {
                    case TEXT_CRLF: {
                             stripCRLF = true;
                    } break;
                    case TEXT_CR: {
                             stripCR = true;
                    } break;
                    case TEXT_LF: {
                             stripLF = true;
                    } break;
                    case TEXT_ALL: {
                        stripCR = true;
                        stripLF = true;
                        stripCRLF = true;
                    } break;
                    case TEXT_SYSTEM:
                    {
                        byte[] tmp = systemNL.getBytes();
                        if(tmp.length == 2 && tmp[0] == '\r' && tmp[1] == '\n') {
                            stripCRLF = true;
                        } else if(tmp.length==1 && tmp[0] == '\r') {
                            stripCR = true;
                        } else if(tmp.length==1 && tmp[0] == '\n') {
                            stripLF = true;
                        } else {
                           throw new IOException("Unsupported system EOL mode");
                        }
                        break;
                    }
                    default: {
                            throw new IllegalArgumentException("Unknown text style: " + outputStyle);
                    }
            }


            switch (outputStyle) {
                    case TEXT_SYSTEM: {
                             lineEnding = systemNL.getBytes();
                    } break;
                    case TEXT_CRLF: {
                             lineEnding = new byte[]{(byte)'\r',(byte)'\n'};
                    } break;
                    case TEXT_CR: {
                             lineEnding = new byte[]{(byte)'\r'};
                    } break;
                    case TEXT_LF: {
                             lineEnding = new byte[]{(byte)'\n'};
                    } break;
                    case TEXT_ALL: {
                        throw new IllegalArgumentException("TEXT_ALL cannot be used for an output style");
                    }
                    default: {
                            throw new IllegalArgumentException("Unknown text style: " + outputStyle);
                    }
            }
        }

        /**
         * Check to see if binary data was encountered during the encoding
         * process
         * @return boolean
         */
        public boolean hasBinary() {
            return encounteredBinary;
        }

        public void close() throws IOException {

            if(lastCharacterWasCR && !stripCR)
                out.write('\r');
            
            out.close();
        }

        /**
         *
         * @param buf byte[]
         * @param off int
         * @param len int
         * @param out OutputStream
         * @throws IOException
         */
        public void processBytes(byte[] buf,
                                    int off,
                                    int len)
                throws IOException {


                BufferedInputStream bin = new BufferedInputStream(
                         new ByteArrayInputStream(buf, off, len), 32768);

                int b;
                while((b = bin.read()) != -1){

                    if (b == '\r') {

                        if(stripCRLF) {
                            bin.mark(1);
                            int ch = bin.read();
                            if(ch==-1) {
                                lastCharacterWasCR = true;
                                break;
                            }
                            if(ch == '\n') {
                                // This is STYLE_RN do we output as is or replace with NL?
                                out.write(lineEnding);

                            } else {
                                // move the stream back and process a single CR
                                bin.reset();
                                if(stripCR) {
                                    out.write(lineEnding);
                                } else
                                    out.write(b);
                            }
                        } else {
                            // This is STYLE_R do we output as is or replace with NL?
                            if (stripCR)
                                out.write(lineEnding);
                            else
                                out.write(b);

                        }
                    } else if(b == '\n') {

                        // Are we processing between blocks and was the last character a CR
                        if(lastCharacterWasCR) {
                            out.write(lineEnding);
                            lastCharacterWasCR = false;
                        } else {
                                // This is STYLE_N do we output as is or replace with NL?
                            if (stripLF)
                                out.write(lineEnding);
                            else
                                out.write(b);
                        }
                    } else {

                        // Check for previous CR in stream
                        if(lastCharacterWasCR) {
                            if(stripCR) {
                               out.write(lineEnding);
                            } else
                                out.write(b);
                        }

                        // Not an EOL
                        if(b != 't'
                           && b != '\f'
                           && (b & 0xff) < 32) {
                            encounteredBinary = true;
                        }

                        out.write(b);
                    }
                }

        }


        public static OutputStream createOutputStream(int inputStyle, int outputStyle, OutputStream out) throws IOException {
            return new EOLProcessorOutputStream(inputStyle, outputStyle, out);
        }

        public static InputStream createInputStream(int inputStyle, int outputStyle, InputStream in)
                 throws IOException {
            return new EOLProcessorInputStream(inputStyle, outputStyle, in);
        }




}
