package com.messagemanagement;

import com.messagemanagement.modal.Status;
import com.messagemanagement.modal.StatusCode;
import com.messagemanagement.modal.StatusFilterUtil;

public class Main {
	
	public static void main(String[] args) {
        Status statusVO = new Status();
        StatusFilterUtil statusFilterUtil = StatusFilterUtil.getInstance();

        String message = "1 Die Bankverbindung IBAN: DE63370205000005023307 BIC: BFSWDE33XXX ist bereits genutzt worden.\n" +
                "Es kann nur eine künftige Bankverbindung eingetragen werden (frühestens gültig ab 30.01.2021).";

        String message2 = "2 Die Bankverbindung ist bereits genutzt worden.\n" +
                "Es kann nur eine k�nftige Bankverbindung eingetragen werden (frühestens gültig ab 30.01.2021).";

        String message3 = "";

        String message4 = "4 Die Bankverbindung IBAN: DE63370205000005023307 ist bereits genutzt worden.\n" +
                "Es kann nur eine künftige Bankverbindung  eingetragen werden (frühestens gültig ab 30.01.2028).";

        String message5 = "5 Die Bankverbindung BIC: BFSWDE33XXX ist bereits genutzt worden.\n";

        String message6 = "6 Die Bankverbindung ist nicht korrekt.";

        String message7 = "7 Die Bankverbindung IBAN: DE63370205000005023307 ist bereits genutzt worden.\n" +
                "Es kann nur eine künftige Bankverbindung eingetragen werden (frühestens gültig ab 30.01.2021).";

        statusVO.addMessage(message, StatusCode.ERROR);
        statusVO.addMessage(message2, StatusCode.INFO);
        statusVO.addMessage(message3, StatusCode.INFO);
        statusVO.addMessage(message4, StatusCode.ERROR);
        statusVO.addMessage(message5, StatusCode.ERROR);
        statusVO.addMessage(message6, StatusCode.OK);
        statusVO.addMessage(message7, StatusCode.ERROR);
        
        statusFilterUtil.filterInterneInformationen(statusVO);
    }

}
