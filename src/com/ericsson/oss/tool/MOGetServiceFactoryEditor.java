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

public class MOGetServiceFactoryEditor {
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
                if(nextLine.contains("public void testGetCapabilityModelForVMRFNodeReference() {"))
                {
                    for (int i = 0; i < 5; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetCapabilityModelFor" + neTypeEnum + "NodeReference() {");
                    list.add(index++, tab + tab + "when(moGetServiceInstances.select(any(NscsCertificateStateInfoTypeQualifier.class))).thenReturn(moGetServiceInstances);");
                    list.add(index++, tab + tab + "when(moGetServiceInstances.get()).thenReturn(comEcimCertificateStateInfo);");
                    list.add(index++, tab + tab + "assertEquals(comEcimCertificateStateInfo, beanUnderTest.getMOGetService(" + neTypeString + "NodeRef));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("private static final String VMRF_NODE_NAME = "))
                {
                    it.next();
                    index++;
                    list.add(index++, tab + "private static final String " + neTypeEnum + "_NODE_NAME = \"" + neTypeEnum + "123\";");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("private final NodeReference vMRFNodeRef = new NodeRef(VMRF_NODE_NAME);"))
                {
                    it.next();
                    index++;
                    list.add(index++, tab + "private final NodeReference " + neTypeString + "NodeRef = new NodeRef(" + neTypeEnum + "_NODE_NAME);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("when(capabilityService.getMOGetServiceType(vMRFNodeRef)).thenReturn(COM_ECIM_FAMILY);"))
                {
                    it.next();
                    index++;
                    list.add(index++, tab + "when(capabilityService.getMOGetServiceType(" + neTypeString + "NodeRef)).thenReturn(COM_ECIM_FAMILY);");
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
