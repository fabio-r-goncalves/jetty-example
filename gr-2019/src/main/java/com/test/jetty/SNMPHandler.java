package com.test.jetty;

import java.io.IOException;
import java.net.SocketException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 * SNMPHandler
 */
public class SNMPHandler {

    private String ip = "localhost";
    private int port = 161;
    private String oid = ".1.3.6.1.2.1.1.3.0";
    private String community = "public";
    private int snmpVersion = SnmpConstants.version2c;
    private CommunityTarget comtarget;
    private TransportMapping transport;

    public SNMPHandler() throws IOException {
        transport = new DefaultUdpTransportMapping();
        transport.listen();

        // Create Target Address object
        comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        comtarget.setVersion(snmpVersion);
        comtarget.setAddress(new UdpAddress(ip + "/" + port));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

    }

    public String snmpGet() throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        pdu.setRequestID(new Integer32(1));

        // Create Snmp object for sending data to Agent
        Snmp snmp = new Snmp(transport);

        System.out.println("Sending Request to Agent...");
        ResponseEvent response = snmp.get(pdu, comtarget);   
        if(response == null){
            return "Error";
        }  

        if(response.getResponse().getErrorStatus() != PDU.noError){
            return "Error";
        }

        return response.getResponse().getVariableBindings().get(0).toString();
        
    }
}