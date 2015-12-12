package com.self.test.core;

import com.self.test.model.Product;
import junit.framework.Assert;
import org.junit.Test;

public class DatabaseUtilityTest {
    @Test
    public void testInsert(){
        try {
            final boolean insertedStatus = DatabaseUtility.insertProduct("C231Test", "Test", 23.12);
            Assert.assertEquals(true, insertedStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertAndRetrieve(){
        try {
            final boolean insertedStatus = DatabaseUtility.insertProduct("C231TestSuccess", "Test", 23.12);
            Assert.assertEquals(true, insertedStatus);
            final Product product = DatabaseUtility.retrieveProduct("C231TestSuccess");
            Assert.assertEquals("C231TestSuccess", product.getCode());
            Assert.assertEquals("Test", product.getName());
            Assert.assertEquals(23.12, product.getPrice());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void retrieveWrongProduct(){
        try {
            final Product product = DatabaseUtility.retrieveProduct("UnavailableCode");
            Assert.assertTrue(product.getCode()==null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
