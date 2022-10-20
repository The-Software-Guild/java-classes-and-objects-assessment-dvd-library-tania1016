/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sal.dvdlibrary.dao;

/**
 *
 * @author Tania
 */
public class DvdLibraryDaoException extends Exception{

    // constructor with one argument
    public DvdLibraryDaoException(String message) {

        super(message);
    }

    // constructor with two arguments
    
    public DvdLibraryDaoException(String message, Throwable cause) {

        super(message, cause);
    }
    
}
