package com.messagemanagement;

import org.junit.Assert;
import org.junit.Test;

import com.messagemanagement.modal.Status;
import com.messagemanagement.modal.StatusCode;
import com.messagemanagement.modal.StatusFilterUtil;

public class StatusFilterUtilTest {
	
	   @Test
		public void shouldReturnFilteredInformation_whenAddMessage_givenIBAN() {
	        // given
	        Status statusVO = new Status();
	        StatusFilterUtil statusFilterUtil = StatusFilterUtil.getInstance();
			String iban = "Da ist etwas falsch mit ihrer IBAN: DE63370205000005023309";
			String expectedResult = "[ERROR: Da ist etwas falsch mit ihrer IBAN: DE63 XXXX XXXX XXXX XXXX XX]";

	        // when
	        statusVO.addMessage(iban, StatusCode.ERROR);
	        statusFilterUtil.filterInterneInformationen(statusVO);

	        //then
	        Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
	    }

	    @Test
		public void shouldReturnFilteredInformation_whenAddMessage_givenBIC() {
	        // given
	        Status statusVO = new Status();
	        StatusFilterUtil statusFilterUtil = StatusFilterUtil.getInstance();
			String bic = "Irgendeine BIC: BFSWDE33XXX steht hier";
			String expectedResult = "[ERROR: Irgendeine BIC: XXXXXXXXXXX steht hier]";

	        // when
	        statusVO.addMessage(bic, StatusCode.ERROR);
	        statusFilterUtil.filterInterneInformationen(statusVO);

	        //then
	        Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
	    }

	    @Test
		public void shouldReturnFilteredInformation_whenAddMessage_givenIBAN_BIC() {
	        // given
	        Status statusVO = new Status();
	        StatusFilterUtil statusFilterUtil = StatusFilterUtil.getInstance();
	        String iban_bic = "IBAN: DE63370205000005023309 BIC: BFSWDE33XXX";
	        String expectedResult = "[ERROR: IBAN: DE63 XXXX XXXX XXXX XXXX XX BIC: XXXXXXXXXXX]";

	        // when
	        statusVO.addMessage(iban_bic, StatusCode.ERROR);
	        statusFilterUtil.filterInterneInformationen(statusVO);

	        //then
	        Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
	    }

		@Test
		public void shouldReturnFilteredInformation_whenAddMessage_given_Multiple_IBAN_BIC() {
			// given
			Status statusVO = new Status();
			StatusFilterUtil statusFilterUtil = StatusFilterUtil.getInstance();
			String iban_bic =
					"IBAN: DE63370205000005023309 BIC: BFSWDE33XXX und nochne IBAN: DE63370205000005023301 BIC: XYSWDE33XXX";
			String expectedResult =
					"[ERROR: IBAN: DE63 XXXX XXXX XXXX XXXX XX BIC: XXXXXXXXXXX und nochne IBAN: DE63 XXXX XXXX XXXX XXXX XX BIC: XXXXXXXXXXX]";

			// when
			statusVO.addMessage(iban_bic, StatusCode.ERROR);
			statusFilterUtil.filterInterneInformationen(statusVO);

			//then
			Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
		}

//		@Test
//		public void shouldReturnFilteredInformation_whenAddMessage_given_TEST1() {
//			// given
//			Status statusVO = new Status();
//			StatusFilterUtil statusFilterUtil = new StatusFilterUtil("response-filter-test");
//			String expectedResult = "[ERROR: Bitte sofort aufh�ren!]";
//
//			// when
//			statusVO.addMessage("Irgendwas mit TEST1 oder so", StatusCode.ERROR);
//			statusFilterUtil.filterInterneInformationen(statusVO);
//
//			//then
//			Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
//		}

//		@Test
//		public void shouldReturnFilteredInformation_whenAddMessage_given_TEST2() {
//			// given
//			Status statusVO = new Status();
//			StatusFilterUtil statusFilterUtil = new StatusFilterUtil("response-filter-test");
//			String expectedResult = "[]";
//
//			// when
//			statusVO.addMessage("Irgendwas mit TEST2 oder so", StatusCode.ERROR);
//			statusFilterUtil.filterInterneInformationen(statusVO);
//
//			//then
//			Assert.assertEquals(expectedResult, statusVO.getMessages().toString());
//		}

}
