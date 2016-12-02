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
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;

public class NodeSecurityCodeGen {
    public static String fullPathNscsCapabilityModelMock = "node-security-jar\\src\\main\\java\\com\\ericsson\\nms\\security\\nscs\\capabilitymodel\\service\\NscsCapabilityModelMock.java";
    public static String fullPathNeType = "node-security-jar\\src\\main\\java\\com\\ericsson\\nms\\security\\nscs\\data\\NeType.java";
    public static String fullPathNscsCapabilityModelServiceTest = "node-security-jar\\src\\test\\java\\com\\ericsson\\nms\\security\\nscs\\capabilitymodel\\service\\NscsCapabilityModelServiceTest.java";
    public static String fullPathModelTest = "node-security-jar\\src\\test\\java\\com\\ericsson\\nms\\security\\nscs\\data\\ModelTest.java";
    public static String fullPathMOGetServiceFactory = "node-security-jar\\src\\test\\java\\com\\ericsson\\nms\\security\\nscs\\data\\moget\\MOGetServiceFactoryTest.java";
    public static String neTypeEnum = null;
    public static String neTypeString = null;
    public static String userInputPath = null;
    public static final String tab = "    ";

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out
                .println("Please input the full folder address for the node-security repo, e.g. C:\\Users\\efanfag\\ENMRepos\\8nodes\\node-security");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            userInputPath = bufferedReader.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Path path = Paths.get(userInputPath);
        if (Files.exists(path) && (userInputPath.endsWith("node-security") || userInputPath.endsWith("node-security\\"))) {
            try {
                System.out.println("Input the NeType Enum name, e.g. MSRBS_V2");
                neTypeEnum = bufferedReader.readLine();
                System.out.println("Input the NeType String name, e.g. RadioNode");
                neTypeString = bufferedReader.readLine();
                if (userInputPath.endsWith("node-security")) {
                    fullPathNeType = userInputPath + "\\" + fullPathNeType;
                    fullPathNscsCapabilityModelMock = userInputPath + "\\" + fullPathNscsCapabilityModelMock;
                    fullPathNscsCapabilityModelServiceTest = userInputPath + "\\" + fullPathNscsCapabilityModelServiceTest;
                    fullPathModelTest = userInputPath + "\\" + fullPathModelTest;
                    fullPathMOGetServiceFactory = userInputPath + "\\" + fullPathMOGetServiceFactory;
                } else {
                    fullPathNeType = userInputPath + fullPathNeType;
                    fullPathNscsCapabilityModelMock = userInputPath + fullPathNscsCapabilityModelMock;
                    fullPathNscsCapabilityModelServiceTest = userInputPath + fullPathNscsCapabilityModelServiceTest;
                    fullPathModelTest = userInputPath + fullPathModelTest;
                    fullPathMOGetServiceFactory = userInputPath + fullPathMOGetServiceFactory;
                }

                editNeTypeJavaFile(fullPathNeType, neTypeEnum, neTypeString);
                editNscsCapabilityModelMock(fullPathNscsCapabilityModelMock, neTypeEnum, neTypeString);
                NscsCapabilityModelServiceTestEditor.editNscsCapabilityModelServiceTest(fullPathNscsCapabilityModelServiceTest, neTypeEnum, neTypeString);
                ModelTestEditor.editModelTest(fullPathModelTest, neTypeEnum, neTypeString);
                MOGetServiceFactoryEditor.editModelTest(fullPathMOGetServiceFactory, neTypeEnum, neTypeString);
                System.out.println("Done! Check the code change in node-security repo. ");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            System.out.println("Wrong folder input" + userInputPath);
        }
    }

