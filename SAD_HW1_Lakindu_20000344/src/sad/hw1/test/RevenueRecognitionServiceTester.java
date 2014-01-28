package sad.hw1.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import sad.hw1.gateway.ContractGateway;
import sad.hw1.gateway.ProductGateway;
import sad.hw1.service.RevenueRecognitionService;
import sad.util.ApplicationException;
import sad.util.MfDate;
import sad.util.Money;

public class RevenueRecognitionServiceTester {

	@Test
	public void testCalculateRevenueRecognitionsForWordProcessor() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewWordProcessor("Word Processor 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(2500.50);
		MfDate dateSigned = new MfDate(2014, 1, 27);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		
		RevenueRecognitionService rrs = new RevenueRecognitionService();
		try {
			rrs.calculateRevenueRecognitions(contractID);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("Application exception occurred");
		}
	}
	
	@Test
	public void testCalculateRevenueRecognitionsForSpreadSheet() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewSpreadsheet("Spread Sheet 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(3000.00);
		MfDate dateSigned = new MfDate(2014, 1, 26);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		
		RevenueRecognitionService rrs = new RevenueRecognitionService();
		try {
			rrs.calculateRevenueRecognitions(contractID);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("Application exception occurred");
		}
	}
	
	@Test
	public void testCalculateRevenueRecognitionsForDatabase() {
		long productID = 0;
		try {
			productID = ProductGateway.insertNewDatabase("Database 2014");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		Money revenue = new Money(3541.00);
		MfDate dateSigned = new MfDate(2014, 1, 27);
		long contractID = -1;
		ContractGateway cg = new ContractGateway();
		try {
			contractID = cg.insertContract(revenue, dateSigned, productID);
			cg.close();
		} catch (SQLException | ApplicationException e) {
			e.printStackTrace();
		}
		
		RevenueRecognitionService rrs = new RevenueRecognitionService();
		try {
			rrs.calculateRevenueRecognitions(contractID);
		} catch (ApplicationException e) {
			e.printStackTrace();
			fail("Application exception occurred");
		}
	}

}
