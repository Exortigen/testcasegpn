package com.testcase.testcasegpn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.soap.*;
import java.io.IOException;

@SpringBootApplication
public class TestcasegpnApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestcasegpnApplication.class, args);
        String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
        String soapActionTemplate = "http://tempuri.org/";
        //Указываем нужный метод SOAP
        String method = "Add";
        //Строим URL
        String soapAction = soapActionTemplate + method;
        int intA = 10;
        int intB = 2;
        //Вызываем
        callSoapWebService(soapEndpointUrl, soapAction, method, intA, intB );
    }


private static void createSoapEnvelope(SOAPMessage soapMessage,String method, int intA, int intB) throws SOAPException{
    SOAPPart soapPart = soapMessage.getSOAPPart();

   /* <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Divide>
         <tem:intA>10</tem:intA>
         <tem:intB>2</tem:intB>
      </tem:Divide>
    </soapenv:Body>
    </soapenv:Envelope>*/

    String myNamespace = "tem";
    String myNamespaceURI = "http://tempuri.org/";

    //Назначаем пространство имен
    SOAPEnvelope envelope = soapPart.getEnvelope();
    envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

    //Создаем тело запроса
    SOAPBody soapBody = envelope.getBody();
    SOAPElement soapBodyElem = soapBody.addChildElement(method, myNamespace);

    SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("intA", myNamespace);
    soapBodyElem1.addTextNode(String.valueOf(intA));

    SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("intB", myNamespace);
    soapBodyElem2.addTextNode(String.valueOf(intB));
}
    private static void callSoapWebService(String soapEndpointUrl, String soapAction, String method, int intA, int intB){
        try {
            //Открыть соед.
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            //Отправить SOAP message на SOAP сервер
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, method, intA, intB), soapEndpointUrl);

           /* System.out.println("Response SOAP: ");
            soapResponse.writeTo(System.out);
            System.out.println();*/
            //Достаем body из ответа
            SOAPBody result = soapResponse.getSOAPBody();
            //Получаем ответ от сервера и оборачиваем в Integer
            Integer methodResponse = Integer.valueOf(result.getElementsByTagName(method + "Result").item(0).getTextContent());
            System.out.println("Method response: " + methodResponse);

            soapConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private static SOAPMessage createSOAPRequest(String soapAction, String method, int intA, int intB) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, method, intA, intB);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
}