    public static void editNeTypeJavaFile(String fullPathFileName, String neTypeEnum, String neTypeString) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        ArrayList list = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(fullPathFileName));
            String tmp;
            while ((tmp = reader.readLine()) != null)
                list.add(tmp);

            Iterator it = list.iterator();
            int index = 0;
            while (it.hasNext()) {
                if (it.next().toString().contains("UNKNOWN(\"UNKNOWN\")")) {
                    break;
                }
                index++;
            }

            list.add(index, tab + neTypeEnum + "(\"" + neTypeString + "\"),");

            writer = new BufferedWriter(new FileWriter(fullPathFileName));
            for (int i = 0; i < list.size(); i++)
                writer.write(list.get(i) + "\r\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void editNscsCapabilityModelMock(String fullPathFileName, String neTypeEnum, String neTypeString){
        BufferedReader reader = null;
        BufferedWriter writer = null;
        ArrayList<String> list = new ArrayList<String>();
        boolean isTheSupportedNeTypesStarted = false;
        boolean isFinalListStringStarted = false;
        boolean isUnsupportedAddAllStarted = false;
        boolean isTheUnsupportedCommandsPutStarted = false;
        boolean isTheCredentialsParamsPutStarted = false;
        boolean istheEnrollmentParamsPutStarted = false;
        boolean istheCertificateParamsPutStarted = false;
        boolean istheCertificateReissueParamsPutStarted = false;
        boolean istheTrustParamsPutStarted = false;
        boolean istheSnmpAuthPrivParamsPutStarted = false;
        boolean istheSnmpAuthNoPrivParamsPutStarted = false;
        boolean isMapMoInfoStarted = false;
        boolean istheMirrorRootMoInfoPutStarted = false;
        boolean istheSupportedCertTypesPutStarted = false;
        boolean isMapDefaultEntityProfilesStarted = false;
        boolean istheDefaultEntityProfilesPutStarted = false;
        boolean istheIssueCertWorkflowsPutStarted = false;
        boolean istheTrustDistrWorkflowsPutStarted = false;
        boolean isthetheTrustRemoveWorkflowsPutStarted = false;
        boolean istheMOGetServiceTypePutStarted = false;
        boolean istheIsCertificateSupportedPutStarted = false;
        boolean istheSupportedSecurityLevelsPutStarted = false;
        boolean istheSupportedEnrollmentModesPutStarted = false;
        boolean istheDefaultEnrollmentModePutStarted = false;
        boolean istheDefaultKeyAlgorithmPutStarted = false;
        boolean istheDefaultFingerprintAlgorithmPutStarted = false;
        boolean istheIsCertificateAuthorityDNSupportedPutStarted = false;
        boolean istheIsSynchronousEnrollmentSupportedPutStarted = false;
        
        try {
            reader = new BufferedReader(new FileReader(fullPathFileName));
            String tmp;
            while ((tmp = reader.readLine()) != null)
                list.add(tmp);

            Iterator<String> it = list.iterator();
            int index = 0;
            while (it.hasNext()) {
                String nextLine = it.next().toString();
                if(((isTheSupportedNeTypesStarted || isFinalListStringStarted || isUnsupportedAddAllStarted || isTheUnsupportedCommandsPutStarted ||
                        isTheCredentialsParamsPutStarted || istheEnrollmentParamsPutStarted || istheCertificateParamsPutStarted || istheCertificateReissueParamsPutStarted ||
                        istheTrustParamsPutStarted || istheSnmpAuthPrivParamsPutStarted || istheSnmpAuthNoPrivParamsPutStarted || isMapMoInfoStarted || istheMirrorRootMoInfoPutStarted ||
                        istheSupportedCertTypesPutStarted || isMapDefaultEntityProfilesStarted || istheDefaultEntityProfilesPutStarted || istheIssueCertWorkflowsPutStarted || 
                        istheTrustDistrWorkflowsPutStarted || isthetheTrustRemoveWorkflowsPutStarted || istheMOGetServiceTypePutStarted || istheIsCertificateSupportedPutStarted ||
                        istheSupportedSecurityLevelsPutStarted || istheSupportedEnrollmentModesPutStarted || istheDefaultEnrollmentModePutStarted || istheDefaultKeyAlgorithmPutStarted ||
                        istheDefaultFingerprintAlgorithmPutStarted || istheIsCertificateAuthorityDNSupportedPutStarted || istheIsSynchronousEnrollmentSupportedPutStarted) &&
                        nextLine.trim().isEmpty()) || (isMapMoInfoStarted && (nextLine.contains("NSCS_CLI_CAPABILITY_MIRROR_ROOT_MO_INFO_NS_ATTRIBUTE,") || nextLine.contains("\");"))))
                {
                    index++;
                    continue;
                }
                else if(nextLine.contains("theSupportedNeTypes.add("))
                {
                    isTheSupportedNeTypesStarted = true;
                }
                else if(isTheSupportedNeTypesStarted && !nextLine.contains("theSupportedNeTypes.add("))
                {
                    list.add(index++, tab + tab + "theSupportedNeTypes.add(NeType." + neTypeEnum + ".name());");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isTheSupportedNeTypesStarted = false;
                }
                else if(nextLine.contains("List<String>") && nextLine.contains("Unsupported = new ArrayList<String>();"))
                {
                    isFinalListStringStarted = true;
                }
                else if(isFinalListStringStarted && (!nextLine.contains("new ArrayList<String>();") || !nextLine.contains("List<String>")))
                {
                    list.add(index++, tab + tab + "final List<String> "+neTypeString+"Unsupported = new ArrayList<String>();");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isFinalListStringStarted = false;
                }
                else if(nextLine.contains("Unsupported.addAll("))
                {
                    isUnsupportedAddAllStarted = true;
                }
                else if(isUnsupportedAddAllStarted && !nextLine.contains("Unsupported.addAll("))
                {
                    list.add(index++, tab + tab);
                    list.add(index++, tab + tab + neTypeString + "Unsupported.addAll(keygenCommands);");
                    list.add(index++, tab + tab + neTypeString + "Unsupported.addAll(securityLevelCommands);");
                    list.add(index++, tab + tab + neTypeString + "Unsupported.addAll(ipsecCommands);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isUnsupportedAddAllStarted = false;
                }
                else if(nextLine.contains("theUnsupportedCommands.put("))
                {
                    isTheUnsupportedCommandsPutStarted = true;
                }
                else if(isTheUnsupportedCommandsPutStarted && !nextLine.contains("theUnsupportedCommands.put("))
                {
                    list.add(index++, tab + tab + "theUnsupportedCommands.put(NeType." + neTypeEnum + ".name(), " + neTypeString + "Unsupported);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isTheUnsupportedCommandsPutStarted = false;
                }
                else if(nextLine.contains("theCredentialsParams.put("))
                {
                    isTheCredentialsParamsPutStarted = true;
                }
                else if(isTheCredentialsParamsPutStarted && !nextLine.contains("theCredentialsParams.put("))
                {
                    list.add(index++, tab + tab + "theCredentialsParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isTheCredentialsParamsPutStarted = false;
                }
                else if(nextLine.contains("theEnrollmentParams.put("))
                {
                    istheEnrollmentParamsPutStarted = true;
                }
                else if(istheEnrollmentParamsPutStarted && !nextLine.contains("theEnrollmentParams.put("))
                {
                    list.add(index++, tab + tab + "theEnrollmentParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheEnrollmentParamsPutStarted = false;
                }
                else if(nextLine.contains("theCertificateParams.put("))
                {
                    istheCertificateParamsPutStarted = true;
                }
                else if(istheCertificateParamsPutStarted && !nextLine.contains("theCertificateParams.put("))
                {
                    list.add(index++, tab + tab + "theCertificateParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheCertificateParamsPutStarted = false;
                }
                else if(nextLine.contains("theCertificateReissueParams.put("))
                {
                    istheCertificateReissueParamsPutStarted = true;
                }
                else if(istheCertificateReissueParamsPutStarted && !nextLine.contains("theCertificateReissueParams.put("))
                {
                    list.add(index++, tab + tab + "theCertificateReissueParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheCertificateReissueParamsPutStarted = false;
                }
                else if(nextLine.contains("theTrustParams.put("))
                {
                    istheTrustParamsPutStarted = true;
                }
                else if(istheTrustParamsPutStarted && !nextLine.contains("theTrustParams.put("))
                {
                    list.add(index++, tab + tab + "theTrustParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheTrustParamsPutStarted = false;
                }
                else if(nextLine.contains("theSnmpAuthPrivParams.put("))
                {
                    istheSnmpAuthPrivParamsPutStarted = true;
                }
                else if(istheSnmpAuthPrivParamsPutStarted && !nextLine.contains("theSnmpAuthPrivParams.put("))
                {
                    list.add(index++, tab + tab + "theSnmpAuthPrivParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheSnmpAuthPrivParamsPutStarted = false;
                }
                else if(nextLine.contains("theSnmpAuthNoPrivParams.put("))
                {
                    istheSnmpAuthNoPrivParamsPutStarted = true;
                }
                else if(istheSnmpAuthNoPrivParamsPutStarted && !nextLine.contains("theSnmpAuthNoPrivParams.put("))
                {
                    list.add(index++, tab + tab + "theSnmpAuthNoPrivParams.put(NeType." + neTypeEnum + ".name(), " + "defaultCommandParams);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheSnmpAuthNoPrivParamsPutStarted = false;
                }
                else if(nextLine.contains("Map<String, String>") && nextLine.contains("MoInfo = new HashMap<String, String>"))
                {
                    isMapMoInfoStarted = true;
                    it.next();
                    index++;
                    it.next();
                    index++;
                }
                else if(isMapMoInfoStarted && (!nextLine.contains("Map<String, String>") || !nextLine.contains("MoInfo = new HashMap<String, String>")))
                {
                    list.add(index++, tab + tab + "Map<String, String> "+neTypeString+"MoInfo = new HashMap<String, String>();");
                    list.add(index++, tab + tab + neTypeString+"MoInfo.put(NscsCapabilityModelConstants.NSCS_CLI_CAPABILITY_MIRROR_ROOT_MO_INFO_TYPE_ATTRIBUTE, \"ManagedElement\");");
                    list.add(index++, tab + tab + neTypeString+"MoInfo.put(NscsCapabilityModelConstants.NSCS_CLI_CAPABILITY_MIRROR_ROOT_MO_INFO_NS_ATTRIBUTE, \"ComTop\");");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isMapMoInfoStarted = false;
                }
                else if(nextLine.contains("theMirrorRootMoInfo.put("))
                {
                    istheMirrorRootMoInfoPutStarted = true;
                }
                else if(istheMirrorRootMoInfoPutStarted && !nextLine.contains("theMirrorRootMoInfo.put("))
                {
                    list.add(index++, tab + tab + "theMirrorRootMoInfo.put(NeType." + neTypeEnum + ".name(), " + neTypeString + "MoInfo);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheMirrorRootMoInfoPutStarted = false;
                }
                else if(nextLine.contains("theSupportedCertTypes.put("))
                {
                    istheSupportedCertTypesPutStarted = true;
                }
                else if(istheSupportedCertTypesPutStarted && !nextLine.contains("theSupportedCertTypes.put("))
                {
                    list.add(index++, tab + tab + "theSupportedCertTypes.put(NeType." + neTypeEnum + ".name(), " + "defaultSupportedCertTypes);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheSupportedCertTypesPutStarted = false;
                }
                else if(nextLine.contains("Map<String, String>") && nextLine.contains("faultEntityProfiles = new HashMap<String, String>"))
                {
                    isMapDefaultEntityProfilesStarted = true;
                    it.next();
                    index++;
                    it.next();
                    index++;
                }
                else if(isMapDefaultEntityProfilesStarted && (!nextLine.contains("Map<String, String>") || !nextLine.contains("faultEntityProfiles = new HashMap<String, String>")))
                {
                    list.add(index++, tab + tab + "Map<String, String> "+neTypeString+"DefaultEntityProfiles = new HashMap<String, String>();");
                    list.add(index++, tab + tab + neTypeString+"DefaultEntityProfiles.put(\"IPSEC\", \"DUSGen2IPSec_SAN_CHAIN_EP\");");
                    list.add(index++, tab + tab + neTypeString+"DefaultEntityProfiles.put(\"OAM\", \"DUSGen2OAM_CHAIN_EP\");");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isMapDefaultEntityProfilesStarted = false;
                }
                else if(nextLine.contains("theDefaultEntityProfiles.put("))
                {
                    istheDefaultEntityProfilesPutStarted = true;
                }
                else if(istheDefaultEntityProfilesPutStarted && !nextLine.contains("theDefaultEntityProfiles.put("))
                {
                    list.add(index++, tab + tab + "theDefaultEntityProfiles.put(NeType." + neTypeEnum + ".name(), " + neTypeString + "DefaultEntityProfiles);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheDefaultEntityProfilesPutStarted = false;
                }
                else if(nextLine.contains("theIssueCertWorkflows.put("))
                {
                    istheIssueCertWorkflowsPutStarted = true;
                }
                else if(istheIssueCertWorkflowsPutStarted && !nextLine.contains("theIssueCertWorkflows.put("))
                {
                    list.add(index++, tab + tab + "theIssueCertWorkflows.put(NeType." + neTypeEnum + ".name(), " + "defaultIssueCertWorkflows);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheIssueCertWorkflowsPutStarted = false;
                }
                else if(nextLine.contains("theTrustDistrWorkflows.put("))
                {
                    istheTrustDistrWorkflowsPutStarted = true;
                }
                else if(istheTrustDistrWorkflowsPutStarted && !nextLine.contains("theTrustDistrWorkflows.put("))
                {
                    list.add(index++, tab + tab + "theTrustDistrWorkflows.put(NeType." + neTypeEnum + ".name(), " + "defaultTrustDistrWorkflows);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheTrustDistrWorkflowsPutStarted = false;
                }
                else if(nextLine.contains("theTrustRemoveWorkflows.put("))
                {
                    isthetheTrustRemoveWorkflowsPutStarted = true;
                }
                else if(isthetheTrustRemoveWorkflowsPutStarted && !nextLine.contains("theTrustRemoveWorkflows.put("))
                {
                    list.add(index++, tab + tab + "theTrustRemoveWorkflows.put(NeType." + neTypeEnum + ".name(), " + "defaultTrustRemoveWorkflows);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    isthetheTrustRemoveWorkflowsPutStarted = false;
                }
                else if(nextLine.contains("theMOGetServiceType.put("))
                {
                    istheMOGetServiceTypePutStarted = true;
                }
                else if(istheMOGetServiceTypePutStarted && !nextLine.contains("theMOGetServiceType.put("))
                {
                    list.add(index++, tab + tab + "theMOGetServiceType.put(NeType." + neTypeEnum + ".name(), " + "defaultMOGetServiceType);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheMOGetServiceTypePutStarted = false;
                }
                else if(nextLine.contains("theIsCertificateSupported.put("))
                {
                    istheIsCertificateSupportedPutStarted = true;
                }
                else if(istheIsCertificateSupportedPutStarted && !nextLine.contains("theIsCertificateSupported.put("))
                {
                    list.add(index++, tab + tab + "theIsCertificateSupported.put(NeType." + neTypeEnum + ".name(), " + "defaultIsCertificateSupported);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheIsCertificateSupportedPutStarted = false;
                }
                else if(nextLine.contains("theSupportedSecurityLevels.put("))
                {
                    istheSupportedSecurityLevelsPutStarted = true;
                }
                else if(istheSupportedSecurityLevelsPutStarted && !nextLine.contains("theSupportedSecurityLevels.put("))
                {
                    list.add(index++, tab + tab + "theSupportedSecurityLevels.put(NeType." + neTypeEnum + ".name(), " + "defaultSupportedSecurityLevels);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheSupportedSecurityLevelsPutStarted = false;
                }
                else if(nextLine.contains("theSupportedEnrollmentModes.put("))
                {
                    istheSupportedEnrollmentModesPutStarted = true;
                }
                else if(istheSupportedEnrollmentModesPutStarted && !nextLine.contains("theSupportedEnrollmentModes.put("))
                {
                    list.add(index++, tab + tab + "theSupportedEnrollmentModes.put(NeType." + neTypeEnum + ".name(), " + "defaultSupportedEnrollmentModes);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheSupportedEnrollmentModesPutStarted = false;
                }
                else if(nextLine.contains("theDefaultEnrollmentMode.put("))
                {
                    istheDefaultEnrollmentModePutStarted = true;
                }
                else if(istheDefaultEnrollmentModePutStarted && !nextLine.contains("theDefaultEnrollmentMode.put("))
                {
                    list.add(index++, tab + tab + "theDefaultEnrollmentMode.put(NeType." + neTypeEnum + ".name(), " + "defaultEnrollmentMode);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheDefaultEnrollmentModePutStarted = false;
                }
                else if(nextLine.contains("theDefaultKeyAlgorithm.put("))
                {
                    istheDefaultKeyAlgorithmPutStarted = true;
                }
                else if(istheDefaultKeyAlgorithmPutStarted && !nextLine.contains("theDefaultKeyAlgorithm.put("))
                {
                    list.add(index++, tab + tab + "theDefaultKeyAlgorithm.put(NeType." + neTypeEnum + ".name(), " + "defaultKeyAlgorithm);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheDefaultKeyAlgorithmPutStarted = false;
                }
                else if(nextLine.contains("theDefaultFingerprintAlgorithm.put("))
                {
                    istheDefaultFingerprintAlgorithmPutStarted = true;
                }
                else if(istheDefaultFingerprintAlgorithmPutStarted && !nextLine.contains("theDefaultFingerprintAlgorithm.put("))
                {
                    list.add(index++, tab + tab + "theDefaultFingerprintAlgorithm.put(NeType." + neTypeEnum + ".name(), " + "defaultFingerprintAlgorithm);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheDefaultFingerprintAlgorithmPutStarted = false;
                }
                else if(nextLine.contains("theIsCertificateAuthorityDNSupported.put("))
                {
                    istheIsCertificateAuthorityDNSupportedPutStarted = true;
                }
                else if(istheIsCertificateAuthorityDNSupportedPutStarted && !nextLine.contains("theIsCertificateAuthorityDNSupported.put("))
                {
                    list.add(index++, tab + tab + "theIsCertificateAuthorityDNSupported.put(NeType." + neTypeEnum + ".name(), " + "defaultIsCertificateAuthorityDNSupported);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheIsCertificateAuthorityDNSupportedPutStarted = false;
                }
                else if(nextLine.contains("theIsSynchronousEnrollmentSupported.put("))
                {
                    istheIsSynchronousEnrollmentSupportedPutStarted = true;
                }
                else if(istheIsSynchronousEnrollmentSupportedPutStarted && !nextLine.contains("theIsSynchronousEnrollmentSupported.put("))
                {
                    list.add(index++, tab + tab + "theIsSynchronousEnrollmentSupported.put(NeType." + neTypeEnum + ".name(), " + "defaultIsSynchronousEnrollmentSupported);");
                    it = list.subList(index+1, list.size() - 1).iterator();
                    istheIsSynchronousEnrollmentSupportedPutStarted = false;
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
