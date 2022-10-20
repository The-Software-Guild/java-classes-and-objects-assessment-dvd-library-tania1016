/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sal.dvdlibrary.dao;

import com.sal.dvdlibrary.dto.DvD;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Tania
 */
public class DvdLibraryDaoFileImpl implements dvdLibraryDao {
 // create constants DVD_FILE and Delimiter
    public final String DVD_FILE ;
    public static final String DELIMITER = "::";
    /*
    Hash Map to store and retrieve the dvd with title name
     */
    private Map<String, DvD> dvds = new HashMap<>();

      public DvdLibraryDaoFileImpl(){ //no arg constructor typically used
        // Txt file that data will be stored in
          DVD_FILE = "dvdTest.txt";
    }
      public DvdLibraryDaoFileImpl(String libraryTextFile){

          DVD_FILE = libraryTextFile;
    }
    
    @Override
    public DvD addDvd(String title, DvD dvd) 
      {
       
        //implement AddDVd method and returns Dvd
          DvD prevDvd = dvds.put(title, dvd);
          return prevDvd;
    }

    /*
    This code gets all of the DvDs objects out of the dvds map as a collection by calling the values() method.
     */
    @Override
    public List<DvD> getAllDvds() throws DvdLibraryDaoException {
    
       //implement getALLDvds method and return ArrayList
        return new ArrayList<DvD>(dvds.values());
    }

    /*
    This method is quite simple — we just ask the dvds map for the dvd object with the given title and return it
     */
    @Override
    public DvD getDvd(String title)throws DvdLibraryDaoException {
      
      //implement get Dvd and pass title as parameter
        return dvds.get(title);
    }

    @Override
    public DvD removeDvd(String title) throws DvdLibraryDaoException{
    
       //implement remove Dvd and pass title as parameter
        DvD removedDvd = dvds.remove(title);
        writeDvdFile();
        return removedDvd;
    }

    

    /*
    Method to unmarshall the object or read a line of string
    from the line and convert it into an object
     */
    private DvD unmarshallDvd(String dvdAsText) {
        /* Implement unmarshalling( read data from file) method
         * and set each property/token value as
         * Dvd field
         * Return Dvd object from file
         */
        String[] dvdTokens= dvdAsText.split(DELIMITER);
        String title = dvdTokens[0];
        System.out.println(title);
        DvD dvdFromFile = new DvD(title);
        System.out.println(dvdTokens[1]);
        dvdFromFile.setReleaseDate(dvdTokens[1]);
        dvdFromFile.setMPAA(dvdTokens[2]);
        dvdFromFile.setDirectorsName(dvdTokens[3]);
        dvdFromFile.setStudio(dvdTokens[4]);
        dvdFromFile.setUserRating(dvdTokens[5]);
        return dvdFromFile;

    }

    /*
    Method to Load file DVD_FILE into memory
     */
     private void loadDvdFile() throws DvdLibraryDaoException {
         //implement loadDvdFile and read data
         Scanner scanner;
         // Use Scanner to read line by line
         try {
             scanner = new Scanner(new BufferedReader(new FileReader(DVD_FILE)));
         } catch (FileNotFoundException e) {
             // throw error if DVD file not found
             throw new DvdLibraryDaoException("-- Could not load dvd data into memory", e);
         }
         String currentLine;
         DvD currentDvD;

         while (scanner.hasNextLine()) {
             // read line by line until no lines left
             currentLine = scanner.nextLine();
             currentDvD = unmarshallDvd(currentLine);
             // Set current DVD and get title
             dvds.put(currentDvD.getTitle(), currentDvD);
         }
         // Close Scanner object
         scanner.close();
     }

    private String marshallDvd(DvD aDvd) {
       //implement marshallDvd method
        // transfer data to text file and use Delimiter to separate
        // properties and return object
        String dvdAsText = aDvd.getTitle() + DELIMITER;

        dvdAsText += aDvd.getReleaseDate()+DELIMITER;

        dvdAsText += aDvd.getMPAA()+ DELIMITER;

        dvdAsText += aDvd.getDirectorsName() + DELIMITER;

        dvdAsText += aDvd.getStudio()+ DELIMITER;
        dvdAsText += aDvd.getUserRating()+DELIMITER;

        return dvdAsText;

    }

    /**
     * Writes all Dvds in the library out to a DVD_FILE. See loadDvdFile
     * for file format.
     *
     * @throws Exception if an error occurs writing to the file
     */
    private void writeDvdFile() throws DvdLibraryDaoException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and 
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to 
        // handle any errors that occur.
      //implement writeDvdFile to transfer data to txt file

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(DVD_FILE));
        } catch (IOException e){
            throw new DvdLibraryDaoException(
                    "Could not save dvd data", e);

        }

        // Write out the DvD objects to the DVD file.
        // NOTE TO THE APPRENTICES: We could just grab the dvd map,
        // get the Collection of dvd and iterate over them but we've
        // already created a method that gets a List of dvds so
        // we'll reuse it.
        //implement marshallDvd method for each Dvd
        String dvdAsText;
        List<DvD> dvdList = this.getAllDvds();
        for( DvD currentDvd : dvdList){
            dvdAsText = marshallDvd(currentDvd);
            out.println(dvdAsText);
            out.flush();
        }
        out.close();
    }

    // Edit Methods for Release Date, MPAA, user rating , studio, and director's name
    // load Dvd file
    // Set new Date/rating/name
    // Write DVD file
    // Return DVD file
    @Override
    public DvD editReleaseDate(String title, String newReleaseDate) throws DvdLibraryDaoException {
       loadDvdFile();
        DvD currentDVD = dvds.get(title);
        currentDVD.setReleaseDate(newReleaseDate);
        writeDvdFile();
        return currentDVD;
    }

    @Override
    public DvD editMPAA(String title, String newMpaaRating) throws DvdLibraryDaoException {
        loadDvdFile();
        DvD currentDVD = dvds.get(title);
        currentDVD.setMPAA(newMpaaRating);
        writeDvdFile();
        return currentDVD; 
    }

    @Override
    public DvD editDirectorName(String title, String newDirectorName) throws DvdLibraryDaoException {
        loadDvdFile();
        DvD currentDVD = dvds.get(title);
        currentDVD.setDirectorsName(newDirectorName);
        writeDvdFile();
        return currentDVD; 
    }

    @Override
    public DvD editUserRating(String title, String newUserRating) throws DvdLibraryDaoException {
       loadDvdFile();
        DvD currentDVD = dvds.get(title);
        currentDVD.setUserRating(newUserRating);
        writeDvdFile();
        return currentDVD;  
    }

    @Override
    public DvD editStudio(String title, String newStudioName) throws DvdLibraryDaoException {
        loadDvdFile();
        DvD currentDVD = dvds.get(title);
        currentDVD.setStudio(newStudioName);
        writeDvdFile();
        return currentDVD;    
    }

}
