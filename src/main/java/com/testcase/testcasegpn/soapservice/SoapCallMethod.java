package com.testcase.testcasegpn.soapservice;

import org.springframework.stereotype.Service;

import javax.xml.soap.*;


@Service
public class SoapCallMethod {
    public static Integer callWeb(String method, int intA, int intB){
        String soapEndpointUrl = "http://www.dneonline.com/calculator.asmx";
        String soapActionTemplate = "http://tempuri.org/";
        Integer result;

        //Строим URL
        String soapAction = soapActionTemplate + method;

        //Вызываем
        return (result = callSoapWebService(soapEndpointUrl, soapAction, method, intA, intB ));

    }


    private static void createSoapEnvelope(SOAPMessage soapMessage, String method, int intA, int intB) throws SOAPException {
      SOAPPart soapPart = soapMessage.getSOAPPart();

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
    private static Integer callSoapWebService(String soapEndpointUrl, String soapAction, String method, int intA, int intB) {
        Integer methodResponse = 0;
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
            methodResponse = Integer.valueOf(result.getElementsByTagName(method + "Result").item(0).getTextContent());

            soapConnection.close();
            return methodResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return methodResponse;
    }
    private static SOAPMessage createSOAPRequest(String soapAction, String method, int intA, int intB) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, method, intA, intB);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */

        return soapMessage;
    }
}

