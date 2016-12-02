/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2013
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelTestEditor {
    public static final String tab = "    ";
    public static void editModelTest(String fullPathFileName, String neTypeEnum, String neTypeString)
    {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(fullPathFileName));
            String tmp;
            while ((tmp = reader.readLine()) != null)
                list.add(tmp);

            Iterator<String> it = list.iterator();
            int index = 0;
            while (it.hasNext()) {
                String nextLine = it.next().toString();
                if(nextLine.contains("public void vMRFTypeAndNamespaceTest() {"))
                {
                    for (int i = 0; i < 15; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void " + neTypeString + "TypeAndNamespaceTest() {");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"ComTop\", ModelDefinition.COM_TOP_NS);");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"ComSecM\", ModelDefinition.COM_SEC_M_NS);");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"RcsCertM\", ModelDefinition.COM_CERT_M_NS);");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"ComSysM\", ModelDefinition.COM_SYS_M_NS);");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"RcsOamAccessPoint\", ModelDefinition.COM_OAM_ACCESS_POINT_NS);");
                    list.add(index++, tab + tab + "Assert.assertEquals(\"RtnIkev2PolicyProfile\", ModelDefinition.COM_IKEV2_POLICY_PROFILE_NS);");
                    list.add(index++, tab + tab + "comEcimTypeAndNamespaceTest(Model.ME_CONTEXT.comManagedElement, \"ComTop\", \"ComSecM\", \"RcsCertM\", \"ComSysM\", \"RcsOamAccessPoint\", \"RtnIkev2PolicyProfile\");");
                    list.add(index++, tab + tab + "comEcimTypeAndNamespaceTest(Model.COM_MANAGED_ELEMENT, \"ComTop\",\"ComSecM\", \"RcsCertM\", \"ComSysM\", \"RcsOamAccessPoint\",\"RtnIkev2PolicyProfile\");");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                
                index++;
            }
            
            writer = new BufferedWriter(new FileWriter(fullPathFileName));
            for (int i = 0; i < list.size(); i++)
                writer.write(list.get(i) + "\r\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
