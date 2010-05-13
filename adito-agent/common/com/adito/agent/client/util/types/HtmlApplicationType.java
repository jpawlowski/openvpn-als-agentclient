
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
			
package com.adito.agent.client.util.types;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import com.adito.agent.client.util.AbstractApplicationLauncher;
import com.adito.agent.client.util.ApplicationLauncherEvents;
import com.adito.agent.client.util.ApplicationType;
import com.adito.agent.client.util.ProcessMonitor;
import com.adito.agent.client.util.TunnelConfiguration;
import com.adito.agent.client.util.XMLElement;

/**
 * Application type that launchs an HTML application. Nothing is really executed
 * locally, merely the correct redirect parameters are returned when the
 * agent gives control back to Adito during launch.
 */
public class HtmlApplicationType implements ApplicationType {

    private AbstractApplicationLauncher launcher;

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.vpn.util.ApplicationType#prepare(com.adito.vpn.util.ApplicationLauncher,
     *      com.adito.vpn.util.XMLElement)
     */
    public void prepare(AbstractApplicationLauncher launcher, ApplicationLauncherEvents events, XMLElement element) throws IOException {
        this.launcher = launcher;
    }

    public void start() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.vpn.util.ApplicationType#getProcessMonitor()
     */
    public ProcessMonitor getProcessMonitor() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.adito.vpn.util.ApplicationType#getRedirectParameters()
     */
    public String getRedirectParameters() {
        StringBuffer buf = new StringBuffer();
        for (Enumeration e = launcher.getTunnels().elements(); e.hasMoreElements();) {
            TunnelConfiguration l = (TunnelConfiguration) e.nextElement();
            if (buf.length() == 0) {
                buf.append("tunnels="); //$NON-NLS-1$
            } else {
                buf.append(","); //$NON-NLS-1$
            }
            buf.append(l.getName());
            buf.append(":"); //$NON-NLS-1$
            buf.append("localhost"); //$NON-NLS-1$
            buf.append(":"); //$NON-NLS-1$
            buf.append(l.getSourcePort());
            buf.append("&"); //$NON-NLS-1$
        }
        buf.append("adito="); //$NON-NLS-1$
        buf.append(URLEncoder.encode(launcher.getApplicationStoreProtocol() + "://" //$NON-NLS-1$
            + launcher.getApplicationStoreHost()
            + ":" //$NON-NLS-1$
            + launcher.getApplicationStorePort()));
        return buf.toString();
    }

	/* (non-Javadoc)
	 * @see com.adito.agent.client.util.ApplicationType#getTypeName()
	 */
	public String getTypeName() {
		return "html";
	}

}
