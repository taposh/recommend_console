/**
 * Author: Taposh
 * Date: 9/22/13
 * Time: 10:28 PM
 */
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.RoundingMode;
import java.util.*;

import au.com.bytecode.opencsv.CSVReader;



/*
Your inputs for Programming Assignment 1 are:
98
854
1637
 */

class NonPersRecommender {


    public static void main(String[] args) {

        String outputResult = "";

        ArrayList<Integer> Input = new ArrayList<Integer>();

        Input.add(98);
        Input.add(854);
        Input.add(1637);

        String strFilePath = "/Users/taposhdr/Dropbox/WebIntelligence/Reco_system/module2/recsys-data-ratings.csv";
        try {
            int ij =0;
            for (ij=0; ij<Input.size(); ij++)
            {
            outputResult = outputResult + "\n" + LoadDataAssociations(strFilePath,Input.get(ij));
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
        System.out.println(outputResult);
    }


    public static String  LoadDataAssociations(String strFilePath, Integer AssocId) throws FileNotFoundException
    {

        Map<java.lang.Integer, java.lang.Double> UserCompare = new TreeMap<java.lang.Integer, java.lang.Double>();
        Collection<ratingsData> UserRatings = new ArrayList<ratingsData>();
        String output = AssocId.toString() ;

        try {

            int ii=0;

            //Read the input file and convert into
            CSVReader reader = new CSVReader(new FileReader(strFilePath),',');

            String [] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                ratingsData ratingsDataVal = new ratingsData();

                if(java.lang.Integer.parseInt(nextLine[1]) == AssocId)
                {
                    UserCompare.put(java.lang.Integer.parseInt(nextLine[0]), java.lang.Double.parseDouble(nextLine[2]));
                }
                else
                {
                    ratingsDataVal.setUserId(java.lang.Integer.parseInt(nextLine[0]));
                    ratingsDataVal.setMovieId(java.lang.Integer.parseInt(nextLine[1]));
                    ratingsDataVal.setRatingValue(java.lang.Double.parseDouble(nextLine[2]));
                    UserRatings.add(ratingsDataVal);
                }

            }

        //System.out.println("X = " + UserCompare.size());
        Map<java.lang.Integer, java.lang.Integer> MovieRatings = new TreeMap<java.lang.Integer, java.lang.Integer>();
        //Y Values
        MovieRatings = findYvalue(UserRatings,UserCompare);

        //simple association
        Map<java.lang.Integer, java.lang.Double> MovieAssociations = new TreeMap<java.lang.Integer, java.lang.Double>();

        for (Iterator<java.lang.Integer> mr = MovieRatings.keySet().iterator(); mr.hasNext();)
        {
            //(x+y)/y
            java.lang.Integer dmr = mr.next();

            java.lang.Double Val = (double) (MovieRatings.get(dmr) + UserCompare.size())/UserCompare.size();

            MovieAssociations.put(dmr,(Val -1))   ;

        }

        //Sorted
        Map<java.lang.Integer, java.lang.Double> SortedMovieAssociations = new TreeMap<java.lang.Integer, java.lang.Double>();
        SortedMovieAssociations = sortMapByValue(MovieAssociations);
        int jjjj = 0;

        for (Iterator<java.lang.Integer> smr = SortedMovieAssociations.keySet().iterator(); smr.hasNext();)
        {
            jjjj = jjjj +1;
            java.lang.Integer dsmr = smr.next();

            output = output + ","+ dsmr + "," + SortedMovieAssociations.get(dsmr).toString().substring(0,5);

            if (jjjj >= 5){break;}


        }

        //System.out.println(output);
        //return output;
        }

        catch (Exception e) {
        System.out.println(e.getMessage());
         }
        return output;

  }

    public static<Integer, Double extends Comparable<Double>> Map<Integer, Double> sortMapByValue(Map<Integer, Double> map)
    {
        List<Map.Entry<Integer, Double>> list = new LinkedList<Map.Entry<Integer, Double>>(
                map.entrySet());
        Collections.sort(list,
                new Comparator<Map.Entry<Integer, Double>>() {
                    public int compare(Map.Entry<Integer, Double> o1,
                                       Map.Entry<Integer, Double> o2) {
                        return (o2.getValue().compareTo(o1.getValue()));
                    }
                });

        Map<Integer, Double> result = new LinkedHashMap<Integer, Double>();
        for (Iterator<Map.Entry<Integer, Double>> it = list.iterator(); it.hasNext();) {
            Map.Entry<Integer, Double> entry = it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }




    public static Map<Integer,Integer> findYvalue(Collection<ratingsData> UserRatings, Map<Integer,Double> UserCompare)
    {
       // System.out.println(UserRatings.size());

        //Map of MovieID and Rating
        Map<Integer,Integer> MovieRatings = new TreeMap<Integer, Integer>();


        try {

         //Iterator<ratingsdata> ii=UserRatings.keySet().iterator();ii.hasNext();
        for (Iterator<ratingsData> ii=UserRatings.iterator();ii.hasNext();)
        {


            ratingsData rd = ii.next();
           // System.out.println(ii + " " + rd.getUserId());

            if (UserCompare.containsKey(rd.getUserId()) )
            {

               if( MovieRatings.containsKey(rd.getMovieId()))
               {
                     MovieRatings.put(rd.getMovieId(),MovieRatings.get(rd.getMovieId()) +1)   ;
               }
                else
               {
                       //First time
                       MovieRatings.put(rd.getMovieId(),1);
               }

            }
        }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return MovieRatings;
    }



}