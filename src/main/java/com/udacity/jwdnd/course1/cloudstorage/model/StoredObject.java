package com.udacity.jwdnd.course1.cloudstorage.model;

/**********************************************************************************************************************
 * A common interface for CloudStorage model objects that contains simple helper utilities.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public interface StoredObject {
    String getType();
    Integer getUserId();
}
