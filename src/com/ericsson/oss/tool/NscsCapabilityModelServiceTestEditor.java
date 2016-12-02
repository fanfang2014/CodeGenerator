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

public class NscsCapabilityModelServiceTestEditor {
    public static final String tab = "    ";
    public static void editNscsCapabilityModelServiceTest(String fullPathFileName, String neTypeEnum, String neTypeString)
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
                if(nextLine.contains("NodeModelInformation vMRFNodeModelInfo = new NodeModelInformation"))
                {
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "private static final String "+ neTypeEnum + "_NODE_NAME = \"" + neTypeEnum + "123\";");
                    list.add(index++, tab + "private static final String "+ neTypeEnum + "_FDN_WITH_ME_CONTEXT = \"MeContext=\" + "+ neTypeEnum + "_NODE_NAME + \",ManagedElement=1\";");
                    list.add(index++, tab + "private static final String " + neTypeEnum +"_FDN_WITH_ME_CONTEXT_AND_SUB_NETWORK = \"SubNetwork=SUB1,MeContext=\" + "+neTypeEnum+"_NODE_NAME + \",ManagedElement=1\";");
                    list.add(index++, tab + "private static final String " + neTypeEnum + "_FDN_WITHOUT_ME_CONTEXT = \"ManagedElement=\" + " + neTypeEnum + "_NODE_NAME;");
                    list.add(index++, tab + "private static final String " + neTypeEnum + "_FDN_WITHOUT_ME_CONTEXT_AND_WITH_SUB_NETWORK = \"SubNetwork=SUB1,ManagedElement=\" + " + neTypeEnum + "_NODE_NAME;");
                    list.add(index++, tab + "private static final String " + neTypeEnum + "_OSS_MODEL_IDENTITY = \"17A-R1X\";");
                    list.add(index++, tab + "private static final NodeModelInformation "+ neTypeString + "NodeModelInfo = new NodeModelInformation(" + neTypeEnum +"_OSS_MODEL_IDENTITY, ModelIdentifierType.OSS_IDENTIFIER, \""+neTypeString + "\");");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("private final NodeReference vMRFNodeRef = new NodeRef("))
                {
                    it.next();
                    index++;
                    list.add(index++, tab + "private final NodeReference "+ neTypeString +"NodeRef = new NodeRef(" + neTypeEnum + "_NODE_NAME);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("private NormalizableNodeReference vMRFNormNodeRef"))
                {
                    it.next();
                    index++;
                    list.add(index++, tab + "@Mock");
                    list.add(index++, tab + "private NormalizableNodeReference "+ neTypeString + "NormNodeRef;");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("doReturn(vMRFNormNodeRef).when(reader).getNormalizableNodeReference"))
                {
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "doReturn(" + neTypeEnum + "_NODE_NAME).when(" + neTypeString + "NormNodeRef).getName();");
                    list.add(index++, tab + "doReturn(" + neTypeEnum + "_FDN_WITHOUT_ME_CONTEXT).when(" + neTypeString + "NormNodeRef).getFdn();");
                    list.add(index++, tab + "doReturn(NeType." + neTypeEnum+ ").when(" + neTypeString + "NormNodeRef).getNeType();");
                    list.add(index++, tab + "doReturn(" + neTypeEnum + "_OSS_MODEL_IDENTITY).when(" + neTypeString + "NormNodeRef).getOssModelIdentity();");
                    list.add(index++, tab + "doReturn(" + neTypeString + "NodeRef).when(" + neTypeString + "NormNodeRef).getNormalizedRef();");
                    list.add(index++, tab + "doReturn(true).when("+ neTypeString + "NormNodeRef).hasNormalizedRef();");
                    list.add(index++, tab + "doReturn(" + neTypeString + "NormNodeRef).when(reader).getNormalizedNodeReference("+ neTypeString + "NodeRef);");
                    list.add(index++, tab + "doReturn(" + neTypeString + "NormNodeRef).when(reader).getNormalizableNodeReference("+ neTypeString + "NodeRef);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSupportedVMRFNeType()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSupported" + neTypeEnum + "NeType() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isNeTypeSupported(NeType."+ neTypeEnum + ".name()));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCredsCreateSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCredsCreateSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CREDENTIALS_CREATE_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CREDENTIALS_CREATE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CREDS_CREATE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCredsUpdateSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCredsUpdateSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CREDENTIALS_UPDATE_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CREDENTIALS_UPDATE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CREDS_UPDATE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsKeygenCreateUnsupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsKeygenCreateUnsupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, KEYGEN_CREATE_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), KEYGEN_CREATE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), KG_CREATE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsKeygenUpdateUnsupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsKeygenUpdateUnsupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, KEYGEN_UPDATE_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), KEYGEN_UPDATE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), KG_UPDATE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSetEnrollmentSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSetEnrollmentSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SET_ENROLLMENT_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SET_ENROLLMENT_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCertIssueSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCertIssueSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CERTIFICATE_ISSUE_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CERTIFICATE_ISSUE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), CERT_ISSUE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsGetCertIssueSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsGetCertIssueSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, GET_CERT_ISSUE_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), GET_CERT_ISSUE_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsTrustDistrSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsTrustDistrSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, TRUST_DISTRIBUTE_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), TRUST_DISTRIBUTE_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), TRUST_DISTR_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsGetTrustDistrSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsGetTrustDistrSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, GET_TRUST_DISTR_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), GET_TRUST_DISTR_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSnmpAuthPrivSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSnmpAuthPrivSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SNMP_AUTHPRIV_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SNMP_AUTHPRIV_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSnmpAuthNoPrivSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSnmpAuthNoPrivSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SNMP_AUTHNOPRIV_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SNMP_AUTHNOPRIV_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testAreIpsecCommandsUnsupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testAreIpsecCommandsUnsupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, IPSEC_STATUS_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), IPSEC_STATUS_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, IPSEC_STATUS_SHORTCUT_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), IPSEC_STATUS_SHORTCUT_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, IPSEC_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), IPSEC_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testAreSecurityLevelCommandsUnsupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testAreSecurityLevelCommandsUnsupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SECURITY_LEVEL_GET_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SECURITY_LEVEL_GET_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SECURITY_LEVEL_GET_SHORTCUT_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SECURITY_LEVEL_GET_SHORTCUT_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SECURITY_LEVEL_SET_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SECURITY_LEVEL_SET_COMMAND, null, null));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SECURITY_LEVEL_SET_SHORTCUT_COMMAND));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), SECURITY_LEVEL_SET_SHORTCUT_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsLdapCommandSupportedForVMRF()"))
                {
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    it.next();
                    index++;
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsLdapCommandSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, LDAP_COMMAND));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupportedForNeType(NeType." + neTypeEnum + ".name(), LDAP_COMMAND, null, null));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testCredsCreateParamsForVMRF() {"))
                {
                    for (int i = 0; i < 16; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testCredsCreateParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(SECURE_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualParams.add(SECURE_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(NORMAL_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(NORMAL_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(ROOT_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(ROOT_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CREDENTIALS_CREATE_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testCredsUpdateParamsForVMRF()"))
                {
                    for (int i = 0; i < 16; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testCredsUpdateParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(SECURE_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualParams.add(SECURE_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(NORMAL_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(NORMAL_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(ROOT_USER_NAME_PROPERTY);");
                    list.add(index++, tab + tab + "actualUnexpectedParams.add(ROOT_USER_PASSWORD_PROPERTY);");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CREDENTIALS_UPDATE_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testSetEnrollmentParamsForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testSetEnrollmentParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(ENROLLMENT_MODE_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SET_ENROLLMENT_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testCertIssueParamsForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testCertIssueParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(CERT_TYPE_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, CERT_ISSUE_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetCertIssueParamsForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetCertIssueParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(CERT_TYPE_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, GET_CERT_ISSUE_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testTrustDistrParamsForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testTrustDistrParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(CERT_TYPE_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, TRUST_DISTRIBUTE_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetTrustDistrParamsForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetTrustDistrParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(CERT_TYPE_PROPERTY);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, GET_TRUST_DISTR_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testSnmpAuthPrivParamsForVMRF() {"))
                {
                    for (int i = 0; i < 14; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testSnmpAuthPrivParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.AUTH_ALGO_PARAM);");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.AUTH_PWD_PARAM);");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.PRIV_ALGO_PARAM);");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.PRIV_PWD_PARAM);");
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SNMP_AUTHPRIV_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testSnmpAuthNoPrivParamsForVMRF() {"))
                {
                    for (int i = 0; i < 12; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testSnmpAuthNoPrivParamsFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> unexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "final Set<String> actualParams = new HashSet<>();");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.AUTH_ALGO_PARAM);");
                    list.add(index++, tab + tab + "actualParams.add(SnmpAuthpriv.AUTH_PWD_PARAM);");
                    
                    list.add(index++, tab + tab + "final Set<String> actualUnexpectedParams = new HashSet<>();");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCliCommandSupported(" + neTypeString + "NormNodeRef, SNMP_AUTHNOPRIV_COMMAND, expectedParams, unexpectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualParams.equals(expectedParams));");
                    list.add(index++, tab + tab + "assertTrue(actualUnexpectedParams.equals(unexpectedParams));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsEnrollmentModeSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 11; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsEnrollmentModeSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<EnrollmentMode> expectedSupported = new HashSet<EnrollmentMode>();");
                    list.add(index++, tab + tab + "expectedSupported.add(EnrollmentMode.CMPv2_VC);");
                    list.add(index++, tab + tab + "expectedSupported.add(EnrollmentMode.OFFLINE_CSR);");
                    list.add(index++, tab + tab + "expectedSupported.add(EnrollmentMode.OFFLINE_PKCS12);");
                    
                    list.add(index++, tab + tab + "for (final EnrollmentMode enrollmentMode : expectedSupported) {");
                    list.add(index++, tab + tab + tab + "assertTrue(beanUnderTest.isEnrollmentModeSupported(enrollmentMode, " + neTypeString + "NodeModelInfo));");
                    list.add(index++, tab + tab + "}");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testSupportedEnrollmentModesForVMRF() {"))
                {
                    for (int i = 0; i < 12; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testSupportedEnrollmentModesFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "final Set<String> expectedSupported = new HashSet<String>();");
                    list.add(index++, tab + tab + "expectedSupported.add(\"CMPv2_VC\");");
                    list.add(index++, tab + tab + "expectedSupported.add(\"OFFLINE_PKCS12\");");
                    list.add(index++, tab + tab + "expectedSupported.add(\"OFFLINE_CSR\");");
                    
                    list.add(index++, tab + tab + "final Set<String> actualSupported = new HashSet<String>();");
                    list.add(index++, tab + tab + "final boolean result = beanUnderTest.getSupportedEnrollmentModes(" + neTypeEnum + "_NODE_NAME, NscsCommandType.CERTIFICATE_ISSUE, actualSupported);");
                    list.add(index++, tab + tab + "actualSupported.removeAll(expectedSupported);");
                    list.add(index++, tab + tab + "assertTrue(result);");
                    list.add(index++, tab + tab + "assertTrue(actualSupported.isEmpty());");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetDefaultEnrollmentModeForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetDefaultEnrollmentModeFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(\"CMPv2_VC\".equals(beanUnderTest.getDefaultEnrollmentMode(" + neTypeString + "NormNodeRef)));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSynchronousEnrollmentSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSynchronousEnrollmentSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isSynchronousEnrollmentSupported(" + neTypeString + "NodeModelInfo));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetDefaultEntityProfileForVMRF() {"))
                {
                    for (int i = 0; i < 4; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetDefaultEntityProfileFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(\"DUSGen2IPSec_SAN_CHAIN_EP\".equals(beanUnderTest.getEntityProfileFromNodeModel(NodeEntityCategory.IPSEC, " + neTypeString + "NodeModelInfo)));");
                    list.add(index++, tab + tab + "assertTrue(\"DUSGen2OAM_CHAIN_EP\".equals(beanUnderTest.getEntityProfileFromNodeModel(NodeEntityCategory.OAM, " + neTypeString + "NodeModelInfo)));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetIssueOrReissueCertWfForVMRF() {"))
                {
                    for (int i = 0; i < 5; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetIssueOrReissueCertWfFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIM_ComIssueCert.toString(), beanUnderTest.getIssueOrReissueCertWf(" + neTypeEnum + "_NODE_NAME, \"IPSEC\"));");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIM_ComIssueCert.toString(), beanUnderTest.getIssueOrReissueCertWf(" + neTypeEnum + "_NODE_NAME, \"OAM\"));");
                    list.add(index++, tab + tab + "assertNull(beanUnderTest.getIssueOrReissueCertWf(" + neTypeEnum + "_NODE_NAME, \"OEM\"));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetTrustDistributeWfForVMRF() {"))
                {
                    for (int i = 0; i < 5; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetTrustDistributeWfFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIM_ComIssueTrustCert.toString(), beanUnderTest.getTrustDistributeWf(" + neTypeEnum + "_NODE_NAME, \"IPSEC\"));");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIM_ComIssueTrustCert.toString(), beanUnderTest.getTrustDistributeWf(" + neTypeEnum + "_NODE_NAME, \"OAM\"));");
                    list.add(index++, tab + tab + "assertNull(beanUnderTest.getTrustDistributeWf(" + neTypeEnum + "_NODE_NAME, \"OEM\"));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetTrustRemoveWfForVMRF() {"))
                {
                    for (int i = 0; i < 5; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetTrustRemoveWfFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIMRemoveTrust.toString(), beanUnderTest.getTrustRemoveWf(" + neTypeEnum + "_NODE_NAME, \"IPSEC\"));");
                    list.add(index++, tab + tab + "assertEquals(WorkflowNames.WORKFLOW_COMECIMRemoveTrust.toString(), beanUnderTest.getTrustRemoveWf(" + neTypeEnum + "_NODE_NAME, \"OAM\"));");
                    list.add(index++, tab + tab + "assertNull(beanUnderTest.getTrustRemoveWf(" + neTypeEnum + "_NODE_NAME, \"OEM\"));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetMirrorRootMoForVIPWORKSWithoutMeContextAndWithSubNetwork() {"))
                {
                    for (int i = 0; i < 6; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithoutMeContextNodeReference() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeString + "NodeRef);");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertNull(mo.parent());");
                    list.add(index++, tab +"}");
                    
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithoutMeContextNormalizableNodeReference() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeString + "NormNodeRef);");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertNull(mo.parent());");
                    list.add(index++, tab +"}");
                    
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithMeContext() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeEnum + "_FDN_WITH_ME_CONTEXT, NeType." + neTypeEnum + ".name());");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertTrue(\"MeContext\".equals(mo.parent().type()));");
                    list.add(index++, tab + tab + "assertTrue(\"OSS_TOP\".equals(mo.parent().namespace()));");
                    list.add(index++, tab +"}");
                    
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithMeContextAndSubNetwork() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeEnum + "_FDN_WITH_ME_CONTEXT_AND_SUB_NETWORK, NeType." + neTypeEnum + ".name());");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertTrue(\"MeContext\".equals(mo.parent().type()));");
                    list.add(index++, tab + tab + "assertTrue(\"OSS_TOP\".equals(mo.parent().namespace()));");
                    list.add(index++, tab +"}");
                    
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithoutMeContext() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeEnum + "_FDN_WITHOUT_ME_CONTEXT, NeType." + neTypeEnum + ".name());");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertNull(mo.parent());");
                    list.add(index++, tab +"}");
                    
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMirrorRootMoFor" + neTypeEnum + "WithoutMeContextAndWithSubNetwork() {");
                    list.add(index++, tab + tab + "final Mo mo = beanUnderTest.getMirrorRootMo(" + neTypeEnum + "_FDN_WITHOUT_ME_CONTEXT_AND_WITH_SUB_NETWORK, NeType." + neTypeEnum + ".name());");
                    list.add(index++, tab + tab + "assertTrue(\"ManagedElement\".equals(mo.type()));");
                    list.add(index++, tab + tab + "assertTrue(\"ComTop\".equals(mo.namespace()));");
                    list.add(index++, tab + tab + "assertNull(mo.parent());");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCertificateSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCertificateSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCertificateManagementSupported(" + neTypeString + "NormNodeRef));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCertTypeSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 6; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCertTypeSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCertTypeSupported(\"IPSEC\", NeType." + neTypeEnum + ".name()));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCertTypeSupported(\"OAM\", NeType." + neTypeEnum + ".name()));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCertTypeSupported(\"OEM\", NeType." + neTypeEnum + ".name()));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isCertTypeSupported(\"\", NeType." + neTypeEnum + ".name()));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetDefaultAlgorithmKeysForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetDefaultAlgorithmKeysFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(AlgorithmKeys.RSA_2048.equals(beanUnderTest.getDefaultAlgorithmKeys(" + neTypeString + "NodeModelInfo)));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetDefaultDigestAlgorithmForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetDefaultDigestAlgorithmFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(DigestAlgorithm.SHA1.equals(beanUnderTest.getDefaultDigestAlgorithm(" + neTypeString + "NodeModelInfo)));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsCertificateAuthorityDnSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 3; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsCertificateAuthorityDnSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isCertificateAuthorityDnSupported(" + neTypeString + "NodeModelInfo));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testGetMOGetServiceTypeForVMRF() {"))
                {
                    for (int i = 0; i < 5; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testGetMOGetServiceTypeFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertTrue(\"COM_ECIM_family\".equals(beanUnderTest.getMOGetServiceType(" + neTypeString + "NodeRef)));");
                    list.add(index++, tab + tab + "assertTrue(\"COM_ECIM_family\".equals(beanUnderTest.getMOGetServiceType(" + neTypeString + "NormNodeRef)));");
                    list.add(index++, tab + tab + "assertTrue(\"COM_ECIM_family\".equals(beanUnderTest.getMOGetServiceType(NeType." + neTypeEnum + ".name())));");
                    list.add(index++, tab +"}");
                    it = list.subList(index+1, list.size() - 1).iterator();
                }
                else if(nextLine.contains("public void testIsSecurityLevelSupportedForVMRF() {"))
                {
                    for (int i = 0; i < 6; i++)
                    {
                        it.next();
                        index++;
                    }
                    list.add(index++, tab);
                    list.add(index++, tab + "@Test");
                    list.add(index++, tab + "public void testIsSecurityLevelSupportedFor" + neTypeEnum + "() {");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isSecurityLevelSupported(NeType." + neTypeEnum + ".name(), \"LEVEL_1\"));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isSecurityLevelSupported(NeType." + neTypeEnum + ".name(), \"LEVEL_2\"));");
                    list.add(index++, tab + tab + "assertTrue(beanUnderTest.isSecurityLevelSupported(NeType." + neTypeEnum + ".name(), \"LEVEL_NOT_SUPPORTED\"));");
                    list.add(index++, tab + tab + "assertFalse(beanUnderTest.isSecurityLevelSupported(NeType." + neTypeEnum + ".name(), \"UNKNOWN\"));");
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
