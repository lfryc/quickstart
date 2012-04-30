/**
 JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.warp.ClientAction;
import org.jboss.arquillian.warp.ServerAssertion;
import org.jboss.arquillian.warp.Warp;
import org.jboss.arquillian.warp.jsf.AfterPhase;
import org.jboss.arquillian.warp.jsf.BeforePhase;
import org.jboss.arquillian.warp.jsf.Phase;
import org.jboss.arquillian.warp.test.BeforeServlet;
import org.jboss.as.quickstarts.jsf.RichBean;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(Arquillian.class)
public class ClientTest {

    @Drone
    RemoteWebDriver browser;

    @ArquillianResource
    URL contextPath;

    @Deployment
    public static WebArchive createDeployment() {

        return ShrinkWrap.create(WebArchive.class, "jsf-test.war").addClasses(RichBean.class)
                .addAsWebResource(new File("src/main/webapp/index.xhtml"))
                .addAsWebResource(new File("src/main/webapp/templates/template.xhtml"), "templates/template.xhtml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/faces-config.xml"));
    }

    @Test
    @RunAsClient
    public void test() {
        Warp.execute(new ClientAction() {
            public void action() {
                browser.navigate().to(contextPath + "index.jsf");
            }
        }).verify(new JsfAssertion());

    }

    @SuppressWarnings("serial")
    public static class JsfAssertion implements ServerAssertion {

        @BeforeServlet
        public void test() {
            System.out.println("before servlet");
        }

        @BeforePhase(Phase.RENDER_RESPONSE)
        public void beforeRender() {
            System.out.println("before render");

        }

        @AfterPhase(Phase.RENDER_RESPONSE)
        public void afterRender() {
            System.out.println("after render");

        }

    }
}
